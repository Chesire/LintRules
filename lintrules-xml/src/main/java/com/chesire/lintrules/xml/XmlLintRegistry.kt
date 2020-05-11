package com.chesire.lintrules.xml

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.chesire.lintrules.xml.issues.ColorCasing
import com.chesire.lintrules.xml.issues.MultipleNewline
import com.chesire.lintrules.xml.issues.MultipleSpace
import com.chesire.lintrules.xml.issues.UnexpectedAttribute

/**
 * Custom registry to add custom issues.
 */
class XmlLintRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            ColorCasing.issue,
            MultipleNewline.issue,
            MultipleSpace.issue,
            UnexpectedAttribute.issue
        )

    override val api = CURRENT_API
}
