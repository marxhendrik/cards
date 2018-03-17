package de.marxhendrik.testbase

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.support.annotation.CallSuper
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.TestScheduler

abstract class BaseTest : LifecycleOwner {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    val testScheduler = TestScheduler()

    @CallSuper
    open fun setup() {
        lifecycleRegistry = LifecycleRegistry(this)
        RxAndroidPlugins.initMainThreadScheduler { testScheduler }
    }

    fun markStarted() = lifecycleRegistry.markState(Lifecycle.State.STARTED)

    @CallSuper
    open fun teardown() {
        RxAndroidPlugins.reset()
    }
}