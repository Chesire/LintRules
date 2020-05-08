package com.chesire.lintrules.xml.detectors

import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.chesire.lintrules.xml.issues.MultipleSpace
import org.junit.Test

class MultipleSpaceDetectorTests {
    @Test
    fun `multipleSpace should be no issue with valid XML file`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/layout/layout_file.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:focusableInTouchMode="true"
        android:gravity="center_horizontal"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/headerImage"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    </LinearLayout>
</ScrollView>
                """.trimIndent()
                ).indented()
            )
            .issues(MultipleSpace.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `multipleSpace should flag issues in valid xml file`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/layout/layout_file.xml",
                    """
<?xml version="1.0"  encoding="utf-8"?>
<ScrollView  xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:focusableInTouchMode="true"
        android:gravity="center_horizontal"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/headerImage"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"  />
    </LinearLayout>
</ScrollView>
                """.trimIndent()
                ).indented()
            )
            .issues(MultipleSpace.issue)
            .run()
            .expect(
                """
                |res/layout/layout_file.xml:1: Information: Multiple spaces should not be used. [MultipleSpace]
                |<?xml version="1.0"  encoding="utf-8"?>
                |                   ~~
                |res/layout/layout_file.xml:2: Information: Multiple spaces should not be used. [MultipleSpace]
                |<ScrollView  xmlns:android="http://schemas.android.com/apk/res/android"
                |           ~~
                |res/layout/layout_file.xml:21: Information: Multiple spaces should not be used. [MultipleSpace]
                |            app:layout_constraintTop_toTopOf="parent"  />
                |                                                     ~~
0 errors, 0 warnings""".trimMargin()
            )
    }
}
