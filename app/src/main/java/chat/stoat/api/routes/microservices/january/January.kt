package com.tails1154.tailstalk2.api.routes.microservices.january

import com.tails1154.tailstalk2.core.model.data.STOAT_PROXY
import java.net.URLEncoder

fun asJanuaryProxyUrl(url: String): String {
    return "$STOAT_PROXY/proxy?url=${URLEncoder.encode(url, "utf-8")}"
}
