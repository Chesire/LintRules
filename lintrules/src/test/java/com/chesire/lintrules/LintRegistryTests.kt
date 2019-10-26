package com.chesire.lintrules

import com.android.tools.lint.detector.api.CURRENT_API
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LintRegistryTests {
    @Test
    fun `issues returns expected list`() {
        val registry = LintRegistry()
        assertTrue(registry.issues.contains(DependencyDetector.duplicateDependency))
        assertTrue(registry.issues.contains(DependencyDetector.lexicographicOrder))
    }

    @Test
    fun `api should return CURRENT_API`() {
        assertEquals(CURRENT_API, LintRegistry().api)
    }
}
