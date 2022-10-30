# LintRules  

> Extra lint rules to add to your project.

[![](https://jitpack.io/v/Chesire/LintRules.svg)](https://jitpack.io/#Chesire/LintRules)
![Android CI](https://github.com/Chesire/LintRules/workflows/Android%20CI/badge.svg)

## Installation

Add the following line to your modules `build.gradle`

```gradle
implementation 'com.github.chesire:lintrules:{version}'
```

## Rules Added

### lint-gradle
- **[DuplicateDependency]** - Highlights when the same dependency has been used multiple times within the same Gradle file. 
- **[LexicographicDependencies]** - Highlights when dependencies within a Gradle file are not ordered lexicographically. 
- **[MultipleNewline]** - Highlights when multiple blank lines  are detected in a row. 
- **[MultipleSpaces]** - Highlights when multiple spaces are detected in a row. 

### lint-xml
- **[ColorCasing]** - Highlights when a color has been defined, but is not all uppercased. 
- **[MultipleNewline]** - Highlights when multiple blank lines  are detected in a row. 
- **[MultipleSpaces]** - Highlights when multiple spaces are detected in a row. 
- **[UnexpectedAttribute]** - Highlights when an attribute has been used in a layout file, but it is not expected on that element.

## License

Apache 2.0 - See [LICENSE](https://github.com/Chesire/LintRules/blob/master/LICENSE) for more information.
