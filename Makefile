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

include Makefile_$(platform)

print-%:
	@echo "$* ($(flavor $*)) = $($*)"

clean:
	rm -rf out

distclean: clean
	rm -rf platform/jvm/libs/

again: clean all

.PHONY: all clean distclean
