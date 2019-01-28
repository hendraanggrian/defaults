package com.hendraanggrian.local.internal;

import com.hendraanggrian.local.LocalSettings;
import com.hendraanggrian.local.Saver;

public abstract class LocalBinding implements Saver {

    protected final LocalSettings<?> source;

    protected LocalBinding(LocalSettings<?> source) {
        this.source = source;
    }

    protected LocalSettings.Editor getEditor() {
        return source.getEditor();
    }

    protected void setValue(
        LocalSettings.Editor editor,
        String key,
        String value
    ) {
        editor.setString(key, value);
    }

    protected void setValue(
        LocalSettings.Editor editor,
        String key,
        int value
    ) {
        editor.setInt(key, value);
    }
}