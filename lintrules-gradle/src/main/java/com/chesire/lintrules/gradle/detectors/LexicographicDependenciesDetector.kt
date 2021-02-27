package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleContext
import com.android.tools.lint.detector.api.GradleScanner
import com.chesire.lintrules.gradle.Dependency
import com.chesire.lintrules.gradle.DependencyParser
import com.chesire.lintrules.gradle.Domain
import com.chesire.lintrules.gradle.issues.LexicographicDependencies
import org.jetbrains.kotlin.backend.common.onlyIf

private const val PARENT_TAG = "dependencies"

/**
 * Detector used to find issues with the dependencies within Gradle.
 */
class LexicographicDependenciesDetector : Detector(), GradleScanner {

    private val dependencyItems = mutableListOf<Dependency>()

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
            dependencyItems.add(dependency)
        }
    }

    // Certain items should not be included when checking
    private fun isValidItem(value: String) = !value.contains("fileTree")

    private fun reportIfNotLexicographicOrder(
        context: GradleContext,
        dependency: Dependency,
        valueCookie: Any
    ) {
        dependencyItems
            .lastOrNull()
            ?.onlyIf(
                {
                    if (type != dependency.type) {
                        return@onlyIf false
                    }

                    when (dependency.domain) {
                        Domain.Project.value -> isInvalidOrder(dependency.value, this.value)
                        else -> isInvalidOrder(dependency.name, this.name)
                    }
                },
                {
                    context.report(
                        LexicographicDependencies.issue,
                        context.getLocation(valueCookie),
                        LexicographicDependencies.message
                    )
                }
            )
    }

    private fun isInvalidOrder(a: String, b: String): Boolean {
        val aString = a.sanitize()
        val bString = b.sanitize()
        return bString.compareTo(aString, false) > 0
    }

    private fun String.sanitize(): String = replace(':', '.')
}
