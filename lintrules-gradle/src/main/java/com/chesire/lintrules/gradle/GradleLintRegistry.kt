package com.chesire.lintrules.gradle

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.chesire.lintrules.gradle.issues.DuplicateDependency
import com.chesire.lintrules.gradle.issues.LexicographicDependencies
import com.chesire.lintrules.gradle.issues.MultipleSpace

/**
 * Custom registry to add custom issues.
 */
class GradleLintRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            DuplicateDependency.issue,
            LexicographicDependencies.issue,
            MultipleSpace.issue
        )

    override val api = CURRENT_API
}
