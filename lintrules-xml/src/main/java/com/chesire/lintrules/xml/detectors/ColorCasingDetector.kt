package com.chesire.lintrules.xml.detectors

import com.android.SdkConstants.ATTR_COLOR
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.XmlContext
import com.android.utils.forEach
import com.chesire.lintrules.xml.issues.ColorCasing
import org.w3c.dom.Attr
import org.w3c.dom.Element
import org.w3c.dom.Node

/**
 * Detector to find issue types of [ColorCasing].
 */
class ColorCasingDetector : ResourceXmlDetector() {
    private val colorRegex = Regex("#[a-fA-F\\d]{3,8}")

    override fun getApplicableElements(): Collection<String> = ALL

    override fun visitElement(context: XmlContext, element: Element) {
        if (element.nodeName == ATTR_COLOR) {
            if (shouldReportOn(element.textContent)) {
                ColorCasing.report(context, element)
            }
        } else {
            element.attributes.forEach { node ->
                if (shouldReportOn(node.nodeValue)) {
                    ColorCasing.report(context, node as Attr)
                }
            }
        }
    }

    private fun shouldReportOn(value: String) =
        value.matches(colorRegex) && value.any { it.isLowerCase() }

    private fun ColorCasing.report(context: XmlContext, node: Node) =
        context.report(issue, context.getLocation(node), message)

    private fun ColorCasing.report(context: XmlContext, attr: Attr) =
        context.report(issue, context.getValueLocation(attr), message)
}
