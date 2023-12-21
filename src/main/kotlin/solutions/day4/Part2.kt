package net.nzti.solutions.day4

import net.nzti.readInput


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

    var numCardsWonTotal: Long =  0
    val solutionsMap: MutableMap<Int, Long> = mutableMapOf()
    numMap.forEach { card ->
        println("Processing Card ${card.key} ${card.value}")

        // Get the number of cards won and solutions found
        val (numCardsWon, solutions) = getNums(card.toPair(), numMap, solutionsMap)

        // Increase the total number of cards won
        numCardsWonTotal += numCardsWon

        // Update solutions map
        solutionsMap.putAll(solutions)
    }

    println(numMap)

    // Cards won plus number of original cards
    println(numCardsWonTotal + numMap.size)
}


/**
 * Given a num get the list of copies of the num
 * Example: Given start 1 and steps 6, return 2, 3, 4, 5, 6, 7
 * @param start         Key, Value of a map to start from
 * @param numMap        Input map
 * @param solutionsMap  Map of solved values
 * @param stopNum       Value to stop at if encountered
 * @return              Number of cards won for the original starting card
 */
fun getNums(
    start: Pair<Int, Int>,
    numMap: MutableMap<Int, Int> = mutableMapOf(),
    solutionsMap: MutableMap<Int, Long> = mutableMapOf(),
    stopNum: Long = 0
): Pair<Long, MutableMap<Int, Long>> {

    if (start.second.toLong() == stopNum) // Reached card that hasn't won anymore cards
        return Pair(0, solutionsMap)

    // Already solved for this number, return solution
    if (solutionsMap.containsKey(start.first))
        return Pair(solutionsMap.getOrDefault(start.first, stopNum), solutionsMap)

    // Get list of cards won
    // Will be 2 10, 3 10, 4 10, 5 10, 6 5
    var result: Long = start.second.toLong()
    for ( i in start.first + 1..start.first + start.second) {
        result += getNums(Pair(i, numMap.getOrDefault(i, stopNum.toInt())), numMap, solutionsMap).first
    }

    // Solved, add to solutions map
    solutionsMap[start.first] = result

    return Pair(result, solutionsMap)
}
