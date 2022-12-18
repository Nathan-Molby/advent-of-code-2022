package Day17

class PlusRock(chasm: Chasm, y: Int): Rock(chasm) {
    override var position: Pair<Int, Int> = Pair(2, y)
    override val width: Int = 3

    init {
        //bottom row
        chasm.matrix[y][3] = true

        //middle row
        chasm.matrix[y + 1][2] = true
        chasm.matrix[y + 1][3] = true
        chasm.matrix[y + 1][4] = true

        //top row
        chasm.matrix[y + 2][3] = true
    }

    override fun moveDown(): Pair<Boolean, Int> {
        if (position.second == 0) {
            return Pair(false, position.second + 2)
        }

        if(chasm.matrix[position.second][position.first]
            || chasm.matrix[position.second][position.first + 2]
            || chasm.matrix[position.second - 1][position.first + 1]) {
            return Pair(false, position.second + 2)
        }

        chasm.matrix[position.second + 1][position.first] = false
        chasm.matrix[position.second + 1][position.first + 2] = false
        chasm.matrix[position.second][position.first] = true
        chasm.matrix[position.second][position.first + 2] = true

        chasm.matrix[position.second + 2][position.first + 1] = false
        chasm.matrix[position.second - 1][position.first + 1] = true

        movePositionDown()

        return Pair(true, -1)
    }

    override fun canMoveRight(): Boolean {
        return super.canMoveRight()
                && !chasm.matrix[position.second][position.first + 2]
                && !chasm.matrix[position.second + 2][position.first + 2]
                && !chasm.matrix[position.second + 1][position.first + 3]
    }

    override fun canMoveLeft(): Boolean {
        return super.canMoveLeft()
                && !chasm.matrix[position.second][position.first]
                && !chasm.matrix[position.second + 2][position.first]
                && !chasm.matrix[position.second + 1][position.first - 1]
    }

    override fun moveRight() {
        if (canMoveRight()) {
            chasm.matrix[position.second][position.first + 2] = true
            chasm.matrix[position.second + 2][position.first + 2] = true
            chasm.matrix[position.second + 1][position.first + 3] = true

            chasm.matrix[position.second][position.first + 1] = false
            chasm.matrix[position.second + 2][position.first + 1] = false
            chasm.matrix[position.second + 1][position.first] = false
            movePositionRight()
        }
    }

    override fun moveLeft() {
        if (canMoveLeft()) {
            chasm.matrix[position.second][position.first] = true
            chasm.matrix[position.second + 2][position.first] = true
            chasm.matrix[position.second + 1][position.first - 1] = true

            chasm.matrix[position.second][position.first + 1] = false
            chasm.matrix[position.second + 2][position.first + 1] = false
            chasm.matrix[position.second + 1][position.first + 2] = false
            movePositionLeft()
        }
    }
}