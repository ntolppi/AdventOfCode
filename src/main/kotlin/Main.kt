package net.nzti


class PipeMap(start: Pair<Int, Int>) {
    val startPipe: Pipe = Pipe(start)
}

class Pipe(coord: Pair<Int, Int>) {
    val adjPipes: MutableList<Pair<Int, Int>> = mutableListOf()
}

class Coord(start: Pair<Int, Int>, val input: MutableList<String>, val pipeMap: PipeMap) {
    var steps: Int = 0
    val coords: MutableList<Pair<Int, Int>> = mutableListOf()
    var pipes: MutableList<Pipe> = mutableListOf()
    private val dirs: MutableList<Pair<Int, Int>> = mutableListOf()

    init {
        val adjCoords: MutableList<Pair<Int, Int>> = mutableListOf(
            Pair(0, -1), Pair(-1, 0), Pair(0, 1), Pair(1, 0)
        )
        val validOpts: MutableList<MutableList<Char>> = mutableListOf(
            mutableListOf('L', '-', 'F'), mutableListOf('7', '|', 'F'),
            mutableListOf('7', '-', 'J'), mutableListOf('J', '|', 'L')
        )
        adjCoords.forEachIndexed adjCoordsForEach@ { idx, coord ->
            val adjCoord: Pair<Int, Int> = Pair(start.first + coord.first, start.second + coord.second)
            val adjChar: Char = input[adjCoord.first][adjCoord.second]
            val validNextChars: MutableList<Char> = validOpts[idx]
            if (!validNextChars.contains(adjChar))
                return@adjCoordsForEach
            coords.add(adjCoord)
            pipes.add(Pipe(adjCoord))
            dirs.add(adjCoords[idx])
        }
        println(coords)
        println(dirs)
        pipeMap.startPipe.adjPipes.addAll(coords)
    }

    /**
     * Map of characters to where to go next
     * Direction is what is done to x, y to move in that direction
     * | move up (y - 1, x) or down (y + 1, x)
     * - move left (y, x - 1) or right (y, x + 1)
     * L move diagonally up-left (y - 1, x - 1) or down-right (y + 1, x + 1)
     * J move diagonally down-left (y + 1, x - 1) or up-right (y - 1, x + 1)
     */
    fun followPipe(): Pair<Int, Int> {
        steps += 1
        if (coords[0].first == coords[1].first && coords[0].second == coords[1].second)
            return coords[0]


        for (idx in coords.indices) {
            getNext(idx)
        }

        println(coords)
        return followPipe()
    }

    private fun getNext(idx: Int) {
        val coord: Pair<Int, Int> = coords[idx]
        val dir: Pair<Int, Int> = dirs[idx]
        val pipeType: Char = input[coord.first][coord.second]

        var nextCoord: Pair<Int, Int> = dir
        when(pipeType) {
            'F', 'J' -> nextCoord = Pair(dir.second * -1, dir.first * -1)
            '|', '-' -> nextCoord = Pair(dir.first, dir.second)
            'L', '7' -> nextCoord = Pair(dir.second, dir.first)
        }
        val nextPipe: Pair<Int, Int> = Pair(coord.first + nextCoord.first, coord.second + nextCoord.second)
        coords[idx] = nextPipe
        dirs[idx] = nextCoord
    }
}

fun main() {
    val inputList: MutableList<String> = readInput("day10_input.txt")

    var start: Pair<Int, Int> = Pair(0, 0)
    run findStart@{
        for (y in inputList.indices) {
            for (x in inputList[y].indices) {
                if (inputList[y][x] == 'S') {
                    println("$y, $x")
                    start = Pair(y, x)
                    return@findStart
                }
            }
        }
    }

    val pipeMap = PipeMap(start)
    val coord = Coord(start, inputList, pipeMap)
    coord.followPipe()
    println(coord.coords)
    println(start)
    println(coord.steps)
}
