package Day16

import readInput
import java.util.Dictionary
import java.util.PriorityQueue
import kotlin.math.*

class Valve (val id: String, val flowRate: Int) {
    var connectedValves = mutableListOf<Valve>()

    fun addConnectedValves(vararg valves: Valve) {
        connectedValves.addAll(valves)
    }
}

class Node (val valve: Valve): Comparable<Node> {
    var distance = Int.MAX_VALUE - 1
    var prev: Node? = null

    override fun compareTo(other: Node) = compareValuesBy(this, other, {it.distance}, {it.distance})
}
class ValveSimulator {
    var shortestDistanceBetweenPointedVales: HashMap<Valve, List<Pair<Int, Valve>>> = hashMapOf()
    var allValves = hashMapOf<String, Valve>()

    fun readInput(input: List<String>) {
        for (row in input) {
            val rowSplit = row.split(" ")
            val id = rowSplit[1]
            val flowRateString = rowSplit[4]
            val flowRate = flowRateString
                .split("=")[1]
                .trimEnd(';')
                .toInt()

            allValves[id] = Valve(id, flowRate)
        }

        for (row in input) {
            val rowSplit = row.split(" ")
            val id = rowSplit[1]

            for (i in 9 until rowSplit.size) {
                allValves[id]?.addConnectedValves(allValves[rowSplit[i].trimEnd(',')]!!)
            }
        }
    }

    fun preprocessValves() {
        val filteredValves =  allValves
            .filter { it.value.flowRate > 0 || it.value.id == "AA" }
        for (valve in filteredValves) {
            shortestDistanceBetweenPointedVales[valve.value] = runDjikstras(valve.value)
        }
    }

    fun getOptimalRoute(): Int {
        val result =  getOptimalRoute(allValves["AA"]!!, arrayListOf(allValves["AA"]!!),0,  30)
        return result.second
    }

    fun getOptimalRouteWithElephant(): Int {
        val result =  getOptimalRoute(allValves["AA"]!!, allValves["AA"]!!, arrayListOf(allValves["AA"]!!), 0,  26, 26)
        return result.second
    }

    private fun getOptimalRoute(currentValve: Valve, visitedValves: ArrayList<Valve>, currentScore: Int, turnsRemaining: Int): Pair<ArrayList<Valve>, Int> {
        val possibleOtherValves = shortestDistanceBetweenPointedVales[currentValve]!!
            .filter { !visitedValves.contains(it.second) && it.first < turnsRemaining }

        var maxScore = currentScore
        var bestRoute = visitedValves
        for (distance_otherValve in possibleOtherValves) {
            val newSet = ArrayList(visitedValves)
            newSet.add(distance_otherValve.second)
            val turnsRemainingAfterMove = turnsRemaining - distance_otherValve.first - 1
            val newScore = currentScore + turnsRemainingAfterMove * distance_otherValve.second.flowRate

            val optimalRouteResult = getOptimalRoute(distance_otherValve.second, newSet, newScore, turnsRemainingAfterMove)
            if (optimalRouteResult.second > maxScore) {
                bestRoute = optimalRouteResult.first
                maxScore = optimalRouteResult.second
            }
        }

        return Pair(bestRoute, maxScore)
    }

