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
     * Name of the dependency.
     */
    val name: String,
    /**
     * Version of the dependency.
     */
    val version: String
)
