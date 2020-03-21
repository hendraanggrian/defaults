package com.hendraanggrian.prefs

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFails

class BindingTest {

    @BeforeTest fun initial() {
        assertFails { Prefs.BINDINGS.clear() }
    }

    @Test fun bind() {
    }
}