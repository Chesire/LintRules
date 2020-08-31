package com.chesire.lintrules.gradle

/**
 * Object to aid with parsing out dependencies into [Dependency] objects.
 */
object DependencyParser {
    /**
     * Parses [value] into a [Dependency] object, takes the compile type [type] to aid with
     * creation of the object.
     */
    fun parseDependency(type: String, value: String): Dependency? {
        val encasingChar = getEncasingChar(value) ?: return null
        val dependencyItem = value.substring(
            value.indexOf(encasingChar) + 1,
            value.lastIndexOf(encasingChar)
        )
        val dependencyDomain = getDependencyDomain(value)
        val dependencyName = getDependencyName(dependencyItem)
        val dependencyVersion = getDependencyVersion(dependencyItem)

        return Dependency(type, dependencyDomain, dependencyName, dependencyVersion, dependencyItem)
    }

    private fun getEncasingChar(value: String): Char? = when {
        value.contains("\"") -> '\"'
        value.contains("\'") -> '\''
        else -> null
    }

    private fun getDependencyDomain(value: String) = value.takeWhile { it.isLetterOrDigit() }

    private fun getDependencyName(value: String) =
        value
            .substring(0, value.lastIndexOf(':'))
            .takeIf { it.isNotEmpty() }
            ?: value

    private fun getDependencyVersion(value: String) = value.substring(value.lastIndexOf(':') + 1)
}
