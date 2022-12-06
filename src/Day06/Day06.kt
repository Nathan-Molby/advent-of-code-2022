package Day06

import readInput
import java.util.*

fun firstUniqueIndex(input: String, sizeOfmarker: Int): Int {
    var elementsQueue = LinkedList<Char>()
    var elementsDictionary = mutableMapOf<Char, Int>()
    var duplicateElementsCount = 0
    var index = 0

    for (char in input) {
        elementsQueue.add(char)

        var newCharsInDict = (elementsDictionary[char] ?: 0) + 1
        elementsDictionary[char] = newCharsInDict
        if(newCharsInDict == 2) {
            duplicateElementsCount += 1
        }


        if (index >= sizeOfmarker) {
            val elementToRemove = elementsQueue.remove()
            val newElementCount = elementsDictionary[elementToRemove]!! - 1

            if(newElementCount == 1) {
                duplicateElementsCount -= 1
                if (duplicateElementsCount == 0) {
                    return index + 1
                }
            }

            elementsDictionary[elementToRemove] = newElementCount
        }

        index++
    }

    return -1
}

fun main() {
    fun part1(input: List<String>): Int {
        return firstUniqueIndex(input[0], 4)
    }

    fun part2(input: List<String>): Int {
        return firstUniqueIndex(input[0], 14)
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06","Day06_test")
    println(part1(testInput))
    check(part1(testInput) == 11)
    println(part2(testInput))
    check(part2(testInput) == 26)

    val input = readInput("Day06","Day06")
    println(part1(input))
    println(part2(input))
}
