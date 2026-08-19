package com.tails1154.tailstalk2.api.routes.microservices.geo

import kotlinx.serialization.Serializable

@Serializable
data class GeoResponse(
    val countryCode: String,
    val isAgeRestrictedGeo: Boolean,
)

suspend fun queryGeo(): GeoResponse {
    return GeoResponse(countryCode = "", isAgeRestrictedGeo = false)
}
