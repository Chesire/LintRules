package com.chesire.lintrules.common.detectors

import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleScanner
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScanner
import org.w3c.dom.Document

abstract class BaseMultipleNewlineDetector : Detector(), GradleScanner, XmlScanner {
    override fun visitBuildScript(context: Context) {
        val contents = context.getContents() ?: return
        findIssues(contents) { context.report(contents, it) }
    }

    override fun visitDocument(context: XmlContext, document: Document) {
        val contents = context.getContents() ?: return
        findIssues(contents) { context.report(contents, it) }
    }

    private fun findIssues(contents: CharSequence, onIssueFound: (IntRange) -> Unit) {
        //
    }

    /**
     * Report that an issue has been found in [contents] at [offset].
     */
    abstract fun Context.report(contents: CharSequence, offset: IntRange)
}
