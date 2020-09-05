# Eh-Ho-Android

[![forthebadge](https://forthebadge.com/images/badges/built-for-android.svg)](https://forthebadge.com)

> Eh-Ho-Android is a practice of 'fundamentos-android' for KeepCoding Mobile 10

![Splash](/screenCaptures/login.png)
![Topics](/screenCaptures/topics.png)
![Users](/screenCaptures/posts.png)

## Application Structure

Little Android client for consume `https://mdiscourse.keepcoding.io/` API

[Kotlin](https://kotlinlang.org/) app.

App Features:

###### Sign Up and Sign In
   - Change between sign up and sign in view with validations on the forms
   - Auto-login saving user on SharedPreferences
   - Logout

###### Topics
   - List recent topic (Swipe to refresh)
   - Create new topic
   - Loading placeholder while load topics
   - Retry system when load topics fails
   - Animated floating button when scroll topic list

###### Posts
   - List posts of a specific topic (Swipe to refresh)
   - Create new post for a topic
   - Loading placeholder while load posts
   - Retry system when load posts fails

###### UnitTest
   - Mapping Json to objects
   - Mapping objects to Json

##### Author
> Javier Laguna
