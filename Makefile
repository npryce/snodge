groupid=com.natpryce
package=snodge
version:=$(shell git describe --tags --always --dirty=-local --match='r*' | sed -e 's/^r//')
release=$(package)-$(version)

outdir=out
srcdir_main=src/main
srcdir_test=src/test

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

published_jars = \
    $(outdir)/$(release).jar \
    $(outdir)/$(release)-standalone.jar \
    $(outdir)/$(release)-sources.jar \
    $(outdir)/$(release)-javadoc.jar

test_jars = $(outdir)/$(release)-test.jar

published_files = $(published_jars) $(outdir)/$(release).pom
published_signatures = $(published_files:%=%.asc)

all: tested jars

tested: $(outdir)/junit-report.txt

jars: $(published_jars) $(test_jars)

include libs/main.mk
include libs/test.mk
include libs/tool.mk

libs/%.mk: %.dependencies
	rm -rf libs/$*
	mkdir -p libs/$*
	tools/sm-download $< libs/$*

$(outdir)/$(release).compiled: $(src_main) $(libs_main)
$(outdir)/$(release)-test.compiled: $(src_test) $(outdir)/$(release).jar $(libs_main) $(libs_test)

%.compiled:
	@mkdir -p $*/
	$(JAVAC) $(JAVACFLAGS) $(classpath) -d $*/ $(filter %.java,$^)
	@touch $@

%.jar: %.compiled
	$(JAR) -cf$(JARFLAGS) $@ -C $* .

$(outdir)/junit-report.txt: TESTS=$(subst /,.,$(filter %Test,$(patsubst $(srcdir_test)/%.java,%,$(src_test))))
$(outdir)/junit-report.txt: $(outdir)/$(release).jar $(outdir)/$(release)-test.jar $(libs_main) $(libs_test)
	$(JAVA) $(classpath) org.junit.runner.JUnitCore $(TESTS) | tee $@

$(outdir)/$(release)-sources.jar: $(src_main)
	$(JAR) cf $@ -C $(srcdir_main) .

$(outdir)/$(release)-javadoc.jar: $(outdir)/$(release)-javadoc/index.html
	$(JAR) cf $@ -C $(dir $<) .

$(outdir)/$(release)-javadoc/index.html: $(src_main) $(libs_main)
	@mkdir -p $(dir $@)
	$(JAVADOC) -d $(dir $@) $(classpath) $(src_main)

$(outdir)/$(release).pom: main.dependencies $(published_jars)
	@mkdir -p $(dir $@)
	tools/sm-pom mvn:$(groupid):$(package):jar:$(version) main.dependencies $(dir $@)
	mv $@ $@-tmp
	xsltproc tools/pom.xslt $@-tmp | xmllint --format --nsclean - > $@

$(outdir)/$(release)-standalone.jar: standalone.jarjar $(outdir)/tmp/$(release)-combined.jar
	@mkdir -p $(dir $@)
	$(JARJAR) process $^ $@

$(outdir)/tmp/$(release)-combined.jar: $(libs_main) $(outdir)/$(release).jar
	@mkdir -p $@.contents
	cd $@.contents && for f in $(abspath $^); do jar xf $$f; done
	rm -rf $@.contents/META-INF/
	jar cf $@ -C $@.contents .

$(outdir)/%.asc: $(outdir)/%
	gpg --detach-sign --armor $<

clean:
	rm -rf $(outdir)

distclean: clean
	rm -rf libs/

again: clean all

published: $(published_files) $(published_signatures)
	publish-to-bintray $(groupid) $(package) $(version) $^

ifeq "$(origin version)" "command line"
tagged:
	git tag -s r$(version) -m "tagging version $(version)"
else
tagged:
	@echo set the version to tag on the command line
	@false
endif

.PHONY: all jars tested clean distclean published tagged

tmp:
	echo $(libs_tool)