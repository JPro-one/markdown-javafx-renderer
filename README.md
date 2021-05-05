# markdown-javafx-renderer

[![JPro supported](https://img.shields.io/badge/JPro-supported-brightgreen.svg)](https://www.jpro.one/)

[![Build Status](https://travis-ci.org/JPro-one/markdown-javafx-renderer.svg?branch=master)](https://travis-ci.org/JPro-one/markdown-javafx-renderer)

MDFX is a simple markdown-renderer for JavaFX.
It's based on [flexmark-java](https://github.com/vsch/flexmark-java).
It is used to render the [documentation for jpro](https://www.jpro.one/?page=docs/current/1.1/) at [jpro.one](https://www.jpro.one/).


## Usage


### Add the library to your project:
For Gradle, add the following to your `build.gradle`:

Add the following repository:
```
repositories {
    maven {
        url "https://sandec.jfrog.io/artifactory/repo"
    }

```
Add The following dependency:
```
dependencies {
    compile "com.sandec:mdfx:0.1.8"
}
```

Usage:
```
import com.sandec.mdfx.MDFXNode;

MDFXNode mdfx = new MDFXNode("your-markdown");
content.getStylesheets().add("/com/sandec/mdfx/mdfx-default.css");
```

Simple Application:
[Source Code](https://github.com/jpro-one/markdown-javafx-renderer/blob/master/example/src/main/java/com/sandec/mdfx/ExampleMDFX.java)

Feature Overview:
[Reference-Markdown-File](https://github.com/jpro-one/markdown-javafx-renderer/blob/master/example/src/main/resources/com/sandec/mdfx/sample.md)


You can personalize the looking of your markdown via css.
[Minimal default-file](https://github.com/jpro-one/markdown-javafx-renderer/blob/master/src/main/resources/com/sandec/mdfx/mdfx-default.css)
Instead of using `/com/sandec/mdfx/mdfx-default.css` you can create your own css-file, to personalize the looking of your markdown-code.

## Changelog

### 0.1.9
* Added support for strikethrough
* It's now possible to change the link color in the css with `-mdfx-link-color`
* Updated flexmark to 0.62.2
### 0.1.8
* The property mdString of MDFXNode is now public

## Development
Run the sample:
```
./gradlew example:run
./gradlew example:jproRun
```

Deploy new release:
```
./gradlew :publish
```
