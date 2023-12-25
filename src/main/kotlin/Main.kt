package net.nzti

import io.ktor.http.cio.*
import kotlin.math.exp


fun main() {
    val inputList: MutableList<String> = readInput("day11_input.txt")

    val rowsWithGalaxies: MutableSet<Int> = mutableSetOf()
    val columnsWithGalaxies: MutableSet<Int> = mutableSetOf()
    var numGalaxies = 0
    inputList.forEachIndexed inputListForEach@ { idx, row ->
        row.forEachIndexed { cidx, column ->
            if (column == '#') {
                numGalaxies += 1
                columnsWithGalaxies.add(cidx)
                rowsWithGalaxies.add(idx)
            }
        }
    }

    val rowsWithNoGalaxies: Set<Int> = inputList.indices.toSet() subtract rowsWithGalaxies

    val expandedGalaxy: MutableList<String> = mutableListOf()
    var prvIdx = 0
    for (idx in rowsWithNoGalaxies.indices) {
        expandedGalaxy.addAll(inputList.subList(prvIdx, idx + 1))
        prvIdx = idx
    }
    expandedGalaxy.addAll(inputList.subList(prvIdx, inputList.size))

    val columnsWithNoGalaxies: Set<Int> = inputList[0].indices.toSet() subtract columnsWithGalaxies
    expandedGalaxy.forEachIndexed { idx, row ->
        prvIdx = 0
        var expandedColumn: String = ""
        for (cidx in columnsWithNoGalaxies.indices) {
            expandedColumn += row.substring(prvIdx, cidx + 1)
            prvIdx = cidx
        }
        expandedColumn += row.substring(prvIdx, inputList[0].length)
        expandedGalaxy[idx] = expandedColumn
    }

    expandedGalaxy.forEach {
        println(it)
    }
    println(inputList.size)
    println(expandedGalaxy.size)
    println(rowsWithNoGalaxies)

    println(inputList[0].length)
    println(expandedGalaxy[0].length)
    println(columnsWithNoGalaxies)

    println(numGalaxies)

    // Get coords for each galaxy expanded
    val coordsGalaxies: MutableList<Pair<Int, Int>> = mutableListOf()

    expandedGalaxy.forEachIndexed() { ridx, row ->
        row.forEachIndexed { cidx, column ->
            if (column == '#')
                coordsGalaxies.add(Pair(ridx, cidx))
        }
    }
    println(coordsGalaxies)
    println(coordsGalaxies.size)
    println(((coordsGalaxies.size * coordsGalaxies.size) + coordsGalaxies.size)/2)


    coordsGalaxies.forEachIndexed { idx1, coord1 ->
        coordsGalaxies.subList(idx1 + 1, coordsGalaxies.size).forEachIndexed { idx2, coord2 ->

        }
    }

    // Calculate the shortest path to each galaxy
}
