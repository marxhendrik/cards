package de.marxhendrik.healthcheckcards.base

interface LifecycleAware {
    fun onAttach() {}
    fun onStart() {}
    fun onStop() {}
    fun onDetach() {}
}
