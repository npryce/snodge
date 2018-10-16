package com.natpryce.snodge

fun <T> Random.sample(maxSampleSize: Int, sequence: Sequence<T>): List<T> =
    sampleFrom(sequence.iterator(), maxSampleSize, this)
fun <T> Random.sample(maxSampleSize: Int, sequence: Iterable<T>): List<T> =
    sampleFrom(sequence.iterator(), maxSampleSize, this)

fun <T> Sequence<T>.sample(maxSampleSize: Int, rng: Random = Random()): List<T> =
    sampleFrom(this.iterator(), maxSampleSize, rng)
fun <T> Iterable<T>.sample(maxSampleSize: Int, rng: Random = Random()): List<T> =
    sampleFrom(this.iterator(), maxSampleSize, rng)

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
