package com.chesire.lintrules

import com.android.tools.lint.detector.api.Issue

/**
 * Provides a common interface for issues to use, ensuring it has all the required information.
 */
interface IssueHolder {
    /**
     * The issue in question, created using [Issue.create].
     */
    val issue: Issue
    /**
     * Message to display when reporting on this issue.
     */
    val message: String
}
