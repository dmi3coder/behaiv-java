# Behaiv: User Behavior Prediction for everyone
![Behaiv logo](https://github.com/dmi3coder/behaiv-java/raw/master/docs/img/behaiv_logo.png)

[![Build Status](https://travis-ci.com/dmi3coder/behaiv-java.svg?branch=master)](https://travis-ci.com/dmi3coder/behaiv-java) [![Coverage Status](https://coveralls.io/repos/github/dmi3coder/behaiv-java/badge.svg?branch=master)](https://coveralls.io/github/dmi3coder/behaiv-java?branch=master) [![](https://jitpack.io/v/dmi3coder/behaiv-java.svg)](https://jitpack.io/#dmi3coder/behaiv-java) Java version ([Not Java?](https://github.com/dmi3coder/behaiv-java#looking-for-other-languages))

## Wow, you've found Behaiv!
Behaiv is a high-level AI tool that helps users to navigate faster through the app, 
predict what users want to open and take appropriate actions. 
It was developed with a focus on simplicity of usage. 
Instead of overwhelming developers with burden of implementation, they only need to write few lines of code.

(Largely inspired by app suggestion in IOS.)
![Chat example](https://github.com/dmi3coder/behaiv-java/raw/master/docs/img/behaiv_usage.png)

## Getting started: 30 seconds of Behaiv
Main object in Behaiv is, in fact, `Behaiv`.
 This is the class that you'll access all the time.
  To init this class, use a static method `Behaiv.with(id)` where id is unique value that will be used to restore your data on restart.
   Behaiv itself is a semi-builder, you don't need to call `.build()` on it.
In upcoming version you'll be able to add more providers any time.

```java
Behaiv behaiv = new Behaiv.Builder("myId")
                .setProvider(new DayTimeProvider())
                // add more providers
                .setStorage(new SimpleStorage(storageDirectoryFile))
                .build();
```
Done, in order to start capturing user's behavior you need to call next methods: 
* `behaiv.startCapturing(predict)` in order to start capturing parameters for prediction, it takes optional variable to trigger prediction, if it's false, it won't predict.
`behaiv.startCapturing()` will trigger providers to get all the data needed for prediction.
* `behaiv.registerLabel(label)` to attach label or result of parameters. Without it data won't be saved.
* `behaiv.stopCapturing(discard)` to save and train network if threshold is reached.

```java
behaiv.startCapturing(true);

//user takes actions
behaiv.registerLabel("Lili_12345 chat")
behaiv.stopCapturing(false)
```

In order to use prediction, you need to subscribe for them. If behaiv.startCapturing(**false**) was used, you won't receive prediction 

```java
//call this before behaiv.startCapturing(true);
behaiv.subscribe().subscribe(predictionLabel -> {
            // snow prediction on ui
        });
``` 

That's all, everything else is handled by Behaiv

For more in-depth tutorial about Behaiv you can checkout:

 * [Why user behavior prediction is the next big thing in mobile and web?](https://becominghuman.ai/why-user-behavior-prediction-is-the-next-big-thing-in-mobile-and-web-a2e2537dc658) in becominghuman.ai
### How to use in Android?
 
 1. [Add dependency as in gradle section](https://github.com/dmi3coder/behaiv-java#gradle)
 1. Instantiate `Behaiv.Builder` object in `Application` with `new Behaiv.Builder(id)`
 1. If needed, change Kernel type with `.setKernel(Kernel)` on a Builder
     1. <s>`DummyKernel` doesn't do any computations, only suggest most similar result by comparing count of steps</s>(deprecated)
     1. `LogisticRegressionKernel` uses Logistic Regression to predict actions.
     1. `RemoteKernel`(not implemented yet), sends data to a API, depending on type of an API receives suggested action or receives model
 1. Provide external factors with `Builder.setProvider()`such as GPS, Wifi/Bluetooth and headphons info.
     1. Use `DayTimeProvider` to use time of a day as a features
     1. Use `GpsProvider` for adding GPS feature into prediction from [behaiv-android](https://github.com/dmi3coder/behaiv-android)
     1. Use `NetworkProvider` for adding Wifi, Bluetooth and Network features into prediction(TODO)
     1. Use `HeadsetProvider` to include headphones feature into predcition(TODO)
     1. There's more options like Weather and Firebase provides, see more at (TODO)
     1. Use `SyntheticProvider` to include custom self-made features into prediction (e.g)(TODO, can be implemented easily)
 1. Build `Behaiv` object by calling `Builder.build()`    
 1. Set threshold after which Behaiv can start suggesting with `.setThreshold(int)`
 1. Subscribe to predictions in main view e.g `MainActivity` with `behaiv.subscribe().subscribe(predictionLabel -> {})`
     1. When prediction received, do a switch/when and show option for user to open
 1. When view is ready for usage by user, call `behaiv.startCapturing(true)` e.g in `onViewCreated()` or `onCreate()`
 1. Find objects to target(e.g chats with user) and when user clicks on target, execute `behaiv.registerLabel(String)`
 1. At the same time call `behaiv.stopCapturing(false)` if you're sure that it's correct action.

Model uses "Continuous learning" so it will be trained as long as more features will arrive. To see model in action you need to wait amount of examples reach threshold.

## Installation
We use jitpack for now. Beware that in near future we can migrate to different maven repository.
Add it in your root build.gradle at the end of repositories:

Latest tag is [![](https://jitpack.io/v/dmi3coder/behaiv-java.svg)](https://jitpack.io/#dmi3coder/behaiv-java)
### Gradle
Step 1. Add the JitPack repository to your build file

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.dmi3coder:behaiv-java:0.4.10-alpha'
	}

### Maven
Step 1. Add the JitPack repository to your build file

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
Step 2. Add the dependency

	<dependency>
	    <groupId>com.github.dmi3coder</groupId>
	    <artifactId>behaiv-java</artifactId>
	    <version>0.4.10-alpha</version>
	</dependency>
	
Look at more information about build [on Jitpack](https://jitpack.io/#dmi3coder/behaiv-java/)

### Want to help?
Behaiv project is really open for help. If you want to help this project to shine, I firstly suggest you to contact [me](https://github.com/dmi3coder)(Dmytro Chaban) by any convinient way e.g email. Second thing is to look at [issues](https://github.com/dmi3coder/behaiv-java/issues). Another way is to spread this project to your friends and contacts, it'll help bring more developers to this project. Reporting issues with project wont hurt also ;)

### Looking for other languages?
Behaiv has different implementations(though many of them is in progress of development). 

| Project | Language | Version/Arriving date | Developed by |
|---------|----------|---------|--------------|
|[behaiv-android](https://github.com/dmi3coder/behaiv-android)(extension)| Java | [![](https://jitpack.io/v/dmi3coder/behaiv-android.svg)](https://jitpack.io/#dmi3coder/behaiv-android)| dmi3coder|
|[behaiv-swift](https://github.com/donautech/behaiv-swift)| Swift |2Q 2020|DonauTech & dmi3coder|
|[behaiv-remote-kernel](https://github.com/donautech/behaiv-remote-kernel)| Python(Backend) | [![Release](https://img.shields.io/github/v/release/donautech/behaiv-remote-kernel?include_prereleases)](https://github.com/donautech/behaiv-remote-kernel/releases) |DonauTech & dmi3coder|
|[behaiv-js]()| JavaScript |2Q 2020|DonauTech & dmi3coder
