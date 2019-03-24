package de.marxhendrik.healthcheckcards.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event.ON_STOP
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class LifecycleAwarePresenter : LifecycleAware {

    private val onStopDisposable = CompositeDisposable()

    fun Disposable.manage(event: Lifecycle.Event = ON_STOP) {
        when (event) {
            ON_STOP -> onStopDisposable.add(this)
            else -> throw NotImplementedError("only ON_STOP is managed")
        }
    }

    @CallSuper
    override fun onStop() {
        onStopDisposable.clear()
    }

}
