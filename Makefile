package=snodge
version:=$(shell git describe --tags --always --dirty=-local --match='r*' | sed -e 's/^r//')

platform?=jvm

ifndef JAR
    JAR:=$(shell jenv which jar)
endif

ifndef JAVA
    JAVA:=$(shell jenv which java)
endif

KOTLIN=kotlin

ifndef KOTLINHOME
    KOTLINHOME:=$(realpath $(shell which $(KOTLIN))/../..)
endif

ifndef DOKKA
    DOKKA=dokka
endif

srcfiles=$(shell find platform/$1/{common-src,src}/$2 -name '*.kt')
topath=$(subst $(eval) ,:,$1)

all: jvm
include Makefile_$(platform)

print-%:
	@echo "$* ($(flavor $*)) = $($*)"

clean:
	rm -rf out

distclean: clean
	rm -rf platform/jvm/libs/

again: clean all

ci: jvm-ci

tested: jvm-tested

ifeq "$(origin version)" "command line"
tagged:
	git tag -s r$(version) -m "tagging version $(version)"
else
tagged:
	@echo set the version to tag on the command line
	@false
endif

published: jvm-published

.PHONY: all clean distclean tested tagged ci published
