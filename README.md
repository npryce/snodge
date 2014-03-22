Snodge
======

A small, extensible Java library to randomly mutate JSON documents. Useful for fuzz testing.

Examples of things you can test by mutating known good JSON documents:

- unexpected JSON structures will not make your application code throw unchecked exceptions
- your application code ignores additional fields
- your application code does not throw unchecked exceptions when parsing values from JSON strings
- and more!

