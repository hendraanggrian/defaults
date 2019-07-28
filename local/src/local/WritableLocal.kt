package local

interface WritableLocal : ReadableLocal {

    val writer: LocalWriter
}
