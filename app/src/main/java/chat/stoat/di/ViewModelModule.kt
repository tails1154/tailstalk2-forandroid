package com.tails1154.tailstalk2.di

import com.tails1154.tailstalk2.activities.MainActivityViewModel
import com.tails1154.tailstalk2.activities.ShareTargetScreenViewModel
import com.tails1154.tailstalk2.screens.chat.ChatRouterViewModel
import com.tails1154.tailstalk2.screens.chat.views.channel.ChannelScreenViewModel
import com.tails1154.tailstalk2.screens.login.LoginViewModel
import com.tails1154.tailstalk2.screens.login.MfaScreenViewModel
import com.tails1154.tailstalk2.screens.settings.AccountSettingsScreenViewModel
import com.tails1154.tailstalk2.screens.settings.AppearanceSettingsScreenViewModel
import com.tails1154.tailstalk2.screens.settings.DebugSettingsScreenViewModel
import com.tails1154.tailstalk2.screens.settings.MfaSettingsScreenViewModel
import com.tails1154.tailstalk2.screens.settings.NotificationsSettingsScreenViewModel
import com.tails1154.tailstalk2.screens.settings.ProfileSettingsScreenViewModel
import com.tails1154.tailstalk2.screens.settings.SettingsScreenViewModel
import com.tails1154.tailstalk2.screens.settings.channel.ChannelSettingsOverviewViewModel
import com.tails1154.tailstalk2.sheets.MemberListSheetViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get(), androidContext()) }
    viewModel { ChatRouterViewModel(get(), androidContext()) }
    viewModel { MemberListSheetViewModel(androidApplication()) }
    viewModel { ShareTargetScreenViewModel(get()) }
    viewModel { ChannelScreenViewModel(get()) }
    viewModel { MfaScreenViewModel(get()) }
    viewModel { SettingsScreenViewModel(get()) }
    viewModel { DebugSettingsScreenViewModel(get()) }
    viewModel { NotificationsSettingsScreenViewModel(get(), androidContext()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileSettingsScreenViewModel(androidApplication()) }
    viewModel { AppearanceSettingsScreenViewModel(androidApplication()) }
    viewModel { ChannelSettingsOverviewViewModel(androidApplication()) }
    viewModel { AccountSettingsScreenViewModel(androidApplication()) }
    viewModel { MfaSettingsScreenViewModel(androidApplication()) }
}