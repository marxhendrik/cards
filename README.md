# HealthCheck app

A simple (?) app that shows three cards in different colors and highlights a card when clicked upon.
 
## This is a playground not a showcase 
This code shows some architectural examples and experiments that are not necessarily recommended approaches.

## Requirements
1. TODO: formulate the requirements of the app regardless of architecture


## MVP 

1. Views are the root of the MVP, create the Component via the builder and are thus in charge of the dependencies in their scope
2. Every view has as little logic as possible and delegates logical decisions to its presenters
3. Presenters are free of View/Android classes, so that they can be easily unit tested
4. Views can have sub-views with their own Presenters, Modules and Scopes, that they should not expose to other components.


## SubComponents with dagger

1. Sub-Views can have their own Components/Modules/Scopes
2. The Android *View* classes themselves are unaware of their modules, parent components, parent *View* etc.
3. SubComponentBuilders are provided along common dependencies by the feature-Activity for all Views in the hierarchy
4. Activities use AndroidInjection via a *BaseActivity* class
5. A set of helper functions and classes reduces some boilerplate and makes injection easy from the perspective of the involved classes (see *InjectionTools.kt*)

## AAC LifecycleOwner/Observer

1. A LifecycleAwarePresenter base class manages rx.Disposables. A LifecycleOwner has to be provided in the constructor.
2. ViewLifecycle is required to make sure that the lifecycle events (attach/detach) are called when view is added removed

## Remarks

1. We do not use Daggers "Map Multibinding" to provide the Map of *SubComponentBuilders* because there is a lot of ceremony involved like defining the key annotation and an extra 
abstract Module class to use abstract *@Binds* methods in. It just seems less natural and more complicated to do for this usecase


## Coming up: Forks
 * TODO Base Readme here will direct to MVP fork with the content of this Readme
 * TODO Base Readme will explain requirements of the architecture and a quick summary of the forks
 * TODO MVVM-classic fork
 * TODO MVVM without fragments fork (???)
