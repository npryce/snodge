
platform?=jvm

KOTLIN=kotlin
ifndef KOTLINHOME
    KOTLINHOME:=$(realpath $(shell which $(KOTLIN))/../..)
endif

ifndef DOKKA
    DOKKA=dokka
endif

srcfiles=$(shell find platform/$1/{common-src,src}/$2 -name '*.kt')

include Makefile_$(platform)

clean:
	rm -rf out

distclean: clean
	rm -rf platform/jvm/libs/

again: clean all

.PHONY: all clean distclean
