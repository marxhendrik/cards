package marxhendrik.de.healthcheckcards.dagger

import dagger.BindsInstance
import dagger.Component
import marxhendrik.de.healthcheckcards.HealthCheckApplication

@PerApplication
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: HealthCheckApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: HealthCheckApplication): Builder

        fun build(): AppComponent
    }
}