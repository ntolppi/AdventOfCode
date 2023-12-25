package net.nzti.solutions.day10

import net.nzti.readInput
import kotlin.math.abs


class Coord2(val start: Pair<Int, Int>, val input: MutableList<String>) {
    var steps: Int = 0
    var area: Int = 0
    val coords: MutableList<Pair<Int, Int>> = mutableListOf()
    private val dirs: MutableList<Pair<Int, Int>> = mutableListOf()

    init {
        val adjCoords: MutableList<Pair<Int, Int>> = mutableListOf(
            Pair(0, -1), Pair(-1, 0), Pair(0, 1), Pair(1, 0)
        )
        val validOpts: MutableList<MutableList<Char>> = mutableListOf(
            mutableListOf('L', '-', 'F'), mutableListOf('7', '|', 'F'),
            mutableListOf('7', '-', 'J'), mutableListOf('J', '|', 'L')
        )
        adjCoords.forEachIndexed adjCoordsForEach@ { idx, coord ->
            val adjCoord: Pair<Int, Int> = Pair(start.first + coord.first, start.second + coord.second)

            if (adjCoord.first < 0 || adjCoord.second < 0 || adjCoord.second >= input[0].length)
                return@adjCoordsForEach
            val adjChar: Char = input[adjCoord.first][adjCoord.second]
            val validNextChars: MutableList<Char> = validOpts[idx]
            if (!validNextChars.contains(adjChar))
                return@adjCoordsForEach
            coords.add(adjCoord)
            dirs.add(adjCoords[idx])
        }
        // Calculate area using shoelace formula
        for (idx in coords.indices) {
            when (idx) {
                0 -> area += (start.first * coords[idx].second) - (start.second * coords[idx].first)
                1 -> area += (coords[idx].first * start.second) - (coords[idx].second * start.first)
            }
        }
        println(coords)
        println(dirs)
    }

    /**
     * Map of characters to where to go next
     * Direction is what is done to x, y to move in that direction
     * | move up (y - 1, x) or down (y + 1, x)
     * - move left (y, x - 1) or right (y, x + 1)
     * L move diagonally up-left (y - 1, x - 1) or down-right (y + 1, x + 1)
     * J move diagonally down-left (y + 1, x - 1) or up-right (y - 1, x + 1)
     */
    fun followPipe(): Pair<Int, Int> {
        steps += 1
        if (coords[0].first == coords[1].first && coords[0].second == coords[1].second)
            return coords[0]

        for (idx in coords.indices)
            getNext(idx)

        println(coords)
        return followPipe()
    }

    private fun getNext(idx: Int) {
        val coord: Pair<Int, Int> = coords[idx]
        val dir: Pair<Int, Int> = dirs[idx]
        val pipeType: Char = input[coord.first][coord.second]

        var nextCoord: Pair<Int, Int> = dir
        when(pipeType) {
            'F', 'J' -> nextCoord = Pair(dir.second * -1, dir.first * -1)
            '|', '-' -> nextCoord = Pair(dir.first, dir.second)
            'L', '7' -> nextCoord = Pair(dir.second, dir.first)
        }

        // Next coord
        val nextPipe: Pair<Int, Int> = Pair(coord.first + nextCoord.first, coord.second + nextCoord.second)

        // Calculate area using shoelace formula
        when (idx) {
            0 -> area += (coord.first * nextPipe.second) - (coord.second * nextPipe.first)
            1 -> area += (nextPipe.first * coord.second) - (nextPipe.second * coord.first)
        }

        coords[idx] = nextPipe
        dirs[idx] = nextCoord
    }
}

fun main() {
    val inputList: MutableList<String> = readInput("day10_input.txt")

    var start: Pair<Int, Int> = Pair(0, 0)
    run findStart@{
        for (y in inputList.indices) {
            for (x in inputList[y].indices) {
                if (inputList[y][x] == 'S') {
                    println("$y, $x")
                    start = Pair(y, x)
                    return@findStart
                }
            }
        }
    }

    val coord = Coord2(start, inputList)
    coord.followPipe()
    println("Start: $start")
    println("Number of steps to furthest point: ${coord.steps}")
    coord.area = abs(coord.area) / 2
    println("Area: ${coord.area}")

    /*
     * Pick's theorem
     * A = i + b/2 - 1
     * A is area
     * i is interior points
     * b is boundary points
     * i = A - b/2 + 1
     */
    println("Interior coords: ${coord.area - coord.steps + 1}")
}
