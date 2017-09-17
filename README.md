# A2I Feedback SDK for Android

The A2I Mobile Feedback SDK gives you all the tools you need to collect feedback from your consumers via your app.


### How It Works

1. Log in to A2I and create surveys.
2. Integrate the survey into your mobile app using our [mobile SDK]
3. Get feedback in real time. 


## Steps To Integrate sdk
### Step 1: Add the jitpack.io repository to your main build.gradle file

```
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```
Note: do not add the jitpack.io repository under `buildscript`

### Step 2: Add dependency for A2I Survey Sdk to your app's build.gradle file

```
dependencies {
    compile 'com.github.a2ianalytics:a2i-survey-sdk:v1.0.1'
}
```

### Step 3: Initialize the SDK in activity
```java
private A2I_Handler a2IHandler = new A2I_Handler();
```

#### The Intercept Modal 
To kick off the A2I Feedback SDK Intercept process, call the following from your main activity:
```java
a2IHandler.onCreate(this, [DEFAULT_SURVEY_CODE], [TITLE], [MESSAGE_TEXT_FOR_SURVEY_DIALOG], [REQUEST_CODE]);
```
OR

```java
a2IHandler.onCreate(this, [DEFAULT_SURVEY_CODE], [TITLE], [MESSAGE_TEXT_FOR_SURVEY_DIALOG], [REQUEST_CODE], [AFTER_INSTALL_INTERVAL], [AFTER_DECLINE_INTERVAL], [AFTER_ACCEPT_INTERVAL]);
```

#### Presenting a Survey to the User
To present a survey for the user to take, call:
```java
a2IHandler.startSurveyActivityForResult(this, [DEFAULT_SURVEY_CODE], [TITLE], [REQUEST_CODE]);
```

#### Opening Dashboard for User
To open dashboard, call:
```java
a2IHandler.startDashboardActivity(this, [DEFAULT_DASHBOARD_CODE], [TITLE]);
```

#### Issues and Bugs
Please submit any issues with the SDK to us via Github issues. We strive to fix bugs as quickly as possible. We plan to add cocoapods integration in the coming months. Watch our Github repo to stay up to date for new features.

#### FAQ
*What can I use the mobile SDK for?*

###### Measure overall satisfaction
Based on surveys you've created in A2I, you can get real-time feedback from the users on your app. Based on their feedback, you can tailor the rest of their in-app experience. For example, if a user reports a moderate level of satisfaction, you can ask them a follow-up question to identify the problem and prioritize a solution.

###### Conduct real-time product research
The best way to perform user research is to listen. A product manager can quickly find out which features a user is yearning for, and which features aren’t meeting expectations. Based on the findings, a product team can adjust roadmaps to be more responsive for their growing mobile user base and don’t have to passively wait for an app store review to see what is and isn’t working.

*How can I prompt users to give feedback?*

###### Random polling
You can set up predefined time intervals to prompt for in-app feedback from a random sample of your users. This is referred to as a “scheduled intercept” in the developer documents. For example, you can set the in-app feedback to prompt a user within 3 days after they install or update the app. If the user selects “Give Feedback,” you don’t show the prompt for 3 months. If the user selects “Not Now,” you prompt them again in 3 weeks. The time intervals are completely customizable by the developer.

###### Event-based triggers
You can prompt the user for in-app feedback if they visit a certain part of the app or click a specific button. For example, if you notice users dropping out of your checkout screen, you can ask them why in real time. This information can lead to key product insights, such as moving certain fields around to better align with your customers’ priorities.

###### Passive feedback
Many apps have a slide-out menu that allows their users to access a variety of items such as Account, About Us, or Help. At A2I, we’ve included Feedback in our slide-out menu to passively prompt users for feedback. You can incorporate this into your own app so users can provide feedback whenever they want.


*How can I style the survey?  How will it look on a mobile device?*

As with all A2I surveys, you have the ability to customize the look and feel of your survey to match your mobile app.  The survey page is mobile responsive so you can use the SDK on both smartphone and tablet devices.


If you close or delete a collector in A2I, your users will not see a prompt for in-app feedback.

*Is the survey native to the app?*

Yes, although the SDK requires an internet connection to load the survey. If the user’s device is offline he/she will not be prompted to take the survey.

*I have an app on Android and iOS. How many mobile collectors do I need to create?*

You can create one survey and set up multiple collectors to help you track where responses are coming from, i.e. one for your Android app and one for your iOS app.
