package com.chesire.lintrules.gradle.issues

import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.chesire.lintrules.common.issues.MultipleSpaceIssue
import com.chesire.lintrules.gradle.detectors.MultipleSpaceDetector

/**
 * [Issue] for when there are multiple spaces together.
 */
object MultipleSpace : MultipleSpaceIssue {
    override val issue = Issue.create(
        id,
        briefDescription,
        explanation,
        category,
        priority,
        severity,
        Implementation(MultipleSpaceDetector::class.java, Scope.GRADLE_SCOPE)
    )
}
