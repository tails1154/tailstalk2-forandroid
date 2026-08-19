package com.tails1154.tailstalk2.api.routes.auth

import com.tails1154.tailstalk2.api.StoatHttp
import com.tails1154.tailstalk2.api.StoatJson
import com.tails1154.tailstalk2.api.api
import com.tails1154.tailstalk2.core.model.schemas.Session
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.builtins.ListSerializer

suspend fun fetchAllSessions(): List<Session> {
    val response = StoatHttp.get("/auth/session/all".api())
        .bodyAsText()

    return StoatJson.decodeFromString(
        ListSerializer(Session.serializer()),
        response
    )
}

suspend fun logoutSessionById(id: String) {
    StoatHttp.delete("/auth/session/$id".api())
}

suspend fun logoutAllSessions(includingSelf: Boolean = false) {
    StoatHttp.delete("/auth/session/all".api()) {
        parameter("revoke_self", includingSelf)
    }
}
