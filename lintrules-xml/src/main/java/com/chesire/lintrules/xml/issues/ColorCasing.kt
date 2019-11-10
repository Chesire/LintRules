package com.chesire.lintrules.xml.issues

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.chesire.lintrules.xml.detectors.ColorCasingDetector

/**
 * Issue for when colors are not defined in all upper case.
 */
object ColorCasing {
    /**
     * The issue in question, created using [Issue.create].
     */
    val issue = Issue.create(
        "ColorCasing",
        "Colors should be uppercase",
        "Colors should be defined with all uppercase (#FF00A2) instead of using lowercase (#ff00a2).",
        Category.CORRECTNESS,
        0,
        Severity.WARNING,
        Implementation(ColorCasingDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
    )

    /**
     * Message to display when reporting on this issue.
     */
    val message = """
        Color definitions should be all uppercase.
    """.trimIndent()
}
