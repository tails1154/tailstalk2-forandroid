package com.tails1154.tailstalk2.api.routes.safety

import com.tails1154.tailstalk2.api.StoatAPIError
import com.tails1154.tailstalk2.api.StoatHttp
import com.tails1154.tailstalk2.api.StoatJson
import com.tails1154.tailstalk2.core.model.schemas.ContentReportReason
import com.tails1154.tailstalk2.core.model.schemas.FullMessageReport
import com.tails1154.tailstalk2.core.model.schemas.FullServerReport
import com.tails1154.tailstalk2.core.model.schemas.FullUserReport
import com.tails1154.tailstalk2.core.model.schemas.MessageReport
import com.tails1154.tailstalk2.core.model.schemas.ServerReport
import com.tails1154.tailstalk2.core.model.schemas.UserReport
import com.tails1154.tailstalk2.core.model.schemas.UserReportReason
import com.tails1154.tailstalk2.api.api
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException

suspend fun putMessageReport(
    messageId: String,
    reason: ContentReportReason,
    additionalContext: String? = null
) {
    val fullMessageReport = FullMessageReport(
        content = MessageReport(
            type = "Message",
            report_reason = reason,
            id = messageId
        ),
        additional_context = additionalContext
    )

    val response = StoatHttp.post("/safety/report".api()) {
        setBody(
            StoatJson.encodeToString(
                FullMessageReport.serializer(),
                fullMessageReport
            )
        )
    }
        .bodyAsText()

    try {
        val error = StoatJson.decodeFromString(StoatAPIError.serializer(), response)
        throw Error(error.type)
    } catch (e: SerializationException) {
        // Not an error
    }
}

suspend fun putServerReport(
    serverId: String,
    reason: ContentReportReason,
    additionalContext: String? = null
) {
    val fullServerReport = FullServerReport(
        content = ServerReport(
            type = "Server",
            report_reason = reason,
            id = serverId
        ),
        additional_context = additionalContext
    )

    val response = StoatHttp.post("/safety/report".api()) {
        setBody(
            StoatJson.encodeToString(
                FullServerReport.serializer(),
                fullServerReport
            )
        )
    }
        .bodyAsText()

    try {
        val error = StoatJson.decodeFromString(StoatAPIError.serializer(), response)
        throw Error(error.type)
    } catch (e: SerializationException) {
        // Not an error
    }
}

suspend fun putUserReport(
    userId: String,
    reason: UserReportReason,
    additionalContext: String? = null
) {
    val fullUserReport = FullUserReport(
        content = UserReport(
            type = "User",
            report_reason = reason,
            id = userId
        ),
        additional_context = additionalContext
    )

    val response = StoatHttp.post("/safety/report".api()) {
        setBody(
            StoatJson.encodeToString(
                FullUserReport.serializer(),
                fullUserReport
            )
        )
    }
        .bodyAsText()

    try {
        val error = StoatJson.decodeFromString(StoatAPIError.serializer(), response)
        throw Error(error.type)
    } catch (e: SerializationException) {
        // Not an error
    }
}
