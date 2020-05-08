package com.hendraanggrian.prefy;

/**
 * Subinterface of {@link ReadablePreferences} that, in contrast to {@link EditablePreferences},
 * can directly modify preferences since it also inherits {@link PreferencesEditor}.
 */
public interface WritablePreferences extends ReadablePreferences, PreferencesEditor {
}
