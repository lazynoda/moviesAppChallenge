# moviesAppChallenge

## Architecture

I've used a Clean architecture because of it's simplicity. This is a small app with no special requirements. In addition, I've used Android architecture lifecycle classes (like `ViewModel` and `LiveData`) to persist data across configuration changes and notify changes to the previous layer.

Special mention to `UseCase` class, which contains all logic to run background jobs and notify the result to it's invoker. I've used WorkManager from Android Jetpack libraries. With few lines of code, you can enqueue a background job and be notified when it's finished. It has a limitation about which type of params can be used as IO, but I've solved it mapping the input/output object to a JSON representation of it and then creating the original object again.

## Testing

About Unit test, I've used JUnit 4, Mockito and Mockito-kotlin (which contains a lot of helpers to avoid some Kotlin features, like null-validation). About what to test or not, I always test "for this input, I expect this output" and never test internal code itself. I'm never sure if test call dependencies (for example test if a presenter calls a use case) because it doesn't test input-output pair. But sometimes it's important to verify call params or verify that some dependency is never called. I've only tested presenters because it contains some complex code. Use case and repository are most of time one line code.

For Instrumentation test I've tested basic view values, like "this label should contains that text" or "this click should launch that intent". In detail screen I've overridden the intent to be the same that list screen launches.

I should use MockWebServer to not depend of API response calls and know exactly what will be showed in each screen. But I haven't time to do it :(

## UI/UX

This is my... Anchilles hell? I'm very very bad designing an interface. I know most of the platform design guidelines, but I haven't creativity with colors, forms, where to show this or that data. I've created a simple list/detail screens using OMDB colors from their website.

## Dependency injection

I've used multiple times Dagger 2 and I know Koin and Kodein, but for simple and small projects like this one, I think it's not necessary. I've created a constructor with no params (or minimal required params) which calls to other constructor that receive all class dependencies. This second constructor should have package-private visbility, but in Kotlin I use internal, and it's used in test.

I don't like to use interfaces if they are not necessary for production environment. Yes, that second constructor it's not required and it's used only for test, but I need something to inject mock dependencies. Because Kotlin has final modifier by default, I've used `mock-maker-inline` to open it in test environment.

## 3rd party libs

* **OkHttp**: I don't like to use other libraries like Retrofit because they use reflection to parse the service response, which can turn into problems with release builds. The response is tiny to write som JSON code. OkHttp is a simple and powerfull tool where, with 3 lines of code, I can make a GET call and receive a JSONObject as a response.

* **Glide**: IMHO one of the best libraries to manage image downloads. It supports multiple cache configs, thumbnails, and GIFs! Typically in Kotlin we, as developers, create an extension function of ImageView to call Glide and put an image from server into it.

* **Android architecture lifecycle**: As I said before, this library allows me to persist data across configuration changes and notify data changes to the view.

* **Android support**: Nowadays it's impossible to create an app without this library and try to reach as many OS versions as possible. It doesn't require explanation :P

* And, of course, **Kotlin**.

## Other comments

I've tried to put in the code all Kotlin knowledge I have. Of course this is an Android challenge, but as Cesar said:

> We're looking for a Senior Kotlin Developer, not only Android developer.

Don't misunderstand me, I think I'm a very good Android developer, but with this pull request I try to show you my knowledge about Kotlin and, maybe, how I think (abstract way) when I must develop code.

Please feel free to ask or comment me anything you want. This is a world where we can learn new things every day.