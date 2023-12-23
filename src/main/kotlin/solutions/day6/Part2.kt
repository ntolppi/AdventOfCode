package net.nzti

fun main() {
    val inputList: MutableList<String> = readInput("day6_input.txt")

    val raceTimes: MutableList<Int> = mutableListOf()
    inputList[0].split(' ').filter { input -> input.isNotEmpty() }.forEach { time ->
        if (time.isNotEmpty() && time[0].isDigit())
            raceTimes.add(time.toInt())
    }

    val distanceToWin: MutableList<Int> = mutableListOf()
    inputList[1].split(' ').filter { input -> input.isNotEmpty() }.forEach { distance ->
        if (distance.isNotEmpty() && distance[0].isDigit())
            distanceToWin.add(distance.toInt())
    }

    println(raceTimes)
    println(distanceToWin)

    // Quadratic function
    // Loop through race time / 2
    // if (time pressed (race time - time pressed) > distance to win) increment race won
    val raceTime: Long = raceTimes.joinToString("").toLong()
    val raceDistance: Long = distanceToWin.joinToString("").toLong()

    println(raceTime)
    println(raceDistance)

    var timesWon = 0
    for (tp in 1..<raceTime)
        if (tp * (raceTime - tp) > raceDistance)
            timesWon++

    println(timesWon)
}
