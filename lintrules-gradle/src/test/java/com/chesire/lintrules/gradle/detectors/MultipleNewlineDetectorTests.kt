package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.checks.infrastructure.TestFiles.gradle
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.chesire.lintrules.gradle.issues.MultipleNewline
import org.junit.Test

class MultipleNewlineDetectorTests {

    @Test
    fun `multipleNewlines should be no issue with valid Gradle file`() {
        lint()
            .allowMissingSdk()
            .files(
                gradle(
                    """
apply plugin: 'java-library'
apply plugin: 'kotlin'

dependencies {
    implementation project(path: ":lintrules-common")

    compileOnly "org.jetbrains.kotlin:kotlin-stdlib-jdk7:0.0.0"

    compileOnly "com.android.tools.lint:lint-api:0.0.0"
    compileOnly "com.android.tools.lint:lint-checks:0.0.0"

    testImplementation "com.android.tools.lint:lint:0.0.0"
    testImplementation "com.android.tools.lint:lint-tests:0.0.0"
}

jar {
    manifest {
        attributes('Lint-Registry-v2': 'com.chesire.lintrules.gradle.GradleLintRegistry')
    }
}
                """.trimIndent()
                ).indented()
            )
            .issues(MultipleNewline.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `multipleNewlines should flag issues in invalid Gradle file`() {
        lint()
            .allowMissingSdk()
            .files(
                gradle(
                    """
apply plugin: 'java-library'
apply plugin: 'kotlin'


dependencies {
    implementation project(path: ":lintrules-common")

    compileOnly "org.jetbrains.kotlin:kotlin-stdlib-jdk7:0.0.0"

    compileOnly "com.android.tools.lint:lint-api:0.0.0"
    compileOnly "com.android.tools.lint:lint-checks:0.0.0"
    

    testImplementation "com.android.tools.lint:lint:0.0.0"
    testImplementation "com.android.tools.lint:lint-tests:0.0.0"
}

jar {


    manifest  {
        attributes('Lint-Registry-v2': 'com.chesire.lintrules.gradle.GradleLintRegistry')
    }
}
                """.trimIndent()
                ).indented()
            )
            .issues(MultipleNewline.issue)
            .run()
            .expect(
                """
                |build.gradle:1: Warning: Multiple spaces should not be used. [MultipleSpace]
                |apply  plugin: 'java-library'
                |     ~~
                |build.gradle:5: Warning: Multiple spaces should not be used. [MultipleSpace]
                |    implementation project(path:  ":lintrules-common")
                |                                ~~
                |build.gradle:9: Warning: Multiple spaces should not be used. [MultipleSpace]
                |    compileOnly  "com.android.tools.lint:lint-api:0.0.0"
                |               ~~
                |build.gradle:17: Warning: Multiple spaces should not be used. [MultipleSpace]
                |    manifest  {
                |            ~~
                |0 errors, 4 warnings""".trimMargin()
            )
    }
}
