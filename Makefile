
version:=$(shell git describe --tags --always --dirty=-local --match='r*')

outdir=out
srcdir_main=src/main
libs_main=gson guava
srcdir_test=src/tests
libs_test=junit

JAR=jar
JAVAC=javac

topath=$(subst $(eval) ,:,$1)
libjars=$(filter-out %-sources.jar,$(wildcard $(patsubst %,libs/%/*.jar,$1)))


all: $(outdir)/snodge-$(version).jar $(outdir)/snodge-test-$(version).jar

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
	$(JAVAC) $(JAVACFLAGS) $(patsubst %,-classpath %,$(call topath,$(filter %.jar,$^))) -d $*/ $(filter %.java,$^)
	@touch $@

clean:
	rm -rf $(outdir)

