package de.marxhendrik.healthcheckcards.dagger

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection

interface Injecting

object ActivityInjector {
    fun inject(activity: AppCompatActivity) {
        if (activity is Injecting) {
            AndroidInjection.inject(activity)
        }
    }
}

interface HasSubComponentBuilders : Injecting {
    var builders: SubComponentBuilderMap
}

interface SubComponentBuilder

class SubComponentBuilderMap : MutableMap<Class<out InjectingView>, SubComponentBuilder> by mutableMapOf()

interface InjectingView {
    fun getContext(): Context
}


@Suppress("UNCHECKED_CAST")
fun <Builder : SubComponentBuilder> InjectingView.getComponentBuilder(): Builder {
    val context = extractHasSubComponentBuilders(getContext())
    return context.builders[javaClass] as Builder
}

private fun extractHasSubComponentBuilders(context: Context?): HasSubComponentBuilders {
    return when (context) {
        is HasSubComponentBuilders -> context
        is Activity -> throw IllegalArgumentException("The Activity should implement HasSubComponentBuilders")
        is ContextWrapper -> extractHasSubComponentBuilders(context.baseContext)
        null -> throw IllegalArgumentException("The content provided to extractHasSubComponentBuilders() is null")
        else -> {
            throw IllegalArgumentException("The context provided does not implement HasSubComponentBuilders: $context")
        }
    }
}
