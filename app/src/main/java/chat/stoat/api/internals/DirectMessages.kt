package com.tails1154.tailstalk2.api.internals

import com.tails1154.tailstalk2.api.StoatAPI
import com.tails1154.tailstalk2.api.internals.SpecialUsers.PLATFORM_MODERATION_USER
import com.tails1154.tailstalk2.core.model.schemas.Channel
import com.tails1154.tailstalk2.core.model.schemas.ChannelType

object DirectMessages {
    fun unreadDMs(): List<Channel> {
        return StoatAPI.channelCache.values
            .filter {
                it.channelType in listOf(
                    ChannelType.DirectMessage, ChannelType.Group
                ) && it.active == true && it.lastMessageID != null
            }
            .filter {
                it.id?.let { id ->
                    StoatAPI.unreads.hasUnread(
                        id,
                        it.lastMessageID!!,
                        serverId = null
                    )
                } ?: false
            }
    }

    fun hasPlatformModerationDM(): Boolean {
        return unreadDMs().any {
            it.channelType == ChannelType.DirectMessage &&
                    it.recipients?.contains(PLATFORM_MODERATION_USER) ?: false
        }
    }

    fun getPlatformModerationDM(): Channel? {
        return unreadDMs().firstOrNull {
            it.channelType == ChannelType.DirectMessage &&
                    it.recipients?.contains(PLATFORM_MODERATION_USER) ?: false
        }
    }
}
