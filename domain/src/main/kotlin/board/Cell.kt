/**
 * ボードの上の状態
 */
sealed interface Cell {
    object Nothing : Cell
    sealed interface Piece : Cell {
        fun reverse(): Piece
        object Black : Piece {
            override fun reverse(): Piece = White
        }
        object White : Piece {
            override fun reverse(): Piece = Black
        }
        companion object {
            fun from(value: Int) = when (value) {
                1 -> Black
                2 -> White
                else -> throw DomainException.SystemException("Int to Piece validate error.", null)
            }
        }
    }

    fun toInt() = when (this) {
        is Nothing -> 0
        is Piece.Black -> 1
        is Piece.White -> 2
    }

    companion object {
        fun from(value: Int) = when (value) {
            0 -> Nothing
            1, 2 -> Piece.from(value)
            else -> throw DomainException.SystemException("Int to Cell validate error.", null)
        }
    }
}

fun nothing(size: Int) = List(size) { Cell.Nothing }
fun white(size: Int) = List(size) { Cell.Piece.White }
fun black(size: Int) = List(size) { Cell.Piece.Black }
fun List<Cell>.nothing(size: Int) = this + List(size) { Cell.Nothing }
fun List<Cell>.white(size: Int) = this + List(size) { Cell.Piece.White }
fun List<Cell>.black(size: Int) = this + List(size) { Cell.Piece.Black }

data class PieceCount(val black: Int, val white: Int) {
    operator fun plus(other: PieceCount) = this.black + other.black vs this.white + other.white
    operator fun get(piece: Cell.Piece) = when (piece) {
        is Cell.Piece.Black -> black
        is Cell.Piece.White -> white
    }
    companion object {
        infix fun Int.vs(white: Int) = PieceCount(black = this, white = white)
    }
}
