package com.example.newbankdemo

import android.app.Application
import com.example.newbankdemo.core.di.networkModule
import com.example.newbankdemo.core.di.repoModule
import com.example.newbankdemo.core.di.useCaseModule
import com.example.newbankdemo.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@MainApplication)

            modules(listOf(viewModelModule, repoModule, networkModule,useCaseModule))

        }
    }

}




