package com.hendraanggrian.defaults.internal

import com.hendraanggrian.defaults.Defaults
import java.lang.ref.WeakReference

/**
 * Represents local settings that always has access to read and write values, therefore making
 * editor concept irrelevant.
 */
abstract class SimpleDefaults : Defaults<SimpleDefaults.Editor>, Defaults.Editor {

    private var editorRef = WeakReference<Editor>(null)

    final override fun getEditor(): Editor {
        var editor = editorRef.get()
        if (editor == null) {
            editor = Editor()
            editorRef = WeakReference(editor)
        }
        return editor
    }

    inner class Editor : Defaults.Editor by this
}