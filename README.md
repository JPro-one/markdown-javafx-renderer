# markdown-javafx-renderer

[![Build Status](https://travis-ci.org/markdown-javafx-renderer/.svg?branch=master)](https://travis-ci.org/jpro-one/markdown-javafx-renderer)

MDFX is a simple markdown-renderer for JavaFX.
It's based on [flexmark-java](https://github.com/vsch/flexmark-java).
It is used to render the [documentation for jpro](https://www.jpro.one/?page=docs/current/1.1/) at [jpro.one](https://www.jpro.one/).


## Usage


### Add the library to your project:
For Gradle:
```
repositories {
    maven {
        url "http://sandec.bintray.com/repo"
    }
}
dependencies {
    compile "com.sandec:mdfx:0.1.0-SNAPSHOT"
}
```

Usage:
```
import com.sandec.mdfx.MDFXNode;

MDFXNode mdfx = new MDFXNode("your-markdown");
content.getStylesheets().add("/com/sandec/mdfx/mdfx-default.css");
```

Simple Application:
link: TODO

Feature Overview:




## Development
Run the sample:
```
gradle example:run
gradle example:jproRun
```