package com.tails1154.tailstalk2.di

import com.tails1154.tailstalk2.persistence.KVStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

//
val appModule = module {
    single { KVStorage(androidContext()) }
}