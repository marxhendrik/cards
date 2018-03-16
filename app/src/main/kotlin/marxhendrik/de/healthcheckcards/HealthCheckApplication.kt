package marxhendrik.de.healthcheckcards

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import marxhendrik.de.healthcheckcards.dagger.DaggerAppComponent
import javax.inject.Inject

class HealthCheckApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        DaggerAppComponent.builder()
                .app(this)
                .build()
                .inject(this)
        super.onCreate()
    }


    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}