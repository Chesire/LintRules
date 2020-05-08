package com.chesire.lintrules.common.issues

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Severity

/**
 * Base interface for other issues to implement.
 */
interface LintIssue {
    /**
     * The id of the issue, see [Issue.create].
     */
    val id: String

    /**
     * The description of the issue, see [Issue.create].
     */
    val briefDescription: String

    /**
     * The explanation of the issue, see [Issue.create].
     */
    val explanation: String

    /**
     * The category of the issue, see [Issue.create].
     */
    val category: Category

    /**
     * The priority of the issue, see [Issue.create].
     */
    val priority: Int

    /**
     * The severity of the issue, see [Issue.create].
     */
    val severity: Severity

    /**
     * The issue in question, created using [Issue.create].
     */
    val issue: Issue

    /**
     * Message to display when reporting on this issue.
     */
    val message: String
}
