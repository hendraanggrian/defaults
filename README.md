[![bintray](https://img.shields.io/badge/bintray-defaults-brightgreen.svg)](https://bintray.com/hendraanggrian/defaults)
[![download](https://api.bintray.com/packages/hendraanggrian/defaults/defaults/images/download.svg) ](https://bintray.com/hendraanggrian/defaults/defaults/_latestVersion)
[![build](https://travis-ci.com/hendraanggrian/defaults.svg)](https://travis-ci.com/hendraanggrian/defaults)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Defaults
========
![icon](/art/ic_launcher_small.png)

Local settings library for JVM and Android.

Download
--------

```gradle
repositories {
    jcenter()
}
dependencies {
    // features, mixing may be supported
    compile "com.hendraanggrian.defaults:defaults-jre:$version"
    compile "com.hendraanggrian.defaults:defaults-android:$version"
    
    // optional annotation processor
    annotationProcessor "com.hendraanggrian.defaults:defaults-compiler:$version" // or kapt
}
```

Usage
-----

#### Defaults and editor

Create defaults instance from `File`, or `SharedPreferences` in Android.

```kotlin
import com.hendraanggrian.defaults.Defaults

val fileDefaults = Defaults.from(mFile)
val androidDefaults = Defaults.from(mContext)

val name = fileDefaults["name"]
val age = fileDefaults.getInt("age", 0)

androidDefaults {
    set("name", "Hendra")
    set("age", 25)
}
```

#### Defaults saver

With optional annotation processor, bind these local settings to local variables.

```kotlin
import com.hendraanggrian.defaults.Defaults
import com.hendraanggrian.defaults.bindDefaults

@Bind lateinit var name: String
@Bind @JvmField var age: Int = 0

init {
    bindDefaults(Defaults.from(mFile))
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