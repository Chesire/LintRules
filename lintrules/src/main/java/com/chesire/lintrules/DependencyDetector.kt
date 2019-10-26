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
import org.jetbrains.kotlin.backend.common.onlyIf

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

        /**
         * Issue for when the dependencies are not listed in lexicographic order.
         */
        val lexicographicOrder = Issue.create(
            "LexicographicDependencies",
            "Dependencies not listed in lexicographic order",
            "Dependencies should be listed in lexicographic order to make it easier to find and update them.",
            Category.CORRECTNESS,
            1,
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
        if (parent == PARENT_TAG && isValidItem(value)) {
            val dependency = value.substringBeforeLast(':').trim('"')
            reportIfDuplicate(context, property, dependency, valueCookie)
            reportIfNotLexicographicOrder(context, property, dependency, valueCookie)
            dependencyItems.add(property to dependency)
        }
    }

    // Certain items should not be included when checking
    private fun isValidItem(value: String) =
        !(value.contains("org.jetbrains.kotlin:kotlin-stdlib") || value.contains("fileTree"))

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

    private fun reportIfNotLexicographicOrder(
        context: GradleContext,
        property: String,
        dependency: String,
        valueCookie: Any
    ) {
        dependencyItems
            .lastOrNull()
            ?.onlyIf({
                first == property && second.compareTo(dependency, false) > 0
            }, {
                context.report(
                    lexicographicOrder,
                    context.getLocation(valueCookie),
                    lexicographicOrder.getBriefDescription(TextFormat.TEXT)
                )
            })
    }
}
