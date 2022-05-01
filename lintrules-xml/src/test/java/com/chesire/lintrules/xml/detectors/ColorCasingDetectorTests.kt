package com.chesire.lintrules.xml.detectors

import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.chesire.lintrules.xml.issues.ColorCasing
import org.junit.Test

class ColorCasingDetectorTests {
    @Test
    fun `ColorCasing should have no issue with valid colors xml`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/values/colors.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="colorPrimary">#A1B2C3</color>
    <color name="colorPrimaryDark">#000FFF</color>
    <color name="colorAccent">#FFF</color>

    <color name="black">#000</color>
    <color name="white">#FFFFFF</color>
    <color name="darkerGrey">#F0F091</color>
</resources>
                    """.trimIndent()
                ).indented()
            )
            .issues(ColorCasing.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `ColorCasing should have no issue with valid layout file`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FF2A0B">
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="Hello World!" />
</FrameLayout>
                    """.trimIndent()
                ).indented()
            )
            .issues(ColorCasing.issue)
            .run()
            .expectClean()
    }

    @Test
    fun `ColorCasing flags error with lowercase in colors xml`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/values/colors.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="colorPrimary">#A1b2C3</color>
    <color name="colorPrimaryDark">#000FFF</color>
    <color name="colorAccent">#FfF</color>

    <color name="black">#000</color>
    <color name="white">#ff0</color>
    <color name="darkerGrey">#F0F091</color>
</resources>
                    """.trimIndent()
                ).indented()
            )
            .issues(ColorCasing.issue)
            .run()
            .expect(
                """
res/values/colors.xml:3: Warning: Color definitions should be all uppercase. [ColorCasing]
    <color name="colorPrimary">#A1b2C3</color>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
res/values/colors.xml:5: Warning: Color definitions should be all uppercase. [ColorCasing]
    <color name="colorAccent">#FfF</color>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
res/values/colors.xml:8: Warning: Color definitions should be all uppercase. [ColorCasing]
    <color name="white">#ff0</color>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
0 errors, 3 warnings
                """.trimIndent()
            )
    }

    @Test
    fun `ColorCasing flags error with lowercase in layout file`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#ff2a0b">
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="Hello" />
</FrameLayout>
                    """.trimIndent()
                ).indented()
            )
            .issues(ColorCasing.issue)
            .run()
            .expect(
                """
res/layout/layout.xml:6: Warning: Color definitions should be all uppercase. [ColorCasing]
    android:background="#ff2a0b">
                        ~~~~~~~
0 errors, 1 warnings
                """.trimIndent()
            )
    }

    @Test
    fun `ColorCasing flags error with lowercase in multiple file types`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#ff2a0b">
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="Hello" />
</FrameLayout>
                    """.trimIndent()
                ).indented(),
                xml(
                    "res/values/colors.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="colorPrimary">#A1b2C3</color>
    <color name="colorPrimaryDark">#000FFF</color>
    <color name="colorAccent">#FfF</color>

    <color name="black">#000</color>
    <color name="white">#ff0</color>
    <color name="darkerGrey">#F0F091</color>
</resources>
                    """.trimIndent()
                )
            )
            .issues(ColorCasing.issue)
            .run()
            .expect(
                """
res/values/colors.xml:3: Warning: Color definitions should be all uppercase. [ColorCasing]
    <color name="colorPrimary">#A1b2C3</color>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
res/values/colors.xml:5: Warning: Color definitions should be all uppercase. [ColorCasing]
    <color name="colorAccent">#FfF</color>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
res/values/colors.xml:8: Warning: Color definitions should be all uppercase. [ColorCasing]
    <color name="white">#ff0</color>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
res/layout/layout.xml:6: Warning: Color definitions should be all uppercase. [ColorCasing]
    android:background="#ff2a0b">
                        ~~~~~~~
0 errors, 4 warnings
                """.trimIndent()
            )
    }
}
