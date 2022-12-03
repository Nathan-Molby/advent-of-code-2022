package Day03

import readInput
import java.lang.Error
import java.util.BitSet


fun charToInt(char: Char): Int {
    val asciiValue = char.code
    return when(asciiValue) {
        in 65..90 -> asciiValue - 38
        in 97..122 -> asciiValue - 96
        else -> 0
    }
}

fun findCommonInt(leftInput: List<Int>, rightInput: List<Int>) : Int {
    val leftInputBitArray = BitSet(64)
    val rightInputBitarray = BitSet(64)

    for ((leftValue, rightValue) in leftInput.zip(rightInput)) {
        leftInputBitArray.set(leftValue)
        rightInputBitarray.set(rightValue)
        if (rightInputBitarray[leftValue]) {
            return leftValue
        } else if (leftInputBitArray[rightValue]) {
            return rightValue
        }
    }
    throw Error()
}

fun findCommonInt(inputs: List<List<Int>>): Int {
    var bitArrays = mutableListOf(BitSet(64), BitSet(64), BitSet(64))

    var index = 0
    for (inputList in inputs) {
        for (input in inputList) {
            bitArrays[index].set(input)
        }
        index++
    }
    bitArrays[0].and(bitArrays[1])
    bitArrays[0].and(bitArrays[2])
    return bitArrays[0].nextSetBit(0)
}
fun main() {
    fun part1(input: List<String>): Int {
        var total: Int = 0
        for(line in input) {
            val leftHalf = line.subSequence(0, line.length / 2)
            val rightHalf = line.subSequence(line.length / 2, line.length)
            total += findCommonInt(leftHalf.map(::charToInt), rightHalf.map(::charToInt))
        }

        return total
    }

    fun part2(input: List<String>): Int {
        var total: Int = 0
        for(lineIndex in 0..input.size - 1 step 3) {
            val inputStrings = input.subList(lineIndex, lineIndex + 3)
            total += findCommonInt(inputStrings.map{it.map(::charToInt)})
        }
        return total
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03","Day03_test")
    check(part1(testInput) == 157)

    val input = readInput("Day03","Day03")
    println(part1(input))
    println(part2(input))
}
