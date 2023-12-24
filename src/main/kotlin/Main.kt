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
    var steps: Long = 0

    val startingLocations: MutableList<String> = locations.filter { it[it.length - 1] == 'A' }.toMutableList()
    val currentLocations: MutableList<String> = locations.filter { it[it.length - 1] == 'A' }.toMutableList()

    val locationsFound: MutableMap<String, Set<Long>> = mutableMapOf()
    startingLocations.forEach { startingLocation ->
        locationsFound[startingLocation] = setOf()
    }

    // while (currentLocations.filter { it[it.length - 1] == 'Z' }.size != currentLocations.size) {
    while (locationsFound.values.minOf { it.size } < 2) {
        // Get direction based on current step
        val currentStep: Int = (steps % directions.length).toInt()
        steps += 1
        val dir: Char = directions[currentStep]
        for (idx in currentLocations.indices) {
            val location: String = currentLocations[idx]
            val destination: Pair<String, String> = locationMap.getOrDefault(location, Pair("-1", "-1"))
            when (dir) {
                'L' -> {
                    currentLocations[idx] = destination.first
                }
                'R' -> {
                    currentLocations[idx] = destination.second
                }
            }

            if (currentLocations[idx][currentLocations[idx].length - 1] == 'Z') {
                val startingLocation: String = startingLocations[idx]
                val currentPeriodList: MutableSet<Long> = locationsFound.getOrDefault(startingLocation, setOf()).toMutableSet()
                currentPeriodList.add(steps)
                locationsFound[startingLocation] = currentPeriodList
                println("${startingLocations[idx]} ${currentLocations[idx]} $currentStep")
                println("$currentLocations $steps")
            }
        }
    }
    println(steps)
    println(currentLocations)

    val locationsPeriod: MutableList<Long> = mutableListOf()
    locationsFound.forEach { locationFound ->
        println(locationFound)
        val locationSteps: List<Long> = locationFound.value.toList()
        locationsPeriod.add(locationSteps[1] - locationSteps[0])
    }
    println(locationsPeriod)

    // val locationsPeriodMap: MutableMap<Long, MutableList<Long>> = mutableMapOf()
    // locationsPeriod.forEach { locationPeriod ->
    //     locationsPeriodMap[locationPeriod] = 0
    // }
}
