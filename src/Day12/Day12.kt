package Day12

import readInput
import java.util.PriorityQueue
import kotlin.math.*

class Node(val elevation: Int): Comparable<Node> {
    var distance = Int.MAX_VALUE - 1
    var prev: Node? = null
    var neighbors = mutableListOf<Node>()

    fun isReachable(other: Node): Boolean {
        return other.elevation <= elevation + 1
    }

    fun reset() {
        distance = Int.MAX_VALUE - 1
        prev = null
    }
    override fun compareTo(other: Node) = compareValuesBy(this, other, {it.distance}, {it.distance})
}
class Maze() {
    var nodesPriorityQueue = PriorityQueue<Node>()
    var allNodes = mutableListOf<Node>()
    var targetNode: Node? = null

    var smallestStartNodeDist = Int.MAX_VALUE


    fun processInput(input: List<String>, multipleStarts: Boolean) {
        val matrix = mutableListOf<MutableList<Node>>()

        for (row in input) {
            var matrixRow = mutableListOf<Node>()
            matrix.add(matrixRow)
            for (nodeChar in row) {
                if (nodeChar == 'S') {
                    val node = Node('a'.code)
                    node.distance = 0
                    matrixRow.add(node)
                    nodesPriorityQueue.add(node)
                } else if (nodeChar == 'E') {
                    val node = Node('z'.code)
                    targetNode = node
                    matrixRow.add(node)
                    nodesPriorityQueue.add(node)
                } else {
                    val node = Node(nodeChar.code)
                    if(multipleStarts && nodeChar == 'a') {
                        node.distance = 0
                    }
                    matrixRow.add(node)
                    nodesPriorityQueue.add(node)
                }
            }
        }


        allNodes = nodesPriorityQueue.toMutableList()

        for (rowIndex in matrix.indices) {
            val row = matrix[rowIndex]

            for (nodeIndex in row.indices) {
                val node = row[nodeIndex]

                //N
                if(rowIndex > 0 && node.isReachable(matrix[rowIndex - 1][nodeIndex])) {
                    node.neighbors.add(matrix[rowIndex - 1][nodeIndex])
                }

                //S
                if(rowIndex < matrix.size - 1 && node.isReachable(matrix[rowIndex + 1][nodeIndex])) {
                    node.neighbors.add(matrix[rowIndex + 1][nodeIndex])
                }

                //E
                if(nodeIndex < row.size - 1 && node.isReachable(matrix[rowIndex][nodeIndex + 1])) {
                    node.neighbors.add(matrix[rowIndex][nodeIndex + 1])
                }

                //W
                if(nodeIndex > 0 && node.isReachable(matrix[rowIndex][nodeIndex - 1])) {
                    node.neighbors.add(matrix[rowIndex][nodeIndex - 1])
                }
            }
        }
    }

    fun runDjikstras() {
        while (nodesPriorityQueue.isNotEmpty()) {
            val node = nodesPriorityQueue.remove()
            for (neighbor in node.neighbors) {
                val dist = node.distance + 1

                if(dist < neighbor.distance) {
                    neighbor.distance = dist
                    neighbor.prev = node
                    nodesPriorityQueue.remove(neighbor)
                    nodesPriorityQueue.add(neighbor)
                }
            }
        }
    }

    fun getShortsDist(): Int {
        return targetNode!!.distance
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val maze = Maze()
        maze.processInput(input, false)
        maze.runDjikstras()
        return maze.getShortsDist()
    }

    fun part2(input: List<String>): Int {
        val maze = Maze()
        maze.processInput(input, true)
        maze.runDjikstras()
        return maze.getShortsDist()
    }



    val testInput = readInput("Day12","Day12_test")
    println(part1(testInput))
    check(part1(testInput) == 31)
    println(part2(testInput))
    check(part2(testInput) == 29)

    val input = readInput("Day12","Day12")
    println(part1(input))
    println(part2(input))
}
