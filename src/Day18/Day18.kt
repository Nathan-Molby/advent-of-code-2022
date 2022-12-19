package Day18

import readInput
import java.util.Objects
import kotlin.math.*

class Coordinate(var x: Int, var y: Int, var z: Int) {
    override fun equals(other: Any?) = (other is Coordinate)
            && x == other.x
            && y == other.y
            && z == other.z

    override fun hashCode(): Int {
        return Objects.hash(x, y, z)
    }

    operator fun plus(coordinate: Coordinate): Coordinate {
        return Coordinate(x + coordinate.x, y + coordinate.y, z + coordinate.z)
    }
}

abstract class Block(var coordinate: Coordinate) {
    enum class Type {
        CUBE, OUTSIDE_AIR, INSIDE_AIR, UNKNOWN
    }

    abstract var type: Type
}

class OtherBlock(coordinate: Coordinate, override var type: Type): Block(coordinate)

class Cube(coordinate: Coordinate): Block(coordinate) {
    var numSidesExposed = 6
    fun calculateCubeAdded(otherCube: Cube) {
        if(otherCube.coordinate.x == coordinate.x && otherCube.coordinate.y == coordinate.y) {
            if (otherCube.coordinate.z == coordinate.z - 1) {
                numSidesExposed--
            } else if (otherCube.coordinate.z == coordinate.z + 1) {
                numSidesExposed--
            }
        } else if(otherCube.coordinate.x == coordinate.x && otherCube.coordinate.z == coordinate.z) {
            if (otherCube.coordinate.y == coordinate.y - 1) {
                numSidesExposed--
            } else if (otherCube.coordinate.y == coordinate.y + 1) {
                numSidesExposed--
            }
        } else if(otherCube.coordinate.y == coordinate.y && otherCube.coordinate.z == coordinate.z) {
            if (otherCube.coordinate.x == coordinate.x - 1) {
                numSidesExposed--
            } else if (otherCube.coordinate.x == coordinate.x + 1) {
                numSidesExposed--
            }
        }
    }

    override var type = Type.CUBE
}

class Lava() {
    var cubes = mutableListOf<Cube>()
    var coordinate_block = hashMapOf<Coordinate, Block>()
    var additionCoordinates = arrayOf(Coordinate(-1, 0, 0),
        Coordinate(1, 0, 0),
        Coordinate(0, -1, 0),
        Coordinate(0, 1, 0),
        Coordinate(0, 0, -1),
        Coordinate(0, 0, 1))

    fun addCube(newCube: Cube) {
        for (cube in cubes) {
            cube.calculateCubeAdded(newCube)
            newCube.calculateCubeAdded(cube)
        }

        cubes.add(newCube)
    }

    fun processInput(input: List<String>) {
        for(x in 1 until 20) {
            for (y in 1 until 20) {
                for (z in 1 until 20) {
                    val coordinate = Coordinate(x, y, z)
                    coordinate_block[coordinate] = OtherBlock(coordinate, Block.Type.UNKNOWN)
                }
            }
        }

        for (row in input) {
            val rowSplit = row.split(",").map { it.toInt() }
            val coordinate = Coordinate(rowSplit[0], rowSplit[1], rowSplit[2])
            val newCube = Cube(coordinate)
            coordinate_block[coordinate] = newCube
            addCube(newCube)
        }
    }

    fun identifyUnknownBlocks() {
        var blocksRemaining = coordinate_block.filter { it.value.type == Block.Type.UNKNOWN }

        while(blocksRemaining.isNotEmpty()) {
            var blockToIdentify = blocksRemaining.entries.first()

            identifyBlock(blockToIdentify.toPair())

            blocksRemaining = coordinate_block.filter { it.value.type == Block.Type.UNKNOWN }
        }
    }

    fun identifyBlock(coordinate_block: Pair<Coordinate, Block>) {
        val searchedBlocks = hashMapOf<Coordinate, Block>(coordinate_block.first to coordinate_block.second)
        var result = identifyBlockRecursive(coordinate_block.second, searchedBlocks)
        for (block in searchedBlocks.values) {
            block.type = result
        }
    }

    fun identifyBlockRecursive(block: Block, searchedBlocks: HashMap<Coordinate, Block>): Block.Type {
        for (addedCoordinate in additionCoordinates) {
            val coordinate = block.coordinate + addedCoordinate
            if (!searchedBlocks.contains(coordinate)) {
                //this means the block is at the edge of the 20x20x20 cube, so it is air
                if (coordinate.x == 0 || coordinate.x == 20 || coordinate.y == 0 || coordinate.y == 20 || coordinate.z == 0 || coordinate.z == 20) {
                    return Block.Type.OUTSIDE_AIR
                }

                val blockToSearch = coordinate_block[coordinate]!!
                when (blockToSearch.type) {
                    Block.Type.CUBE -> continue
                    Block.Type.OUTSIDE_AIR -> return Block.Type.OUTSIDE_AIR
                    else -> {}
                }

                //is this block is unknown, identify it
                searchedBlocks[coordinate] = blockToSearch
                val blockType = identifyBlockRecursive(blockToSearch, searchedBlocks)
                when (blockType) {
                    Block.Type.CUBE -> continue
                    Block.Type.OUTSIDE_AIR -> return Block.Type.OUTSIDE_AIR
                    else -> {}
                }
            }
        }

        return Block.Type.INSIDE_AIR
    }

    fun fillInInsideAir() {
//        for (z in 1..19) {
//            for(y in 1..19) {
//                for (x in 1..19) {
//                    val coordinate = Coordinate(x, y, z)
//                    when(coordinate_block[coordinate]!!.type) {
//                        Block.Type.INSIDE_AIR -> print("O")
//                        Block.Type.CUBE -> print("X")
//                        Block.Type.OUTSIDE_AIR -> print(" ")
//                        else -> {}
//                    }
//                }
//                println()
//            }
//            println("---------------------")
//        }

        val blocksToFill = coordinate_block.filter { it.value.type == Block.Type.INSIDE_AIR }
        for (block in blocksToFill) {
            addCube(Cube(block.key))
        }
    }


}

fun main() {
    fun part1(input: List<String>): Int {
        var lava = Lava()
        lava.processInput(input)

        return lava.cubes.sumOf { it.numSidesExposed }
    }

    fun part2(input: List<String>): Int {
        var lava = Lava()
        lava.processInput(input)
        lava.identifyUnknownBlocks()
        lava.fillInInsideAir()
        println(lava.coordinate_block.filter { it.value.type == Block.Type.OUTSIDE_AIR }.count())
        return lava.cubes.sumOf { it.numSidesExposed }
    }

//    val testInput = readInput("Day18","Day18_test")
//    println(part1(testInput))
//    check(part1(testInput) == 64)
//    println(part2(testInput))
//    check(part2(testInput) == 58)

    val input = readInput("Day18","Day18")
//    println(part1(input))
    println(part2(input))
}
