package com.chesire.lintrules.xml

import com.android.SdkConstants.ANDROID_URI
import com.android.SdkConstants.ATTR_ORIENTATION
import com.android.SdkConstants.CONSTRAINT_LAYOUT
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.XmlContext
import com.chesire.lintrules.xml.issues.UnexpectedAttribute
import org.w3c.dom.Element
import org.w3c.dom.Node

/**
 * Detector to find issue types of [UnexpectedAttribute].
 */
class UnexpectedAttributeDetector : LayoutDetector() {
    override fun getApplicableElements() = listOf(CONSTRAINT_LAYOUT.newName())

    override fun visitElement(context: XmlContext, element: Element) {
        when (element.nodeName) {
            CONSTRAINT_LAYOUT.newName() -> checkConstraintLayout(context, element)
        }
    }

    private fun checkConstraintLayout(context: XmlContext, element: Element) {
        if (element.hasAttributeNS(ANDROID_URI, ATTR_ORIENTATION)) {
            UnexpectedAttribute.report(
                context,
                element.getAttributeNodeNS(ANDROID_URI, ATTR_ORIENTATION)
            )
        }
    }

    private fun UnexpectedAttribute.report(context: XmlContext, node: Node) {
        context.report(issue, context.getNameLocation(node), message)
    }
}
