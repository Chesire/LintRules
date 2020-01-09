package com.chesire.lintrules.gradle.issues

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.chesire.lintrules.gradle.detectors.DuplicateDependencyDetector

/**
 * Issue for when duplicate dependencies are added to Gradle.
 */
object DuplicateDependency {
    /**
     * The issue in question, created using [Issue.create].
     */
    val issue = Issue.create(
        "DuplicateDependency",
        "Dependency defined multiple times",
        """
            The same dependency declared multiple times can make it confusing to know which one is 
            the one being used by Gradle.
        """.trimIndent(),
        Category.CORRECTNESS,
        3,
        Severity.WARNING,
        Implementation(DuplicateDependencyDetector::class.java, Scope.GRADLE_SCOPE)
    )

    /**
     * Message to display when reporting on this issue.
     */
    val message = """
        Dependencies should only be added to a given module once.
    """.trimIndent()
}
