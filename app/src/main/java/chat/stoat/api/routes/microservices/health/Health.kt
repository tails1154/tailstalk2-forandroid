package com.tails1154.tailstalk2.api.routes.microservices.health

import com.tails1154.tailstalk2.core.model.schemas.HealthNotice

suspend fun healthCheck(): HealthNotice {
    return HealthNotice()
}
