package com.tails1154.tailstalk2.sheets

import androidx.compose.runtime.Composable
import com.tails1154.tailstalk2.api.StoatAPI
import com.tails1154.tailstalk2.composables.emoji.EmojiPicker

@Composable
fun ReactSheet(messageId: String, onSelect: (String?) -> Unit) {
    val message = StoatAPI.messageCache[messageId]

    if (message == null) {
        onSelect(null)
        return
    }

    EmojiPicker {
        onSelect(it.removeSurrounding(":"))
    }

}