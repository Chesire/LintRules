package com.chesire.lintrules.common.detectors

import com.android.tools.lint.detector.api.Detector
import com.chesire.lintrules.common.issues.MultipleSpaceIssue

/**
 * Provides a base class for other [MultipleSpaceIssue] detectors to use.
 */
abstract class BaseMultipleSpaceDetector : Detector() {
    private val spaceRegex = Regex("[^\\s][ ]{2,}")

    /**
     * Finds any issues in the [contents], reporting back the start and end of each in [onIssueFound].
     */
    protected fun findIssues(contents: CharSequence, onIssueFound: (IntRange) -> Unit) {
        spaceRegex.findAll(contents).forEach {
            onIssueFound(it.range)
        }
    }
}
