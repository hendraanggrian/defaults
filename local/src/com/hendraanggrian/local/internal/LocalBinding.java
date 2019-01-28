package com.hendraanggrian.local.internal;

import com.hendraanggrian.local.LocalSettings;
import com.hendraanggrian.local.Saver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public abstract class LocalBinding implements Saver {

    @SuppressWarnings("WeakerAccess") protected final LocalSettings<?> source;

    protected LocalBinding(@NotNull LocalSettings<?> source) {
        this.source = source;
    }

    @Nullable
    protected String getValue(@NotNull String key, @Nullable String defaultValue) {
        return source.getString(key, defaultValue);
    }

    protected int getValue(@NotNull String key, int defaultValue) {
        return source.getInt(key, defaultValue);
    }

    protected LocalSettings.Editor getEditor() {
        return source.getEditor();
    }

    protected void setValue(
        @NotNull LocalSettings.Editor editor,
        @NotNull String key,
        @Nullable String value
    ) {
        editor.setString(key, value);
    }

    protected void setValue(
        @NotNull LocalSettings.Editor editor,
        @NotNull String key,
        int value
    ) {
        editor.setInt(key, value);
    }
}