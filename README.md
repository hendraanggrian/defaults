[![download](https://api.bintray.com/packages/hendraanggrian/prefy/prefy/images/download.svg) ](https://bintray.com/hendraanggrian/prefy/prefy/_latestVersion)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![license](https://img.shields.io/github/license/hendraanggrian/prefy)](http://www.apache.org/licenses/LICENSE-2.0)

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

[ButterKnife]: https://github.com/JakeWharton/butterknife
