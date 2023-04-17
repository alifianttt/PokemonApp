package com.submission.pokemonapp

import android.app.Application
import com.submission.pokemonapp.core.di.databaseModule
import com.submission.pokemonapp.core.di.networkModule
import com.submission.pokemonapp.core.di.repoModule
import com.submission.pokemonapp.di.splitManagerModule
import com.submission.pokemonapp.di.useCaseModule
import com.submission.pokemonapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repoModule,
                    useCaseModule,
                    viewModelModule,
                    splitManagerModule
                )
            )
        }
    }
}