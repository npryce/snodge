package com.natpryce.snodge

fun <ROOT, NODE> NODE.walk(
    listChildren: (parent:NODE) -> Sequence<Pair<NODE, (NODE) -> NODE>>,
    replaceInDocument: (newNode: NODE) -> ROOT
): Sequence<Pair<NODE, (NODE) -> ROOT>> =
    sequenceOf(Pair(this, replaceInDocument)) +
        listChildren(this).flatMap { (child, replacer) -> child.walk(listChildren, { replaceInDocument(replacer(it)) }) }

