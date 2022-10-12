package user

interface UserRepository {
    fun getAll(): List<User>
    fun getById(id: UserId): User
    fun insert(user: User)
}