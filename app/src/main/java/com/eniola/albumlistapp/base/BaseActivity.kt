package com.eniola.albumlistapp.base

import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity : DaggerAppCompatActivity(){
    abstract fun observeData()
}