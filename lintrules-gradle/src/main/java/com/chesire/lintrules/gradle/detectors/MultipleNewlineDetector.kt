package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Location
import com.chesire.lintrules.gradle.issues.MultipleNewline

/**
 * Gradle detector for the [BaseMultipleNewlineDetector].
 */
class MultipleNewlineDetector : BaseMultipleNewlineDetector() {
    override fun Context.report(contents: CharSequence, offset: IntRange) {
        report(
            MultipleNewline.issue,
            Location.create(
                file,
                contents,
                offset.first,
                offset.last
            ),
            MultipleNewline.message,
            null
        )
    }
}
