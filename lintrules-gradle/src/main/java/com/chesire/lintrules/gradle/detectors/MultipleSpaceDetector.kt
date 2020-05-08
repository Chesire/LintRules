package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleScanner
import com.android.tools.lint.detector.api.Location
import com.chesire.lintrules.gradle.issues.MultipleSpace

class MultipleSpaceDetector : Detector(), GradleScanner {
    override val customVisitor = true

    override fun visitBuildScript(context: Context) {
        val contents = context.getContents()
        val lines = contents?.lines()
        var lineNumber = 0
        lines?.forEach { line ->
            val trimmed = line.trim()
            if (trimmed.contains("  ")) {
                context.report(
                    MultipleSpace.issue,
                    Location.create(
                        context.file,
                        contents.toString(),
                        lineNumber
                    ),
                    MultipleSpace.message,
                    null
                )
            }

            lineNumber++
        }
    }
}
