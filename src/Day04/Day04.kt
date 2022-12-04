package Day04

import readInput
import java.lang.Error
import java.util.BitSet

fun fullContained(leftPair: Pair<Int, Int>, rightPair: Pair<Int, Int>): Int {
    if (leftPair.first >= rightPair.first && leftPair.second <= rightPair.second) {
        return 1
    } else if (rightPair.first >= leftPair.first && rightPair.second <= leftPair.second) {
        return 1
    }

    return 0
}

fun partialOverlap(leftPair: Pair<Int, Int>, rightPair: Pair<Int, Int>): Int {
    if (leftPair.first >= rightPair.first && leftPair.first <= rightPair.second) {
        return 1
    } else if (leftPair.second >= rightPair.first && leftPair.first <= rightPair.second) {
        return 1
    } else if (rightPair.first >= leftPair.first && rightPair.first <= leftPair.second) {
        return 1
    } else if (rightPair.second >= leftPair.first && rightPair.second <= leftPair.second) {
        return 1
    }
    return 0
}

fun rowToPairs(row: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val splitRow = row.split(",")
    val leftSide = splitRow[0].split("-")
    val rightSide = splitRow[1].split("-")
    return Pair(Pair(leftSide[0].toInt(), leftSide[1].toInt()), Pair(rightSide[0].toInt(), rightSide[1].toInt()))
}
fun main() {
    fun part1(input: List<String>): Int {
        var total = 0

        for(row in input) {
            val pairs = rowToPairs(row)
            total += fullContained(pairs.first, pairs.second)
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0

        for(row in input) {
            val pairs = rowToPairs(row)
            total += partialOverlap(pairs.first, pairs.second)
        }
        return total
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04","Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04","Day04")
    println(part1(input))
    println(part2(input))
}
