
version:=$(shell git describe --tags --always --dirty=-local --match='r*' | sed -e 's/^r//')
release=snodge-$(version)

outdir=out
srcdir_main=src/main
libs_main=gson guava
srcdir_test=src/test
libs_test=junit

JAR=jar
JAVAC=javac
JAVA=java
JAVADOC=javadoc

JAVACFLAGS=-g

java_src=$(shell find $1 -name '*.java')
topath=$(subst $(eval) ,:,$1)
ivy_classpath=$(shell $(patsubst %,cat %,$(filter $(outdir)/cachepath-%.txt,$^)) && true)
classpath=$(patsubst %,-classpath %,$(call topath,$(strip $(ivy_classpath) $(filter %.jar,$^))))

src_main:=$(call java_src,$(srcdir_main))
src_test:=$(call java_src,$(srcdir_test))

all: tested jars

tested: $(outdir)/junit-report.txt

jars: $(outdir)/$(release).jar $(outdir)/$(release)-test.jar
jars: $(outdir)/$(release)-sources.jar
jars: $(outdir)/$(release)-javadoc.jar

$(outdir)/$(release).compiled: $(src_main)
$(outdir)/$(release).compiled: $(outdir)/cachepath-main.txt

$(outdir)/$(release)-test.compiled: $(src_test)
$(outdir)/$(release)-test.compiled: $(outdir)/cachepath-test.txt
$(outdir)/$(release)-test.compiled: $(outdir)/$(release).jar


$(outdir)/$(release)-sources.jar: $(src_main)
	$(JAR) cf $@ -C $(srcdir_main) .

$(outdir)/$(release)-javadoc.jar: $(outdir)/$(release)-javadoc/index.html
	$(JAR) cf $@ -C $(dir $<) .

$(outdir)/$(release)-javadoc/index.html: $(src_main) $(outdir)/cachepath-main.txt
	@mkdir -p $(dir $@)
	$(JAVADOC) -d $(dir $@) $(classpath) $(src_main)

%.jar: %.compiled
	$(JAR) -cf$(JARFLAGS) $@ -C $* .

%.compiled:
	@mkdir -p $*/
	$(JAVAC) $(JAVACFLAGS) $(classpath) -d $*/ $(filter %.java,$^)
	@touch $@

$(outdir)/junit-report.txt: $(call libjars,$(libs_main))
$(outdir)/junit-report.txt: $(call libjars,$(libs_test))
$(outdir)/junit-report.txt: $(outdir)/$(release).jar
$(outdir)/junit-report.txt: $(outdir)/$(release)-test.jar
$(outdir)/junit-report.txt: TESTS=$(subst /,.,$(filter %Test,$(patsubst $(srcdir_test)/%.java,%,$(src_test))))
$(outdir)/junit-report.txt:
	ivy -error -confs test -main org.junit.runner.JUnitCore -cp $(outdir)/$(release).jar:$(outdir)/$(release)-test.jar -args $(TESTS) | tee $@


$(outdir)/cachepath-%.txt: ivy.xml
	@mkdir -p $(dir $@)
	ivy -error -cachepath $@ -confs $*


clean:
	rm -rf $(outdir)

init:
	ivy -error -confs dev

again: clean all

.PHONY: all jars tested clean init
