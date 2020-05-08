package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleScanner
import com.android.tools.lint.detector.api.Location
import com.chesire.lintrules.gradle.issues.MultipleSpace

class MultipleSpaceDetector : Detector(), GradleScanner {
    private val spaceRegex = Regex("\\w[ ]{2,}")

    override val customVisitor = true

    override fun visitBuildScript(context: Context) {
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
