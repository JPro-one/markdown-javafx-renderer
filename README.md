# markdown-javafx-renderer

MDFX is a simple markdown-renderer for JavaFX.
It's based on [flexmark-java](https://github.com/vsch/flexmark-java).
It is used to render the [documentation for jpro](https://www.jpro.one/?page=docs/current/1.1/) at [jpro.one](https://www.jpro.one/).


## Usage


Add the repository and the library to your build:
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


## Development
Run the sample:
```
gradle example:run
gradle example:jproRun
```