# Yalantis Android Internship Task #2
* Application with a Pager and a Navigation Drawer.

![Animated final screen](yalantis_task2.gif)

## Requirements
* [Screen Design from Specifications](task2_screen_initial.png)
* Create a screen with controls according to the specification.
* The Application should contain 3 Tabs and a Navigation Drawer.
* App should have at least 10 data entries.
* Minimal SDK version = 16.
* No hardcoded values, use styles.
* App should conform to [google coding style](https://source.android.com/source/code-style.html).
* 1 & 2 Tabs should use RecyclerView, 3 Tab - ListView.
* On item click - open first task [Details Activity](../YalantisInternship/).
* Floating Action Button hide on swipe with animation.
* Only portrait orientation.

## Additionally implemented
* ContentProvider, Loader to store data.
* Retrofit client (singleton) to fetch data.
* MapsActivity (don't forget to add Google Maps API key).
* Filtering of tasks.
* ZoomOut transitions between Fragments.
* SharedTransitions between activities.

## Possible improvements
* Make Navigation Drawer narrower (wrap_content).
* Change icons to density independent (place, list, menu).

## Important
* For the MapsActivity to work you must add api key to res/values/google_maps_api.xml as a
\<string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">API KEY\</string>

## Link to used resources
* Launcher icon: http://gsg.usc.edu/sites/gsg.usc.edu/files/yellow_arrow_circle.png
* Gear icon: http://plainicon.com/download-icon/45939/wrench-gear