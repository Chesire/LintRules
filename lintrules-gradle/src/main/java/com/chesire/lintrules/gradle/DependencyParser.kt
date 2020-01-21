package com.chesire.lintrules.gradle

/**
 * Object to aid with parsing out dependencies into [Dependency] objects.
 */
object DependencyParser {
    /**
     * Parses [value] into a [Dependency] object, takes the compile type [type] to aid with
     * creation of the object,
     */
    fun parseDependency(type: String, value: String): Dependency? {
        val encasingChar = getEncasingChar(value) ?: return null
        val dependencyItem = value.substring(
            value.indexOf(encasingChar),
            value.lastIndexOf(encasingChar)
        )
        val dependencyName = dependencyItem.substring(0, dependencyItem.lastIndexOf(':'))
        val dependencyVersion = dependencyItem.substring(dependencyItem.lastIndexOf(':') + 1)

        return Dependency(type, dependencyName, dependencyVersion)
    }

    private fun getEncasingChar(value: String): Char? = when {
        value.contains("\"") -> '\"'
        value.contains("\'") -> '\''
        else -> null
    }
}
