package com.tails1154.tailstalk2.api.routes.user

import com.tails1154.tailstalk2.api.StoatAPIError
import com.tails1154.tailstalk2.api.StoatHttp
import com.tails1154.tailstalk2.api.StoatJson
import com.tails1154.tailstalk2.api.api
import com.tails1154.tailstalk2.core.model.schemas.Channel
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException

suspend fun openDM(userId: String): Channel {
    val response = StoatHttp.get("/users/$userId/dm".api())
        .bodyAsText()

    try {
        val error = StoatJson.decodeFromString(StoatAPIError.serializer(), response)
        throw Error(error.type)
    } catch (e: SerializationException) {
        // Not an error
    }

    return StoatJson.decodeFromString(Channel.serializer(), response)
}