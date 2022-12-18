package Day17

class SquareRock(chasm: Chasm, y: Int): Rock(chasm) {
    override var position: Pair<Int, Int> = Pair(2, y)
    override val width: Int = 2

    init {
        for (x in 2 ..3) {
            for (y in y..y+1)
            chasm.matrix[y][x] = true
        }
    }

    override fun moveDown(): Pair<Boolean, Int> {
        if (position.second == 0) {
            return Pair(false, position.second + 1)
        }

        for (x in position.first until position.first + width) {
            if (chasm.matrix[position.second - 1][x]) {
                return Pair(false, position.second + 1)
            }
        }

        for (x in position.first until position.first + width) {
            chasm.matrix[position.second + 1][x] = false
            chasm.matrix[position.second - 1][x] = true
        }
        movePositionDown()

        return Pair(true, -1)
    }

    override fun canMoveRight(): Boolean {
        return super.canMoveRight()
                && !chasm.matrix[position.second][position.first + width]
                && !chasm.matrix[position.second + 1][position.first + width]
    }

    override fun canMoveLeft(): Boolean {
        return super.canMoveLeft()
                && !chasm.matrix[position.second][position.first - 1]
                && !chasm.matrix[position.second + 1][position.first - 1]
    }

    override fun moveRight() {
        if (canMoveRight()) {
            for (y in position.second..position.second+1) {
                chasm.matrix[y][position.first] = false
                chasm.matrix[y][position.first + width] = true
            }

            movePositionRight()
        }
    }

    override fun moveLeft() {
        if (canMoveLeft()) {
            for (y in position.second..position.second+1) {
                chasm.matrix[y][position.first + 1] = false
                chasm.matrix[y][position.first - 1] = true
            }

            movePositionLeft()
        }
    }
}