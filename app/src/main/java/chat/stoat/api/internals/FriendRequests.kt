package com.tails1154.tailstalk2.api.internals

import com.tails1154.tailstalk2.api.StoatAPI
import com.tails1154.tailstalk2.core.model.schemas.User

object FriendRequests {
    fun getIncoming(): List<User> {
        return StoatAPI.userCache.values.filter { user ->
            user.relationship == "Incoming"
        }
    }

    fun getOutgoing(): List<User> {
        return StoatAPI.userCache.values.filter { user ->
            user.relationship == "Outgoing"
        }
    }

    fun getBlocked(): List<User> {
        return StoatAPI.userCache.values.filter { user ->
            user.relationship == "Blocked"
        }
    }

    fun getOnlineFriends(): List<User> {
        return StoatAPI.userCache.values.filter { user ->
            user.relationship == "Friend" && user.online == true
        }
    }

    fun getFriends(excludeOnline: Boolean = false): List<User> {
        return StoatAPI.userCache.values.filter { user ->
            user.relationship == "Friend" && if (excludeOnline) user.online == false else true
        }
    }
}