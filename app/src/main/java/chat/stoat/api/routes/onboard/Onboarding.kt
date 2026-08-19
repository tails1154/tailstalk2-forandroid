package com.tails1154.tailstalk2.api.routes.onboard

import com.tails1154.tailstalk2.api.RateLimitResponse
import com.tails1154.tailstalk2.api.StoatAPI
import com.tails1154.tailstalk2.api.StoatAPIError
import com.tails1154.tailstalk2.api.StoatHttp
import com.tails1154.tailstalk2.api.StoatJson
import com.tails1154.tailstalk2.api.api
import com.tails1154.tailstalk2.core.model.util.RsResult
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

@Serializable
data class OnboardingResponse(
    val onboarding: Boolean
)

suspend fun needsOnboarding(sessionToken: String = StoatAPI.sessionToken): Boolean {
    val response = StoatHttp.get("/onboard/hello".api()) {
        header(StoatAPI.TOKEN_HEADER_NAME, sessionToken)
    }

    val responseContent = response.bodyAsText()

    try {
        val rateLimitResponse =
            StoatJson.decodeFromString(RateLimitResponse.serializer(), responseContent)
        throw rateLimitResponse.toException()
    } catch (e: SerializationException) {
        // good path
    }

    return StoatJson.decodeFromString(OnboardingResponse.serializer(), responseContent).onboarding
}

@Serializable
data class OnboardingCompletionBody(
    val username: String
)

suspend fun completeOnboarding(
    body: OnboardingCompletionBody,
    sessionToken: String = StoatAPI.sessionToken
): RsResult<Unit, StoatAPIError> {
    val response = StoatHttp.post("/onboard/complete".api()) {
        setBody(body)
        contentType(ContentType.Application.Json)
        header(StoatAPI.TOKEN_HEADER_NAME, sessionToken)
    }

    if (response.status == HttpStatusCode.Conflict) {
        return RsResult.err(StoatAPIError("UsernameTaken"))
    }

    if (response.status == HttpStatusCode.BadRequest) {
        return RsResult.err(StoatAPIError("InvalidUsername"))
    }

    val responseContent = response.bodyAsText()

    try {
        val error = StoatJson.decodeFromString(StoatAPIError.serializer(), responseContent)
        return RsResult.err(error)
    } catch (e: SerializationException) {
        // Not an error
    }

    return RsResult.ok(Unit)
}
