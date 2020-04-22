[![bintray](https://img.shields.io/badge/bintray-prefy-brightgreen.svg)](https://bintray.com/hendraanggrian/prefy)
[![download](https://api.bintray.com/packages/hendraanggrian/prefy/prefy/images/download.svg) ](https://bintray.com/hendraanggrian/prefy/prefy/_latestVersion)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

Prefy
=====
Local settings library that runs in plain Java and Android.
* Retrieve and assign values with Kotlin operator functions.
* Annotation processor to bind preferences' values to JVM fields, similar to [ButterKnife].

```kotlin
@BindPreference lateinit var name: String
@BindPreference @JvmField var age: Int = 0

lateinit var saver: PreferencesSaver = Prefy.bind(this)

fun applyChanges(person: Person) {
    name = person.name
    age = person.age
    saver.save()
}
```

Download
--------
```gradle
repositories {
    jcenter()
}

dependencies {
    compile "com.hendraanggrian.prefy:prefy-jvm:$version" // for non-Android project
    api "com.hendraanggrian.prefy:prefy-android:$version" // for Android project

    // optional annotation processor
    annotationProcessor "com.hendraanggrian.prefy:prefy-compiler:$version" // use kapt when necessary
}
```

Usage
-----
Coming soon.

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

[ButterKnife]: https://github.com/JakeWharton/butterknife
