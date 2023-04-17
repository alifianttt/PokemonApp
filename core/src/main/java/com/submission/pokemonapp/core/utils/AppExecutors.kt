package com.submission.pokemonapp.core.utils

import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors @VisibleForTesting constructor(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {
    companion object{
        private const val THREAD_COUNT = 3
    }

    constructor(): this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    )

    fun diskIO(): Executor = diskIO

    fun netWorkIO(): Executor = networkIO

    fun mainThread(): Executor = mainThread

    private class MainThreadExecutor: Executor{
        private val mainHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainHandler.post(command)
        }

    }
}