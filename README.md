# moviesAppChallenge

## Architecture

I've used a Clean architecture because of it's simplicity. This is a small app with no special requirements. In addition, I've used Android architecture lifecycle classes (like `ViewModel` and `LiveData`) to persist data across configuration changes and notify changes to the previous layer.

Special mention to `UseCase` class, which contains all logic to run background jobs and notify the result to it's invoker. I've used WorkManager from Android Jetpack libraries. With few lines of code, you can enqueue a background job and be notified when it's finished. It has a limitation about which type of params can be used as IO, but I've solved it mapping the input/output object to a JSON representation of it and then creating the original object again.

## Testing

I'm sorry to inform that right now there are not test (unit nor instrumentation).

## UI/UX

This is my... Anchilles hell? I'm very very bad designing an interface. I know most of the platform design guidelines, but I haven't creativity with colors, forms, where to show this or that data. I've created a simple list/detail screens using OMDB colors from their website.

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