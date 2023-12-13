package net.nzti

import java.io.BufferedReader
import java.io.File

fun readInput(filename: String = "input.txt", path: String = ".\\src\\main\\assets\\"): MutableList<String> {
    val lineList = mutableListOf<String>()
    val bufferedReader: BufferedReader = File(path + filename).bufferedReader()
    bufferedReader.useLines { lines -> lines.forEach { lineList.add(it) } }
    lineList.forEach{ line -> println(line) }
    return lineList
}