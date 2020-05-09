package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Location
import com.chesire.lintrules.common.detectors.BaseMultipleSpaceDetector
import com.chesire.lintrules.gradle.issues.MultipleSpace

/**
 * Gradle detector for the [BaseMultipleSpaceDetector].
 */
class MultipleSpaceDetector : BaseMultipleSpaceDetector() {
    override fun Context.report(contents: CharSequence, offset: IntRange) {
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
