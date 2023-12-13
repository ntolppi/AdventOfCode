package net.nzti.solutions.day1

import net.nzti.readInput


fun main() {
    /*
    For each line return first number and last number in string
    Example:
        Input: 3mlfdone42
        Output: 32
     */
    val inputList: MutableList<String> = readInput("day1_input.txt")
    val result: MutableList<Int> = mutableListOf()
    inputList.forEach {
        var first = true
        var left = -1
        var (right, rPos) = Pair(-1, -1)
        for (i in it.indices) {
            // If not a digit continue to next character
            if (!it[i].isDigit()) continue

            // First digit
            if (first) {
                first = false
                left = it[i].digitToInt()
                right = left
                rPos = i
            }

            // Check digit is further right then current right
            if (rPos < i) {
                right = it[i].digitToInt()
                rPos = i
            }
        }

        // Got coordinates
        result.add(left*10 + right)
    }
    println(result.sum())
}
