const val VERSION_COMMONS_LANG = "3.9"

fun Dependencies.apache(repo: String, module: String, version: String) =
    "org.apache.$repo:$module:$version"
