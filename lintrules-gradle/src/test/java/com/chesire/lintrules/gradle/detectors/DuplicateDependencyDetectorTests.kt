package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.chesire.lintrules.gradle.issues.DuplicateDependency
import org.junit.Test

class DuplicateDependencyDetectorTests {
    @Test
    fun `duplicateDependency should be no issue with valid Gradle file`() {
        TestLintTask
            .lint()
            .allowMissingSdk()
            .files(
                TestFiles.gradle(
                    """
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'

android {
    compileSdkVersion 28
    defaultConfig {
        applicationId "com.chesire.linttest"
        minSdkVersion 21
        targetSdkVersion 28
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }   
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    
    lintChecks project(":lintrules")
}
                """.trimIndent()
                ).indented()
            )
            .issues(DuplicateDependency.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `duplicateDependency should be no issue with short valid Gradle file`() {
        TestLintTask
            .lint()
            .allowMissingSdk()
            .files(
                TestFiles.gradle(
                    """
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    
    lintChecks project(":lintrules")
}
                """.trimIndent()
                ).indented()
            )
            .issues(DuplicateDependency.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `duplicateDependency should be no issue with same entry with different implementation type`() {
        TestLintTask
            .lint()
            .allowMissingSdk()
            .files(
                TestFiles.gradle(
                    """
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    
    testImplementation 'androidx.core:core-ktx:1.1.0'
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    
    lintChecks project(":lintrules")
}
                """.trimIndent()
                ).indented()
            )
            .issues(DuplicateDependency.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `duplicateDependency should be an issue when duplicate entry`() {
        TestLintTask
            .lint()
            .allowMissingSdk()
            .files(
                TestFiles.gradle(
                    """
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    
    lintChecks project(":lintrules")
}
                """.trimIndent()
                ).indented()
            )
            .issues(DuplicateDependency.issue)
            .run()
            .expect(
                """
                |build.gradle:6: Warning: Dependencies should only be added to a given module once. [DuplicateDependency]
                |    implementation 'androidx.core:core-ktx:1.1.0'
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings""".trimMargin()
            )
    }

    @Test
    fun `duplicateDependency should be an issue when duplicate entry with different version`() {
        TestLintTask
            .lint()
            .allowMissingSdk()
            .files(
                TestFiles.gradle(
                    """
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    implementation 'androidx.core:core-ktx:1.5.0'
    
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    
    lintChecks project(":lintrules")
}
                """.trimIndent()
                ).indented()
            )
            .issues(DuplicateDependency.issue)
            .run()
            .expect(
                """
                |build.gradle:6: Warning: Dependencies should only be added to a given module once. [DuplicateDependency]
                |    implementation 'androidx.core:core-ktx:1.5.0'
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings""".trimMargin()
            )
    }

    @Test
    fun `duplicateDependency should be an issue when duplicate entry at different location`() {
        TestLintTask
            .lint()
            .allowMissingSdk()
            .files(
                TestFiles.gradle(
                    """
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    implementation 'androidx.core:core-ktx:1.1.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    
    lintChecks project(":lintrules")
}
                """.trimIndent()
                ).indented()
            )
            .issues(DuplicateDependency.issue)
            .run()
            .expect(
                """
                |build.gradle:11: Warning: Dependencies should only be added to a given module once. [DuplicateDependency]
                |    implementation 'androidx.core:core-ktx:1.1.0'
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings""".trimMargin()
            )
    }
}
