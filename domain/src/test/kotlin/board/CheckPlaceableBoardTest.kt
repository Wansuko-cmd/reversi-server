@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package board

import Board
import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class CheckPlaceableBoardTest {
    @Test
    fun placeableCoordinateで駒を置ける座標のリストを取得() {
        val board = Board.create(8)
        assertThat(board.placeableCoordinates(piece = Cell.Piece.Black).toSet())
            .isEqualTo(
                setOf(
                    board.Coordinate(2, 3),
                    board.Coordinate(3, 2),
                    board.Coordinate(4, 5),
                    board.Coordinate(5, 4),
                )
            )
    }
}
