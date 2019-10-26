package com.chesire.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.chesire.lintrules.issues.DuplicateDependency
import com.chesire.lintrules.issues.LexicographicDependencies

/**
 * Custom registry to add custom issues.
 */
class LintRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            DuplicateDependency.issue,
            LexicographicDependencies.issue
        )

    override val api: Int
        get() = CURRENT_API
}
