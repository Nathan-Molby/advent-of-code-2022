package Day09
import kotlin.math.*

import readInput

class RopeMover(val ropeSize: Int) {
    var rope = MutableList(ropeSize) { _ -> Pair(0, 0) }

    var visitedLocations = hashSetOf(Pair(0, 0))

    fun processInput(input: List<String>) {
        for (row in input) {
            processRow(row)
        }
    }

    private fun processRow(row: String) {
        val rowElements = row.split(" ")
        val countToMove = rowElements[1].toInt()
        val movement = when(rowElements[0]) {
            "R" -> Pair(1, 0)
            "L" -> Pair(-1, 0)
            "U" -> Pair(0, 1)
            "D" -> Pair(0, -1)
            else -> Pair(0, 0)
        }
        for (i in 0 until countToMove) {
            moveHead(movement)
            moveRestOfRope()
        }
    }

    private fun moveHead(movement: Pair<Int, Int>) {
        rope[0] += movement
    }

    private fun moveRestOfRope() {
        for (index in 1 until rope.size) {
            moveTailPartAt(index)
        }
    }

    private fun moveTailPartAt(index: Int) {
        val difference = rope[index - 1] - rope[index]

        var xMovement = when (difference.first) {
            in -Int.MAX_VALUE..-2 -> -1
            in 2..Int.MAX_VALUE -> 1
            else -> 0
        }

        var yMovement = when (difference.second) {
            in -Int.MAX_VALUE..-2 -> -1
            in 2..Int.MAX_VALUE -> 1
            else -> 0
        }

        if (xMovement != 0 && difference.second != 0) {
            yMovement = max(-1, min(difference.second, 1))
        } else if (yMovement != 0 && difference.first != 0) {
            xMovement = max(-1, min(difference.first, 1))
        }

        rope[index] += Pair(xMovement, yMovement)
        if (index == rope.size - 1) {
            visitedLocations.add(rope[index])
        }
    }
}

operator fun Pair<Int, Int>.plus(pair: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(first + pair.first, second + pair.second)
}

operator fun Pair<Int, Int>.minus(pair: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(first - pair.first, second - pair.second)
}

fun main() {
    fun part1(input: List<String>): Int {
        val ropeMover = RopeMover(2)
        ropeMover.processInput(input)
        return ropeMover.visitedLocations.count()
    }

    fun part2(input: List<String>): Int {
        val ropeMover = RopeMover(10)
        ropeMover.processInput(input)
        return ropeMover.visitedLocations.count()
    }



    val testInput = readInput("Day09","Day09_test")
//    println(part1(testInput))
//    check(part1(testInput) == 13)
    println(part2(testInput))
    check(part2(testInput) == 36)

    val input = readInput("Day09","Day09")
    println(part1(input))
    println(part2(input))
}
