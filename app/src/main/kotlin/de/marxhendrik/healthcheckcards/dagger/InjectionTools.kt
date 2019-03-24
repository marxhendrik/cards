package de.marxhendrik.healthcheckcards.dagger

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.ContributesAndroidInjector

/**
 * Signifies that this Activity is using AndroidInjection and has Defined the Binding with [ContributesAndroidInjector]
 */
interface Injecting

object ActivityInjector {
    /**
     * Inject Activity if it implements [Injecting]
     */
    fun inject(activity: AppCompatActivity) {
        if (activity is Injecting) {
            AndroidInjection.inject(activity)
        }
    }
}

/**
 * Activities should implement this when they are providing [SubComponentBuilder]s for their Views
 */
interface HasSubComponentBuilders : Injecting {
    var builders: SubComponentBuilderMap
}

/**
 *  The Builder interface in the Dagger Feature Components should implement this
 */
interface SubComponentBuilder

/**
 * Sugar to hide Signature of the map (as well as make injection possible without the [SuppressWildCards] Annotation
 */
class SubComponentBuilderMap : MutableMap<Class<out InjectingView>, SubComponentBuilder> by mutableMapOf()

/**
 * A "View" which does not necessarily require to be an Android View. Needs to have a context that implements [HasSubComponentBuilders]
 */
interface InjectingView {
    /**
     * The context returned here should extend [HasSubComponentBuilders]
     */
    fun getContext(): Context
}

@Suppress("UNCHECKED_CAST")
fun <Builder : SubComponentBuilder> InjectingView.getComponentBuilder(): Builder =
    extractHasSubComponentBuilders(getContext()).let {
        it.builders[javaClass] as Builder
    }

private fun extractHasSubComponentBuilders(context: Context?): HasSubComponentBuilders {
    return when (context) {
        is HasSubComponentBuilders -> context
        is Activity -> throw IllegalArgumentException("The Activity should implement HasSubComponentBuilders")
        is ContextWrapper -> extractHasSubComponentBuilders(context.baseContext)
        null -> throw IllegalArgumentException("The context provided to can't be null")
        else -> {
            throw IllegalArgumentException("The context provided does not implement HasSubComponentBuilders: $context")
        }
    }
}
