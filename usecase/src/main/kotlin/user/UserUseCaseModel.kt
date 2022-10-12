package user

data class UserUseCaseModel(
    val id: UserId,
    val name: UserName,
) {
    companion object {
        fun from(user: User): UserUseCaseModel =
            UserUseCaseModel(
                id = user.id,
                name = user.name,
            )
    }
}
