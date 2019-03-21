package de.marxhendrik.healthcheckcards.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View

class ViewLifecycle(private val view: View, private val lifecycleOwner: LifecycleOwner) : LifecycleObserver,
    View.OnAttachStateChangeListener {

    private var lifecycleObserver: LifecycleAware? = null

    fun register(observer: LifecycleAware) {
        lifecycleObserver = observer
        view.addOnAttachStateChangeListener(this)
    }

    override fun onViewAttachedToWindow(v: View?) {
        lifecycleObserver?.onAttach()
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onViewDetachedFromWindow(v: View?) {
        lifecycleObserver?.onStop()
        lifecycleObserver?.onDetach()
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        lifecycleObserver?.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        lifecycleObserver?.onStop()
    }

}
