package Day10
import kotlin.math.*

import readInput
import java.util.Dictionary

class CPU() {
    var cycleNum = 1
    var x = 1
    var part1Result = 0


    fun part1(input: List<String>): Int {
        for (line in input) {
            processLine(line)
        }

        return part1Result
    }

    fun part2(input: List<String>) {
        for (line in input) {
            processLine(line)
        }
    }

    fun processLine(line: String) {
        val lineSplit = line.split(" ")
        when(lineSplit[0]) {
            "noop" -> noop()
            "addx" -> addx(lineSplit[1].toInt())
        }
    }

    fun noop() {
        checkPart1()
        part2()
        cycleNum++
    }

    fun addx(numToAdd: Int) {
        checkPart1()
        part2()
        cycleNum++
        checkPart1()
        part2()
        cycleNum++
        x += numToAdd
    }

    fun checkPart1() {
        if ((cycleNum - 20) % 40 == 0) {
            part1Result += cycleNum * x
        }
    }

    fun part2() {
        if ((cycleNum - 1) % 40 in (x - 1) % 40 .. (x + 1) % 40) {
            print("#")
        } else {
            print(".")
        }

        if (cycleNum % 40 == 0) {
            println()
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val cpu = CPU()
        return cpu.part1(input)
    }

    fun part2(input: List<String>) {
        val cpu = CPU()
        cpu.part2(input)
    }



//    val testInput = readInput("Day10","Day10_test")
//    println(part1(testInput))
//    check(part1(testInput) == 13140)
//    println(part2(testInput))
//    check(part2(testInput) == 36)

    val input = readInput("Day10","Day10")
//    println(part1(input))
    part2(input)
}
