package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleContext
import com.android.tools.lint.detector.api.GradleScanner
import com.chesire.lintrules.gradle.Dependency
import com.chesire.lintrules.gradle.DependencyParser
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
        DependencyParser.parseDependency(property, value)?.let { dependency ->
            reportIfDuplicate(context, dependency, valueCookie)
            dependencyItems.add(dependency.type to dependency.name)
        }
    }

    private fun reportIfDuplicate(
        context: GradleContext,
        dependency: Dependency,
        valueCookie: Any
    ) {
        if (dependencyItems.any { it.first == dependency.type && it.second == dependency.name }) {
            context.report(
                DuplicateDependency.issue,
                context.getLocation(valueCookie),
                DuplicateDependency.message
            )
        }
    }
}
