# `svg-labels` help

```
Usage: svg-labels [-hv] [COMMAND]
  -h, --help      display a help message
  -v, --version   display version info
Commands:
  tile      Tile labels
  instance  Fill label template with instance data
```

Command `tile`:
```
Usage: svg-labels tile [OPTIONS] SOURCE TARGET
Tile labels
      SOURCE                 Path to SVG file containing a label
      TARGET                 Path to SVG file which should be generated
      --instance-definitions-location FOLDER
                             Path to folder containing JSON files for instances
      --instance-json FILE   Path to JSON file containing values for single instance
      --instances-json FILE  Path to JSON file containing array of instances
      --label-delta mm mm    X and Y delta between labels in mm, ie. 5 5
      --label-offset mm mm   X and Y offset of the first label in mm, ie. 5 5
      --label-size mm mm     Width and height of label in mm, ie.
      --paper-size mm mm     Width and height of the paper in mm, ie. 210 297 for A4
                               paper portrait
      --template-descriptor FILE
                             Path to JSON file containing descriptor of template
  -h, --help                 display a help message
```

Command `instance`:
```
Usage: svg-labels instance [OPTIONS] SOURCE TARGET
Fill label template with instance data
      SOURCE   Path of a SVG file containing a label
      TARGET   Path of a SVG file which should be generated
      --instance-json <instanceJsonFile>

  -h, --help   display a help message
```
