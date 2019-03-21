package de.marxhendrik.testbase

import android.support.annotation.CallSuper
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.TestScheduler

abstract class BaseTest {

    val testScheduler = TestScheduler()

    @CallSuper
    open fun setup() {
        RxAndroidPlugins.initMainThreadScheduler { testScheduler }
    }

    @CallSuper
    open fun teardown() {
        RxAndroidPlugins.reset()
    }
}
