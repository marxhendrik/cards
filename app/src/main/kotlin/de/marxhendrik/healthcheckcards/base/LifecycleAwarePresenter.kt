package de.marxhendrik.healthcheckcards.base

import android.arch.lifecycle.Lifecycle.Event.ON_START
import android.arch.lifecycle.Lifecycle.Event.ON_STOP
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class LifecycleAwarePresenter(lifecycleOwner: LifecycleOwner) : LifecycleObserver {

    private val onStopDisposable = CompositeDisposable()

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    fun addDisposable(disposable: Disposable) = onStopDisposable.add(disposable)

    @OnLifecycleEvent(ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(ON_STOP)
    open fun onStop() {
    }

    @OnLifecycleEvent(ON_STOP)
    fun dispose() = onStopDisposable.clear()
}