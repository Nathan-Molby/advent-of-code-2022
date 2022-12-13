package Day13

import readInput
import java.util.PriorityQueue
import kotlin.math.*

class Index(var index: Int = 0)

fun main() {
    fun processInput(input: List<String>): List<List<String>> {
         return input
            .withIndex()
            .groupBy { it.index / 3 }
            .map { it.value.map { it.value }.dropLast(1) }
    }

    fun processSignal(input: String, index: Index) : List<Any> {
        var list = mutableListOf<Any>()

        while(index.index < input.count()) {
            var char = input[index.index]
            index.index++
            when (char) {
                '[' -> list.add(processSignal(input, index))
                ',' -> continue
                ']' -> return list
                else -> {
                    val startIndex = index.index - 1
                    while(input[index.index].isDigit()) {
                        index.index++
                    }
                    list.add(input.subSequence(startIndex, index.index).toString().toInt())
                }
            }
        }

        return list
    }

    fun processPair(input: List<String>): Pair<List<Any>, List<Any>> {
        return Pair(processSignal(input[0], Index()), processSignal(input[1], Index()))
    }

    fun isPairInRightOrderHelper(left: List<Any>, right: List<Any>): Boolean? {
        for (i in 0 until min(left.size, right.size)) {
            if (left[i] is Int && right[i] is Int) {
                val leftInt = left[i] as Int
                val rightInt = right[i] as Int

                if(leftInt < rightInt) return true
                if (leftInt > rightInt) return false
            }
            else if(left[i] is List<*> && right[i] is List<*>) {
                val arrayResult = isPairInRightOrderHelper(left[i] as List<Any>, right[i] as List<Any>)
                if (arrayResult != null) return arrayResult
            }
            else if(left[i] is Int) {
                if(!(right[i] is List<*>)) print("SOMETHING WENT VERY WRONG")
                val arrayResult = isPairInRightOrderHelper(listOf(left[i]), right[i] as List<Any>)
                if (arrayResult != null) return arrayResult
            }
            else if(right[i] is Int) {
                if(!(left[i] is List<*>)) print("SOMETHING WENT VERY WRONG")
                val arrayResult = isPairInRightOrderHelper(left[i] as List<Any>, listOf(right[i]))
                if (arrayResult != null) return arrayResult
            } else {
                print("SOMETHING WENT VERY WRONG")
            }
        }

        if(left.size < right.size) {
            return true
        } else if (left.size > right.size) {
            return false
        }

        return null
    }

    fun isPairInRightOrder(pair: Pair<List<Any>, List<Any>>): Boolean {
        return isPairInRightOrderHelper(pair.first, pair.second)!!
    }

    val myCustomComparator =  Comparator<List<Any>> { a, b ->
        val result = isPairInRightOrderHelper(a, b)
        if(result == null)  0
        else if (result) 1
        else -1
    }

    fun part1(input: List<String>): Int {
        val pairStrings = processInput(input)
        val pairs = pairStrings.map { processPair(it) }


        return pairs.withIndex().sumOf { if (isPairInRightOrder(it.value)) it.index + 1 else 0  }
    }

    fun part2(input: List<String>): Int {
        var signals = input.filter { !it.isEmpty() }.map { processSignal(it, Index()) }.toMutableList()
        signals.add(listOf(listOf(2)))
        signals.add(listOf(listOf(6)))
        val sortedSignals = signals.sortedWith(myCustomComparator).reversed()

        val result = sortedSignals.withIndex().filter { !(it.value is ArrayList<*>) }

        return (result[0].index + 1) * (result[1].index + 1)
    }

    val testInput = readInput("Day13","Day13_test")
    println(part1(testInput))
    check(part1(testInput) == 13)
    println(part2(testInput))
    check(part2(testInput) == 140)

    val input = readInput("Day13","Day13")
    println(part1(input))
    println(part2(input))
}
