package com.chesire.lintrules.gradle

/**
 * Represents a single item within the 'dependencies' section of a Gradle file.
 */
data class Dependency(
    /**
     * Compile type.
     *
     * Ex: implementation, androidTestImplementation.
     */
    val type: String,
    /**
     * The domain of the dependency.
     *
     * Ex: project or "".
     */
    val domain: String,
    /**
     * Name of the dependency.
     */
    val name: String,
    /**
     * Version of the dependency.
     */
    val version: String,
    /**
     * Full value provided of the [name] & [version].
     */
    val value: String
) {
    /**
     * Checks if [other] is the same dependency as this one.
     */
    fun sameAs(other: Dependency): Boolean {
        return if (domain.isNotBlank()) {
            domain == other.domain && type == other.type && value == other.value
        } else {
            type == other.type && name == other.name
        }
    }
}
