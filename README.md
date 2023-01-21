### Step 1. Add the JitPack repository to your build file

Add it in your root settings.gradle at the end of repositories:

```ruby
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2. Add the dependency build.gradle(app) file

```ruby
dependencies {
    implementation 'com.github.dhavanikgithub:attendance_moodle_api_lib:1.0.4'
}
```

# Change SDK in build.gradle(app) file

```ruby
android {
    compileSdk 33

    defaultConfig {
        ...
        targetSdk 33
        ...
    }
}
```

# About Library
Frontend team use only two methods
<ol>
  <li>getMoodleUserID</li>
  <li>getMoodleUserCoursesList</li>
</ol>

#### getMoodleUserID
##### parameters
1. Application Context
2. username [String]
3. call back function [ServerCallBack]

##### success response
- [{"id":"userid"}] 

    Example:- [{"id":"10"}]

We can use the getMoodleUserID function to authenticate the faculty; if an ID is found, the faculty is authorized.

#### getMoodleUserCoursesList
##### parameters
1. Application Context
2. username [String]
3. call back function [ServerCallBack]

##### success response
- [{"course1_name":"course1_id","course2_name":"course2_id",...}] 

    Example:- [{"6CEIT_A-UED":"1","6CEIT-A_CC":"2",...}]

#### Sample Code How to call function
```ruby
val attRepo = MoodleController.getAttendanceRepository(URL,CORE_TOKEN,ATTENDANCE_TOKEN)
attRepo.getMoodleUserID(this,"username",object:ServerCallback{
    override fun onSuccess(result: JSONArray) {
        //is success
    }

    override fun onError(result: String) {
        //is error
    }

})
```

#### Frontend Team Input

- URL
- CORE_TOKEN
- ATTENDANCE_TOKEN
- Username
- Application Context
