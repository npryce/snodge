
package=snodge
version:=$(shell git describe --tags --always --dirty=-local --match='r*' | sed -e 's/^r//')
release=$(package)-$(version)
groupid=com.natpryce

outdir=out
srcdir_main=src/main
srcdir_test=src/test

JAR=jar
JAVAC=javac
JAVA=java
JAVADOC=javadoc

JAVACFLAGS=-g

java_src=$(shell find $1 -name '*.java')
topath=$(subst $(eval) ,:,$1)
ivy_classpath=$(shell $(patsubst %,cat %,$(filter $(outdir)/cachepath-%.txt,$^)) && true)
classpath=$(patsubst %,-classpath %,$(call topath,$(filter %.jar,$^)))

src_main:=$(call java_src,$(srcdir_main))
src_test:=$(call java_src,$(srcdir_test))

published_jars = $(outdir)/$(release).jar $(outdir)/$(release)-sources.jar $(outdir)/$(release)-javadoc.jar
test_jars = $(outdir)/$(release)-test.jar

all: tested jars

tested: $(outdir)/junit-report.txt

jars: $(published_jars) $(test_jars)

include libs/main.mk
include libs/test.mk

libs/%.mk: %.dependencies
	rm -rf libs/$*
	mkdir -p libs/$*
	tools/sm-download $< libs/$*
	echo 'libs_$*=$$(filter-out %-source.jar,$$(wildcard libs/$*/*.jar))' > $@

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

clean:
	rm -rf $(outdir)

distclean: clean
	rm -rf libs/

again: clean all

ifdef bintray-login
reporoot=https://api.bintray.com/content/npryce/maven/$(subst .,/,$(groupid))/$(package)/$(version)
published: $(published_jars) $(outdir)/$(release).pom
	@for f in $(notdir $^); do echo curl -T $(outdir)/$$f -u$(bintray-login) $(reporoot)/$$f; done
else
published:
	@echo set the bintray-login make variable to '<user>:<key>'
	@false
endif

.PHONY: all jars tested clean distclean published
