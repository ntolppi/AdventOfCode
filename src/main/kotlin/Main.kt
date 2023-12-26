package net.nzti


fun main() {
    val inputList: MutableList<String> = readInput("day11_example_input.txt")

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
    rowsWithNoGalaxies.forEach { row ->
        expandedGalaxy.addAll(inputList.subList(prvIdx, row + 1))
        prvIdx = row
    }
    expandedGalaxy.addAll(inputList.subList(prvIdx, inputList.size))

    val columnsWithNoGalaxies: List<Int> = (inputList[0].indices.toSet() subtract columnsWithGalaxies).toList()
    expandedGalaxy.forEachIndexed { idx, row ->
        prvIdx = 0
        var expandedColumn = ""
        columnsWithNoGalaxies.forEach { column ->
            expandedColumn += row.substring(prvIdx, column + 1)
            prvIdx = column
        }
        expandedColumn += row.substring(prvIdx, expandedGalaxy[idx].length)
        expandedGalaxy[idx] = expandedColumn
    }

    expandedGalaxy.forEach {
        println(it)
    }
    println("Number of rows: ${inputList.size}")
    println("Number of rows expanded: ${expandedGalaxy.size}")
    println("Rows with no galaxy: $rowsWithNoGalaxies")

    println("Number of columns: ${inputList[0].length}")
    println("Number of columns expanded: ${expandedGalaxy[0].length}")
    println("Columns with no galaxy: $columnsWithNoGalaxies")

    println("Number of galaxies: $numGalaxies")

    // Get coords for each galaxy expanded
    val coordsGalaxies: MutableList<Pair<Int, Int>> = mutableListOf()

    expandedGalaxy.forEachIndexed { ridx, row ->
        row.forEachIndexed { cidx, column ->
            if (column == '#')
                coordsGalaxies.add(Pair(ridx, cidx))
        }
    }
    println("Galaxy coords: $coordsGalaxies")
    println("Number of galaxies expanded: ${coordsGalaxies.size}")

    val numGalaxyPairs: Int = ((coordsGalaxies.size - 1) * (coordsGalaxies.size)) / 2
    println("Number of galaxy combinations: $numGalaxyPairs")

    coordsGalaxies.forEachIndexed { idx1, coord1 ->
        coordsGalaxies.subList(idx1 + 1, coordsGalaxies.size).forEachIndexed { idx2, coord2 ->
            println("$coord1 $coord2")
        }
    }

    // Calculate the shortest path to each galaxy
}
