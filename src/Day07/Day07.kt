package Day07

import Day06.firstUniqueIndex
import readInput

class Node(val name: String, val parentNode: Node?, val size: Int?, val isDirectory: Boolean) {
    var childNodes = mutableListOf<Node>()

    fun addNode(node: Node) {
        childNodes.add(node)
    }

    fun getSize(): Int {
        if (size != null) {
            return size
        } else {
            return childNodes.map{ it.getSize() }.sum()
        }
    }
}

class CommandParser(val commands: List<String>) {
    var headNode: Node = Node("/", null, null, true)
    var parentNode: Node? = null
    var nodes = mutableListOf<Node>(headNode)

    fun parseCommands() {
        for(command in commands) {
            parseCommand(command)
        }
    }

    private fun parseCommand(command: String) {
        if (command[0] == '$') {
            parseExecutedCommands(command.substring(2))
        } else {
            parseFileOrDir(command)
        }
    }

    private fun parseExecutedCommands(command: String) {
        val commandElements = command.split(" ")

        when(commandElements[0]) {
            "ls" -> return
            "cd" -> processCd(commandElements[1])
        }
    }

    private fun processCd(newDirectory: String) {
        when(newDirectory) {
            ".." -> parentNode = parentNode?.parentNode
            "/" -> parentNode = headNode
            else -> parentNode = parentNode?.childNodes?.first { it.name == newDirectory && it.isDirectory }
        }
    }

    private fun parseFileOrDir(fileLine: String) {
        val lineElements = fileLine.split(" ")
        when(lineElements[0]) {
            "dir" -> addDirectory(lineElements[1])
            else -> addFile(lineElements[0], lineElements[1])
        }
    }

    private fun addDirectory(dirName: String) {
        val newNode = Node(dirName, parentNode, null, true)
        addNode(newNode)
    }

    private fun addFile(fileSize: String, fileName: String) {
        val newNode = Node(fileName, parentNode, fileSize.toInt(), false)
        addNode(newNode)
    }

    private fun addNode(node: Node) {
        nodes.add(node)
        parentNode?.addNode(node)
    }
}



fun main() {
    fun part1(input: List<String>): Int {
        val parser = CommandParser(input)
        parser.parseCommands()
        return parser.nodes.filter { it.isDirectory && it.getSize() <= 100000 }.sumOf { it.getSize() }
    }

    fun part2(input: List<String>): Int {
        val parser = CommandParser(input)
        parser.parseCommands()
        val sizeRequired = 30000000 - (70000000 - parser.headNode.getSize())

        val directoriesOfRequisiteSize = parser.nodes.filter { it.isDirectory && it.getSize() > sizeRequired }.sortedBy { it.getSize() }
        return directoriesOfRequisiteSize[0].getSize()
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07","Day07_test")
    println(part1(testInput))
    check(part1(testInput) == 95437)
    println(part2(testInput))
    check(part2(testInput) == 24933642)

    val input = readInput("Day07","Day07")
    println(part1(input))
    println(part2(input))
}
