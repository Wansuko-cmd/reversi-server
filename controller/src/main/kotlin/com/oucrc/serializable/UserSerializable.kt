package com.oucrc.serializable

import user.UserStatus
import user.UserUseCaseModel

@kotlinx.serialization.Serializable
data class UserSerializable(
    val id: String,
    val name: String,
    val status: String?,
) {
    companion object {
        fun from(user: UserUseCaseModel) =
            UserSerializable(
                id = user.id.value,
                name = user.name.value,
                status = when (val status = user.status) {
                    is UserStatus.WaitMatting -> null
                    is UserStatus.OnMatch -> status.roomId.value
                }
            )
    }
}
