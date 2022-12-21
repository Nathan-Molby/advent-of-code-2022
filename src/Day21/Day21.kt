package Day21

import readInput
import java.math.BigInteger

data class Node(val id: String, val nodeType: NodeType) {
    enum class NodeType { LEAF, BRANCH }
    var leafValue: BigInteger = BigInteger.ZERO
    var nodeChildren: Pair<Node, Node>? = null
    var nodeChildrenIds: Pair<String, String>? = null
    var operation: String? = null

    fun setNodeChildren(node1: Node, node2: Node) {
        nodeChildren = Pair(node1, node2)
    }

    fun getValue(): BigInteger {
        return when (nodeType) {
            NodeType.LEAF -> leafValue
            NodeType.BRANCH -> {
                val firstValue = nodeChildren!!.first.getValue()
                val secondValue = nodeChildren!!.second.getValue()
                return when (operation) {
                    "+" -> firstValue + secondValue
                    "-" -> firstValue - secondValue
                    "*" -> firstValue * secondValue
                    "/" -> firstValue / secondValue
                    else -> {
                        print("AHHHHHHH")
                        BigInteger.ZERO
                    }
                }

            }
        }
    }

    fun getAlgebraicEquation(): String {
        return when (nodeType) {
            NodeType.LEAF -> leafValue.toString()
            NodeType.BRANCH -> {
                val firstValue = nodeChildren!!.first.getAlgebraicEquation()
                val secondValue = nodeChildren!!.second.getAlgebraicEquation()
                return when (operation) {
                    "+" -> "($firstValue + $secondValue)"
                    "-" -> "($firstValue - $secondValue)"
                    "*" -> "($firstValue * $secondValue)"
                    "/" -> "($firstValue / $secondValue)"
                    "=" -> "($firstValue = $secondValue)"
                    else -> {
                        print("AHHHHHHH")
                        ""
                    }
                }

            }
        }
    }

}
class MonkeySolver() {
    var nodes = mutableMapOf<String, Node>()

    fun readInput(input: List<String>) {
        for (monkeyString in input) {
            val monkeyStringSplit = monkeyString.split(" ")
            val monkeyId = monkeyStringSplit[0].trimEnd(':')
            val nodeType = if (monkeyStringSplit.count() == 4) Node.NodeType.BRANCH else Node.NodeType.LEAF
            val node = Node(monkeyId, nodeType)
            nodes[monkeyId] = node

            when (nodeType) {
                Node.NodeType.LEAF -> node.leafValue = monkeyStringSplit[1].toBigInteger()
                Node.NodeType.BRANCH -> {
                    node.nodeChildrenIds = Pair(monkeyStringSplit[1], monkeyStringSplit[3])
                    node.operation = monkeyStringSplit[2]
                }
            }
        }
    }

    fun matchNodes() {
        for (id_node in nodes) {
            if (id_node.value.nodeType == Node.NodeType.BRANCH) {
                val node = id_node.value
                node.nodeChildren = Pair(nodes[node.nodeChildrenIds!!.first]!!, nodes[node.nodeChildrenIds!!.second]!!)
            }
        }
    }

}

fun main() {
    fun part1(input: List<String>): BigInteger {
        var monkeySolver = MonkeySolver()
        monkeySolver.readInput(input)
        monkeySolver.matchNodes()
        return monkeySolver.nodes["root"]!!.getValue()
    }

    fun part2(input: List<String>): String {
        var monkeySolver = MonkeySolver()
        monkeySolver.readInput(input)
        monkeySolver.matchNodes()
        monkeySolver.nodes["root"]!!.operation = "="
        return monkeySolver.nodes["root"]!!.getAlgebraicEquation()
    }

    val testInput = readInput("Day21","Day21_test")
//    println(part1(testInput))
//    check(part1(testInput) == 64)
//    println(part2(testInput))
//    check(part2(testInput) == 58)

    val input = readInput("Day21","Day21")
//    println(part1(input))
    println(part2(input))
}
