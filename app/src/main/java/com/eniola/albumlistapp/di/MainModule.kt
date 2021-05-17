package com.eniola.albumlistapp.di

import androidx.lifecycle.ViewModel
import com.eniola.albumlistapp.ui.MainActivity
import com.eniola.albumlistapp.ui.album.AlbumViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class MainModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel::class)
    internal abstract fun albumViewModel(viewModel: AlbumViewModel): ViewModel
}