package Day17

class HorizontalRock(chasm: Chasm, y: Int): Rock(chasm) {
    override var position: Pair<Int, Int> = Pair(2, y)
    override val width: Int = 4

    init {
        for (i in 2 until 6) {
            chasm.matrix[y][i] = true
        }
    }

    override fun moveDown(): Pair<Boolean, Int> {
        if (position.second == 0) {
            return Pair(false, position.second)
        }

        for (x in position.first until position.first + width) {
            if (chasm.matrix[position.second - 1][x]) {
                return Pair(false, position.second)
            }
        }

        for (x in position.first until position.first + width) {
            chasm.matrix[position.second][x] = false
            chasm.matrix[position.second - 1][x] = true
        }
        movePositionDown()

        return Pair(true, -1)
    }

    override fun canMoveRight(): Boolean {
        return super.canMoveRight() && !chasm.matrix[position.second][position.first + width]
    }

    override fun canMoveLeft(): Boolean {
        return super.canMoveLeft() && !chasm.matrix[position.second][position.first - 1]
    }

    override fun moveRight() {
        if (canMoveRight()) {
            chasm.matrix[position.second][position.first] = false
            chasm.matrix[position.second][position.first + width] = true
            movePositionRight()
        }
    }

    override fun moveLeft() {
        if (canMoveLeft()) {
            chasm.matrix[position.second][position.first + width - 1] = false
            chasm.matrix[position.second][position.first - 1] = true
            movePositionLeft()
        }
    }
}