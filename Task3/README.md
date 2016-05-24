# Yalantis Android Internship - Task #3
* Client-Server Application with a Model View Presenter (MVP) Architecture, Database, Retrofit and RxJava.
* Used Realm database this time instead of SQLite.

![Animated final screen](materials/yalantis_task3.gif)

## Requirements
* [Technical specifications from Yalantis.](materials/YalantisAndroidInternship.Task3.pdf)
* The app should store data in a database of your choice: realm, native SQL, or sqlbrite.
* Facebook login. The app should save profile into the database and save access_token. 
* Profile page with a user name and photos.
* MVP architecture.
* API to get data: http://dev-contact.yalantis.com/rest/v1/tickets	
* Minimal SDK version = 16.
* No hardcoded values, use styles.
* App should conform to [google coding style](https://source.android.com/source/code-style.html).
* Only portrait orientation.

## Important
* You must add Google Maps API key for Maps Activity to work correctly.

## Used libraries
* [RxJava + RxAndroid.](https://github.com/ReactiveX/RxAndroid)
* DI with [Dagger 2.](https://github.com/google/dagger)
* [Realm database.](https://github.com/realm/realm-java)
* [Butterknife.](https://github.com/JakeWharton/butterknife)
* [Picasso.](https://github.com/square/picasso)
* [Retrofit.](https://github.com/square/retrofit)
* [Facebook SDK.](https://developers.facebook.com/docs/android/getting-started)
* [SmartTabLayout](https://github.com/ogaclejapan/SmartTabLayout) to have a round-shaped Tab indicator.
* [Material Dialogs](https://github.com/afollestad/material-dialogs) as AlertDialogs with multiple choice lists don't work correctly with Android 6+ ([see the error](https://code.google.com/p/android/issues/detail?can=2&start=0&num=100&q=&colspec=ID%20Status%20Priority%20Owner%20Summary%20Stars%20Reporter%20Opened&groupby=&sort=&id=208886)).

## Additionally implemented
* Maps Activity with task markers.
* Filtering of tasks.
* SharedTransitions, Zoom Out page transitions.

## Other
* Possible improvement: Lambda support to improve readability via Retrolambda plugin.
* Google Play Services Bug Acknowledged by Google: [link](http://stackoverflow.com/questions/37333220/googlesignatureverifier-signature-not-valid-message-not-using-the-google-maps-a)