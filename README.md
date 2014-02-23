TilePreview
==
Really simple program to see how well an image tiles, and pick up changes when you save them.

![screenshot](http://imgur.com/jiyPLkm.jpg)

(window is resizeable)

Requirements
--
* java
* some technical knowledge, because this isn't really user-friendly at all right now

Command-line Usage
--
```
    java -jar ./dist/tilepreview-0.1.jar [-xywhz] path/to/image/file
    
    Options: (all are integer values)
        -x <x>  - The x-offset inside the source image from which to sample. Default = 0
        -y <y>  - The y-offset inside the image from which to sample. Default = 0
        -w <w>  - The width inside the image to sample. Defaults to the whole thing.
        -h <h>  - The height inside the image to sample. Defaults to the whole thing.
        -z <z>  - Zoom factor. Defaults to 1. 2 would mean 200% zoom.
```

GUI Usage
--
TODO
