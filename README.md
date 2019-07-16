[![bintray](https://img.shields.io/badge/bintray-local-brightgreen.svg)](https://bintray.com/hendraanggrian/local)
[![download](https://api.bintray.com/packages/hendraanggrian/local/local/images/download.svg) ](https://bintray.com/hendraanggrian/local/local/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

Local
=====
![icon](/art/local-small.png)

Local settings library that runs in plain Java and Android.
Comes with optional annotation processor to bind properties with existing settings.

##### JVM

```kotlin
val local = preferences.toLoCal()
val username = local["username"]
```

##### Android

```kotlin
val local = toLoCal() // if this is context/activity, or use `sharedPreferences.toLocal()`
val username = local["username"]
```

Download
--------
All artifacts should be linked to JCenter, otherwise add maven url `https://dl.bintray.com/hendraanggrian/defaults`.

```gradle
repositories {
    jcenter()
}
dependencies {
    compile "com.hendraanggrian.local:local:$version"
    api "com.hendraanggrian.local:local-android:$version" // for Android

    // optional property binding, use kapt when necessary
    annotationProcessor "com.hendraanggrian.local:local-compiler:$version"
}
```

Usage
-----

#### Manual get/set

Create defaults instance from `File`, or `SharedPreferences` in Android.

```kotlin
import com.hendraanggrian.local.toLocal

// file defaults can set/get
val fileLocal = file.toLocal()
val name = fileLocal["name"]
val age = fileLocal.getInt("age", 0)
fileLocal["name"] = "Hendra"
fileLocal["age"] = 25

// shared preferences must open to set
val androidLocal = context.toLocal()
androidLocal {
    it["name"] = "Hendra"
    it["age"] = 25
}
```

#### Bind properties

With optional annotation processor, bind these local settings to local variables.

```kotlin
import com.hendraanggrian.local.BindLocal
import com.hendraanggrian.local.Local.Saver

@BindLocal lateinit var name: String
@BindLocal @JvmField var age: Int = 0

lateinit var saver: Local.Saver

init {
    saver = file.bindLocal(this)
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
