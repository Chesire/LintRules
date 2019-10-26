package com.chesire.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import java.util.Collections.singletonList

/**
 * Custom registry to add custom issues.
 */
class LintRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = singletonList(DependencyDetector.duplicateDependency)

    override val api: Int
        get() = CURRENT_API
}
