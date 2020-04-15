include("prefy")
include("prefy-annotations")
include("prefy-compiler")
include("prefy-jvm")
include("prefy-android")

include("website")
includeDir("example")

fun includeDir(path: String) = file(path)
    .listFiles()!!
    .filter { it.isDirectory }
    .forEach { include("$path:${it.name}") }
