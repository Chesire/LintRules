package com.chesire.lintrules.xml

import com.android.tools.lint.detector.api.CURRENT_API
import com.chesire.lintrules.xml.issues.ColorCasing
import com.chesire.lintrules.xml.issues.MultipleSpace
import com.chesire.lintrules.xml.issues.UnexpectedAttribute
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class XmlLintRegistryTests {
    @Test
    fun `issues returns expected list`() {
        val registry = XmlLintRegistry()
        assertTrue(registry.issues.contains(ColorCasing.issue))
        assertTrue(registry.issues.contains(MultipleSpace.issue))
        assertTrue(registry.issues.contains(UnexpectedAttribute.issue))
    }

    @Test
    fun `api should return CURRENT_API`() {
        assertEquals(CURRENT_API, XmlLintRegistry().api)
    }
}
