package net.nzti

import kotlin.math.pow

fun main() {
    val inputList: MutableList<String> = readInput("day4_input.txt")

    val numOfWinningNums: MutableList<Int> = mutableListOf()
    val pointsList: MutableList<Int> = mutableListOf()
    inputList.forEach { card ->
        println(card)

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
        numOfWinningNums.add((winningNums.toSet() intersect nums.toSet()).size)
    }
    numOfWinningNums.forEach numOfWinningNumsForEach@ {num ->
        if (num == 0) return@numOfWinningNumsForEach
        val points: Int = 2.0.pow(num - 1).toInt()
        println("$num = $points")
        pointsList.add(points)
    }
    println(pointsList.sum())
}
