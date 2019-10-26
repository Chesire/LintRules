package com.chesire.lintrules

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleContext
import com.android.tools.lint.detector.api.GradleScanner
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.TextFormat

/**
 * Detector used to find issues with the dependencies within Gradle.
 */
class DependencyDetector : Detector(), GradleScanner {
    companion object {
        /**
         * Issue for when duplicate dependencies are added to Gradle.
         */
        val duplicateDependency = Issue.create(
            "DuplicateDependency",
            "Dependency defined multiple times",
            "The same dependency declared multiple times could be highlighting the wrong version is being used.",
            Category.CORRECTNESS,
            3,
            Severity.WARNING,
            Implementation(DependencyDetector::class.java, Scope.GRADLE_SCOPE)
        )

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
        if (parent == PARENT_TAG) {
            val dependency = value.substringBeforeLast(':')
            reportIfDuplicate(context, property, dependency, valueCookie)
            dependencyItems.add(property to dependency)
        }
    }

    private fun reportIfDuplicate(
        context: GradleContext,
        property: String,
        dependency: String,
        valueCookie: Any
    ) {
        if (dependencyItems.any { it.second == dependency && it.first == property }) {
            context.report(
                duplicateDependency,
                context.getLocation(valueCookie),
                duplicateDependency.getBriefDescription(TextFormat.TEXT)
            )
        }
    }
}
