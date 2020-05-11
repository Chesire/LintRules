package com.chesire.lintrules.common.detectors

import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleScanner
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScanner
import com.chesire.lintrules.common.issues.MultipleNewlineIssue
import org.w3c.dom.Document

/**
 * Provides a base class for other [MultipleNewlineIssue] detectors to use.
 * This base class will do all of the logic required to detect the usage of multiple newlines across
 * different file types, consumers need to provide an implementation of the [Context.report] method
 * which this will call internally.
 */
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
