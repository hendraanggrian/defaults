package com.hendraanggrian.local

interface EditableLocal : ReadableLocal {

    val editor: WritableLocal
}
