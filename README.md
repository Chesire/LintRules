# LintRules  

> Extra lint rules to add to your project.

[![Build Status](https://app.bitrise.io/app/21368f5327d4f32e/status.svg?token=TMTWcLyeJ2NzlcqwsFLKeQ&branch=master)](https://app.bitrise.io/app/21368f5327d4f32e)
[![Download](https://api.bintray.com/packages/chesire/LintRules/lint-gradle/images/download.svg) ](https://bintray.com/chesire/LintRules/lint-gradle/_latestVersion)
[![](https://jitpack.io/v/Chesire/LintRules.svg)](https://jitpack.io/#Chesire/LintRules)

## Installation

Add the following line to your modules `build.gradle`

```gradle
implementation 'com.chesire.lintrules:lint-gradle:{version}'
implementation 'com.chesire.lintrules:lint-xml:{version}'
```

the project is also available via jitpack

```gradle
implementation 'com.github.Chesire:LintRules:{version}'
```

## Rules Added

### lint-gradle
- **[DuplicateDependency]** - Highlights when the same dependency has been used multiple times within the same Gradle file.  
- **[LexicographicDependencies]** - Highlights when dependencies within a Gradle file are not ordered lexicographically.

### lint-xml
- **[ColorCasing]** - Highlights when a color has been defined, but is not all uppercased. 
- **[UnexpectedAttribute]** - Highlights when an attribute has been used in a layout file, but it is not expected on that element.

## License

Apache 2.0 - See [LICENSE](https://github.com/Chesire/LintRules/blob/master/LICENSE) for more information.