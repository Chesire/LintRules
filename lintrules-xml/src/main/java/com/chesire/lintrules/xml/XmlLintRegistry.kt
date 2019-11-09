package com.chesire.lintrules.xml

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

/**
 * Custom registry to add custom issues.
 */
class XmlLintRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf()

    override val api: Int
        get() = CURRENT_API
}
