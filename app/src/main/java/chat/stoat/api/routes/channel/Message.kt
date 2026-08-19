package com.tails1154.tailstalk2.api.routes.channel

import com.tails1154.tailstalk2.api.StoatHttp
import com.tails1154.tailstalk2.api.api
import io.ktor.client.request.delete
import io.ktor.client.request.put

suspend fun react(channelId: String, messageId: String, emoji: String) {
    StoatHttp.put("/channels/$channelId/messages/$messageId/reactions/$emoji".api())
}

suspend fun unreact(channelId: String, messageId: String, emoji: String) {
    StoatHttp.delete("/channels/$channelId/messages/$messageId/reactions/$emoji".api())
}