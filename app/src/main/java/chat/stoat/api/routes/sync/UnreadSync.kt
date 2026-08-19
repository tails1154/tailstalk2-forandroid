package com.tails1154.tailstalk2.api.routes.sync

import com.tails1154.tailstalk2.api.StoatHttp
import com.tails1154.tailstalk2.api.StoatJson
import com.tails1154.tailstalk2.api.api
import com.tails1154.tailstalk2.core.model.schemas.ChannelUnreadResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.builtins.ListSerializer

suspend fun syncUnreads(): List<ChannelUnreadResponse> {
    val response = StoatHttp.get("/sync/unreads".api())
        .bodyAsText()

    return StoatJson.decodeFromString(
        ListSerializer(ChannelUnreadResponse.serializer()),
        response
    )
}
