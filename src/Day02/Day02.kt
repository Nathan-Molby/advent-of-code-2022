package Day02

import readInput

enum class RPS {
    ROCK, PAPER, SCISSORS;

    fun kryptonite(inputRPS: RPS): RPS {
        return when(inputRPS) {
            RPS.ROCK -> RPS.PAPER
            RPS.PAPER -> RPS.SCISSORS
            RPS.SCISSORS -> RPS.ROCK
        }
    }

    fun archNemesis(inputRPS: RPS): RPS {
        return when(inputRPS) {
            RPS.ROCK -> RPS.SCISSORS
            RPS.PAPER -> RPS.ROCK
            RPS.SCISSORS -> RPS.PAPER
        }
    }
}

class RPSRound(playerInput: String, opponentInput: String) {
    var playerInput: RPS = rpsFromString(playerInput)
    val opponentInput: RPS = rpsFromString(opponentInput)

    fun calculatePlayerScore(): Int {
        var playerScore = 0

        //calculate shape score
        playerScore += when(playerInput) {
            RPS.ROCK -> 1
            RPS.PAPER -> 2
            RPS.SCISSORS -> 3
        }

        //calculate outcome score
        playerScore += when(opponentInput) {
            playerInput.archNemesis(playerInput) -> 6
            playerInput.kryptonite(playerInput) -> 0
            else -> 3
        }

        return playerScore
    }
}

fun rpsFromString(inputString: String): RPS {
    return when(inputString) {
        "A", "X" -> RPS.ROCK
        "B", "Y" -> RPS.PAPER
        "C", "Z" -> RPS.SCISSORS
        else -> RPS.ROCK
    }
}
fun main() {
    fun part1(input: List<String>): Int {
        var rounds = mutableListOf<RPSRound>()

        for (row in input) {
            val rowValues = row.split(" ")
            rounds.add(RPSRound(rowValues[1], rowValues[0]))
        }

        return rounds.map { it.calculatePlayerScore() }.sum()
    }

    fun part2(input: List<String>): Int {
        var rounds = mutableListOf<RPSRound>()

        for(row in input) {
            val rowValues = row.split(" ")

            var currentRound = RPSRound(rowValues[0], rowValues[0])
            currentRound.playerInput = when(rowValues[1]) {
                "X" -> currentRound.opponentInput.archNemesis(currentRound.opponentInput)
                "Y" -> currentRound.opponentInput
                "Z" -> currentRound.opponentInput.kryptonite(currentRound.opponentInput)
                else -> RPS.ROCK
            }

            rounds.add(currentRound)
        }

        return rounds.map { it.calculatePlayerScore() }.sum()
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02","Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02","Day02")
    println(part1(input))
    println(part2(input))
}
