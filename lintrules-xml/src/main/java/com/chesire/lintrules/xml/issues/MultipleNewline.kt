package com.chesire.lintrules.xml.issues

import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.chesire.lintrules.common.issues.MultipleNewlineIssue
import com.chesire.lintrules.xml.detectors.MultipleNewlineDetector

/**
 * [Issue] for when there are multiple new line characters together.
 */
object MultipleNewline : MultipleNewlineIssue {
    override val issue = Issue.create(
        id,
        briefDescription,
        explanation,
        category,
        priority,
        severity,
        Implementation(
            MultipleNewlineDetector::class.java,
            Scope.ALL_RESOURCES_SCOPE,
            Scope.MANIFEST_SCOPE
        )
    )
}
