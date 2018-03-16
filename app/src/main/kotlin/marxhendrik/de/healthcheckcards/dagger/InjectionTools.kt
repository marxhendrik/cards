package marxhendrik.de.healthcheckcards.dagger

import android.support.v7.app.AppCompatActivity
import android.view.View
import dagger.android.AndroidInjection
import kotlin.reflect.KClass

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

class SubComponentBuilderMap : MutableMap<KClass<out SubComponentBuilder>, SubComponentBuilder> by mutableMapOf()

interface InjectingView

@Suppress("UNCHECKED_CAST")
fun <T : SubComponentBuilder> InjectingView.getSubComponentBuilder(clazz: KClass<T>): T {
    val builders = ((this as View).context as HasSubComponentBuilders).builders
    return builders[clazz] as? T ?: throw IllegalStateException("no builder of class $clazz")
}