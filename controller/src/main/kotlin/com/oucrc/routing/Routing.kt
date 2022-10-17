package com.oucrc.routing

sealed interface Routing {
    object Room : Routing {
        const val path = "rooms"
        object Index { const val path = "" }
        object Show {
            const val roomId = "roomId"
            const val path = "{$roomId}"
        }
    }

    object User : Routing {
        const val path = "users"
        object Index { const val path = "" }
        object Show {
            const val userId = "userId"
            const val path = "{$userId}"
        }
    }
}

/**
 * Get : /room/ -> 全てのRoomの表示
 * Get : /room/{id} -> 盤面情報の取得
 * Post : /room/{id} -> 打つ手を選択
 *
 * Get : /user/ -> 全てのUserの表示
 * Post : /user/ -> 新しいUserの登録
 * Get : /user/{id} -> そのユーザーの戦績を表示
 */
