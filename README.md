svg-labels
==========

[![Build Status](https://travis-ci.org/kravemir/svg-labels.svg?branch=master)](https://travis-ci.org/kravemir/svg-labels)
[![codebeat badge](https://codebeat.co/badges/8d261543-da3a-4592-bc5f-f5ddc6ead398)](https://codebeat.co/projects/github-com-kravemir-svg-labels-master)

Designed to be simply usable java library and java tool for generation of documents with labels to print.

The library consist of renderers performing SVG manipulations for creation of label materials.

## CLI tool

The tool offers commandline a simple way to invoke these manipulations without need to write any java code.

###  Tool installation

The tool can be installed as [snap package from snapcraft.io][snapcraft-io-package]:

```
sudo snap install svg-labels
```

### Usage examples

Examples are available inside [examples](examples) subdirectory, which contains:

| Example                                                      | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [01-tile-label](examples/01-tile-label/)                     | Create SVG and PDF for print containing tiled label          |
| [02-tile-label-with-instancing](examples/02-tile-label-with-instancing/) | Create SVG(s) and PDF(s) for print containing tiled label filled with data of corresponding instances (products) |
| [91-batch-instacing-using-shell](examples/91-batch-instacing-using-shell/) | Create instanced SVG(s) and PDF(s) for print, in batch, using `bash` script. |
| [92-batch-instacing-using-makefile](examples/92-batch-instacing-using-makefile/) | Create instanced PDF(s) for print, in batch, using `Makefile`. Supports incremental builds, thanks to `make` functionality. |

### CLI options

Check [complete help](docs/help.md) to see all available options.

## Usage as a library

Library's artifacts are published to maven central. See details [at search.maven.org][search-maven-org-by-group], or [at mvnrepository.com][mvnrepository-com-group].

TODO: simple example

TODO: javadocs

## License

The project is licensed under Apache License, Version 2.0, January 2004. See [LICENSE](LICENSE).

[snapcraft-io-package]: https://snapcraft.io/svg-labels
[search-maven-org-by-group]: https://search.maven.org/search?q=kravemir
[mvnrepository-com-group]: https://mvnrepository.com/artifact/org.kravemir.svg.labels
