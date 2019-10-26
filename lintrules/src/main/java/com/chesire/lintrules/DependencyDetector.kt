package com.chesire.lintrules

import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleContext
import com.android.tools.lint.detector.api.GradleScanner
import com.chesire.lintrules.issues.DuplicateDependency
import com.chesire.lintrules.issues.LexicographicDependencies
import org.jetbrains.kotlin.backend.common.onlyIf

/**
 * Detector used to find issues with the dependencies within Gradle.
 */
class DependencyDetector : Detector(), GradleScanner {
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
                DuplicateDependency.issue,
                context.getLocation(valueCookie),
                DuplicateDependency.message
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
                    LexicographicDependencies.issue,
                    context.getLocation(valueCookie),
                    LexicographicDependencies.message
                )
            })
    }
}
