package Day08

import readInput

fun findVisibleTrees(treeMatrix: List<List<Int>>): Int {
    var visibleTrees = HashSet<Pair<Int, Int>>()

    for (rowIndex in treeMatrix.indices) {
        processRow(rowIndex, treeMatrix, visibleTrees)
    }

    val columnCount = treeMatrix[0].size

    for(columnIndex in 0..columnCount - 1) {
        processColumn(columnIndex, treeMatrix, visibleTrees)
    }

    return visibleTrees.size
}

fun processColumn(columnIndex: Int, treeMatrix: List<List<Int>>, visibleTrees: HashSet<Pair<Int, Int>>) {
    val rowCount = treeMatrix.size

    var highestTreeSoFar = -1
    for (rowIndex in 0..rowCount - 1) {
        val coordinateValue = treeMatrix[rowIndex][columnIndex]

        if(coordinateValue > highestTreeSoFar) {
            highestTreeSoFar = coordinateValue
            visibleTrees.add(Pair<Int, Int>(rowIndex, columnIndex))
        }
    }

    highestTreeSoFar = -1
    for(rowIndex in rowCount - 1 downTo 0) {
        val coordinateValue = treeMatrix[rowIndex][columnIndex]

        if(coordinateValue > highestTreeSoFar) {
            highestTreeSoFar = coordinateValue
            visibleTrees.add(Pair<Int, Int>(rowIndex, columnIndex))
        }
    }
}

fun processRow(rowIndex: Int, treeMatrix: List<List<Int>>, visibleTrees: HashSet<Pair<Int, Int>>) {
    var highestTreeSoFar = -1
    for (columnIndex in treeMatrix[rowIndex].indices) {
        val coordinateValue = treeMatrix[rowIndex][columnIndex]
        if (coordinateValue > highestTreeSoFar) {
            highestTreeSoFar = coordinateValue
            visibleTrees.add(Pair<Int, Int>(rowIndex, columnIndex))
        }
    }

    highestTreeSoFar = -1
    for (columnIndex in treeMatrix[rowIndex].size - 1 downTo 0) {
        val coordinateValue = treeMatrix[rowIndex][columnIndex]
        if (coordinateValue > highestTreeSoFar) {
            highestTreeSoFar = coordinateValue
            visibleTrees.add(Pair<Int, Int>(rowIndex, columnIndex))
        }
    }
}

fun findScenicScore(treeMatrix: List<List<Int>>): Pair<Pair<Int, Int>, Int> {
    var maxScenicScore = -1
    var bestCoordinate: Pair<Int, Int> = Pair(0, 0)

    for (rowIndex in treeMatrix.indices) {
        if (rowIndex == 0 || rowIndex == treeMatrix.count() - 1) {
            continue;
        }
        for (columnIndex in treeMatrix[rowIndex].indices) {
            if (columnIndex == 0 || columnIndex == treeMatrix[0].count() - 1) {
                continue;
            }
            val scenicScore = findScenicScore(treeMatrix, rowIndex, columnIndex)
            if(scenicScore > maxScenicScore) {
                maxScenicScore = scenicScore
                bestCoordinate = Pair(rowIndex, columnIndex)
            }
        }
    }

    return Pair(bestCoordinate, maxScenicScore)
}

fun findScenicScore(treeMatrix: List<List<Int>>, initialRowIndex: Int, initialColumnIndex: Int): Int {
    val treeHeight = treeMatrix[initialRowIndex][initialColumnIndex]
    var xPositiveScore = 0
    var xNegativeScore = 0
    var yPositiveScore = 0
    var yNegativeScore = 0

    var rowIndex = initialRowIndex + 1
    while(rowIndex < treeMatrix.count()) {
        yPositiveScore++

        if (treeMatrix[rowIndex][initialColumnIndex] >= treeHeight) {
            break
        }

        rowIndex++
    }

    rowIndex = initialRowIndex - 1
    while(rowIndex >= 0) {
        yNegativeScore++

        if (treeMatrix[rowIndex][initialColumnIndex] >= treeHeight) {
            break
        }

        rowIndex--

    }

    var columnIndex = initialColumnIndex + 1
    while(columnIndex < treeMatrix[initialRowIndex].count()) {
        xPositiveScore++

        if (treeMatrix[initialRowIndex][columnIndex] >= treeHeight) {
            break
        }

        columnIndex++
    }

    columnIndex = initialColumnIndex - 1
    while(columnIndex >= 0) {
        xNegativeScore++

        if (treeMatrix[initialRowIndex][columnIndex] >= treeHeight) {
            break
        }

        columnIndex--
    }

    return xPositiveScore * xNegativeScore * yPositiveScore * yNegativeScore
}

fun main() {
    fun part1(input: List<String>): Int {
        val intMatrix = input.map {
            it
                .map { it.digitToInt() }
        }

        return findVisibleTrees(intMatrix)
    }

    fun part2(input: List<String>): Int {
        val intMatrix = input.map {
            it
                .map { it.digitToInt() }
        }

        val result = findScenicScore(intMatrix)
        return result.second
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08","Day08_test")
    println(part1(testInput))
    check(part1(testInput) == 21)
    println(part2(testInput))
    check(part2(testInput) == 8)

    val input = readInput("Day08","Day08")
    println(part1(input))
    println(part2(input))
}
