[![bintray](https://img.shields.io/badge/bintray-lokal-brightgreen.svg)](https://bintray.com/hendraanggrian/lokal)
[![download](https://api.bintray.com/packages/hendraanggrian/lokal/lokal/images/download.svg) ](https://bintray.com/hendraanggrian/lokal/lokal/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Lokal
=====
![icon](/art/lokal-small.png)

Local settings library that runs in plain Java and Android.
Comes with optional annotation processor to bind properties with existing settings.

##### JVM

```kotlin
val lokal = preferences.toLokal()
val username = lokal["username"]
```

##### Android

```kotlin
val lokal = toLokal() // if this is context/activity, or use `sharedPreferences.toLokal()`
val username = lokal["username"]
```

Download
--------
All artifacts should be linked to JCenter, otherwise add maven url `https://dl.bintray.com/hendraanggrian/defaults`.

```gradle
repositories {
    jcenter()
}
dependencies {
    compile "com.hendraanggrian.lokal:lokal:$version"
    api "com.hendraanggrian.lokal:lokal-android:$version" // for Android

    // optional property binding, use kapt when necessary
    annotationProcessor "com.hendraanggrian.lokal:lokal-compiler:$version"
}
```

Usage
-----

#### Manual get/set

Create defaults instance from `File`, or `SharedPreferences` in Android.

```kotlin
import com.hendraanggrian.lokal.toLokal

// file defaults can set/get
val fileLokal = file.toLokal()
val name = fileLokal["name"]
val age = fileLokal.getInt("age", 0)
fileLokal["name"] = "Hendra"
fileLokal["age"] = 25

// shared preferences must open to set
val androidLokal = context.toLokal()
androidLokal {
    it["name"] = "Hendra"
    it["age"] = 25
}
```

#### Bind properties

With optional annotation processor, bind these local settings to local variables.

```kotlin
import com.hendraanggrian.lokal.LokalSaver
import com.hendraanggrian.lokal.BindLokal
import com.hendraanggrian.lokal.bindLokal

@BindLokal lateinit var name: String
@BindLokal @JvmField var age: Int = 0

lateinit var saver: LokalSaver

init {
    saver = file.bindLokal(this)
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
