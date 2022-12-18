package Day17

class VerticalRock(chasm: Chasm, y: Int): Rock(chasm) {
    override var position: Pair<Int, Int> = Pair(2, y)
    override val width: Int = 1

    init {
        for (y in y until y + 4) {
            chasm.matrix[y][2] = true
        }
    }

    override fun moveDown(): Pair<Boolean, Int> {
        if (position.second == 0 || chasm.matrix[position.second - 1][position.first]) {
            return Pair(false, position.second + 3)
        }

        chasm.matrix[position.second - 1][position.first] = true
        chasm.matrix[position.second + 3][position.first] = false

        movePositionDown()

        return Pair(true, -1)
    }

    override fun canMoveRight(): Boolean {
        if (!super.canMoveRight())  { return false }

        for (y in position.second until position.second + 4) {
            if (chasm.matrix[y][position.first + 1]) { return false }
        }

        return true
    }

    override fun canMoveLeft(): Boolean {
        if (!super.canMoveLeft())  { return false }

        for (y in position.second until position.second + 4) {
            if (chasm.matrix[y][position.first - 1]) { return false }
        }

        return true
    }

    override fun moveRight() {
        if (canMoveRight()) {
            for (y in position.second until position.second + 4) {
                chasm.matrix[y][position.first] = false
                chasm.matrix[y][position.first + 1] = true
            }

            movePositionRight()
        }
    }

    override fun moveLeft() {
        if (canMoveLeft()) {
            for (y in position.second until position.second + 4) {
                chasm.matrix[y][position.first] = false
                chasm.matrix[y][position.first - 1] = true
            }

            movePositionLeft()
        }
    }
}