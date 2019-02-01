[![bintray](https://img.shields.io/badge/bintray-defaults-brightgreen.svg)](https://bintray.com/hendraanggrian/defaults)
[![download](https://api.bintray.com/packages/hendraanggrian/defaults/defaults/images/download.svg) ](https://bintray.com/hendraanggrian/defaults/defaults/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Defaults
========
![icon](/art/defaults-small.png)

Local settings library that runs in plain Java and Android.
Comes with optional annotation processor to bind properties with existing settings.

```kotlin
val defaults = Defaults[source]
val username = defaults["username"]
```

Download
--------

```gradle
repositories {
    jcenter()
}
dependencies {
    // features, usually pick one of these
    compile "com.hendraanggrian.defaults:defaults-jre:$version"
    compile "com.hendraanggrian.defaults:defaults-android:$version"

    // optional property binding, use kapt when necessary
    annotationProcessor "com.hendraanggrian.defaults:defaults-compiler:$version"
}
```

Usage
-----

#### Defaults and editor

Create defaults instance from `File`, or `SharedPreferences` in Android.

```kotlin
import com.hendraanggrian.defaults.Defaults

// file defaults can set/get
val fileDefaults = Defaults[file]
val name = fileDefaults["name"]
val age = fileDefaults.getInt("age", 0)
fileDefaults["name"] = "Hendra"
fileDefaults["age"] = 25

// shared preferences must open to set
val androidDefaults = Defaults[context]
androidDefaults {
    it["name"] = "Hendra"
    it["age"] = 25
}
```

#### Defaults saver

With optional annotation processor, bind these local settings to local variables.

```kotlin
import com.hendraanggrian.defaults.Defaults
import com.hendraanggrian.defaults.bindDefaults

@BindDefault lateinit var name: String
@BindDefault @JvmField var age: Int = 0

init {
    bindDefaults(Defaults[file])
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