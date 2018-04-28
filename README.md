# MxmSoundtrackList
Android application to handle all Tracks from MXM api.

# Clone this repo
First of all clone this repo typing `git clone <github-project-url>`

# Building using Android Studio
- Open Android Studio and launch the Android SDK manager from it (Tools | Android | SDK Manager)

Check:

- Android SDK Platform Tools
- Android Support Library

On Android Studio select:

- Import Project from Gradle

# Java and Android Version support
This project is using Java 8 code with lambda expressions

Project SDK support: 

- Android 8.0 (API 27)
- minSdkVersion = 19
- targetSdkVersion = 27
# Java and Android Version support
This project is using Java 8 code with lambda expressions, method references and try-with-resources statements, but support as well java 7, thanks to Retrolambda open source library [Retrolambda Github](https://github.com/orfjackal/retrolambda)

# Third Part libraries

- **retrofit** (API handler)
- **okhttp** (API handler)
- **gson** (API handler)
- **rxjava2** (Network requests manager)
- **glide** (Image loader)
- **dagger2** (Dependencies Injection)
- **butterknife** (Views injection)

# Application details
The application under test is located in src/main/java
Tests are in src/androidTest/java

To test this project you have to:

- Create the test configuration with a custom runner: android.support.test.runner.AndroidJUnitRunner
- Open Run menu | Edit Configurations
- Add a new Android Tests configuration
- Choose a module
- Add a Specific instrumentation runner: android.support.test.runner.AndroidJUnitRunner

# Support
You can just write me by email:
davide.lamanna.86@gmail.com 
or by Google+ [Davide La Manna](https://plus.google.com/u/1/+davidelamanna86?hl=it)

# Licence
Under apache2 licence [Apache2 licence](https://www.apache.org/licenses/LICENSE-2.0)
