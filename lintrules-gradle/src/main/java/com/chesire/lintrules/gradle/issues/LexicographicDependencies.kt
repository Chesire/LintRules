package com.chesire.lintrules.gradle.issues

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.chesire.lintrules.IssueHolder
import com.chesire.lintrules.gradle.DependencyDetector

/**
 * Issue for when the dependencies are not listed in lexicographic order.
 */
object LexicographicDependencies : IssueHolder {
    override val issue = Issue.create(
        "LexicographicDependencies",
        "Dependencies not listed in lexicographic order",
        "Dependencies should be listed in lexicographic order to make it easier to find and update them.",
        Category.CORRECTNESS,
        1,
        Severity.WARNING,
        Implementation(DependencyDetector::class.java, Scope.GRADLE_SCOPE)
    )

    override val message = """
        Dependencies should be listed in lexicographic order.
    """.trimIndent()
}
