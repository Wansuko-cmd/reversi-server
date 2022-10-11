@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package board

import Board
import black
import com.google.common.truth.Truth.assertThat
import nothing
import white
import kotlin.test.Test

class OpennessTest {
    @Test
    fun opennessで開放度を調べることができる() {
        val board = Board.reconstruct(
            columns = listOf(
                Column.reconstruct(nothing(8)),
                Column.reconstruct(nothing(8)),
                Column.reconstruct(nothing(2).white(1).black(1).nothing(4)),
                Column.reconstruct(nothing(3).white(1).black(1).nothing(3)),
                Column.reconstruct(nothing(3).black(1).white(1).nothing(3)),
                Column.reconstruct(nothing(8)),
                Column.reconstruct(nothing(8)),
                Column.reconstruct(nothing(8)),
            ),
        )
        assertThat(board.openness(board.Coordinate(3, 2), Cell.Piece.Black)).isEqualTo(3)
        assertThat(board.openness(board.Coordinate(4, 5), Cell.Piece.Black)).isEqualTo(5)
        assertThat(board.openness(board.Coordinate(1, 1), Cell.Piece.Black)).isNull()
    }
}
