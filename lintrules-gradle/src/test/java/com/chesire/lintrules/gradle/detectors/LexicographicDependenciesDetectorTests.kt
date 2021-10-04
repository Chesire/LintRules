package com.chesire.lintrules.gradle.detectors

import com.android.tools.lint.checks.infrastructure.TestFiles.gradle
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.chesire.lintrules.gradle.issues.LexicographicDependencies
import org.junit.Test

class LexicographicDependenciesDetectorTests {
    @Test
    fun `lexicographicOrder should be no issue with valid Gradle file`() {
        lint()
            .allowMissingSdk()
            .files(
                gradle(
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
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    
    lintChecks project(":lintrules")
}
                    """.trimIndent()
                ).indented()
            )
            .issues(LexicographicDependencies.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `lexicographicOrder should be no issue with short valid Gradle file`() {
        lint()
            .allowMissingSdk()
            .files(
                gradle(
                    """
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    
    lintChecks project(":lintrules")
}
                    """.trimIndent()
                ).indented()
            )
            .issues(LexicographicDependencies.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `lexicographicOrder should be no issue with nested project dependencies`() {
        lint()
            .allowMissingSdk()
            .files(
                gradle(
                    """
dependencies {
    implementation project(":libraries:kitsu:trending")
    implementation project(":libraries:kitsu:user")
    implementation project(":libraries:library")
}
                    """.trimIndent()
                ).indented()
            )
            .issues(LexicographicDependencies.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `lexicographicOrder should be no issue with wrong order between different implementation type`() {
        lint()
            .allowMissingSdk()
            .files(
                gradle(
                    """
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    implementation 'z.zandroid.ztools.zlint:lint-tests:26.5.1'
    
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    
    lintChecks project(":lintrules")
}
                    """.trimIndent()
                ).indented()
            )
            .issues(LexicographicDependencies.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `lexicographicOrder should be an issue with invalid lexicographic order`() {
        lint()
            .allowMissingSdk()
            .files(
                gradle(
                    """
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.core:core-ktx:1.1.0'
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    
    lintChecks project(":lintrules")
}
                    """.trimIndent()
                ).indented()
            )
            .issues(LexicographicDependencies.issue)
            .run()
            .expect(
                """
                |build.gradle:4: Warning: Dependencies should be listed in lexicographic order. [LexicographicDependencies]
                |    implementation 'androidx.appcompat:appcompat:1.1.0'
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings""".trimMargin()
            )
    }

    @Test
    fun `lexicographicOrder should be an issue with invalid lexicographic order same owner`() {
        lint()
            .allowMissingSdk()
            .files(
                gradle(
                    """
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.chesire.lintrules:lint-gradle:1.1.1'
    implementation 'com.chesire.lintrules:lint-xml:1.1.1'
    implementation 'com.chesire:lifecyklelog:2.1.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    
    testImplementation 'com.android.tools.lint:lint-tests:26.5.1'
    testImplementation 'junit:junit:4.12'
    
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    
    lintChecks project(":lintrules")
}
                    """.trimIndent()
                ).indented()
            )
            .issues(LexicographicDependencies.issue)
            .run()
            .expect(
                """
                |build.gradle:5: Warning: Dependencies should be listed in lexicographic order. [LexicographicDependencies]
                |    implementation 'com.chesire:lifecyklelog:2.1.0'
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings""".trimMargin()
            )
    }
}
