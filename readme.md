# Behaiv: User Behaviour Prediction for everyone
![Behaiv logo](https://github.com/dmi3coder/behaiv-java/raw/master/docs/img/behaiv_logo.png)

[![Build Status](https://travis-ci.com/dmi3coder/behaiv-java.svg?branch=master)](https://travis-ci.com/dmi3coder/behaiv-java) [![Coverage Status](https://coveralls.io/repos/github/dmi3coder/behaiv-java/badge.svg?branch=master)](https://coveralls.io/github/dmi3coder/behaiv-java?branch=master) [![](https://jitpack.io/v/dmi3coder/behaiv-java.svg)](https://jitpack.io/#dmi3coder/behaiv-java) Java version

## Wow, you've found Behaiv
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
Behaiv behaiv = Behaiv.with("myId")
                .setProvider(new DayTimeProvider())
                // add more providers
                .setStorage(new SimpleStorage(storageDirectoryFile));
```
Done, in order to start capturing user's behaviour you need to call next methods: 
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

 * Nothing yet. Maybe that's an opportunity to write about it? ;)

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
	        implementation 'com.github.dmi3coder:behaiv-java:Tag'
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
	    <version>Tag</version>
	</dependency>
	
Look at more information about build [on Jitpack](https://jitpack.io/#dmi3coder/behaiv-java/)
### How to use in Android?

1. Add dependency
1. Instantiate `BehAIv` object in *ApplicationContext*
1. Select one of a Kernel types
    1. `DummyKernel` doesn't do any computations, only suggest most similar result by comparing count of steps
    1. `RNNKernel` uses Recurrent Neural Network to specify actions.
    1. `RemoteKernel`, sends data to a API, depending on type of an API receives suggested action or receives model
1. Set threshold after which Behaiv can start suggesting
1. Register each view that can be tracked and opened by implementing interfaces
    1. `InitiableNode` is a type of view that can initiate capturing of features
    1. `ActionableNode` is a type of view that can be opened by *Behaiv*, as well as capture labels
    1. `RoutableNode` is a type of View that only cannot be opened but only route into next Views
    1. `ConditionableNode` is a type of View which will perform additional actions before proceeding
1. Provide external factors sich as GPS, Wifi/Bluetooth and headphons info.
    1. Use `GeoProvider` for adding GPS feature into prediction
    1. Use `NetworkProvider` for adding Wifi, Bluetooth and Network features into prediction
    1. Use `HeadsetProvider` to include headphones feature into predcition
    1. There's more options like Weather and Firebase provides, see more at (TODO)
    1. Use `SyntheticProvide` to include custom self-made features into prediction (e.g )
1. Model uses "Continuous learning" so it will be trained as long as more features will arrive. To see model in action you need to wait amount of examples reach threshold.

   
