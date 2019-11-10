package com.chesire.lintrules.xml.issues

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.chesire.lintrules.xml.detectors.UnexpectedAttributeDetector

/**
 * Issue for when a layout item contains an expected attribute.
 *
 * Example being a ConstraintLayout containing an orientation attribute.
 */
object UnexpectedAttribute {
    /**
     * The issue in question, created using [Issue.create].
     */
    val issue = Issue.create(
        "UnexpectedAttribute",
        "Attribute was not expected on this element",
        """
            An unexpected attribute could indicate that the element is being used incorrectly, 
            review and remove if necessary.
        """.trimIndent(),
        Category.CORRECTNESS,
        0,
        Severity.WARNING,
        Implementation(UnexpectedAttributeDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
    )

    /**
     * Message to display when reporting on this issue.
     */
    val message = """
        Attribute was not expected on this element.
    """.trimIndent()
}
