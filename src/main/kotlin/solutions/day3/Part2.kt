package net.nzti

import io.ktor.http.*
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
    // val result: MutableList<Int> = mutableListOf()

    // Loop through each line of input
    val numList: MutableList<MutableList<Int>> = mutableListOf()
    val gearList: MutableList<MutableList<Int>> = mutableListOf()

    // List of coordinaates of special characters
    val specialCharCoordsList: MutableList<Pair<Int, Int>> = mutableListOf()
    for (y in inputList.indices) {
        // Loop through each Char in input String
        for (x in inputList[y].indices) {
            // Skip periods and numbers
            if (inputList[y][x] == '.' || inputList[y][x].isDigit()) continue
            specialCharCoordsList.add(Pair(y, x))
        }
    }

    /*
    Loop through each coordinate
    Check adjacent coordinates for digits
    Coordinate is a gear if it is a * and 2 numbers are adjacent
     */
    val coordsChecked: MutableList<Pair<Int, Int>> = mutableListOf()
    specialCharCoordsList.forEach coordsListLoop@ { coord ->

        // Check if coordsChecked already contains coordinates
        if (coord in coordsChecked) return@coordsListLoop

        val numsFound: MutableList<Int> = findNums(inputList, coord, coordsChecked)

        numList.add(numsFound)
        if (inputList[coord.first][coord.second] == '*' && numsFound.size == 2) {
            println("Gear: $coord $numsFound")
            gearList.add(numsFound)
        }
        // val numFound: Int = numsFound.joinToString().toInt()
        println("$coord $numsFound")
    }
    println(numList)
    println(gearList)
    println(numList.sumOf{ it.sum() } )
    print(gearList.sumOf { it.reduce{acc, i -> acc * i} })
}

/**
 * @param inputList     Challenge input as a List of Strings
 * @param coord         Coordinates to start searching at
 * @param coordsChecked List of coordinates that have been checked already
 * @param numsFound     List of numbers found
 */
fun findNums(
    inputList: MutableList<String>,
    coord: Pair<Int, Int>,
    coordsChecked: MutableList<Pair<Int, Int>> = mutableListOf(),
    numsFound: MutableList<Int> = mutableListOf()
): MutableList<Int> {
    /*
    788
    ./.
    ...
     */

    // Check adjacent on X axis
    val beforeX: MutableList<Char> = findNum(inputList, Pair(coord.first, coord.second - 1), -1, coordsChecked)
    if (beforeX.isNotEmpty())
        numsFound.add(beforeX.joinToString("").toInt())

    val afterX: MutableList<Char> = findNum(inputList, Pair(coord.first, coord.second + 1), 1, coordsChecked)
    if (afterX.isNotEmpty())
        numsFound.add(afterX.joinToString("").toInt())

    // Check above
    val aboveLeftX: MutableList<Char> = findNum(inputList, Pair(coord.first - 1, coord.second - 1), -1, coordsChecked)
    val aboveX: MutableList<Char> = findNum(inputList, Pair(coord.first - 1, coord.second), 0, coordsChecked)
    val aboveRightX: MutableList<Char> = findNum(inputList, Pair(coord.first - 1, coord.second + 1), 1, coordsChecked)

    // Get continuous nums
    // If [], ['1'], ['2'] format ['1', '2']
    // If ['1'], [], ['2'] format ['1', '2']
    numsFound.addAll(getNums(aboveLeftX, aboveX, aboveRightX))

    // Check below
    val belowLeftX: MutableList<Char> = findNum(inputList, Pair(coord.first + 1, coord.second - 1), -1, coordsChecked)
    val belowX: MutableList<Char> = findNum(inputList, Pair(coord.first + 1, coord.second), 0, coordsChecked)
    val belowRightX: MutableList<Char> = findNum(inputList, Pair(coord.first + 1, coord.second + 1), 1, coordsChecked)

    // Get continuous num
    numsFound.addAll(getNums(belowLeftX, belowX, belowRightX))

    return numsFound
}

/**
 * Get list of ints found
 * @param left
 * @param center
 * @param right
 */
fun getNums(left: MutableList<Char>, center: MutableList<Char>, right: MutableList<Char>): MutableList<Int> {
    val nums: MutableList<Int> = mutableListOf()
    if (center.isEmpty()) {
        if (left.isNotEmpty()) nums.add(left.joinToString("").toInt())
        if (right.isNotEmpty()) nums.add(right.joinToString("").toInt())
    } else {
        val numsBelow: MutableList<Char> = mutableListOf()
        if (left.isNotEmpty()) numsBelow.addAll(left)
        numsBelow.addAll(center)
        if (right.isNotEmpty()) numsBelow.addAll(right)
        nums.add(numsBelow.joinToString("").toInt())
    }
    return nums
}

/**
 * @param inputList     Challenge input as a List of Strings
 * @param coord         Coordinates to start searching at
 * @param step          Direction and how much to go for checking next element
 * @param coordsChecked List of coordinates that have been checked already
 * @param numsFound     Numbers found so far
 * @return              Number found, default to 0 if no number found
 */
fun findNum(
    inputList: MutableList<String>, coord: Pair<Int, Int>,
    step: Int,
    coordsChecked: MutableList<Pair<Int, Int>> = mutableListOf(),
    numsFound: MutableList<Char> = mutableListOf()
): MutableList<Char> {
    val xHasNext = if (step < 0) coord.second >= 0 else coord.second < inputList[coord.first].length

    if (!xHasNext || !inputList[coord.first][coord.second].isDigit() || coordsChecked.contains(coord)) {
        // If step is negative went backwards and numsFound is reversed, reverse to get in correct order
        if (step < 0)
            numsFound.reverse()
        return numsFound
    }

    numsFound.add(inputList[coord.first][coord.second])
    coordsChecked.add(coord)

    // Step is 0, check and return
    if (step == 0)
        return numsFound

    return findNum(inputList, Pair(coord.first, coord.second + step), step, coordsChecked, numsFound)
}
