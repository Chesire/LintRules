package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.GradleScanner
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Location
import com.chesire.lintrules.common.detectors.BaseMultipleSpaceDetector
import com.chesire.lintrules.gradle.issues.MultipleSpace

/**
 * [Issue] for when there are multiple spaces together.
 */
class MultipleSpaceDetector : BaseMultipleSpaceDetector(), GradleScanner {
    override val customVisitor = true

    override fun visitBuildScript(context: Context) {
        val contents = context.getContents() ?: return
        findIssues(contents) { context.report(contents, it) }
    }

    private fun Context.report(contents: CharSequence, offset: IntRange) {
        report(
            MultipleSpace.issue,
            Location.create(
                file,
                contents,
                offset.first + 1,
                offset.last + 1
            ),
            MultipleSpace.message,
            null
        )
    }
}
