package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleContext
import com.android.tools.lint.detector.api.GradleScanner
import com.android.tools.lint.detector.api.Project
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

    private val dependencyItems = mutableListOf<Dependency>()
    private var currentProject: Project? = null

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
        if (currentProject == null || context.project != currentProject) {
            dependencyItems.clear()
            currentProject = context.project
        }
        DependencyParser.parseDependency(property, value)?.let { dependency ->
            reportIfDuplicate(context, dependency, valueCookie)
            dependencyItems.add(dependency)
        }
    }

    private fun reportIfDuplicate(
        context: GradleContext,
        dependency: Dependency,
        valueCookie: Any
    ) {
        if (isDuplicate(dependency)) {
            context.report(
                DuplicateDependency.issue,
                context.getLocation(valueCookie),
                DuplicateDependency.message
            )
        }
    }

    private fun isDuplicate(dependency: Dependency) = dependencyItems.any { it.sameAs(dependency) }
}
