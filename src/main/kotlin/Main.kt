package net.nzti

fun main() {
    /*
    ...788.............................54.........501...........555.........270........
    ..../..*963........................*..860......................*....53...../.......
    ............*......41..481+.......462....$..187......678.......420....-............

    Steps, think of it like a spreadsheet:
        1. Get to non-period, index
        2. Check previous value index - 1
        3. Check next value index + 1
        4. Check above, index of previous row
        6. Check above, index of previous row - 1
        7. Check above, index of previous row + 1
        8. Check below, index of next row
        9. Check below, index of next row - 1
       10. Check below, index of next row + 1

       Do above using x,y coordinates
     */
    val inputList: MutableList<String> = readInput("day3_input.txt")
    val result: MutableList<Int> = mutableListOf()

    // Loop through each line of input
    val numList: MutableList<MutableList<Char>> = mutableListOf()
    for (y in inputList.indices) {
        // Loop through each Char in input String
        for (x in inputList[y].indices) {
            // Skip periods and numbers
            if (inputList[y][x] == '.' || inputList[y][x].isDigit()) continue

            /*
            Special character, check for numbers
            (x - 1, y - 1) | (x, y - 1) | (x + 1, y - 1)
            (x - 1, y)     | (x, y)     | (x + 1, y)
            (x - 1, y + 1) | (x, y + 1) | (x + 1, y + 1)
             */
            var xHasPrev: Boolean = x - 1 >= 0
            val yHasPrev: Boolean = y - 1 >= 0

            var xHasNext: Boolean = x + 1 < inputList[y].length
            val yHasNext: Boolean = y + 1 < inputList.size

            val coordsList: MutableList<Pair<Int, Int>> = mutableListOf()

            var char: Char
            if (yHasPrev) {
                char = inputList[y - 1][x]
                if (char.isDigit())
                    coordsList.add(Pair(y - 1, x))
            }

            if (yHasNext) {
                char = inputList[y + 1][x]
                if (char.isDigit())
                    coordsList.add(Pair(y + 1, x))
            }

            if (xHasPrev) {
                char = inputList[y][x - 1]
                if (char.isDigit())
                    coordsList.add(Pair(y, x - 1))

                if (yHasPrev) {
                    char = inputList[y - 1][x - 1]
                    if (char.isDigit())
                        coordsList.add(Pair(y - 1, x - 1))
                }

                if (yHasNext) {
                    char = inputList[y + 1][x - 1]
                    if (char.isDigit())
                        coordsList.add(Pair(y + 1, x - 1))
                }

            }

            if (xHasNext) {
                char = inputList[y][x + 1]
                if (char.isDigit())
                    coordsList.add(Pair(y, x + 1))

                if (yHasPrev) {
                    char = inputList[y - 1][x + 1]
                    if (char.isDigit())
                        coordsList.add(Pair(y - 1, x + 1))
                }

                if (yHasNext) {
                    char = inputList[y + 1][x + 1]
                    if (char.isDigit())
                        coordsList.add(Pair(y + 1, x + 1))
                }
            }

            coordsList.forEach { coord ->
                // TODO: Add list of coords found and skip if found already
                val numsFound: MutableList<Char> = mutableListOf(inputList[coord.first][coord.second])
                xHasPrev = coord.second - 1 >= 0
                xHasNext = coord.second + 1 < inputList[y].length

                var second: Int = coord.second
                while (xHasPrev && inputList[coord.first][second].isDigit()) {
                    second -= 1
                    xHasPrev = second - 1 >= 0

                    if (inputList[coord.first][second].isDigit())
                        numsFound.addFirst(inputList[coord.first][second])
                }

                second = coord.second
                while (xHasNext && inputList[coord.first][second].isDigit()) {
                    second += 1
                    if (inputList[coord.first][second].isDigit())
                        numsFound.addFirst(inputList[coord.first][second])
                    xHasNext = second + 1 < inputList[y].length
                }

                numList.add(numsFound)
                // val numFound: Int = numsFound.joinToString().toInt()
                println("$coord $numsFound")
            }

        }
    }

    println(numList)
    println(result.sum())
}
