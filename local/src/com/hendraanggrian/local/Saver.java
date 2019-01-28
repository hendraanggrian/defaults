package com.hendraanggrian.local;

public interface Saver {

    void save();

    void saveAsync();

    Saver EMPTY = new Saver() {
        @Override
        public void save() {
        }

        @Override
        public void saveAsync() {
        }
    };
}
