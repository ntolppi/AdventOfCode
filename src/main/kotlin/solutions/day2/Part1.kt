package net.nzti

fun main() {
    /*
    Game 66: 11 red, 9 blue, 4 green; 8 red, 8 blue; 9 red, 7 blue; 1 blue, 12 green, 4 red; 2 red, 11 blue, 10 green
    12 red cubes, 13 green cubes, and 14 blue cubes
     */
    val comparison: Map<String, Int> = mapOf("red" to 12, "green" to 13, "blue" to 14)

    val result: MutableList<Int> = mutableListOf()

    var validGame: Boolean = true

    val gameMap: MutableMap<Int, Map<String, Int>> = getInputFormatted()
    // Loop through games
    gameMap.forEach{ game ->
        // Game starting!
        var valid: Boolean = true
        game.value.forEach{ color ->
            if (comparison.getOrDefault(color.key, -1) < color.value) {
                // Invalid game
                valid = false
            }
        }
        if (valid) result += game.key
    }
    println(result.sum())
}


fun getInputFormatted(): MutableMap<Int, Map<String,Int>> {
    /*
    Day 2: Get input list and format
     */
    val inputList: MutableList<String> = readInput("day2_input.txt")
    val inputFormatted: MutableMap<Int, Map<String, Int>> = mutableMapOf()
    inputList.forEach {
        // Get number of game being played
        val game: String = it.substring(0, it.indexOf(":"))
        val gameNum: Int = it.substring(it.indexOf(" ") + 1, game.length).toInt()


        // Add to list map of color to number seen
        val cubes: List<String> = it.substring(it.indexOf(":") + 1, it.length).split(';')

        // Get list of colors
        // val colors:  MutableList<List<String>> = mutableListOf()
        val colorMap: MutableMap<String, Int> = mutableMapOf()
        cubes.forEach{ cube ->
            val colors: List<String> = cube.removePrefix(" ").split(", ")

            colors.forEach { color ->
                // Example: 1, red
                val numColor = color.split(' ')

                // Check if value is larger than what is currently stored
                if (colorMap.getOrDefault(numColor[1], -1) < numColor[0].toInt())
                    colorMap[numColor[1]] = numColor[0].toInt()
            }
        }
        inputFormatted[gameNum] = colorMap
    }
    return inputFormatted
}
