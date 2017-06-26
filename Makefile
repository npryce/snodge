
platform?=jvm

KOTLIN=kotlin
ifndef KOTLINHOME
    KOTLINHOME:=$(realpath $(shell which $(KOTLIN))/../..)
endif

ifndef DOKKA
    DOKKA=dokka
endif

srcfiles=$(shell find -f "src/platform/$1/$2" "src/platform-independent/$2" -name '*.kt')

include Makefile_$(platform)

clean:
	rm -rf out

distclean: clean
	rm -rf libs/

again: clean all

.PHONY: all clean distclean
