# `svg-labels` help

```
Usage: <main class> [-hv] [COMMAND]
  -h, --help      display a help message
  -v, --version   display version info
Commands:
  tile      Tile labels
  instance  Fill label template with instance data
```

Command `tile`:
```
Usage: <main class> tile [-h] [--instance-json=FILE] [--instances-json=FILE]
                         [--label-delta=mm mm]... [--label-offset=mm mm]...
                         [--label-size=mm mm]... [--paper-size=mm mm]... SOURCE
                         TARGET
Tile labels
      SOURCE                 Path to SVG file containing a label
      TARGET                 Path to SVG file which should be generated
      --instance-json=FILE   Path to JSON file containing values for single instance
      --instances-json=FILE  Path to JSON file containing array of instances
      --label-delta=mm mm    X and Y delta between labels in mm, ie. 5 5
      --label-offset=mm mm   X and Y offset of the first label in mm, ie. 5 5
      --label-size=mm mm     Width and height of label in mm, ie.
      --paper-size=mm mm     Width and height of the paper in mm, ie. 210 297 for A4
                               paper portrait
  -h, --help                 display a help message
```

Command `instance`:
```
Usage: <main class> instance [-h] [--instance-json=<instanceJsonFile>] SOURCE
                             TARGET
Fill label template with instance data
      SOURCE   Path of a SVG file containing a label
      TARGET   Path of a SVG file which should be generated
      --instance-json=<instanceJsonFile>

  -h, --help   display a help message
```
