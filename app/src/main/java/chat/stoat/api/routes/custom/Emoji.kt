package com.tails1154.tailstalk2.api.routes.custom

import com.tails1154.tailstalk2.api.StoatHttp
import com.tails1154.tailstalk2.api.StoatJson
import com.tails1154.tailstalk2.api.api
import com.tails1154.tailstalk2.core.model.schemas.Emoji
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

suspend fun fetchEmoji(id: String): Emoji {
    val response = StoatHttp.get("/custom/emoji/$id".api()).bodyAsText()
    return StoatJson.decodeFromString(
        Emoji.serializer(),
        response
    )
}
