package com.chesire.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

/**
 * Custom registry to add custom issues.
 */
class LintRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            DependencyDetector.duplicateDependency,
            DependencyDetector.lexicographicOrder
        )

    override val api: Int
        get() = CURRENT_API
}
