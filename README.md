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
