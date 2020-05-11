package com.chesire.lintrules.common.issues

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Severity

/**
 * [Issue] for when there are multiple newlines in a file.
 */
interface MultipleNewlineIssue : LintIssue {
    override val id: String
        get() = "MultipleNewline"
    override val briefDescription: String
        get() = "Detected the use of more than one empty line"
    override val explanation: String
        get() = "Stylistic choice to remove duplicated empty lines"
    override val category: Category
        get() = Category.CORRECTNESS
    override val priority: Int
        get() = 1
    override val severity: Severity
        get() = Severity.WARNING
    override val message: String
        get() = "Only a single empty line should be used."
}
