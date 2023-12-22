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

    println(seeds)

    // Order of numbers source destination range
    println(seeds)
    seedsMap.forEach { println(it) }
    println(srcToDstMap)

    val locations: MutableList<Long> = mutableListOf()
    seeds.forEach {
        val locationID: Long = getSeedLocation(it, srcToDstMap, seedsMap)
        println("Seed $it Location $locationID")
        locations.add(locationID)
    }
    locations.sort()
    println("Locations: $locations")
    println(locations[0])
}

/**
 * Given an ID convert from src to dst
 * @param srcID         ID to convert
 * @param srcToDstMap   Map of strings src to dst
 * @param seedMap       Map of srcID to destination, source, and range of IDs
 * @param src           Source to start getting IDs
 * @return              Long ID of location
 */
fun getSeedLocation(srcID: Long, srcToDstMap: MutableMap<String, String>, seedMap: MutableMap<String, MutableList<MutableList<Long>>>, src: String = "seed"): Long {
    // List of Lists, destination ids to source ids and range
    var dstID: Long = srcID
    val seedMapIds: MutableList<MutableList<Long>> = seedMap.getOrDefault(src, mutableListOf())

    run seedMapIdsForEach@{
        seedMapIds.forEach { seedIDs ->
            if (srcID >= seedIDs[1] && srcID < seedIDs[1] + seedIDs[2]) {
                // Destination ID is the destination start + offset
                val offset: Long = srcID - seedIDs[1]
                dstID = seedIDs[0] + offset
                return@seedMapIdsForEach
            }
        }
    }

    val dst: String = srcToDstMap.getOrDefault(src, "KeyNotFound")
    println("$src $srcID to $dst $dstID")

    if (!srcToDstMap.containsKey(dst)) return dstID

    return getSeedLocation(dstID, srcToDstMap, seedMap, dst)
}
