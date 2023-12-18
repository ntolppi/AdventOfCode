package net.nzti.solutions.day2

import net.nzti.readInput

fun main() {
    /*
    Game 66: 11 red, 9 blue, 4 green; 8 red, 8 blue; 9 red, 7 blue; 1 blue, 12 green, 4 red; 2 red, 11 blue, 10 green
    12 red cubes, 13 green cubes, and 14 blue cubes
     */
    val comparison: Map<String, Int> = mapOf("red" to 12, "green" to 13, "blue" to 14)

    val result: MutableList<Int> = mutableListOf()

    var validGame: Boolean = true

    val gameMap: MutableMap<Int, Map<String, Int>> = getMinValidInput(comparison)
    // Loop through games
    gameMap.forEach{ game ->
        println(game)

        // Game starting!
        var product = 1
        game.value.forEach{ color ->
            product *= color.value
        }
        result.add(product)
    }
    println(result.sum())
}

fun getMinValidInput(comparison: Map<String, Int>): MutableMap<Int, Map<String,Int>> {
    /*
    Day 2: Get input list and format
     */
    val inputList: MutableList<String> = readInput("day2_input.txt")
    val inputFormatted: MutableMap<Int, Map<String, Int>> = mutableMapOf()
    inputList.forEach { input: String ->
        // Get number of game being played
        val game: String = input.substring(0, input.indexOf(":"))
        val gameNum: Int = input.substring(input.indexOf(" ") + 1, game.length).toInt()


        // Add to list map of color to number seen
        val cubes: List<String> = input.substring(input.indexOf(":") + 1, input.length).split(';')

        // Get list of colors
        // val colors:  MutableList<List<String>> = mutableListOf()
        val colorMap: MutableMap<String, Int> = mutableMapOf()
        cubes.forEach{ cube ->
            val colors: List<String> = cube.removePrefix(" ").split(", ")

            colors.forEach colorsForEach@ { color ->
                // Example: 1, red
                val numColor = color.split(' ')

                // Comparison doesn't contain value, somehow, skip
                if (!comparison.containsKey(numColor[1])) return@colorsForEach

                // Check if value is larger than what is currently stored
                if (colorMap.getOrDefault(numColor[1], -1) < numColor[0].toInt())
                    colorMap[numColor[1]] = numColor[0].toInt()
            }
        }
        inputFormatted[gameNum] = colorMap
    }
    return inputFormatted
}
