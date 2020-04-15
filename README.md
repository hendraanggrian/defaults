[![bintray](https://img.shields.io/badge/bintray-prefy-brightgreen.svg)](https://bintray.com/hendraanggrian/prefy)
[![download](https://api.bintray.com/packages/hendraanggrian/prefy/prefy/images/download.svg) ](https://bintray.com/hendraanggrian/prefy/prefy/_latestVersion)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

Prefy
=====
Local settings library that runs in plain Java and Android.
Comes with optional annotation processor to bind properties with existing settings.

Download
--------
All artifacts should be linked to JCenter, otherwise add maven url `https://dl.bintray.com/hendraanggrian/prefy`.

```gradle
repositories {
    jcenter()
}

dependencies {
    compile "com.hendraanggrian.prefy:prefy-jvm:$version" // for non-Android project
    api "com.hendraanggrian.prefy:prefy-android:$version" // for Android project

    // property binding support, use kapt when necessary
    annotationProcessor "com.hendraanggrian.prefy:prefy-compiler:$version"   
}
```

Usage
-----

#### Manual get/set
Create defaults instance from `File`, or `SharedPreferences` in Android.

```kotlin
import com.hendraanggrian.prefy.Prefy

// file defaults can set/get
val filePreferences = Prefy[file]
val name = filePreferences["name"]
val age = filePreferences.getInt("age", 0)
filePreferences["name"] = "Hendra"
filePreferences["age"] = 25

// shared preferences must open to set
val androidPreferences = Prefy[context]
androidPreferences {
    it["name"] = "Hendra"
    it["age"] = 25
}
```

#### Bind properties
With optional annotation processor, bind these local settings to local variables.

```kotlin
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.PreferencesSaver

@BindPref lateinit var name: String
@BindPref @JvmField var age: Int = 0

lateinit var saver: PreferencesSaver

init {
    saver = Prefy.bind(file, this)
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
