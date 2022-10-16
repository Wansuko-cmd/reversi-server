package com.oucrc.serializable

import user.UserUseCaseModel

@kotlinx.serialization.Serializable
data class UserSerializable(
    val id: String,
    val name: String,
) {
    companion object {
        fun from(user: UserUseCaseModel) =
            UserSerializable(
                id = user.id.value,
                name = user.name.value,
            )
    }
}
