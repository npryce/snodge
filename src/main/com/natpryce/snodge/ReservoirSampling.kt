package com.natpryce.snodge

import java.util.*

fun Sequence<DocumentMutation>.sample(maxSampleSize: Int, random: Random = Random()):
    List<DocumentMutation>
{
    val selectedMutations = mutableListOf<DocumentMutation>()
    var counter = 0
    
    forEach { potentialMutation ->
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
