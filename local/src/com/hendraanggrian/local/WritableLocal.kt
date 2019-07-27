package com.hendraanggrian.local

interface WritableLocal : ReadableLocal {

    val writer: LocalWriter
}
