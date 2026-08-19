package com.tails1154.tailstalk2.internals.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.tails1154.tailstalk2.api.StoatAPI
import com.tails1154.tailstalk2.api.internals.Roles

@Composable
fun rememberChannelPermissions(channelId: String, key1: Any = Unit): MutableLongState {
    val permissions = rememberSaveable { mutableLongStateOf(0L) }
    val selfId = StoatAPI.selfId
    val channel = StoatAPI.channelCache[channelId]

    LaunchedEffect(channelId, key1, selfId, channel) {
        if (StoatAPI.selfId == null) return@LaunchedEffect
        if (StoatAPI.userCache[StoatAPI.selfId] == null) return@LaunchedEffect
        if (StoatAPI.channelCache[channelId] == null) return@LaunchedEffect

        val channel = StoatAPI.channelCache[channelId]
        val selfUser = StoatAPI.userCache[StoatAPI.selfId]
        val member = channel?.let {
            it.server?.let { server ->
                StoatAPI.selfId?.let { selfId ->
                    StoatAPI.members.getMember(server, selfId)
                }
            }
        }
        channel?.let { permissions.longValue = Roles.permissionFor(it, selfUser, member) }
    }

    return permissions
}