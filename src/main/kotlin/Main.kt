package net.nzti

import kotlin.contracts.contract

fun main() {
    /*
    ...788.............................54.........501...........555.........270........
    ..../..*963........................*..860......................*....53...../.......
    ............*......41..481+.......462....$..187......678.......420....-............

    Steps, think of it like a spreadsheet:
        1. Get to non-period, index
        2. Check previous value index - 1
        3. Check next value index + 1
        4. Check above, index of previous row
        6. Check above, index of previous row - 1
        7. Check above, index of previous row + 1
        8. Check below, index of next row
        9. Check below, index of next row - 1
       10. Check below, index of next row + 1

       Do above using x,y coordinates
     */
    val inputList: MutableList<String> = readInput("day3_input.txt")
    val result: MutableList<Int> = mutableListOf()

    // Loop through each line of input
    val numList: MutableList<MutableList<Char>> = mutableListOf()
    val gearList: MutableList<MutableList<Char>> = mutableListOf()

    // List of coordinaates of special characters
    val specialCharCoordsList: MutableList<Pair<Int, Int>> = mutableListOf()
    for (y in inputList.indices) {
        // Loop through each Char in input String
        for (x in inputList[y].indices) {
            // Skip periods and numbers
            if (inputList[y][x] == '.' || inputList[y][x].isDigit()) continue
            specialCharCoordsList.add(Pair(x, y))
        }
    }

    val coordsList: MutableList<Pair<Int, Int>> = mutableListOf()
    specialCharCoordsList.forEach {

        /*
        Special character, check for numbers
        (x - 1, y - 1) | (x, y - 1) | (x + 1, y - 1)
        (x - 1, y)     | (x, y)     | (x + 1, y)
        (x - 1, y + 1) | (x, y + 1) | (x + 1, y + 1)
         */
        val x = it.first
        val y = it.second

        var xHasPrev: Boolean = x - 1 >= 0
        val yHasPrev: Boolean = y - 1 >= 0

        var xHasNext: Boolean = x + 1 < inputList[y].length
        val yHasNext: Boolean = y + 1 < inputList.size

        var char: Char
        if (yHasPrev) {
            char = inputList[y - 1][x]
            if (char.isDigit())
                coordsList.add(Pair(y - 1, x))
        }

        if (yHasNext) {
            char = inputList[y + 1][x]
            if (char.isDigit())
                coordsList.add(Pair(y + 1, x))
        }

        if (xHasPrev) {
            char = inputList[y][x - 1]
            if (char.isDigit())
                coordsList.add(Pair(y, x - 1))

            if (yHasPrev) {
                char = inputList[y - 1][x - 1]
                if (char.isDigit())
                    coordsList.add(Pair(y - 1, x - 1))
            }

            if (yHasNext) {
                char = inputList[y + 1][x - 1]
                if (char.isDigit())
                    coordsList.add(Pair(y + 1, x - 1))
            }

        }

        if (xHasNext) {
            char = inputList[y][x + 1]
            if (char.isDigit())
                coordsList.add(Pair(y, x + 1))

            if (yHasPrev) {
                char = inputList[y - 1][x + 1]
                if (char.isDigit())
                    coordsList.add(Pair(y - 1, x + 1))
            }

            if (yHasNext) {
                char = inputList[y + 1][x + 1]
                if (char.isDigit())
                    coordsList.add(Pair(y + 1, x + 1))
            }
        }
    }

    /*
    Loop through each coordinate
    Check adjacent coordinates for digits
    Coordinate is a gear if it is a * and 2 numbers are adjacent
     */
    val coordsChecked: MutableList<Pair<Int, Int>> = mutableListOf()
    coordsList.forEach coordsListLoop@ { coord ->

        // Check if coordsChecked already contains coordinates
        if (coord in coordsChecked) return@coordsListLoop

        // Add coord to list of coordsChecked
        coordsChecked.add(coord)

        val numsFound: MutableList<Char> = mutableListOf(inputList[coord.first][coord.second])
        var xHasPrev = coord.second - 1 >= 0
        var xHasNext = coord.second + 1 < inputList[coord.first].length

        var second: Int = coord.second
        while (xHasPrev && inputList[coord.first][second].isDigit()) {
            second -= 1
            xHasPrev = second - 1 >= 0

            if (Pair(coord.first, second) in coordsChecked) continue

            if (inputList[coord.first][second].isDigit())
                numsFound.addFirst(inputList[coord.first][second])

            // Add coord to checked coords
            coordsChecked.add(Pair(coord.first, second))
        }

        second = coord.second
        while (xHasNext && inputList[coord.first][second].isDigit()) {
            second += 1
            xHasNext = second + 1 < inputList[coord.first].length

            if (Pair(coord.first, second) in coordsChecked) continue

            if (inputList[coord.first][second].isDigit())
                numsFound.add(inputList[coord.first][second])

            // Add coord to checked coords
            coordsChecked.add(Pair(coord.first, second))
        }

        numList.add(numsFound)
        if (inputList[coord.first][coord.second] == '*') {
            println("Gear: $coord numsFound")
            gearList.add(numsFound)
        }
        // val numFound: Int = numsFound.joinToString().toInt()
        println("$coord $numsFound")
    }
    println(numList)
    println(gearList)
    // val nums: MutableList<Int> = mutableListOf()
    // val emptyChar: CharSequence
    numList.forEach{
        result += it.joinToString(" ").replace(" ", "").toInt()
    }
    println(result.sum())
}
