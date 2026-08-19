package com.tails1154.tailstalk2.c2dm

import com.tails1154.tailstalk2.internals.StoatWebLink
import kotlinx.coroutines.flow.MutableStateFlow

object NotificationDeepLink {
    val pendingNavigation = MutableStateFlow<StoatWebLink?>(null)
}
