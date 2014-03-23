
version:=$(shell git describe --tags --always --dirty=-local --match='r*')

outdir=out
srcdir_main=src/main
libs_main=gson guava
srcdir_test=src/test
libs_test=junit

JAR=jar
JAVAC=javac
JAVA=java

topath=$(subst $(eval) ,:,$1)
libjars=$(filter-out %-sources.jar,$(wildcard $(patsubst %,libs/%/*.jar,$1)))
classpath=$(patsubst %,-classpath %,$(call topath,$(filter %.jar,$^)))

tested: $(outdir)/junit-report.txt

jars: $(outdir)/snodge-$(version).jar $(outdir)/snodge-test-$(version).jar


$(outdir)/snodge-$(version).compiled: $(shell find $(srcdir_main) -name '*.java')
$(outdir)/snodge-$(version).compiled: $(call libjars,$(libs_main))

$(outdir)/snodge-test-$(version).compiled: $(shell find $(srcdir_test) -name '*.java')
$(outdir)/snodge-test-$(version).compiled: $(call libjars,$(libs_main))
$(outdir)/snodge-test-$(version).compiled: $(call libjars,$(libs_test))
$(outdir)/snodge-test-$(version).compiled: $(outdir)/snodge-$(version).jar


%.jar: %.compiled
	$(JAR) -cf$(JARFLAGS) $@ -C $* .

%.compiled:
	@mkdir -p $*/
	$(JAVAC) $(JAVACFLAGS) $(classpath) -d $*/ $(filter %.java,$^)
	@touch $@

$(outdir)/junit-report.txt: $(call libjars,$(libs_main))
$(outdir)/junit-report.txt: $(call libjars,$(libs_test))
$(outdir)/junit-report.txt: $(outdir)/snodge-$(version).jar
$(outdir)/junit-report.txt: $(outdir)/snodge-test-$(version).jar
$(outdir)/junit-report.txt: TESTS=$(subst /,.,$(patsubst %.class,%,$(filter %Test.class,$(shell $(JAR) tf $(outdir)/snodge-test-$(version).jar))))
$(outdir)/junit-report.txt:
	$(JAVA) $(classpath) org.junit.runner.JUnitCore $(TESTS) | tee $@

clean:
	rm -rf $(outdir)

.PHONY: jars tested clean

