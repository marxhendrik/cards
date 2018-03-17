package de.marxhendrik.healthcheckcards

import timber.log.Timber

fun Any?.log(s: String, t: Throwable? = null) {
    this ?: return

    if (t == null) {
        Timber.i(s)
    } else {
        Timber.e(t, s, null)
    }
}