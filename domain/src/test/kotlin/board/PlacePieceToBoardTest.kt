@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package board

import Board
import black
import com.google.common.truth.Truth.assertThat
import nothing
import white
import kotlin.test.Test

class PlacePieceToBoardTest {
    @Test
    fun place関数でBoardにPieceを置いた結果を取得() {
        val board = Board.create(8)
        assertThat(board.place(coordinate = board.Coordinate(3, 2), piece = Cell.Piece.Black))
            .isEqualTo(
                Board.reconstruct(
                    columns = listOf(
                        Column.reconstruct(nothing(8)),
                        Column.reconstruct(nothing(8)),
                        Column.reconstruct(nothing(8)),
                        Column.reconstruct(nothing(2).black(3).nothing(3)),
                        Column.reconstruct(nothing(3).black(1).white(1).nothing(3)),
                        Column.reconstruct(nothing(8)),
                        Column.reconstruct(nothing(8)),
                        Column.reconstruct(nothing(8)),
                    ),
                )
            )
    }

    @Test
    fun 置けるところではなければ同じBoardを返す() {
        val board = Board.create(8)
        assertThat(board.place(coordinate = board.Coordinate(0, 0), piece = Cell.Piece.Black))
            .isEqualTo(Board.create(8))
    }
}
