Spring Speak
===
Spring Speak is an extension to the Spring Framework that enables the creation of browser-based voice experiences.

> IMPORTANT:
> Spring Speak is a personal experimental project. Issues and pull requests are
> very welcome, but the project is in no way supported by the Spring engineering
> team or by Craig Walls. Issues and pull requests may be accepted, addressed,
> and merged as time and opportunity allow, but nothing is promised nor should
> be expected.

Building Spring Speak
---
Spring Speak is not (yet) available in any Maven repository. Therefore, you will need to build Spring Speak yourself and install it into your local Maven cache or into your own Maven repository.

Use the Maven wrapper to build Spring Speak and place it into your local Maven cache:

~~~
$ ./mvnw install
~~~

Spring Speak Components
---
Spring Speak offers three primary components:

 * The Natural Language Understanding (NLU) controller
 * A NLU Service interface and an Amazon Lex based implementation of that service.
 * An intent handler registry
 * An intent handler interface that can be implemented to handle intents resulting from the NLU service
 * A JavaScript library that handles browser client boilerplate for submitting requests

By default, the `NLUController` handles POST requests to /nlu. The request should contain the text to be passed to the NLU service, along with a confidence level indicating how confident that the text given was accurately understood by the browser's speech recognition. In JSON, that request might look like this:

~~~
{
  "text": "hello world",
  "confidence": 0.9
}
~~~

The path to the `NLUController` can be overridden in Spring configuration by setting `nlu.controller.path`. For example, this snippet from application.yml shows how you might change the path to /speech:

~~~
nlu:
  controller:
    path: speech
~~~

The `NLUController` will pass the text from the request to the `NaturalLanguageService` for evaluation. Among other things, the response from the `NaturalLanguageService` includes the intent and any slot values evaluated from the given text.

The `NLUController` will then seek out an `IntentHandler` implementation in the `IntentHandlerRegistry` whose `canHandle()` method returns `true`. Typically, the `canHandle()` method will determine if it can handle the request based on the intent name and other factors, but it could also make its determination on other factors potentially unrelated to the intent.

As an example, here is a `canHandle()` method that indicates that an `IntentHandler` can handle an "OrderFlowers" intent:

~~~
@Override
public boolean canHandle(RecognizedText recognizedText) {
  return recognizedText.sessionState().intent().name().equals("OrderFlowers");
}
~~~

Regardless of the criteria defined in the `canHandle()` function, the first `IntentHandler` that returns `true` will be given the opportunity to handle the request. If no `IntentHandler` returns `true`, then the request will go to a default `FallbackIntentHandler`.

If an `IntentHandler` is chosen to handle a request, then its `handle()` method will be invoked. The `handle()` method can do whatever is needed to handle the request, but it must return a `HandlerResponse`. At this time, the `HandlerResponse` is merely a carrier for the text to be spoken in the browser in response to the intent.

For example, here's an intent handler that simply replies with "Hello world":

~~~
@Override
public HandlerResponse handle(RecognizedText recognizedText) {
  return new HandlerResponse("Hello world!");
}
~~~

Amazon Lex allows for intents to be defined explicitly with response text. To use the text associated with the matched intent instead of handler-defined text, pull the message from the recognized text:

~~~
return new HandlerResponse(recognizedText.messages().get(0).content());
~~~

> NOTE: The intent handler model employed by Spring Speak closely mirrors the
> intent handler model prescribed by the Alexa Skills Kit, the SDK for
> creating voice experiences for Amazon Alexa. If you are familiar with how to
> create Alexa skills for Alexa, then creating intent handler for Spring Speak
> should be very familiar.

JavaScript library
---
Setting up the browser client to recognize speech and to speak results isn't difficult, but does involve a lot of boilerplate. To avoid repeating this boilerplate code, Spring Speak offers a JavaScript library that can be used in your application's HTML. This JavaScript library is served by the `NLUController` and can be referenced at the path /nlu/speech.js. For example:

~~~
<script lang="javascript" src="/nlu/speech.js"></script>
~~~

TODO: Describe how to initialize the JavaScript library.

Examples
---
A Spring Speak application built on top of the OrderFlowers Lex bot is in the /examples directory. See the README file in that directory for details on how to run and use the application.
