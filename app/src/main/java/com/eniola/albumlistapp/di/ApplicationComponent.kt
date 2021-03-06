package com.eniola.albumlistapp.di

import android.content.Context
import com.eniola.albumlistapp.base.AlbumListApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 19-Jan-2021
 */

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, MainModule::class])
interface ApplicationComponent: AndroidInjector<AlbumListApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}