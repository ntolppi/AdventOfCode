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
    println(numMap)
    println(getScratchcardsWon(numMap))
}

/**
 * Loop through each of the cards
 * Get copies of the numbers won
 * Card 1 wins 6 cards: 2, 3, 4, 5, 6, 7
 * Copy of card 2 wins 10 cards: 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
 * etc...
 */
fun getScratchcardsWon(
    numMap: MutableMap<Int, Int>
): Long {
    var numCardsWon: Long =  0
    numMap.forEach { card ->
        println("Processing Card ${card.key} ${card.value}")
        numCardsWon += getNums(card.toPair(), numMap)
    }
    return numCardsWon
}

/**
 * Given a num get the list of copies of the num
 * Example: Given start 1 and steps 6, return 2, 3, 4, 5, 6, 7
 */
fun getNums(
    start: Pair<Int, Int>,
    numMap: MutableMap<Int, Int> = mutableMapOf()
): Long {

    if (start.second == 0) // Reached card that hasn't won anymore cards
        return 0

    // Get list of cards won
    // Will be 2 10, 3 10, 4 10, 5 10, 6 5
    var result: Long = start.second.toLong()
    for ( i in start.first + 1..start.first + start.second) {
        result += getNums(Pair(i, numMap.getOrDefault(i, 0)), numMap)
    }
    println("Card ${start.first} won ${result}")

    return result
}
