package Day17

abstract class Rock(var chasm: Chasm) {
    abstract var position: Pair<Int, Int>
    abstract val width: Int
    abstract fun moveDown() : Pair<Boolean, Int>
    abstract fun moveRight()
    abstract fun moveLeft()

    open fun canMoveRight() : Boolean {
        return position.first + width < 7
    }

    open fun canMoveLeft(): Boolean {
        return position.first > 0
    }

    internal fun movePositionDown() {
        position = Pair(position.first, position.second - 1)
    }

    internal fun movePositionRight() {
        position = Pair(position.first + 1, position.second)
    }

    internal fun movePositionLeft() {
        position = Pair(position.first - 1, position.second)
    }
}