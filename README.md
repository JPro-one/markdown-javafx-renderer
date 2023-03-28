# markdown-javafx-renderer

[![JPro supported](https://img.shields.io/badge/JPro-supported-brightgreen.svg)](https://www.jpro.one/) 
[![JFX-Central](https://img.shields.io/badge/jfxcentral-mdfx-brightgreen.svg)](https://www.jfx-central.com/libraries/mdfx) 
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
}
```
Add The following dependency:
```
dependencies {
    compile "com.sandec:mdfx:0.2.12"
}
```

Usage:
```
import com.sandec.mdfx.MarkdownView;

MarkdownView mdfx = new MarkdownView("your-markdown");
```

Simple Application:
[Source Code](https://github.com/jpro-one/markdown-javafx-renderer/blob/master/example/src/main/java/com/sandec/mdfx/ExampleMDFX.java)

Feature Overview:
[Reference-Markdown-File](https://github.com/jpro-one/markdown-javafx-renderer/blob/master/example/src/main/resources/com/sandec/mdfx/sample.md)


You can personalize the looking of your markdown via css.
[Minimal default-file](https://github.com/jpro-one/markdown-javafx-renderer/blob/master/src/main/resources/com/sandec/mdfx/mdfx-default.css)
To use your own css, you have to overwrite the `getDefaultStylehsheets`.
```java
protected List<String> getDefaultStylehsheets() {
    return List.of("/your-css.css");
}
```
Alternatively, you can also return an empty list, and add your css via the `getStylesheets` of the Scene.


## Changelog

### 0.2.12 (28. March 2021)
* It's no longer necessary to add the default-css. You can add your own css via `getDefaultStylehsheets`.
### 0.2.11
* Fixed wrong layouting of the Image.
### 0.2.10
* Added a new default implementation for Images.
The images no longer expand outside of the viewport.
* The images are now also loaded asynchronously.
### 0.2.9
* Fixed an issue, which caused that sometimes a whitespace was missing in links.
### 0.2.8 (13. October 2022)
* Changed JavaFX Dependencie to be compile only.
### 0.2.7 (10. March 2022)
* Updated Flexmark to 0.64
### 0.2.6 (10. March 2022)
* Fixed Newline character in links.
### 0.2.5 (11. November 2021)
* Fixed an bug with nested Sorted/Unsorted lists.
### 0.2.4
* Fixed an layouting issue with lists. In some situations "..." was shown for left label counting the entries.
### 0.2.3
* The blockquote is now configured with the css variables `-mdfx-bq-color-border` and `-mdfx-bq-color-border`. Users might want to add these variables to their css. 
* Ordered lists start now with 1 instead of 0.
### 0.2.2
* -- skip this version --
### 0.2.1
* Added support for BlockQuotes
* Added support for TaskLists
* Renamed MDFXNode to MarkdownView
* Moved internal classes to an impl package
### 0.2.0
* Added support for ordered lists.
* We now us the Java11 Module System. At least Java11 is now required.
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
