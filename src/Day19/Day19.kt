package Day19

import readInput
import java.util.Objects
import java.util.PriorityQueue
import kotlin.math.*
data class CombinedCost(val oreCost: Int, val clayCost: Int, val obsidianCost: Int)
data class Blueprint(val oreRobotCost: Int, val clayRobotCost: Int, val obsidianRobotCost: CombinedCost, val geodeRobotCost: CombinedCost) {
    var maxGeodesOpened = 0
    var statesSeen = hashMapOf<State, Int>()
}

data class State(var minute: Int,
                 var blueprint: Blueprint,
                 var oreRobotCount: Int,
                 var clayRobotCount: Int,
                 var obsidianRobotCount: Int,
                 var geodeRobotCount: Int,
                 var oreCount: Int,
                 var clayCount: Int,
                 var obsidianCount: Int,
                 var geodeCount: Int): Comparable<State> {

    override fun compareTo(other: State) = other.minute.compareTo(minute)

    override fun hashCode(): Int {
        return Objects.hash(minute, oreRobotCount, clayRobotCount, obsidianRobotCount, geodeRobotCount, oreCount, clayCount, obsidianCount, geodeCount)
    }
}
class RobotOptimizer(val maxTime: Int) {
    var blueprints = mutableListOf<Blueprint>()
    var areActionsPossible = hashMapOf<Action, Boolean>(
        Action.NOTHING to true,
        Action.CREATE_ORE_ROBOT to false,
        Action.CREATE_CLAY_ROBOT to false,
        Action.CREATE_OBSIDIAN_ROBOT to false,
        Action.CREATE_GEODE_ROBOT to false
    )

    enum class Action {
        CREATE_ORE_ROBOT, CREATE_CLAY_ROBOT, CREATE_OBSIDIAN_ROBOT, CREATE_GEODE_ROBOT, NOTHING
    }



    fun readInput(input: List<String>) {
        for (row in input) {
            val robotStrings = row.split(".").map { it.split(" ") }
            val oreRobotCost = robotStrings[0][6].toInt()
            val clayRobotCost = robotStrings[1][5].toInt()
            val obsidianOreCost = robotStrings[2][5].toInt()
            val obsidianClayCost = robotStrings[2][8].toInt()
            val geodeOreCost = robotStrings[3][5].toInt()
            val geodeClayCost = robotStrings[3][8].toInt()
            blueprints.add(Blueprint(oreRobotCost, clayRobotCost, CombinedCost(obsidianOreCost, obsidianClayCost, 0), CombinedCost(geodeOreCost, 0, geodeClayCost)))
        }
    }

    fun findMaxGeodes() {
//        blueprints.parallelStream().forEach { blueprint ->
        for (blueprint in blueprints) {
            findMaxGeodes(blueprint)
            println("blueprint done: " + blueprint.maxGeodesOpened.toString())
        }
    }

    fun findMaxGeodes(blueprint: Blueprint) {
        val initialState = State(1, blueprint, 1, 0, 0, 0, 0, 0, 0, 0)
        val result = findMaxGeodesNotRecursive(initialState)
        blueprint.maxGeodesOpened = result
    }

