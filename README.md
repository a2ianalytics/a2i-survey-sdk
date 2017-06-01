# A2I Survey Sdk for Android
A2I Survey Sdk with sample application for Android

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

### Step 4: In onCreate of activity 
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

#### Opening Dahsboard for User
To open dashboard, call:
```java
a2IHandler.startDashboardActivity(this, [DEFAULT_DASHBOARD_CODE], [TITLE]);
```
