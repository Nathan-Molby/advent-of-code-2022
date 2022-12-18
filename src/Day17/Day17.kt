package Day17

import readInput
import kotlin.math.*

class Chasm() {
    var matrix: MutableList<Array<Boolean>> = mutableListOf()

    fun resize(matrixVerticalSize: Int) {
        for (i in matrix.size..matrixVerticalSize) {
            matrix.add(Array(7) { false })
        }
    }


    fun print() {
        for(row in matrix.reversed()) {
            for (element in row) {
                if (element) {
                    print("X")
                } else {
                    print(".")
                }
            }
            println()
        }
    }
}

class TetrisSimulator() {
    var directions: MutableList<Direction> = mutableListOf()
    var directionIndex = 0
    var topCurrentY = 0
    var chasm = Chasm()
    var memoizedTable: HashMap<Pair<Int, Int>, Int> = hashMapOf()
    enum class Direction {
        RIGHT, LEFT
    }
    fun readInput(input: List<String>) {
        val actualInput = input[0]
        for (char in actualInput) {
            if (char == '>') {
                directions.add(Direction.RIGHT)
            } else {
                directions.add(Direction.LEFT)
            }
        }
    }

    fun runSimulation() {
        var lastHeight = 0
        for(i in 0 until 10000) {
            var rock: Rock? = null
            if (i % 5 == 0) {
                var additionalAddition = 0
                if (i != 0) {
                    additionalAddition = 1
                }
                chasm.resize(topCurrentY + 3 + additionalAddition)
                rock = HorizontalRock(chasm, topCurrentY + 3 + additionalAddition)
            } else if (i % 5 == 1) {
                chasm.resize(topCurrentY + 6)
                rock = PlusRock(chasm, topCurrentY + 4)
            } else if (i % 5 == 2) {
                chasm.resize(topCurrentY + 6)
                rock = LRock(chasm, topCurrentY + 4)
            } else if (i % 5 == 3) {
                chasm.resize(topCurrentY + 7)
                rock = VerticalRock(chasm, topCurrentY + 4)
            } else if (i % 5 == 4) {
                chasm.resize(topCurrentY + 5)
                rock = SquareRock(chasm, topCurrentY + 4)
            }


            while (true) {
                if (directionIndex == directions.count()) {directionIndex = 0}
                val currentDirection = directions[directionIndex]

                when(currentDirection) {
                    Direction.RIGHT -> rock!!.moveRight()
                    Direction.LEFT -> rock!!.moveLeft()
                }

                val result = rock.moveDown()
                topCurrentY = max(result.second, topCurrentY)

                val currentHeight = topCurrentY + 1
                println((i % 5).toString() + "," + directionIndex.toString() + "," + i.toString() + "," + (currentHeight - lastHeight).toString())
                lastHeight = currentHeight

                directionIndex++
                //if rock failed to move down, move to next rock
                if (!result.first) {
                    break
                }
            }

        }
    }

}

fun main() {
    fun part1(input: List<String>): Int {
        val tetrisSimulator = TetrisSimulator()
        tetrisSimulator.readInput(input)
        tetrisSimulator.runSimulation()
        return tetrisSimulator.topCurrentY + 1
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    val testInput = readInput("Day17","Day17_test")
//    part1(testInput)
//    check(part1(testInput) == 1651)
//    println(part2(testInput))
//    check(part2(testInput) == 1707)
//
    val input = readInput("Day17","Day17")
    part1(input)
//    println(part2(input))
}
