package com.tails1154.tailstalk2.api.routes.channel

import com.tails1154.tailstalk2.api.StoatAPIError
import com.tails1154.tailstalk2.api.StoatHttp
import com.tails1154.tailstalk2.api.StoatJson
import com.tails1154.tailstalk2.api.api
import com.tails1154.tailstalk2.core.model.schemas.Channel
import com.tails1154.tailstalk2.screens.create.MAX_ADDABLE_PEOPLE_IN_GROUP
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

@Serializable
data class CreateGroupDMBody(
    val name: String,
    val users: List<String>
)

suspend fun createGroupDM(name: String, members: List<String>): Channel {
    if (members.size > MAX_ADDABLE_PEOPLE_IN_GROUP) {
        throw Exception("Too many members, maximum is $MAX_ADDABLE_PEOPLE_IN_GROUP")
    }

    val response = StoatHttp.post("/channels/create".api()) {
        contentType(ContentType.Application.Json)
        setBody(CreateGroupDMBody(name, members))
    }.bodyAsText()

    try {
        val error = StoatJson.decodeFromString(StoatAPIError.serializer(), response)
        throw Error(error.type)
    } catch (e: SerializationException) {
        // Not an error
    }

    return StoatJson.decodeFromString(Channel.serializer(), response)
}

suspend fun removeMember(channelId: String, userId: String) {
    val response = StoatHttp.delete("/channels/$channelId/recipients/$userId".api())

    if (!response.status.isSuccess()) {
        throw Error(response.status.toString())
    }
}

suspend fun addMember(channelId: String, userId: String) {
    val response = StoatHttp.put("/channels/$channelId/recipients/$userId".api())

    if (!response.status.isSuccess()) {
        throw Error(response.status.toString())
    }
}