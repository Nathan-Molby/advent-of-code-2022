package Day17

class LRock(chasm: Chasm, y: Int): Rock(chasm) {
    override var position: Pair<Int, Int> = Pair(2, y)
    override val width: Int = 3

    init {
        for (i in 2 until 4) {
            chasm.matrix[y][i] = true
        }

        for (i in y until y + 3) {
            chasm.matrix[i][4] = true
        }
    }

    override fun moveDown(): Pair<Boolean, Int> {
        if (position.second == 0) {
            return Pair(false, position.second + 2)
        }

        for (x in position.first until position.first + width) {
            if (chasm.matrix[position.second - 1][x]) {
                return Pair(false, position.second + 2)
            }
        }

        for (x in position.first until position.first + width - 1) {
            chasm.matrix[position.second][x] = false
            chasm.matrix[position.second - 1][x] = true
        }

        chasm.matrix[position.second - 1][position.first + 2] = true
        chasm.matrix[position.second + 2][position.first + 2] = false
        movePositionDown()

        return Pair(true, -1)
    }

    override fun canMoveRight(): Boolean {
        return super.canMoveRight()
                && !chasm.matrix[position.second][position.first + width]
                && !chasm.matrix[position.second + 1][position.first + width]
                && !chasm.matrix[position.second + 2][position.first + width]
    }

    override fun canMoveLeft(): Boolean {
        return super.canMoveLeft()
                && !chasm.matrix[position.second][position.first - 1]
                && !chasm.matrix[position.second + 1][position.first + 1]
                && !chasm.matrix[position.second + 2][position.first + 1]

    }

    override fun moveRight() {
        if (canMoveRight()) {
            for (y in position.second + 1..position.second + 2) {
                chasm.matrix[y][position.first + 2] = false
                chasm.matrix[y][position.first + 3] = true
            }

            chasm.matrix[position.second][position.first] = false
            chasm.matrix[position.second][position.first + width] = true
            movePositionRight()
        }
    }

    override fun moveLeft() {
        if (canMoveLeft()) {
            for (y in position.second + 1..position.second + 2) {
                chasm.matrix[y][position.first + 1] = true
                chasm.matrix[y][position.first + 2] = false
            }

            chasm.matrix[position.second][position.first - 1] = true
            chasm.matrix[position.second][position.first + 2] = false
            movePositionLeft()
        }
    }
}