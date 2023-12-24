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
        nextValList.add(getNextValue(numList))
    }
    println(nextValList)
    println(nextValList.sum())
}

fun getNextValue(numList: MutableList<Int>): Int {
    val diffList: MutableList<Int> = mutableListOf()

    for (idx in 0..<numList.size - 1) {
        diffList.add(numList[idx + 1] - numList[idx])
    }

    // All zeroes, return 0
    if (diffList.toSet().size == 1 && diffList.last() == 0) {
        println(numList + numList.last())
        return numList.last()
    }

    val nextVal: Int = numList.last() + net.nzti.solutions.day9.getNextValue(diffList)
    println(numList + nextVal)
    return nextVal
}
