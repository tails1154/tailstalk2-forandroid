package com.tails1154.tailstalk2.composables.chat.specialembeds

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tails1154.tailstalk2.core.model.schemas.Special
import com.tails1154.tailstalk2.api.settings.LoadedSettings

@Composable
fun SpecialEmbedSwitch(special: Special, modifier: Modifier = Modifier) {
    when {
        (special.type == "YouTube") && LoadedSettings.specialEmbedSettings.embedYouTube -> YoutubeEmbedSwitch(
            special,
            modifier
        )

        (special.type == "AppleMusic") && LoadedSettings.specialEmbedSettings.embedAppleMusic -> AppleMusicEmbed(
            special,
            modifier
        )

        else -> {}
    }
}