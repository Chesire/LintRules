Change Log
==========

## Version 1.1.3
_20-12-21_

* fix: false duplicate issue on project dependencies
  * When having dependencies that were `implementation project(path: ':core')` then the duplicate dependency check would flag up every project as a duplicate.

## Version 1.1.2
_19-11-14_

* fix: lexicographic ordering doesn't properly check for :
  * For users who have muliple repositories then the Lexicographic ordering gets confused, as it will see that `:` is higher than `.`, so it would allow items such as `com.chesire:lifecyklelog` to come after `com.chesire.lintrules:lint-gradle`. Made it replace instances of `:` with a `.` and then do a straight compare, which should produce a valid result.

## Version 1.1.1
_19-11-12_

* fix: xml module not registering correctly
  * The lintrules-xml module was not being correctly registered, causing it to fail when trying to run lint.

## Version 1.1.0
_19-11-10_

* feat: add new xml module
  * Add new module for lint rules in xml files, adds two new rules, ColorCasing & UnexpectedAttribute.


## Version 1.0.0
_19-10-27_

* Initial version.
