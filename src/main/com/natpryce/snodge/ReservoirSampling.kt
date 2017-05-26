package com.natpryce.snodge

import java.util.Random

fun <T> Random.sample(maxSampleSize: Int, sequence: Sequence<T>): List<T> = sampleFrom(sequence.iterator(), maxSampleSize, this)
fun <T> Random.sample(maxSampleSize: Int, sequence: Iterable<T>): List<T> = sampleFrom(sequence.iterator(), maxSampleSize, this)

private fun <T> sampleFrom(sequence: Iterator<T>, maxSampleSize: Int, random: Random): MutableList<T> {
    val selectedMutations = mutableListOf<T>()
    var counter = 0
    
    sequence.forEach { potentialMutation ->
        val count = ++counter
        if (count <= maxSampleSize) {
            selectedMutations.add(potentialMutation)
        }
        else {
            val index = random.nextInt(count)
            
            if (index < selectedMutations.size) {
                selectedMutations[index] = potentialMutation
            }
        }
    }
    
    return selectedMutations
}