    fun findMaxGeodesNotRecursive(initialState: State): Int {
        var queue = PriorityQueue<State>()
        queue.add(initialState)

        while (queue.isNotEmpty()) {
            val state = queue.remove()

            if (state.blueprint.statesSeen.contains(state)) {
                continue
            }

            val timeRemaining = maxTime - state.minute
            val maxPossibleGeodes = state.geodeCount + state.geodeRobotCount * (timeRemaining + 1) + (timeRemaining.toDouble() / 2) * (1 + timeRemaining)

            if(state.blueprint.maxGeodesOpened > maxPossibleGeodes) {
                continue
            }

            if (state.minute > maxTime) {
                state.blueprint.maxGeodesOpened = max(state.blueprint.maxGeodesOpened, state.geodeCount)
                continue
            }

            areActionsPossible[Action.CREATE_GEODE_ROBOT] = false
            areActionsPossible[Action.CREATE_CLAY_ROBOT] = false
            areActionsPossible[Action.CREATE_ORE_ROBOT] = false
            areActionsPossible[Action.CREATE_OBSIDIAN_ROBOT] = false

            val maxOreRequiredPerMinute = max(max(state.blueprint.geodeRobotCost.oreCost, state.blueprint.obsidianRobotCost.oreCost), state.blueprint.clayRobotCost)
            val maxClayRequiredPerMinute = state.blueprint.obsidianRobotCost.clayCost
            val maxObsidianRequiredPerMinute = state.blueprint.geodeRobotCost.obsidianCost

            if (state.oreCount >= state.blueprint.oreRobotCost
                && timeRemaining > state.blueprint.geodeRobotCost.oreCost
                && state.oreRobotCount < maxOreRequiredPerMinute) {
                areActionsPossible[Action.CREATE_ORE_ROBOT] = true
            }
            if (state.oreCount >= state.blueprint.clayRobotCost
                && state.clayRobotCount < maxClayRequiredPerMinute) {
                areActionsPossible[Action.CREATE_CLAY_ROBOT] = true
            }
            if (state.oreCount >= state.blueprint.obsidianRobotCost.oreCost
                && state.clayCount >= state.blueprint.obsidianRobotCost.clayCost
                && state.obsidianRobotCount < maxObsidianRequiredPerMinute) {
                areActionsPossible[Action.CREATE_OBSIDIAN_ROBOT] = true
            }
            if (state.oreCount >= state.blueprint.geodeRobotCost.oreCost && state.obsidianCount >= state.blueprint.geodeRobotCost.obsidianCost) {
                areActionsPossible[Action.CREATE_GEODE_ROBOT] = true
            }

            for (action_possible in areActionsPossible) {
                if( !action_possible.value ) { continue }
                val action = action_possible.key
                var newState = state.copy(minute = state.minute + 1,
                    oreCount = state.oreCount + state.oreRobotCount,
                    clayCount = state.clayCount + state.clayRobotCount,
                    obsidianCount = state.obsidianCount + state.obsidianRobotCount,
                    geodeCount = state.geodeCount + state.geodeRobotCount
                )

                when (action) {
                    Action.NOTHING -> {}
                    Action.CREATE_ORE_ROBOT -> {
                        newState.oreCount -= state.blueprint.oreRobotCost
                        newState.oreRobotCount++
                    }
                    Action.CREATE_CLAY_ROBOT -> {
                        newState.oreCount -= state.blueprint.clayRobotCost
                        newState.clayRobotCount++
                    }
                    Action.CREATE_OBSIDIAN_ROBOT -> {
                        newState.oreCount -= state.blueprint.obsidianRobotCost.oreCost
                        newState.clayCount -= state.blueprint.obsidianRobotCost.clayCost
                        newState.obsidianRobotCount++
                    }
                    Action.CREATE_GEODE_ROBOT -> {
                        newState.oreCount -= state.blueprint.geodeRobotCost.oreCost
                        newState.obsidianCount -= state.blueprint.geodeRobotCost.obsidianCost
                        newState.geodeRobotCount++
                    }
                }
                queue.add(newState)
            }

            state.blueprint.statesSeen[state] = 1
        }

        return initialState.blueprint.maxGeodesOpened
    }
    fun findMaxGeodesRecursive(state: State): Int {
        if (state.minute > maxTime) {
            return state.geodeCount
        }

        if (state.blueprint.statesSeen.contains(state)) {
            return state.blueprint.statesSeen[state]!!
        }

        areActionsPossible[Action.CREATE_GEODE_ROBOT] = false
        areActionsPossible[Action.CREATE_CLAY_ROBOT] = false
        areActionsPossible[Action.CREATE_ORE_ROBOT] = false
        areActionsPossible[Action.CREATE_OBSIDIAN_ROBOT] = false

        val timeRemaining = maxTime - state.minute
        val maxOreRequiredPerMinute = max(max(state.blueprint.geodeRobotCost.oreCost, state.blueprint.obsidianRobotCost.oreCost), state.blueprint.clayRobotCost)
        val maxClayRequiredPerMinute = state.blueprint.obsidianRobotCost.clayCost
        val maxObsidianRequiredPerMinute = state.blueprint.geodeRobotCost.obsidianCost

        if (state.oreCount >= state.blueprint.oreRobotCost
            && timeRemaining > state.blueprint.geodeRobotCost.oreCost
            && state.oreRobotCount < maxOreRequiredPerMinute) {
            areActionsPossible[Action.CREATE_ORE_ROBOT] = true
        }
        if (state.oreCount >= state.blueprint.clayRobotCost
            && state.clayRobotCount < maxClayRequiredPerMinute) {
            areActionsPossible[Action.CREATE_CLAY_ROBOT] = true
        }
        if (state.oreCount >= state.blueprint.obsidianRobotCost.oreCost
            && state.clayCount >= state.blueprint.obsidianRobotCost.clayCost
            && state.obsidianRobotCount < maxObsidianRequiredPerMinute) {
            areActionsPossible[Action.CREATE_OBSIDIAN_ROBOT] = true
        }
        if (state.oreCount >= state.blueprint.geodeRobotCost.oreCost && state.obsidianCount >= state.blueprint.geodeRobotCost.obsidianCost) {
            areActionsPossible[Action.CREATE_GEODE_ROBOT] = true
        }

        var maxGeodesFromThisState = 0

        for (action_possible in areActionsPossible) {
            if( !action_possible.value ) { continue }
            val action = action_possible.key
//        possibleActions.parallelStream().forEach { action ->
            var newState = state.copy(minute = state.minute + 1,
                oreCount = state.oreCount + state.oreRobotCount,
                clayCount = state.clayCount + state.clayRobotCount,
                obsidianCount = state.obsidianCount + state.obsidianRobotCount,
                geodeCount = state.geodeCount + state.geodeRobotCount
            )

            when (action) {
                Action.NOTHING -> {}
                Action.CREATE_ORE_ROBOT -> {
                    newState.oreCount -= state.blueprint.oreRobotCost
                    newState.oreRobotCount++
                }
                Action.CREATE_CLAY_ROBOT -> {
                    newState.oreCount -= state.blueprint.clayRobotCost
                    newState.clayRobotCount++
                }
                Action.CREATE_OBSIDIAN_ROBOT -> {
                    newState.oreCount -= state.blueprint.obsidianRobotCost.oreCost
                    newState.clayCount -= state.blueprint.obsidianRobotCost.clayCost
                    newState.obsidianRobotCount++
                }
                Action.CREATE_GEODE_ROBOT -> {
                    newState.oreCount -= state.blueprint.geodeRobotCost.oreCost
                    newState.obsidianCount -= state.blueprint.geodeRobotCost.obsidianCost
                    newState.geodeRobotCount++
                }
            }

            maxGeodesFromThisState = max(maxGeodesFromThisState, findMaxGeodesRecursive(newState))
        }
        state.blueprint.statesSeen[state] = maxGeodesFromThisState
        state.blueprint.maxGeodesOpened = max(maxGeodesFromThisState, state.blueprint.maxGeodesOpened)
        return maxGeodesFromThisState
    }

    fun part1(): Int {
        return blueprints.withIndex()
            .sumOf { (it.index + 1) * it.value.maxGeodesOpened  }
    }

    fun part2(): Int {
        return blueprints[0].maxGeodesOpened * blueprints[1].maxGeodesOpened * blueprints[2].maxGeodesOpened
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val robotOptimizer = RobotOptimizer(24)
        robotOptimizer.readInput(input)
        robotOptimizer.findMaxGeodes()
        return robotOptimizer.part1()
    }

    fun part2(input: List<String>): Int {
        val robotOptimizer = RobotOptimizer(32)
        robotOptimizer.readInput(input)
        robotOptimizer.findMaxGeodes()
        return robotOptimizer.part2()
    }

    val testInput = readInput("Day19","Day19_test")
//    println(part1(testInput))
//    check(part1(testInput) == 64)
//    println(part2(testInput))
//    check(part2(testInput) == 58)

    val input = readInput("Day19","Day19")
//    println(part1(input))
    println(part2(input))
}
