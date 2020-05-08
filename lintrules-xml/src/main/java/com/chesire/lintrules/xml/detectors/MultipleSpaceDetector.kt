package com.chesire.lintrules.xml.detectors

import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScanner
import com.chesire.lintrules.xml.issues.MultipleSpace
import org.w3c.dom.Document

/**
 * [Issue] for when there are multiple spaces together.
 */
class MultipleSpaceDetector : Detector(), XmlScanner {

    private val spaceRegex = Regex("[^\\s][ ]{2,}")

    override fun visitDocument(context: XmlContext, document: Document) {
        val contents = context.getContents() ?: return

        spaceRegex.findAll(contents).forEach { result ->
            // This should contain autofix data to replace "  " with just " "
            context.report(
                MultipleSpace.issue,
                Location.create(
                    context.file,
                    contents,
                    result.range.first + 1,
                    result.range.last + 1
                ),
                MultipleSpace.message,
                null
            )
        }
    }
}
