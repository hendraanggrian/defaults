[![bintray](https://img.shields.io/badge/bintray-prefs-brightgreen.svg)](https://bintray.com/hendraanggrian/prefs)
[![download](https://api.bintray.com/packages/hendraanggrian/prefs/prefs/images/download.svg) ](https://bintray.com/hendraanggrian/prefs/prefs/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

Prefs
=====
![icon](/art/prefs-small.png)

Local settings library that runs in plain Java and Android.
Comes with optional annotation processor to bind properties with existing settings.

Download
--------
All artifacts should be linked to JCenter, otherwise add maven url `https://dl.bintray.com/hendraanggrian/prefs`.

```gradle
repositories {
    jcenter()
}

dependencies {
    // for non-Android project
    compile "com.hendraanggrian.prefs:prefs-jvm:$version"
    
    // for Android project
    api "com.hendraanggrian.prefs:prefs-android:$version"
    api "com.hendraanggrian.prefs:prefs-android-snappydb:$version" // snappydb extension

    // property binding support, use kapt when necessary
    annotationProcessor "com.hendraanggrian.prefs:prefs-compiler:$version"   
}
```

Usage
-----

#### Manual get/set
Create defaults instance from `File`, or `SharedPreferences` in Android.

```kotlin
import com.hendraanggrian.prefs.Prefs

// file defaults can set/get
val filePreferences = Prefs.of(file)
val name = filePreferences["name"]
val age = filePreferences.getInt("age", 0)
filePreferences["name"] = "Hendra"
filePreferences["age"] = 25

// shared preferences must open to set
val androidPreferences = Prefs.of(context)
androidPreferences {
    it["name"] = "Hendra"
    it["age"] = 25
}
```

#### Bind properties
With optional annotation processor, bind these local settings to local variables.

```kotlin
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.PrefsSaver

@BindPref lateinit var name: String
@BindPref @JvmField var age: Int = 0

lateinit var saver: PrefsSaver

init {
    saver = Prefs.bind(file, this)
}

fun applyChanges(name: String, age: Int) {
    this.name = name
    this.age = age
    saver.saveAsync()
}
```

License
-------
    Copyright 2019 Hendra Anggrian

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
