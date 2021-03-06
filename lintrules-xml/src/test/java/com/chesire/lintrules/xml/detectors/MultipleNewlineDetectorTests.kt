package com.chesire.lintrules.xml.detectors

import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.chesire.lintrules.xml.issues.MultipleNewline
import org.junit.Test

class MultipleNewlineDetectorTests {

    @Test
    fun `multipleNewline should be no issue with valid XML file`() {
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
            .issues(MultipleNewline.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `multipleNewline should flag issues in invalid XML file`() {
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
            .issues(MultipleNewline.issue)
            .run()
            .expect(
                """
                |res/layout/layout_file.xml:14: Warning: Only a single empty line should be used. [MultipleNewline]
                |
                |^
                |res/layout/layout_file.xml:24: Warning: Only a single empty line should be used. [MultipleNewline]
                |    
                |^
                |0 errors, 2 warnings""".trimMargin()
            )
    }
}
