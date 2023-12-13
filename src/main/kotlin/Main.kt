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
    for (i in inputList.indices) {
        // Loop through each Char in input String
        for (j in inputList[i].indices) {
            // Skip periods and numbers
            if (inputList[i][j] == '.' || inputList[i][j].isDigit()) continue

            /*
            Special character, check for numbers
            (x - 1, y - 1) | (x, y - 1) | (x + 1, y - 1)
            (x - 1, y)     | (x, y)     | (x + 1, y)
            (x - 1, y + 1) | (x, y + 1) | (x + 1, y + 1)
             */
            val coordList: List<Pair<Int, Int>> = getAdjacentCoords(i, j)
            coordList.forEach{ coord ->
                // Check each Pair compared to inputList[i][j]
            }
        }
    }

    println(result.sum())
}

fun getAdjacentCoords(x: Int, y: Int): List<Pair<Int, Int>> {
    // TODO: Change to each character at coords instead of returning coords
    // List<Char>
    return listOf(
        Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1),
        Pair(x - 1, y),     Pair(x, y),     Pair(x + 1, y),
        Pair(x - 1, y + 1), Pair(x, y + 1), Pair(x + 1, y + 1),
    )
}
