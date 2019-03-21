package de.marxhendrik.healthcheckcards.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event.ON_STOP
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class LifecycleAwarePresenter : LifecycleAware {

    private val onStopDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) = onStopDisposable.add(disposable)

    fun Disposable.manage(event: Lifecycle.Event = ON_STOP) {
        when (event) {
            ON_STOP -> onStopDisposable.add(this)
            else -> throw NotImplementedError("only ON_STOP is managed")
        }
    }

    override fun onAttach() {
    }

    override fun onStart() {
    }

    override fun onStop() {
        onStopDisposable.clear()
    }

    override fun onDetach() {
    }
}
