package com.chesire.lintrules.issues

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.chesire.lintrules.DependencyDetector

/**
 * Issue for when duplicate dependencies are added to Gradle.
 */
object DuplicateDependency : IssueHolder {
    override val issue = Issue.create(
        "DuplicateDependency",
        "Dependency defined multiple times",
        "The same dependency declared multiple times can make it confusing to know which one is the one being used by Gradle.",
        Category.CORRECTNESS,
        3,
        Severity.WARNING,
        Implementation(DependencyDetector::class.java, Scope.GRADLE_SCOPE)
    )

    override val message = """
        Dependencies should only be added to a given module once.
    """.trimIndent()
}
