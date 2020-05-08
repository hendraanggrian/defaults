package com.hendraanggrian.prefy;

/**
 * Subinterface of {@link ReadablePreferences} that, in contrast to {@link WritablePreferences},
 * requires an instance of {@link PreferencesEditor} to modify preferences.
 */
public interface EditablePreferences<E extends PreferencesEditor> extends ReadablePreferences {

    /**
     * Returns an editor to modify preferences.
     */
    E getEditor();
}
