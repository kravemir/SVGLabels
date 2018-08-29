# Example 02: tile label with instancing

* `svg-labels` is installed (if not, see README),
* `inkscape` is installed,
* `label.svg` is present in current working directory (download [label.svg](label.svg)),
* `product_honey.json` is present in current working directory (download [product_honey.json](product_honey.json)),
* `product_oranges.json` is present in current working directory (download [product_oranges.json](product_oranges.json)).

Then, by invoking following commands:

```bash
svg-labels tile --paper-size 210 297 --label-offset 5 13.5 --label-size 100 30 --label-delta 0 0 label.svg --instance-json product_honey.json label-tiled-honey.svg
svg-labels tile --paper-size 210 297 --label-offset 5 13.5 --label-size 100 30 --label-delta 0 0 label.svg --instance-json product_oranges.json label-tiled-oranges.svg
inkscape --file=label-tiled-honey.svg --without-gui --export-pdf=label-tiled-honey.pdf
inkscape --file=label-tiled-oranges.svg --without-gui --export-pdf=label-tiled-oranges.pdf
```

Following result files should have been created:

* `label-tiled-honey.svg` containing tiled label with `product_honey.json` instance data,
* `label-tiled-oranges.svg` containing tiled label with `product_oranges.json` instance data,
* `label-tiled-honey.pdf` containing `label-tiled-honey.svg` converted to PDF,
* `label-tiled-oranges.pdf` containing `label-tiled-oranges.svg` converted to PDF.
