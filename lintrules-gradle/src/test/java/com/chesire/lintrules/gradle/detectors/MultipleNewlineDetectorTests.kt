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
                |build.gradle:3: Warning: Only a single empty line should be used. [MultipleNewline]
                |
                |^
                |build.gradle:12: Warning: Only a single empty line should be used. [MultipleNewline]
                |    
                |^
                |build.gradle:19: Warning: Only a single empty line should be used. [MultipleNewline]
                |
                |^
                |0 errors, 3 warnings""".trimMargin()
            )
    }
}
