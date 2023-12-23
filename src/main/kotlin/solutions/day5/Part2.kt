package net.nzti.solutions.day5

import net.nzti.readInput

fun main() {
    val inputList: MutableList<String> = readInput("day5_input.txt")

    val seeds: MutableList<Long> = mutableListOf()
    inputList[0].substring(inputList[0].indexOf(' ') + 1, inputList[0].length).split(' ').forEach { seeds.add(it.toLong()) }

    val seedsMap: MutableMap<String, MutableList<MutableList<Long>>> = mutableMapOf()
    val srcToDstMap: MutableMap<String, String> = mutableMapOf()
    var recentKey = ""
    inputList.subList(1, inputList.size).forEach { seedMap ->
        if (seedMap.isEmpty())
            return@forEach

        if (seedMap.contains("map")) {
            val srcDstList: List<String> = seedMap.substring(0, seedMap.indexOf(' ')).split("-to-")
            srcToDstMap[srcDstList[0]] = srcDstList[1]
            recentKey = srcDstList[0]
            seedsMap[recentKey] = mutableListOf()
            return@forEach
        }

        if (recentKey.isEmpty())
            return@forEach

        // Map of destination, source, and range
        val nums: MutableList<Long> = mutableListOf()
        seedMap.split(' ').forEach { nums.add(it.toLong()) }
        seedsMap[recentKey]?.add(nums)
    }

    // Order of numbers source destination range
    println(seeds)
    val seedIDs: List<Long> = seeds.filterIndexed { index, seed -> index % 2 == 0 }
    val seedRanges: List<Long> = seeds.filterIndexed {index, seed -> index % 2 == 1}
    val seedIDsMap: Map<Long, Long> = seedIDs.zip(seedRanges).toMap()

    println(seedIDsMap)
    println(srcToDstMap)

    var location: Long = -1
    seedIDsMap.forEach {seedIDMap ->
        println("Processing ${seedIDs.indexOf(seedIDMap.key) + 1}/${seedIDs.size} Getting locations for seedIDs: ${seedIDMap.key} to ${seedIDMap.value}")
        for (seedID in seedIDMap.key..<seedIDMap.key + seedIDMap.value) {
            val locationID: Long = getSeedLocation(seedID, srcToDstMap, seedsMap)
            if (locationID < location || location < 0)
                location = locationID
        }
    }
    println(location)
}
