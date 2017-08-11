package com.natpryce.snodge

import com.natpryce.jsonk.JsonElement
import com.natpryce.snodge.internal.mapLazy
import com.natpryce.snodge.json.walk

/**
 * Walker for structures in which the root node has the same type as any sub-nodes.
 *
 * For example JSON
 */
fun <NODE> NODE.walk(listChildren: (parent: NODE) -> Sequence<Pair<NODE, (NODE) -> NODE>>): Sequence<Pair<NODE, (NODE) -> NODE>> =
    walk(listChildren, { it })

/**
 * Walker for structures in which the root node has a different type as its sub-nodes.
 *
 * For example, XML (a document contains nodes, but cannot contain a document)
 */
fun <ROOT, NODE> NODE.walk(
    listChildren: (parent: NODE) -> Sequence<Pair<NODE, (NODE) -> NODE>>,
    replaceInDocument: (newNode: NODE) -> ROOT
): Sequence<Pair<NODE, (NODE) -> ROOT>> =
    sequenceOf(Pair(this, replaceInDocument)) +
        listChildren(this).flatMap { (child, replacer) -> child.walk(listChildren, { replaceInDocument(replacer(it)) }) }