    private fun getOptimalRoute(personCurrentValve: Valve, elephantCurrentValve: Valve, visitedValves: ArrayList<Valve>, currentScore: Int, personTurnsRemaining: Int, elephantTurnsRemaining: Int): Pair<ArrayList<Valve>, Int> {
        var maxScore = currentScore
        var bestRoute = visitedValves

        //process human first
        val possiblePersonValves = shortestDistanceBetweenPointedVales[personCurrentValve]!!
            .filter { !visitedValves.contains(it.second) && it.first < personTurnsRemaining }

        possiblePersonValves.stream().parallel().forEach { person_distance_otherValve ->
            val newSet = ArrayList(visitedValves)
            newSet.add(person_distance_otherValve.second)
            val personTurnsRemainingAfterMove = personTurnsRemaining - person_distance_otherValve.first - 1
            val newScore = currentScore + personTurnsRemainingAfterMove * person_distance_otherValve.second.flowRate

            val possibleElephantValves = shortestDistanceBetweenPointedVales[elephantCurrentValve]!!
                .filter { !newSet.contains(it.second) && it.first < elephantTurnsRemaining }
            //process each elephant move for each human move
            possibleElephantValves.stream().parallel().forEach {elephant_distance_otherValue ->
                val newCombinedSet = ArrayList(newSet)
                newCombinedSet.add(elephant_distance_otherValue.second)

                val elephantTurnsRemainingAfterMove = elephantTurnsRemaining - elephant_distance_otherValue.first - 1
                val combinedNewScore = newScore + elephantTurnsRemainingAfterMove * elephant_distance_otherValue.second.flowRate

                val optimalRouteResult = getOptimalRoute(person_distance_otherValve.second, elephant_distance_otherValue.second, newCombinedSet, combinedNewScore, personTurnsRemainingAfterMove, elephantTurnsRemainingAfterMove)
                if (optimalRouteResult.second > maxScore) {
                    bestRoute = optimalRouteResult.first
                    maxScore = optimalRouteResult.second
                }
            }

            //deal with case where only the person moves
            if (possibleElephantValves.isEmpty()) {
                val optimalRouteResult = getOptimalRoute(person_distance_otherValve.second, elephantCurrentValve, newSet, newScore, personTurnsRemainingAfterMove, elephantTurnsRemaining)
                if (optimalRouteResult.second > maxScore) {
                    bestRoute = optimalRouteResult.first
                    maxScore = optimalRouteResult.second
                }
            }
        }

        //process elephant first
        val possibleElephantValves = shortestDistanceBetweenPointedVales[elephantCurrentValve]!!
            .filter { !visitedValves.contains(it.second) && it.first < elephantTurnsRemaining }

        possibleElephantValves.stream().parallel()
            .forEach {elephant_distance_otherValve ->

            val elephantNewSet = ArrayList(visitedValves)
            elephantNewSet.add(elephant_distance_otherValve.second)
            val elephantTurnsRemainingAfterMove = elephantTurnsRemaining - elephant_distance_otherValve.first - 1
            val newScore = currentScore + elephantTurnsRemainingAfterMove * elephant_distance_otherValve.second.flowRate

            val myPossiblePersonValves = shortestDistanceBetweenPointedVales[personCurrentValve]!!
                .filter { !elephantNewSet.contains(it.second) && it.first < personTurnsRemaining }
            //process each elephant move for each human move
            myPossiblePersonValves.stream().parallel()
                .forEach {person_distance_otherValue ->
                val newCombinedSet = ArrayList(elephantNewSet)
                newCombinedSet.add(person_distance_otherValue.second)

                val personTurnsRemainingAfterMove = personTurnsRemaining - person_distance_otherValue.first - 1
                val combinedNewScore = newScore + personTurnsRemainingAfterMove * person_distance_otherValue.second.flowRate

                val optimalRouteResult = getOptimalRoute(person_distance_otherValue.second, elephant_distance_otherValve.second, newCombinedSet, combinedNewScore, personTurnsRemainingAfterMove, elephantTurnsRemainingAfterMove)
                if (optimalRouteResult.second > maxScore) {
                    bestRoute = optimalRouteResult.first
                    maxScore = optimalRouteResult.second
                }
            }

            //deal with case where only the elephant moves
            if (myPossiblePersonValves.isEmpty()) {
                val optimalRouteResult = getOptimalRoute(personCurrentValve, elephant_distance_otherValve.second, elephantNewSet, newScore, personTurnsRemaining, elephantTurnsRemainingAfterMove)
                if (optimalRouteResult.second > maxScore) {
                    bestRoute = optimalRouteResult.first
                    maxScore = optimalRouteResult.second
                }
            }
        }
        return Pair(bestRoute, maxScore)
    }

    fun runDjikstras(valve: Valve): List<Pair<Int, Valve>> {
        var nodesPriorityQueue = PriorityQueue<Node>()
        var allNodes = allValves
            .map { it.key to Node(it.value) }
            .toMap()
        nodesPriorityQueue.add(allNodes[valve.id])
        allNodes[valve.id]!!.distance = 0

        while (nodesPriorityQueue.isNotEmpty()) {
            val node = nodesPriorityQueue.remove()
            for (neighbor in node.valve.connectedValves) {
                val dist = node.distance + 1

                val neighborNode = allNodes[neighbor.id]!!
                if(dist < neighborNode.distance) {
                    neighborNode.distance = dist
                    neighborNode.prev = node
                    nodesPriorityQueue.remove(neighborNode)
                    nodesPriorityQueue.add(neighborNode)
                }
            }
        }

        return allNodes
            .filter { it.value.valve.flowRate > 0 }
            .map { Pair(it.value.distance, it.value.valve) }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val valveSimulator = ValveSimulator()
        valveSimulator.readInput(input)
        valveSimulator.preprocessValves()

        return valveSimulator.getOptimalRoute()
    }

    fun part2(input: List<String>): Int {
        val valveSimulator = ValveSimulator()
        valveSimulator.readInput(input)
        valveSimulator.preprocessValves()

        return valveSimulator.getOptimalRouteWithElephant()
    }

    val testInput = readInput("Day16","Day16_test")
    println(part1(testInput))
    check(part1(testInput) == 1651)
    println(part2(testInput))
    check(part2(testInput) == 1707)
//
    val input = readInput("Day16","Day16")
    println(part1(input))
    println(part2(input))
}
