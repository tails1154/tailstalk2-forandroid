package com.tails1154.tailstalk2.api.routes.tailstalk

import com.tails1154.tailstalk2.api.StoatHttp
import com.tails1154.tailstalk2.api.StoatJson
import com.tails1154.tailstalk2.api.api
import com.tails1154.tailstalk2.core.model.schemas.TailsTalkAvatarDecoration
import com.tails1154.tailstalk2.core.model.schemas.TailsTalkBoostSummary
import com.tails1154.tailstalk2.core.model.schemas.TailsTalkXp
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText

/** Small typed client surface for TailsTalk 2-only APIs. */
suspend fun fetchTailsTalkXp(userId: String): TailsTalkXp =
    StoatJson.decodeFromString(TailsTalkXp.serializer(), StoatHttp.get("/users/$userId/xp".api()).bodyAsText())

suspend fun fetchTailsTalkDecorations(): List<TailsTalkAvatarDecoration> =
    StoatJson.decodeFromString(
        kotlinx.serialization.builtins.ListSerializer(TailsTalkAvatarDecoration.serializer()),
        StoatHttp.get("/users/profile-decorations".api()).bodyAsText()
    )

suspend fun purchaseTailsTalkDecoration(id: String) {
    StoatHttp.post("/users/profile-decorations/$id/purchase".api())
}

suspend fun equipTailsTalkDecoration(id: String) {
    StoatHttp.post("/users/profile-decorations/$id/equip".api())
}

suspend fun fetchTailsTalkBoosts(serverId: String): TailsTalkBoostSummary =
    StoatJson.decodeFromString(
        TailsTalkBoostSummary.serializer(),
        StoatHttp.get("/servers/$serverId/boosts".api()).bodyAsText()
    )
