package com.tails1154.tailstalk2.settings.providers

import com.tails1154.tailstalk2.StoatApplication
import com.tails1154.tailstalk2.persistence.KVStorage

object AgeGateUnlockedStorageProvider {
    private val kv = KVStorage(StoatApplication.instance)

    suspend fun setAgeGateUnlocked(unlocked: Boolean) {
        kv.set("ageGateUnlocked", unlocked)
    }

    suspend fun getAgeGateUnlocked(): Boolean {
        return kv.getBoolean("ageGateUnlocked") ?: false
    }
}