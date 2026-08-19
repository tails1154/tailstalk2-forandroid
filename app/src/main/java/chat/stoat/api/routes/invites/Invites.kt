package com.tails1154.tailstalk2.api.routes.invites

import com.tails1154.tailstalk2.api.StoatAPIError
import com.tails1154.tailstalk2.api.StoatHttp
import com.tails1154.tailstalk2.api.StoatJson
import com.tails1154.tailstalk2.api.api
import com.tails1154.tailstalk2.core.model.schemas.Invite
import com.tails1154.tailstalk2.core.model.schemas.InviteJoined
import com.tails1154.tailstalk2.core.model.util.RsResult
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException

suspend fun fetchInviteByCode(code: String): RsResult<Invite, StoatAPIError> {
    val response = StoatHttp.get("/invites/$code".api())
        .bodyAsText()

    try {
        val error = StoatJson.decodeFromString(StoatAPIError.serializer(), response)
        if (error.type != "Server") return RsResult.err(error)
    } catch (e: SerializationException) {
        // Not an error
    }

    val invite = StoatJson.decodeFromString(Invite.serializer(), response)
    return RsResult.ok(invite)
}

suspend fun joinInviteByCode(code: String): RsResult<InviteJoined, StoatAPIError> {
    val response = StoatHttp.post("/invites/$code".api())
        .bodyAsText()

    try {
        val error = StoatJson.decodeFromString(StoatAPIError.serializer(), response)
        if (error.type != "Server") return RsResult.err(error)
    } catch (e: SerializationException) {
        // Not an error
    }

    val invite = StoatJson.decodeFromString(InviteJoined.serializer(), response)
    return RsResult.ok(invite)
}
