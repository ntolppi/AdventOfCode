package net.nzti


fun main() {
    val inputList: MutableList<String> = readInput("day8_input.txt")

    val locations: MutableList<String> = mutableListOf()
    val locationMap: MutableMap<String, Pair<String, String>> = mutableMapOf()
    inputList.subList(2, inputList.size).forEach { location ->
        val locationAndDirections: List<String> = location.split(" = ")

        // NQT = (TXC, RVJ)
        locations.add(locationAndDirections[0])
        locationMap[locationAndDirections[0]] = locationAndDirections[1].substring(1, locationAndDirections[1].length - 1).split(", ").zipWithNext()[0]
    }

    val directions: String = inputList[0]
    var steps = 0
    var location = "AAA"
    while (location != "ZZZ") {
        // Get direction based on current step
        val dir: Char = directions[steps % directions.length]
        steps++
        val destination: Pair<String, String> = locationMap.getOrDefault(location, Pair("-1", "-1"))
        when(dir) {
            'L' -> location = destination.first
            'R' -> location = destination.second
        }
    }
    println(steps)
    println(location)
}
