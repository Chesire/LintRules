package com.chesire.lintrules

import com.android.tools.lint.detector.api.CURRENT_API
import com.chesire.lintrules.gradle.LintRegistry
import com.chesire.lintrules.gradle.issues.DuplicateDependency
import com.chesire.lintrules.gradle.issues.LexicographicDependencies
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LintRegistryTests {
    @Test
    fun `issues returns expected list`() {
        val registry = LintRegistry()
        assertTrue(registry.issues.contains(DuplicateDependency.issue))
        assertTrue(registry.issues.contains(LexicographicDependencies.issue))
    }

    @Test
    fun `api should return CURRENT_API`() {
        assertEquals(CURRENT_API, LintRegistry().api)
    }
}
