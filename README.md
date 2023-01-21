### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

`
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
`

### Step 2. Add the dependency

dependencies {
`implementation 'com.github.dhavanikgithub:attendance_moodle_api_lib:1.0.1'`
}

# change sdk in gradle file

`targetSdk 33`

`compileSdk 33`

# About Library
Frontend team use only two methods
<ol>
  <li>getMoodleUserID</li>
  <li>getMoodleUserCoursesList</li>
</ol>

#### getMoodleUserID
##### parameters
- Application Contex
- username [String]
- semester [Integer]
- call back function [ServerCallBack]

##### success response
- [{"id":"userid"}] 

Example:- [{"id":10}]

We can use the getMoodleUserID function to authenticate the faculty; if an ID is found, the faculty is authorized.

#### getMoodleUserCoursesList
##### parameters
- Application Contex
- username [String]
- semester [Integer]
- call back function [ServerCallBack]

##### success response
- [{"course1_name":"course1_id","course2_name":"course2_id",...}] 

Example:- [{"6CEIT_A-UED":1,"6CEIT-A_CC":2,...}]
