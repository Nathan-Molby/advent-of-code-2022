package Day01

import readInput

fun main() {
    class Elf(var calories: MutableList<Int>) {
        fun addCalorie(calorie: Int) {
            calories.add(calorie)
        }
        fun totalCalories() = calories.sum()
    }

    fun readElves(input: List<String>): MutableList<Elf> {
        var elves = mutableListOf<Elf>()
        var mostRecentElf = Elf(arrayListOf())
        for (row in input) {
            if (row.isBlank() && mostRecentElf.calories.isNotEmpty()) {
                elves.add(mostRecentElf)
                mostRecentElf = Elf(arrayListOf())
            } else {
                val calorieInt = row.toInt()
                mostRecentElf.addCalorie(calorieInt)
            }
        }
        elves.add(mostRecentElf)


        return elves
    }

    fun part1(input: List<String>): Int {
        val elves = readElves(input)

        return elves.maxOf { it.totalCalories() }
    }

    fun part2(input: List<String>): Int {
        val elves = readElves(input)
        val sortedElves = elves.sortedByDescending { it.totalCalories() }
        val topThreeElves = sortedElves.subList(0, 3)
        return topThreeElves.sumOf { it.totalCalories() }
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01","Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01","Day01")
    println(part1(input))
    println(part2(input))
}
