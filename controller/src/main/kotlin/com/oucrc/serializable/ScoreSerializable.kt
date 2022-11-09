package com.oucrc.serializable

import kotlinx.serialization.Serializable
import score.ScoreUseCaseModel

@Serializable
data class ScoreSerializable(
    val opponent: UserSerializable,
    val win: Int,
    val lose: Int,
    val draw: Int,
) {
    companion object {
        fun from(score: ScoreUseCaseModel) = ScoreSerializable(
            opponent = UserSerializable.from(score.opponent),
            win = score.win,
            lose = score.lose,
            draw = score.draw,
        )
    }
}