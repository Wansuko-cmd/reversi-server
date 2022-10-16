package user

import java.util.UUID

class User private constructor(
    val id: UserId,
    val name: UserName,
) {
    companion object {
        fun create(name: UserName) =
            User(
                id = UserId(UUID.randomUUID().toString()),
                name = name,
            )
        fun reconstruct(id: UserId, name: UserName) =
            User(
                id = id,
                name = name,
            )
    }
}

@JvmInline
value class UserId(val value: String)

@JvmInline
value class UserName(val value: String)
