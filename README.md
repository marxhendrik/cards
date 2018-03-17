# HealthCheck app

A simple (?) app that shows three cards in traffic light colors and highlights a card when clicked upon.
 
This app showcases some architectural examples:

## MVP 

1. Views are the root of the MVP, create the Component via the builder and are thus in charge of the dependencies in their scope
2. Every view has as little logic as possible and delegates logical decisions to its presenters
3. Presenters are free of View/Android classes, so that they can be easily unit tested
4. Views can have sub-views with their own Presenters, Modules and Scopes

## SubComponents with dagger

1. Sub-Views can have their own Components/Modules/Scopes
2. The Android *View* classes themselves are unaware of their modules, parent components, parent *View* etc.
3. SubComponentBuilders are provided along common dependencies by the feature-Activity for all Views in the hierarchy
4. Activities use AndroidInjection via a *BaseActivity* class


## Remarks

1. We do not use Daggers "Map Multibinding" to provide the Map of *SubComponentBuilders* because there is a lot of ceremony involved like defining the key annotation and an extra 
abstract Module class to use abstract *@Binds* methods in. It just seems less natural and more complicated to do for this usecase
