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
    implementation 'com.github.dhavanikgithub:attendance_moodle_api_lib:1.2.3'
}
```

### Change SDK in build.gradle(app) file

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
