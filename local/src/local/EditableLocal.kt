package local

interface EditableLocal : ReadableLocal {

    val writer: LocalWriter
}
