package com.hendraanggrian.lokal

import com.google.common.collect.HashMultimap
import org.junit.Assert.assertEquals
import org.junit.Test

class MultimapTest {

    @Test fun test() {
        val multimap = HashMultimap.create<String, Int>()
        multimap.put("Hello", 0)
        multimap.put("Hello", 1)
        multimap.put("Hello", 1)
        assertEquals(multimap.keySet().size.toLong(), 1)
        assertEquals(multimap.get("Hello").size.toLong(), 2)
    }
}