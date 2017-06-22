groupid=com.natpryce
package=snodge
version:=$(shell git describe --tags --always --dirty=-local --match='r*' | sed -e 's/^r//')

platforms=$(shell cd src/platform &&ls)

outdir_jvm=out/jvm
srcdir_main=src/platform/jvm/main
srcdir_test=src/platform/jvm/test

ifndef JAR
    JAR:=$(shell jenv which jar)
endif
ifndef JAVA
    JAVA:=$(shell jenv which java)
endif


KOTLIN=kotlin
KOTLINC=kotlinc
KOTLINCFLAGS=

ifndef KOTLINHOME
    KOTLINHOME:=$(realpath $(shell which $(KOTLIN))/../..)
endif

ifndef DOKKA
    DOKKA=dokka
endif

srcfiles=$(shell find "src/platform/$1/$2" "src/platform-independent/$2" -name '*.java' -o -name '*.kt')
topath=$(subst $(eval) ,:,$1)
classpath=$(patsubst %,-classpath %,$(call topath,$(filter-out %-sources.jar,$(filter %.jar,$^))))

src_jvm_main:=$(call srcfiles,jvm,main)
src_jvm_test:=$(call srcfiles,jvm,test)

package_distro = \
    $(outdir_jvm)/$(package)-$(version).pom \
    $(outdir_jvm)/$(package)-$(version).jar \
    $(outdir_jvm)/$(package)-$(version)-sources.jar \
    $(outdir_jvm)/$(package)-$(version)-javadoc.jar \

test_jars = $(outdir_jvm)/$(package)-$(version)-test.jar

published_archives = $(package_distro)
published_signatures = $(published_archives:%=%.asc)
published_files = $(published_archives) $(published_signatures)

all: tested distro
ci: tested $(published_archives)
tested: $(outdir_jvm)/junit-report.txt
distro: $(published_files)

include libs/main.mk
include libs/runtime.mk
include libs/test.mk

libs/%.mk: %.dependencies
	rm -rf libs/$*
	mkdir -p libs/$*
	tools/sm-download $< libs/$*
	echo 'libs_$*=$$(filter-out %-source.jar,$$(wildcard libs/$*/*.jar))' > $@

$(outdir_jvm)/$(package)-$(version).jar: $(src_jvm_main) $(libs_main) $(libs_runtime)
$(outdir_jvm)/$(package)-$(version)-test.jar: $(src_jvm_test) $(outdir_jvm)/$(package)-$(version).jar $(libs_main) $(libs_runtime) $(libs_test)

$(outdir_jvm)/junit-report.txt: TESTS=$(subst /,.,$(filter %Test,$(patsubst $(srcdir_test)/%.kt,%,$(src_jvm_test))))
$(outdir_jvm)/junit-report.txt: $(outdir_jvm)/$(package)-$(version)-test.jar $(outdir_jvm)/$(package)-$(version).jar $(libs_main) $(libs_runtime) $(libs_test)
	$(JAVA) $(classpath):$(KOTLINHOME)/lib/kotlin-runtime.jar:$(KOTLINHOME)/lib/kotlin-reflect.jar:$(KOTLINHOME)/lib/kotlin-test.jar \
	    org.junit.runner.JUnitCore $(TESTS) | tee $@

$(outdir_jvm)/$(package)-$(version)-sources.jar: $(src_jvm_main)
	$(JAR) cf $@ -C $(srcdir_main) .

$(outdir_jvm)/$(package)-$(version)-javadoc.jar: $(outdir_jvm)/$(package)-$(version)-javadoc/index.html
	$(JAR) cf $@ -C $(dir $<) .

$(outdir_jvm)/$(package)-$(version)-javadoc/index.html: $(src_jvm_main) $(libs_main)
	@mkdir -p $(dir $@)
	$(DOKKA) $(src_jvm_main) -format html -output $(dir $@)

$(outdir_jvm)/$(package)-$(version).pom: main.dependencies $(published_jars)
	@mkdir -p $(dir $@)
	tools/sm-pom mvn:$(groupid):$(package):jar:$(version) main.dependencies $(dir $@)
	mv $@ $@-tmp
	xsltproc tools/pom.xslt $@-tmp | xmllint --format --nsclean - > $@

$(outdir_jvm)/%.asc: $(outdir_jvm)/%
	gpg --yes --detach-sign --armor $<

%.jar:
	@mkdir -p $(dir $@)
	$(KOTLINC) $(KOTLINCFLAGS) $(filter-out %.jar,$^) $(classpath) -d $@

clean:
	rm -rf out

distclean: clean
	rm -rf libs/

again: clean all

published: $(published_files)
	publish-to-bintray $(groupid) $(package) $(version) $^

ifeq "$(origin version)" "command line"
tagged:
	git tag -s r$(version) -m "tagging version $(version)"
else
tagged:
	@echo set the version to tag on the command line
	@false
endif

.PHONY: all tested clean distclean published tagged distro ci tmp

tmp:
	-echo $(platforms)