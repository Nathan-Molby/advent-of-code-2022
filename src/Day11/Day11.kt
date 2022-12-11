package Day11

import readInput
import java.math.BigInteger

class Item(var worry: BigInteger)
class Monkey(val id: Int, var items: MutableList<Item>, val operation: (BigInteger) -> BigInteger, val test: (BigInteger) -> Boolean, val divisor: BigInteger) {
    var testFalseMoney: Monkey? = null
    var testTrueMonkey: Monkey? = null
    var inspections: BigInteger = BigInteger.ZERO
    var divisors = listOf<BigInteger>()
    fun addTestMonkeys(falseMonkey: Monkey, trueMonkey: Monkey) {
        testFalseMoney = falseMonkey
        testTrueMonkey = trueMonkey
    }

    fun addItem(item: Item) {
        items.add(item)
    }

    fun takeTurn(divideByThree: Boolean) {
        for (item in items) {
            inspections++

            item.worry = operation(item.worry)

            if(divideByThree) {
                item.worry /= BigInteger.valueOf(3)
            }

            item.worry %= divisors.reduce { x, y -> x * y }

            if (test(item.worry)) {
                testTrueMonkey!!.addItem(item)
            } else {
                testFalseMoney!!.addItem(item)
            }
        }

        items = mutableListOf() // I'm sure I won't regret this
    }
}
class MonkeyKeepAway(val divideByThree: Boolean) {
    var monkeys = mutableListOf<Monkey>()

    fun processInput(input: List<String>) {
        val monkeyStrings = input
            .withIndex()
            .groupBy { it.index / 7 }
            .map { it.value.map { it.value }.dropLast(1) }

        for(monkeyString in monkeyStrings) {
            processMonkeyStrings(monkeyString)
        }

        for(monkeyString in monkeyStrings) {
            hookUpMonkeyStrings(monkeyString)
        }
    }

    fun runRounds(roundCount: Int) {
        for (i in 0 until roundCount) {
            for (monkey in monkeys) {
                monkey.takeTurn(divideByThree)
            }
        }
    }

    fun hookUpMonkeyStrings(monkeyStrings: List<String>) {
        val monkeyString = monkeyStrings[0].trim()
        val monkeyIndex = monkeyString.split(" ").last().dropLast(1).toInt()
        val trueMonkeyString = monkeyStrings[4].trim()
        val falseMonkeyString = monkeyStrings[5].trim()
        val trueMonkeyIndex = trueMonkeyString.split(" ").last().toInt()
        val falseMonkeyIndex = falseMonkeyString.split(" ").last().toInt()

        monkeys[monkeyIndex].addTestMonkeys(monkeys[falseMonkeyIndex], monkeys[trueMonkeyIndex])
        monkeys[monkeyIndex].divisors = monkeys.map { it.divisor }
    }

    fun processMonkeyStrings(monkeyStrings: List<String>) {
        val monkeyString = monkeyStrings[0].trim()
        val itemsString = monkeyStrings[1].trim()
        val operationString = monkeyStrings[2].trim()
        val testString = monkeyStrings[3].trim()

        val monkeyIndex = monkeyString.split(" ").last().dropLast(1).toInt()
        val items = itemsString.split(" ").drop(2)
            .map { it.removeSuffix(",").toBigInteger() }
            .map { Item(it) }
            .toMutableList()
        val operation = operationString.split(" ").reversed()[1]
        val operandString = operationString.split(" ").last()

        val operationFunction: (BigInteger) -> BigInteger = {
            val operand = if (operandString == "old")  it else operandString.toBigInteger()
            when(operation) {
                "*" -> it * operand
                "+" -> it + operand
                else -> it
            }
        }

        val testDivisor = testString.split(" ").last().toBigInteger()
        val testFunction: (BigInteger) -> Boolean = {
            it % testDivisor == BigInteger.ZERO
        }

        val newMonkey = Monkey(monkeyIndex, items, operationFunction, testFunction, testDivisor)
        monkeys.add(newMonkey)
    }

}

fun main() {
    fun part1(input: List<String>): BigInteger {
        val keepAway = MonkeyKeepAway(true)
        keepAway.processInput(input)
        keepAway.runRounds(20)

        val sortedMonkeys = keepAway.monkeys.sortedByDescending { it.inspections }
        return sortedMonkeys[0].inspections * sortedMonkeys[1].inspections
    }

    fun part2(input: List<String>): BigInteger {
        val keepAway = MonkeyKeepAway(false)
        keepAway.processInput(input)
        keepAway.runRounds(10000)

        val sortedMonkeys = keepAway.monkeys.sortedByDescending { it.inspections }
        return sortedMonkeys[0].inspections * sortedMonkeys[1].inspections
    }



    val testInput = readInput("Day11","Day11_test")
//    println(part1(testInput))
//    check(part1(testInput) == BigInteger.valueOf(10605))
    println(part2(testInput))
//    check(part2(testInput) == BigInteger.valueOf(2713310158))

    val input = readInput("Day11","Day11")
//    println(part1(input))
    println(part2(input))
}
