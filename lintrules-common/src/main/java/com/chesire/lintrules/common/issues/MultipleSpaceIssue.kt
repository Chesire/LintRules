package com.chesire.lintrules.common.issues

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Severity

/**
 * [Issue] for when there are multiple spaces in a file.
 */
interface MultipleSpaceIssue : LintIssue {
    override val id: String
        get() = "MultipleSpace"
    override val briefDescription: String
        get() = "Detected the use of more than one space"
    override val explanation: String
        get() = "Stylistic choice to remove duplicated spaces"
    override val category: Category
        get() = Category.CORRECTNESS
    override val priority: Int
        get() = 1
    override val severity: Severity
        get() = Severity.WARNING
    override val message: String
        get() = "Multiple spaces should not be used."
}
