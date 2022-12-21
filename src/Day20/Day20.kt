package Day20

import readInput
import java.math.BigInteger
import kotlin.math.*
class LinkedListNode(var number: BigInteger) {
    var nextNode: LinkedListNode? = null
    var prevNode: LinkedListNode? = null

    fun move(listSize: BigInteger) {
        var smallNumber = number % (listSize - BigInteger.ONE)
        if (number < BigInteger.ZERO) {
            smallNumber += listSize - BigInteger.ONE
        }

        val smallNumberInt = smallNumber.toInt()
        for (i in 0 until smallNumberInt) {
            val oldPrevNode = prevNode!!
            val jumpedNode = nextNode!!
            val newNextNode = nextNode!!.nextNode!!

            //deal with old prevNode
            oldPrevNode.nextNode = jumpedNode

            //deal with jumped node
            jumpedNode.prevNode = oldPrevNode
            jumpedNode.nextNode = this
            prevNode = jumpedNode

            // deal with new next node
            nextNode = newNextNode
            newNextNode.prevNode = this
        }
    }


    override fun toString(): String {
        var nextNode = nextNode!!
        var string = number.toString() + ","
        while (nextNode != this) {
            string += nextNode.number.toString() + ","
            nextNode = nextNode.nextNode!!
        }
        return string
    }
}

class EncryptedFile() {
    var nodes = mutableListOf<LinkedListNode>()

    fun readInput(input: List<String>) {
        for (rowIndex in input.withIndex()) {
            val newNode = LinkedListNode(rowIndex.value.toBigInteger())
            if (rowIndex.index != 0) {
                newNode.prevNode = nodes[rowIndex.index - 1]
            }
            nodes.add(newNode)
        }

        for (i in 0 until nodes.size - 1) {
            nodes[i].nextNode = nodes[i + 1]
        }

        nodes[0].prevNode = nodes[nodes.size - 1]
        nodes[nodes.size - 1].nextNode = nodes[0]
    }

    fun multiplyNumbers() {
        for (node in nodes) {
            node.number *= BigInteger.valueOf(811589153)
        }
    }
    fun move() {
        for (node in nodes) {
            node.move(BigInteger.valueOf(nodes.size.toLong()))
        }
    }

    fun getResult(): BigInteger {
        val zeroNode = nodes.first {it.number == BigInteger.ZERO }
        var currentNode = zeroNode
        var nodesOfInterest = mutableListOf<LinkedListNode>()
        for (i in 0 until 1000) {
            currentNode = currentNode.nextNode!!
        }
        nodesOfInterest.add(currentNode)
        for (i in 0 until 1000) {
            currentNode = currentNode.nextNode!!
        }
        nodesOfInterest.add(currentNode)
        for (i in 0 until 1000) {
            currentNode = currentNode.nextNode!!
        }
        nodesOfInterest.add(currentNode)
        return nodesOfInterest.sumOf { it.number }
    }

    fun part2(): BigInteger {
        for (i in 0 until 10) {
            move()
        }

        return getResult()
    }
}

fun main() {
    fun part1(input: List<String>): BigInteger {
        val encryptedFile = EncryptedFile()
        encryptedFile.readInput(input)
        encryptedFile.move()
        return encryptedFile.getResult()
    }

    fun part2(input: List<String>): BigInteger {
        val encryptedFile = EncryptedFile()
        encryptedFile.readInput(input)
        encryptedFile.multiplyNumbers()
        return encryptedFile.part2()
    }

    val testInput = readInput("Day20","Day20_test")
    println(part1(testInput))
//    check(part1(testInput) == 64)
    println(part2(testInput))
//    check(part2(testInput) == 58)

    val input = readInput("Day20","Day20")
//    println(part1(input))
    println(part2(input))
}
