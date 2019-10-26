package com.chesire.lintrules.issues

import com.android.tools.lint.detector.api.Issue

interface IssueHolder {
    val issue: Issue
    /**
     * Message to display when reporting on this issue.
     */
    val message: String
}
