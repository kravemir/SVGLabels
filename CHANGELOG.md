# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
- library: support of template instancing in tiling rendering
- tool: support of template instancing with option `--instances-json` for `tile` command
- tests: separated sourceset to share test classes and resources between modules 

### Changed
- library: extended tiling model to support definition of instances
- library: a bit polished API interfaces and models

## [0.2.0] - 2018-07-01
### Added
- library: instance SVG rendering from template:
    - separate template descriptor and instance data
    - matching text elements with XPath
    - support for multi-line texts
    - conditional rule application based on `if` (JEXL) condition
- tool: added option to render tiled labels from template and content JSON
- tool: added `instance` sub-command to render instance label with tiling

### Changed
- library: lots of refactoring to simplify huge classes
- tool: moved tiled labels rendering into `tile` sub-command

## 0.1.0 - 2018-05-01
### Added
- library implementing tiled SVG rendering
- CLI tool

[Unreleased]: https://github.com/kravemir/svg-labels/compare/0.2.0...master
[0.2.0]: https://github.com/kravemir/svg-labels/compare/0.1.0...0.2.0
