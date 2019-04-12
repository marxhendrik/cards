# HealthCheck app

A simple (?) app that shows three cards in traffic light colors and highlights a card when clicked upon.
 
This code showcases some architectural examples and experiments that are not necessarily recommended approaches.

## Requirements
1. TODO: formulate the requirements of the architecture regardless of which Architectural Template is used (MVP, MVVM...)


## MVP 

1. Views are the root of the MVP, create the Component via the builder and are thus defining the scope of their dependencies
(This allows the memory of the Features dependencies to be removed from memory when the view is removed from the view hierarchy)
2. Every view has as little logic as possible and delegates logical decisions to its presenters
3. Presenters are free of View/Android classes, so that they can be easily unit tested
4. Views can have sub-views with their own Presenters, Modules and Scopes, that they should not expose to other components. (These Views can be nested multiple layers deep in 
theory)
5. Communication between differently scoped presenters has to be done via relays or similar mechanisms
6. Business logic that is not entirely related to "presentation" should be encapsulated in UseCases


## SubComponents with dagger

1. Sub-Views can have their own Components/Modules/Scopes
2. The Android *View* classes themselves are unaware of their modules, parent components, parent *View* etc.
3. SubComponentBuilders are provided along common dependencies by the feature-Activity for all Views in the hierarchy
4. Activities use AndroidInjection via a *BaseActivity* class
5. A set of helper functions and classes reduces some boilerplate and makes injection easy from the perspective of the involved classes (see *InjectionTools.kt*)

## AAC LifecycleOwner/Observer

1. A LifecycleAwarePresenter base class manages rx.Disposables. A LifecycleOwner has to be provided in the constructor.
2. ViewLifecycle is required to make sure that the lifecycle events (attach/detach) are called when view is added or removed


## Entity/Data layer
1. UseCase encapsulated Business logic. The Presenter should only trigger Usesaces and contain View-Specific logic

## Repo/Api layer
1. Api does network
2. Repo decides between different data sources (e.g. network and database or cache)

  
## Remarks

1. We do not use Daggers "Map Multibinding" to provide the Map of *SubComponentBuilders* because there is a lot of ceremony involved like defining the key annotation and an extra 
abstract Module class to use abstract *@Binds* methods in. It just seems less natural and more complicated to do for this case. Also in Kotlin we would need @SuppressWildCards 
to import the Multibinding Map into the Activity

2. MVP version of this arch does not provide any convenient way to save instances of anything when device is rotated. In this current Architecture the Repo would cache the 
result from the api and provide it back to the usecase after rotation.

3. In a clean architecture there would be different entities on the Network/Repo layer, the Data/UseCase layer and the MVP layer with Mappers between them. Modules would ensure 
that Entities can not be used across boundaries. This has been omitted here. 

4. A "Special Requirement" of these Architectures is that features should be removable and addable dynamically. The dependencies of those Features should be releasable from 
memory. This is a major difference to most architectures where you either have one "MV*" per Activity or Fragment.

5. Why not use Fragments? - Fragments used to suck. Maybe they got better but in the end we do not need them. They were always useful as "Views with Lifecycles", but that was 
before LifecycleOwner. Now it is really easy to make components Lifecycle-aware and Views work just fine. Fragments or Activities should only be used as "Hosts" for the inner 
Architecture of the View Layer (until proven otherwise).



## TODOS for MVP version
1. Unit Tests for UseCase/Repo/Api
2. Executor for Usecases to encapsulate RxJava from the Presenter

## Coming up: Forks to try out different architecture Variants
 * TODO Base Readme here will direct to MVP fork with the content of this Readme
 * TODO Base Readme will explain requirements of the architecture and a quick summary of the forks
 * TODO MVVM-classic fork
 * TODO custom MVVM fork with the following adaptions to the google example version: No Fragments, View-Based Scope, AAC-ViewModel is not called ViewModel and does not contain 
 logic but is only used for retaining state during configuration changes, Maybe live-data, maybe Rx, Maybe ViewBinding....
 * In either MVP or MVVM fork: Executor for Usecases that encapsulates Rx, so that later Co-Routines can replace Rx without touching the Presenters
