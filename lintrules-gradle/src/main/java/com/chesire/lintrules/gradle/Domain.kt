package com.chesire.lintrules.gradle

/**
 * Different domains a dependency can exist in.
 * [value] references the value the dependency has.
 */
enum class Domain(val value: String) {
    None(""),
    Project("project")
}
