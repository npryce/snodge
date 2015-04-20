groupid=com.natpryce
package=snodge
version:=$(shell git describe --tags --always --dirty=-local --match='r*' | sed -e 's/^r//')

outdir=out
srcdir_main=src/main
srcdir_test=src/test

ifndef JAVA_HOME
JAVA_HOME:=$(shell realpath `which javac` | sed -e 's:\(/jre\)\?/bin/javac::')
endif

JAR=$(JAVA_HOME)/bin/jar
JAVAC=$(JAVA_HOME)/bin/javac
JAVA=$(JAVA_HOME)/bin/java
JAVADOC=$(JAVA_HOME)/bin/javadoc
JARJAR=$(JAVA) -jar $(libs_tool)

JAVACFLAGS=-g

java_src=$(shell find $1 -name '*.java')
topath=$(subst $(eval) ,:,$1)
classpath=$(patsubst %,-classpath %,$(call topath,$(filter-out %-sources.jar,$(filter %.jar,$^))))

src_main:=$(call java_src,$(srcdir_main))
src_test:=$(call java_src,$(srcdir_test))

package_distro = \
    $(outdir)/$(package)-$(version).jar \
    $(outdir)/$(package)-$(version)-sources.jar \
    $(outdir)/$(package)-$(version)-javadoc.jar \
    $(outdir)/$(package)-$(version).pom

standalone_distro = \
    $(package_distro:$(outdir)/$(package)-%=$(outdir)/$(package)-standalone-%)

test_jars = $(outdir)/$(package)-$(version)-test.jar

published_artefacts = $(package_distro) $(standalone_distro)
published_signatures = $(published_artefacts:%=%.asc)
published_files = $(published_artefacts) $(published_signatures)

all: tested distro
ci: tested $(published_artefacts)
tested: $(outdir)/junit-report.txt
distro: $(published_files)

include libs/main.mk
include libs/test.mk
include libs/tool.mk

libs/%.mk: %.dependencies
	rm -rf libs/$*
	mkdir -p libs/$*
	tools/sm-download $< libs/$*
	echo 'libs_$*=$$(filter-out %-source.jar,$$(wildcard libs/$*/*.jar))' > $@

$(outdir)/$(package)-$(version).compiled: $(src_main) $(libs_main)
$(outdir)/$(package)-$(version)-test.compiled: $(src_test) $(outdir)/$(package)-$(version).jar $(libs_main) $(libs_test)

$(outdir)/junit-report.txt: TESTS=$(subst /,.,$(filter %Test,$(patsubst $(srcdir_test)/%.java,%,$(src_test))))
$(outdir)/junit-report.txt: $(outdir)/$(package)-$(version).jar $(outdir)/$(package)-$(version)-test.jar $(libs_main) $(libs_test)
	$(JAVA) $(classpath) org.junit.runner.JUnitCore $(TESTS) | tee $@

$(outdir)/$(package)-$(version)-sources.jar: $(src_main)
	$(JAR) cf $@ -C $(srcdir_main) .

$(outdir)/$(package)-$(version)-javadoc.jar: $(outdir)/$(package)-$(version)-javadoc/index.html
	$(JAR) cf $@ -C $(dir $<) .

$(outdir)/$(package)-$(version)-javadoc/index.html: $(src_main) $(libs_main)
	@mkdir -p $(dir $@)
	$(JAVADOC) -d $(dir $@) $(classpath) $(src_main)

$(outdir)/$(package)-$(version).pom: main.dependencies $(published_jars)
	@mkdir -p $(dir $@)
	tools/sm-pom mvn:$(groupid):$(package):jar:$(version) main.dependencies $(dir $@)
	mv $@ $@-tmp
	xsltproc tools/pom.xslt $@-tmp | xmllint --format --nsclean - > $@

$(outdir)/$(package)-standalone-$(version).pom: $(outdir)/$(package)-$(version).pom
	xmlstarlet ed -N m='http://maven.apache.org/POM/4.0.0' --delete '/m:project/m:dependencies/m:dependency' $< > $@

$(outdir)/$(package)-standalone-$(version).jar: standalone.jarjar $(outdir)/tmp/$(package)-$(version)-combined.jar
	@mkdir -p $(dir $@)
	$(JARJAR) process $^ $@

$(outdir)/$(package)-standalone-$(version)-%.jar: $(outdir)/$(package)-$(version)-%.jar
	cp $< $@

$(outdir)/tmp/$(package)-$(version)-combined.jar: $(libs_main) $(outdir)/$(package)-$(version).jar
	@mkdir -p $@.contents
	cd $@.contents && for f in $(abspath $^); do jar xf $$f; done
	rm -rf $@.contents/META-INF/
	$(JAR) cf $@ -C $@.contents .

$(outdir)/%.asc: $(outdir)/%
	gpg --detach-sign --armor $<

%.compiled:
	@mkdir -p $*/
	$(JAVAC) $(JAVACFLAGS) $(classpath) -d $*/ $(filter %.java,$^)
	@touch $@

%.jar: %.compiled
	$(JAR) -cf$(JARFLAGS) $@ -C $* .

clean:
	rm -rf $(outdir)

distclean: clean
	rm -rf libs/

again: clean all

published: $(published_files)
	publish-to-bintray $(groupid) $(package) $(version) $(filter-out $(outdir)/$(package)-standalone%,$^)
	publish-to-bintray $(groupid) $(package)-standalone $(version) $(filter $(outdir)/$(package)-standalone%,$^)

ifeq "$(origin version)" "command line"
tagged:
	git tag -s r$(version) -m "tagging version $(version)"
else
tagged:
	@echo set the version to tag on the command line
	@false
endif

.PHONY: all tested clean distclean published tagged distro ci
