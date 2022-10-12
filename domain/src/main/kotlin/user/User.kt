package user

class User(
    val id: UserId,
    val name: UserName,
)

@JvmInline
value class UserId(val value: String)

@JvmInline
value class UserName(val value: String)