# LintRules  

> Extra lint rules to add to your project.

[![Build Status](https://app.bitrise.io/app/21368f5327d4f32e/status.svg?token=TMTWcLyeJ2NzlcqwsFLKeQ&branch=master)](https://app.bitrise.io/app/21368f5327d4f32e)
[![Download](https://api.bintray.com/packages/chesire/LintRules/lint-gradle/images/download.svg) ](https://bintray.com/chesire/LintRules/lint-gradle/_latestVersion)

## Installation

Gradle - add the following line to your projects `build.gradle`

```groovy
compileOnly 'com.chesire.lintrules:{module}:{version}'
```
example
  
```groovy
compileOnly 'com.chesire.lintrules:lint-gradle:1.0.0'
```

## Rules Added

### lint-gradle
- **[DuplicateDependency]** - Highlights when the same dependency has been used multiple times within the same Gradle file.  
- **[LexicographicDependencies]** - Highlights when dependencies within a Gradle file are not ordered lexicographically.


## License

Apache 2.0 - See [LICENSE](https://github.com/Chesire/LintRules/blob/master/LICENSE) for more information.