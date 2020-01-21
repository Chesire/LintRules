package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleContext
import com.android.tools.lint.detector.api.GradleScanner
import com.chesire.lintrules.gradle.Dependency
import com.chesire.lintrules.gradle.DependencyParser
import com.chesire.lintrules.gradle.issues.LexicographicDependencies
import org.jetbrains.kotlin.backend.common.onlyIf

/**
 * Detector used to find issues with the dependencies within Gradle.
 */
class LexicographicDependenciesDetector : Detector(), GradleScanner {
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
        if (parent != PARENT_TAG || !isValidItem(value)) {
            return
        }
        DependencyParser.parseDependency(property, value)?.let { dependency ->
            reportIfNotLexicographicOrder(context, dependency, valueCookie)
            dependencyItems.add(property to dependency.name)
        }
    }

    // Certain items should not be included when checking
    private fun isValidItem(value: String) =
        !(value.contains("org.jetbrains.kotlin:kotlin-stdlib") || value.contains("fileTree"))

    private fun reportIfNotLexicographicOrder(
        context: GradleContext,
        dependency: Dependency,
        valueCookie: Any
    ) {
        dependencyItems
            .lastOrNull()
            ?.onlyIf({
                if (first != dependency.type) {
                    return@onlyIf false
                }

                val firstString = dependency.name.replace(':', '.')
                val seconString = second.replace(':', '.')

                seconString.compareTo(firstString, false) > 0
            }, {
                context.report(
                    LexicographicDependencies.issue,
                    context.getLocation(valueCookie),
                    LexicographicDependencies.message
                )
            })
    }
}
