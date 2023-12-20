package net.nzti

import kotlin.math.exp
import kotlin.math.pow

fun main() {
    val inputList: MutableList<String> = readInput("day4_input.txt")

    val numMap: MutableMap<Int, Int> = mutableMapOf()
    inputList.forEach { card ->
        println(card)
        val cardNum: Int = card.substring(card.indexOf(' '), card.indexOf(':')).replace(" ", "").toInt()
        println("Card $cardNum")

        val winningNums: MutableList<Int> = mutableListOf()
        card.substring(card.indexOf(':') + 2, card.indexOf('|') - 1).split(' ').forEach winningNumsForEach@ {
            if (it.isEmpty()) return@winningNumsForEach
            winningNums.add(it.toInt())
        }
        println(winningNums)

        val nums: MutableList<Int> = mutableListOf()
        card.substring(card.indexOf('|') + 2, card.length).split(' ').forEach numsForEach@ {
            if (it.isEmpty()) return@numsForEach
            nums.add(it.toInt())
        }
        println(nums)

        // Check how many winningNums are in nums
        println(winningNums.toSet() intersect nums.toSet())
        numMap[cardNum] = (winningNums.toSet() intersect nums.toSet()).size
    }

    var numCardsWon: Long =  0
    numMap.forEach { card ->
        println("Processing Card ${card.key} ${card.value}")
        numCardsWon += getNums(card.toPair(), numMap)
    }

    println(numMap)

    // Cards won plus number of original cards
    println(numCardsWon + numMap.size)
}


/**
 * Given a num get the list of copies of the num
 * Example: Given start 1 and steps 6, return 2, 3, 4, 5, 6, 7
 * @param start         Key, Value of a map to start from
 * @param numMap        Input map
 * @param solutionsMap  Map of solved values
 * @param stopNum       Value to stop at if encountered
 */
fun getNums(
    start: Pair<Int, Int>,
    numMap: MutableMap<Int, Int> = mutableMapOf(),
    solutionsMap: MutableMap<Int, Long> = mutableMapOf(),
    stopNum: Long = 0
): Long {

    if (start.second.toLong() == stopNum) // Reached card that hasn't won anymore cards
        return 0

    // Already solved for this number, return solution
    if (solutionsMap.containsKey(start.first))
        return solutionsMap.getOrDefault(start.first, stopNum)

    // Get list of cards won
    // Will be 2 10, 3 10, 4 10, 5 10, 6 5
    var result: Long = start.second.toLong()
    for ( i in start.first + 1..start.first + start.second) {
        result += getNums(Pair(i, numMap.getOrDefault(i, stopNum.toInt())), numMap)
    }

    // Solved, add to solutions map
    solutionsMap[start.first] = result

    return result
}
