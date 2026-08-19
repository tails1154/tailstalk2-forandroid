package com.tails1154.tailstalk2.callbacks

import com.tails1154.tailstalk2.screens.chat.ChatRouterDestination
import kotlinx.coroutines.channels.Channel

sealed class Action {
    data class OpenUserSheet(val userId: String, val serverId: String?) : Action()
    data class SwitchServer(val serverId: String) : Action()
    data class SwitchChannel(val channelId: String) : Action()
    data class JumpToMessage(val channelId: String, val messageId: String) : Action()
    data class LinkInfo(val url: String) : Action()
    data class EmoteInfo(val emoteId: String) : Action()
    data class MessageReactionInfo(val messageId: String, val emoji: String) : Action()
    data class TopNavigate(val route: String) : Action()
    data class ChatNavigate(val destination: ChatRouterDestination) : Action()
    data class ReportUser(val userId: String) : Action()
    data class ReportMessage(val messageId: String) : Action()
    data class OpenVoiceChannelOverlay(val channelId: String) : Action()
    data object OpenWebhookSheet : Action()
}

val ActionChannel = Channel<Action>(
    Channel.BUFFERED
)
