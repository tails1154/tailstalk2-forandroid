package com.tails1154.tailstalk2.core.model.schemas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** TailsTalk 2 additions returned by the shared API. */
@Serializable
data class TailsTalkXp(
    val xp: Long = 0,
    val level: Long = 0,
    @SerialName("next_level_xp") val nextLevelXp: Long = 0,
    @SerialName("current_level_xp") val currentLevelXp: Long = 0
)

@Serializable
data class TailsTalkAvatarDecoration(
    val id: String,
    val name: String,
    @SerialName("asset_url") val assetUrl: String,
    @SerialName("price_xp") val priceXp: Long = 0,
    val enabled: Boolean = true,
    val owned: Boolean = false,
    val equipped: Boolean = false
)

@Serializable
data class TailsTalkBoost(
    val id: String,
    @SerialName("user_id") val userId: String
)

@Serializable
data class TailsTalkBoostSummary(
    @SerialName("server_id") val serverId: String,
    @SerialName("boost_count") val boostCount: Long = 0,
    val level: Int = 0,
    @SerialName("next_level") val nextLevel: Int? = null,
    @SerialName("user_boosts") val userBoosts: List<TailsTalkBoost> = emptyList(),
    @SerialName("available_premium_boosts") val availablePremiumBoosts: Long = 0,
    @SerialName("user_level") val userLevel: Long = 0,
    @SerialName("boost_cost_levels") val boostCostLevels: Long = 1,
    @SerialName("is_premium") val isPremium: Boolean = false,
    @SerialName("progress_enabled") val progressEnabled: Boolean = false,
    @SerialName("can_manage") val canManage: Boolean = false
)
