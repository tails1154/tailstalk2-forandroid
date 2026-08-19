package com.tails1154.tailstalk2.api.internals

import com.tails1154.tailstalk2.api.api
import com.tails1154.tailstalk2.core.model.data.STOAT_FILES
import com.tails1154.tailstalk2.core.model.schemas.User

object ResourceLocations {
    fun userAvatarUrl(user: User?): String {
        if (user?.avatar != null) {
            return "$STOAT_FILES/avatars/${user.avatar!!.id}"
        }
        return "/users/${(user?.id ?: "").ifBlank { "0".repeat(26) }}/default_avatar".api()
    }
}