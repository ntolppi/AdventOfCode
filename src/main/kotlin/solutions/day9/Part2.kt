package net.nzti

fun main() {
    val inputList: MutableList<String> = readInput("day9_input.txt")

    val numsList: MutableList<MutableList<Int>> = mutableListOf()
    inputList.forEach { readings ->
        val numList: MutableList<Int> = mutableListOf()
        readings.split(' ').forEach { reading ->
            numList.add(reading.toInt())
        }
        numsList.add(numList)
    }

    val nextValList: MutableList<Int> = mutableListOf()
    numsList.forEach { numList ->
        nextValList.add(getPrevValue(numList))
    }
    println(nextValList)
    println(nextValList.sum())
}

fun getPrevValue(numList: MutableList<Int>): Int {
    val diffList: MutableList<Int> = mutableListOf()

    for (idx in 0..<numList.size - 1) {
        diffList.add(numList[idx + 1] - numList[idx])
    }

    // All zeroes, return 0
    if (diffList.toSet().size == 1 && diffList[0] == 0) {
        println(numList + numList[0])
        return numList[0]
    }

    val prevVal: Int = numList[0] - getPrevValue(diffList)
    println(mutableListOf(prevVal) + numList)
    return prevVal
}
