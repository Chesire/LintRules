package com.chesire.lintrules.xml.detectors

import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScanner
import com.chesire.lintrules.common.detectors.BaseMultipleSpaceDetector
import com.chesire.lintrules.xml.issues.MultipleSpace
import org.w3c.dom.Document

/**
 * [Issue] for when there are multiple spaces together.
 */
class MultipleSpaceDetector : BaseMultipleSpaceDetector(), XmlScanner {
    override fun visitDocument(context: XmlContext, document: Document) {
        val contents = context.getContents() ?: return
        findIssues(contents) { context.report(contents, it) }
    }

    private fun XmlContext.report(contents: CharSequence, offset: IntRange) {
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
