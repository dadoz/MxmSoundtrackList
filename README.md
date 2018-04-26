# GithubStargazers
Android application to handle all stargazer that are following you.

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
This project is using Java 8 code with lambda expressions, method references and try-with-resources statements, but support as well java 7, thanks to Retrolambda open source library [Retrolambda Github](https://github.com/orfjackal/retrolambda)

Project min SDK support: 

- Android 5.0 (Android Lollipop)

# Testing with Espresso
I used Espresso [Espresso doc](https://google.github.io/android-testing-support-library/docs/index.html) as support library to manage project test suites

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
