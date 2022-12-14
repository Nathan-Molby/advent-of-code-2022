package Day14

import readInput
import kotlin.math.*

class RockStructure(var points: List<Pair<Int, Int>>) {
    fun maxX(): Int {
        return points.maxOf { it.first }
    }

    fun maxY(): Int {
        return points.maxOf { it.second }
    }
}
class MazePart1() {
    var matrix: Array<Array<Int>> = arrayOf(arrayOf())

    fun print() {
        for(row in matrix) {
            for(value in row) {
                if(value == 0) {
                    print("-")
                } else {
                    print("X")
                }
            }
            println()
        }
    }

    fun runSimulation(): Int {
        var piecesOfSand = 0
        while(runSimulationForOnePieceOfSand()) {
            piecesOfSand++
        }
        return piecesOfSand
    }
    fun runSimulationForOnePieceOfSand(): Boolean {
        var x = 500
        var y = -1

        while(true) {
            if (y == matrix.size - 1) {
                return false
            }

            if(matrix[y + 1][x] == 0) {
                y++
            } else {
                if(x == 0) {
                    return false
                }
                if(matrix[y + 1][x-1] == 0){
                    y++
                    x--
                } else {
                    if(x == matrix[0].size - 1) {
                        return false
                    }

                    if(matrix[y+1][x+1] == 0) {
                        y++
                        x++
                    } else {
                        matrix[y][x] = 1
                        return true
                    }
                }
            }
        }
    }

    fun processInput(input: List<String>) {
        var rocks = mutableListOf<RockStructure>()
        for (row in input) {
            val rockFormations = row
                .split("->")
                .map { it.trim() }
                .map { it.split(",") }
                .map { Pair(it[0].toInt(), it[1].toInt()) }
            rocks.add(RockStructure(rockFormations))
        }

        val matrixX = max(500, rocks.maxOf { it.maxX() } + 1)
        val matrixY = rocks.maxOf { it.maxY() } + 1

        matrix = Array(matrixY) { Array(matrixX) { 0 } }

        for (rock in rocks) {
            for (pointIndex in 0 until rock.points.size - 1) {
                val firstRock = rock.points[pointIndex]
                val secondRock = rock.points[pointIndex + 1]
                val maxX = max(firstRock.first, secondRock.first)
                val minX = min(firstRock.first, secondRock.first)
                val maxY = max(firstRock.second, secondRock.second)
                val minY = min(firstRock.second, secondRock.second)
                for (x in minX..maxX) {
                    for (y in minY..maxY) {
                        matrix[y][x] = 1
                    }
                }
            }
        }
    }
}

class MazePart2() {
    var matrix: Array<Array<Int>> = arrayOf(arrayOf())

    fun print() {
        for(row in matrix) {
            for(value in row) {
                if(value == 0) {
                    print("-")
                } else {
                    print("X")
                }
            }
            println()
        }
    }

    fun runSimulation(): Int {
        var piecesOfSand = 0
        while(runSimulationForOnePieceOfSand()) {
            piecesOfSand++
        }
        return piecesOfSand + 1
    }
    fun runSimulationForOnePieceOfSand(): Boolean {
        var x = 500
        var y = -1

        while(true) {
            if(matrix[y + 1][x] == 0) {
                y++
            } else if(matrix[y + 1][x-1] == 0){
                y++
                x--
            } else if(matrix[y+1][x+1] == 0) {
                y++
                x++
            } else {
                if(x == 500 && y == 0) {
                    return false
                }
                matrix[y][x] = 1
                return true
            }
        }
    }

    fun processInput(input: List<String>) {
        var rocks = mutableListOf<RockStructure>()
        for (row in input) {
            val rockFormations = row
                .split("->")
                .map { it.trim() }
                .map { it.split(",") }
                .map { Pair(it[0].toInt(), it[1].toInt()) }
            rocks.add(RockStructure(rockFormations))
        }

        val matrixX = 50000 //this should be big enough
        val matrixY = rocks.maxOf { it.maxY() } + 3

        matrix = Array(matrixY) { Array(matrixX) { 0 } }

        for (rock in rocks) {
            for (pointIndex in 0 until rock.points.size - 1) {
                val firstRock = rock.points[pointIndex]
                val secondRock = rock.points[pointIndex + 1]
                val maxX = max(firstRock.first, secondRock.first)
                val minX = min(firstRock.first, secondRock.first)
                val maxY = max(firstRock.second, secondRock.second)
                val minY = min(firstRock.second, secondRock.second)
                for (x in minX..maxX) {
                    for (y in minY..maxY) {
                        matrix[y][x] = 1
                    }
                }
            }
        }

        for(x in 0 until matrixX) {
            matrix[matrixY - 1][x] = 1 //install the floor
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val maze = MazePart1()
        maze.processInput(input)

        return maze.runSimulation()
    }

    fun part2(input: List<String>): Int {
        val maze = MazePart2()
        maze.processInput(input)

        return maze.runSimulation()
    }

    val testInput = readInput("Day14","Day14_test")
    println(part1(testInput))
    check(part1(testInput) == 24)
    println(part2(testInput))
    check(part2(testInput) == 93)

    val input = readInput("Day14","Day14")
    println(part1(input))
    println(part2(input))
}
