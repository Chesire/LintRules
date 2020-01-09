package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleContext
import com.android.tools.lint.detector.api.GradleScanner
import com.chesire.lintrules.gradle.issues.DuplicateDependency

/**
 * Detector used to find issues with the dependencies within Gradle.
 */
class DuplicateDependencyDetector : Detector(), GradleScanner {
    companion object {
        private const val PARENT_TAG = "dependencies"
    }

    private val dependencyItems = mutableListOf<Pair<String, String>>()

    override fun checkDslPropertyAssignment(
        context: GradleContext,
        property: String,
        value: String,
        parent: String,
        parentParent: String?,
        propertyCookie: Any,
        valueCookie: Any,
        statementCookie: Any
    ) {
        if (parent != PARENT_TAG) {
            return
        }
        parseDependency(value)?.let { dependency ->
            reportIfDuplicate(context, property, dependency, valueCookie)
            dependencyItems.add(property to dependency)
        }
    }

    private fun parseDependency(value: String): String? {
        val encasingCharacter = when {
            value.contains("\"") -> "\""
            value.contains("\'") -> "\'"
            else -> return null
        }

        // could expand to trim form the last index of `:`, and if it is then empty, ignore that trim
        return value.substring(
            value.indexOf(encasingCharacter),
            value.lastIndexOf(encasingCharacter)
        )
    }

    private fun reportIfDuplicate(
        context: GradleContext,
        property: String,
        dependency: String,
        valueCookie: Any
    ) {
        if (dependencyItems.any { it.second == dependency && it.first == property }) {
            context.report(
                DuplicateDependency.issue,
                context.getLocation(valueCookie),
                DuplicateDependency.message
            )
        }
    }
}
