package com.chesire.lintrules.gradle

import com.android.tools.lint.detector.api.CURRENT_API
import com.chesire.lintrules.gradle.issues.DuplicateDependency
import com.chesire.lintrules.gradle.issues.LexicographicDependencies
import com.chesire.lintrules.gradle.issues.MultipleNewline
import com.chesire.lintrules.gradle.issues.MultipleSpace
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GradleLintRegistryTests {
    @Test
    fun `issues returns expected list`() {
        val registry = GradleLintRegistry()
        assertTrue(registry.issues.contains(DuplicateDependency.issue))
        assertTrue(registry.issues.contains(LexicographicDependencies.issue))
        assertTrue(registry.issues.contains(MultipleNewline.issue))
        assertTrue(registry.issues.contains(MultipleSpace.issue))
    }

    @Test
    fun `api should return CURRENT_API`() {
        assertEquals(CURRENT_API, GradleLintRegistry().api)
    }
}
