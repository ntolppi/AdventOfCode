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
        var (left, lPos) = Pair(-1, -1)
        var (right, rPos) = Pair(-1, -1)
        for (i in it.indices) {
            // If not a digit continue to next character
            if (!it[i].isDigit()) continue

                // Handle initial and check if lPos > then current position stored
                if (lPos == -1 || lPos > i) {
                    left = it[i].digitToInt()
                    lPos = i
                }

                // Check digit is further right then current right
                if (rPos < i) {
                    right = it[i].digitToInt()
                    rPos = i
                }
        }

        // Check for digits as words
        val numbers: Array<String> = arrayOf(
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
        )
        for (i in numbers.indices) {
            if (!it.contains(numbers[i], ignoreCase = true)) continue

            // Get index of number from left
            val leftIdx = it.indexOf(numbers[i])

            // Get index of number from right
            val idxFromRight = it.reversed().indexOf(numbers[i].reversed())

            // Correct number from right
            val rightIdx = it.length - numbers[i].length - idxFromRight

            // Handle initial and check if lPos > then current position stored
            if (lPos == -1 || lPos > leftIdx) {
                left = i
                lPos = leftIdx
            }

            // Check digit is further right then current right
            if (rPos < rightIdx) {
                right = i
                rPos = rightIdx
            }
        }

        // Got coordinates
        result.add(left*10 + right)
    }
    println(result.sum())
}
