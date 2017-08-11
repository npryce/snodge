package com.natpryce.snodge

import com.natpryce.snodge.WalkTest.NodeType.B
import com.natpryce.snodge.WalkTest.NodeType.L
import org.junit.Test
import kotlin.test.assertEquals


sealed class Node
data class Branch(val left: Node, val right: Node) : Node()
object Leaf : Node()

fun Node.listChildren() = when (this) {
    is Branch -> sequenceOf(
        left to { newLeft: Node -> copy(left = newLeft) },
        right to { newRight: Node -> copy(right = newRight) })
    Leaf -> emptySequence()
}


class WalkTest {
    enum class NodeType { B, L }
    
    @Test
    fun walks_structure() {
        val tree = Branch(Leaf, Branch(Branch(Leaf, Leaf), Branch(Branch(Leaf, Leaf), Leaf)))
        val steps = tree.walk(Node::listChildren)
        
        val stepTypes = steps.map {
            when (it.first) {
                is Branch -> B
                Leaf -> L
            }
        }.toList()
        
        assertEquals(actual = stepTypes, expected = listOf(B, L, B, B, L, L, B, B, L, L, L))
    }
}
