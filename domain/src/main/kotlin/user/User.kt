package user

import room.RoomId
import java.util.UUID

class User private constructor(
    val id: UserId,
    val name: UserName,
    val status: UserStatus,
) {
    companion object {
        fun create(name: UserName) =
            User(
                id = UserId(UUID.randomUUID().toString()),
                name = name,
                status = UserStatus.WaitMatting,
            )
        fun reconstruct(id: UserId, name: UserName, status: UserStatus) =
            User(
                id = id,
                name = name,
                status = status
            )
    }
}

@JvmInline
value class UserId(val value: String)

@JvmInline
value class UserName(val value: String)

sealed interface UserStatus {
    object WaitMatting : UserStatus
    data class OnMatch(val roomId: RoomId) : UserStatus
}
