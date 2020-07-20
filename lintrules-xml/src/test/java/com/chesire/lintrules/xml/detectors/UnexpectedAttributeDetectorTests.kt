package com.chesire.lintrules.xml.detectors

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.chesire.lintrules.xml.issues.UnexpectedAttribute
import org.junit.Test

class UnexpectedAttributeDetectorTests {
    @Test
    fun `UnexpectedAttribute should have no issue with valid constraint layout`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
                    """.trimIndent()
                ).indented()
            )
            .issues(UnexpectedAttribute.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `UnexpectedAttribute should throw issue with constraint layout with orientation`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"/>
                    """.trimIndent()
                ).indented()
            )
            .issues(UnexpectedAttribute.issue)
            .run()
            .expect(
                """
                |res/layout/layout.xml:7: Warning: Attribute was not expected on this element. [UnexpectedAttribute]
                |    android:orientation="vertical"/>
                |    ~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings
                """.trimMargin()
            )
    }

    @Test
    fun `UnexpectedAttribute should throw issue with multiple constraint layout with orientation`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <androidx.constraintlayout.widget.ConstraintLayout
        android:orientation="horizontal"/>
</androidx.constraintlayout.widget.ConstraintLayout>
                    """.trimIndent()
                ).indented()
            )
            .issues(UnexpectedAttribute.issue)
            .run()
            .expect(
                """
                    |res/layout/layout.xml:7: Warning: Attribute was not expected on this element. [UnexpectedAttribute]
                    |    android:orientation="vertical">
                    |    ~~~~~~~~~~~~~~~~~~~
                    |res/layout/layout.xml:9: Warning: Attribute was not expected on this element. [UnexpectedAttribute]
                    |        android:orientation="horizontal"/>
                    |        ~~~~~~~~~~~~~~~~~~~
                    |0 errors, 2 warnings
                """.trimMargin()
            )
    }

    @Test
    fun `UnexpectedAttribute should throw issue across multiple files with multiple constraint layout with orientation`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/layout/layout1.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"/>
                    """.trimIndent()
                ).indented(),
                xml(
                    "res/layout/layout2.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical">
    <androidx.constraintlayout.widget.ConstraintLayout
        android:orientation="horizontal"/>
</androidx.constraintlayout.widget.ConstraintLayout>
                    """.trimIndent()
                ).indented()
            )
            .issues(UnexpectedAttribute.issue)
            .run()
            .expect(
                """
                    |res/layout/layout1.xml:7: Warning: Attribute was not expected on this element. [UnexpectedAttribute]
                    |    android:orientation="horizontal"/>
                    |    ~~~~~~~~~~~~~~~~~~~
                    |res/layout/layout2.xml:7: Warning: Attribute was not expected on this element. [UnexpectedAttribute]
                    |    android:orientation="vertical">
                    |    ~~~~~~~~~~~~~~~~~~~
                    |res/layout/layout2.xml:9: Warning: Attribute was not expected on this element. [UnexpectedAttribute]
                    |        android:orientation="horizontal"/>
                    |        ~~~~~~~~~~~~~~~~~~~
                    |0 errors, 3 warnings
                """.trimMargin()
            )
    }
}
