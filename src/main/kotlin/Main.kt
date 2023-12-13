package net.nzti

import kotlin.contracts.contract

fun main() {
    /*
    Game 66: 11 red, 9 blue, 4 green; 8 red, 8 blue; 9 red, 7 blue; 1 blue, 12 green, 4 red; 2 red, 11 blue, 10 green
    12 red cubes, 13 green cubes, and 14 blue cubes
     */
    val inputList: MutableList<String> = readInput("day2_input.txt")
    val result: MutableList<Int> = mutableListOf()
    val comparison: Map<String, Int> = mapOf("red" to 12, "green" to 13, "blue" to 14)
    inputList.forEach {
        // Get number of game being played
        val game: String = it.substring(0, it.indexOf(":"))
        val gameNum: Int = it.substring(it.indexOf(" ") + 1, game.length).toInt()


        // Add to list map of color to number seen
        val cubes: List<String> = it.substring(it.indexOf(":") + 1, it.length).split(';')
        println("Cubes: $cubes")
        val cubeMap: MutableList<Map<String, Int>> = mutableListOf()
        cubes.forEach{ cube ->
            val colors: List<String> = cube.removePrefix(" ").split(", ")

            colors.forEach { color ->
                val numColor = color.split(' ')
                val colorMap: Map<String, Int> = mapOf(numColor[1] to numColor[0].toInt())
                cubeMap.add(colorMap)
            }
        }
        println(cubeMap)
    }
    println(result.sum())
}
