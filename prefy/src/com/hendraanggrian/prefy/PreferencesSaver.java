package com.hendraanggrian.prefy;

/**
 * Interface used for applying changes made in {@link PreferencesEditor} object.
 * When using {@link Prefy#bind} to inject preferences' values to JVM fields,
 * {@link PreferencesSaver} is returned back to save changes made in JVM fields.
 */
public interface PreferencesSaver {

    PreferencesSaver EMPTY = new PreferencesSaver() {
        @Override
        public void save() {
            Prefy.warn("Saving an empty preferences.");
        }
    };

    /**
     * Commit your preferences changes back from {@link PreferencesEditor}
     * to the {@link ReadablePreferences} object it is editing.
     * This atomically performs the requested modifications,
     * replacing whatever is currently in the {@link ReadablePreferences}.
     */
    void save();
}
