package Day05

import readInput
import java.util.*

fun applyMovesToTowers(towers: List<Stack<Char>>, moves: List<String>, smartCrane: Boolean) {
    for (move in moves) {
        val moveItems = move.split("move ", " from ", " to ").filter {!it.isEmpty()}.map { it.toInt() }
        val itemCountToMove = moveItems[0]
        val fromTower = moveItems[1] - 1
        val toTower = moveItems[2] - 1

        if (smartCrane) {
            var itemsToMove = mutableListOf<Char>()
            for (i in 1..itemCountToMove) {
                itemsToMove.add(towers[fromTower].pop())
            }
            itemsToMove.reverse()
            for(item in itemsToMove) {
                towers[toTower].add(item)
            }
        } else {
            for (i in 1..itemCountToMove) {
                val itemToMove = towers[fromTower].pop()
                towers[toTower].add(itemToMove)
            }
        }
    }
}

fun getTowers(input: List<String>): List<Stack<Char>> {
    val numOfCols = input.last().split(" ").filter { !it.isBlank() }.count()
    val actualRows = input.dropLast(1)

    var resultList = (0..numOfCols - 1).map { Stack<Char>() }
    for (row in actualRows.reversed()) {
        for (col in 0..numOfCols - 1) {
            val stringIndex = colIndexToStringIndex(col)
            if (row.length >= stringIndex && row[stringIndex].isLetter()) {
                resultList[col].add(row[stringIndex])
            }
        }
    }

    return resultList
}

fun getTopElementsFromTowers(towers: List<Stack<Char>>): String {
    return towers.map { it.peek() }.joinToString(separator = "")
}

fun colIndexToStringIndex(colIndex: Int): Int {
    return colIndex * 4 + 1
}
fun main() {
    fun part1(input: List<String>): String {
        val splitIndex = input.indexOf("")
        val drawing = input.subList(0, splitIndex)
        val moves = input.subList(splitIndex + 1, input.size)

        val towers = getTowers(drawing)
        applyMovesToTowers(towers, moves, false)

        return getTopElementsFromTowers(towers)
    }

    fun part2(input: List<String>): String {
        val splitIndex = input.indexOf("")
        val drawing = input.subList(0, splitIndex)
        val moves = input.subList(splitIndex + 1, input.size)

        val towers = getTowers(drawing)
        applyMovesToTowers(towers, moves, true)

        return getTopElementsFromTowers(towers)
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05","Day05_test")
    println(part1(testInput))
    check(part1(testInput) == "CMZ")
    println(part2(testInput))
    check(part2(testInput) == "MCD")

    val input = readInput("Day05","Day05")
    println(part1(input))
    println(part2(input))
}
