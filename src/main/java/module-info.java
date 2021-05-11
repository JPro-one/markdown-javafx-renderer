module com.sandec.mdfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires flexmark;
    requires flexmark.ext.attributes;
    requires flexmark.ext.tables;
    requires flexmark.ext.gfm.strikethrough;
    requires flexmark.util;
    requires flexmark.util.ast;
    requires flexmark.util.builder;
    requires flexmark.util.misc;
    requires flexmark.util.sequence;
    requires flexmark.util.data;
    requires flexmark.util.collection;

    opens com.sandec.mdfx;
    exports com.sandec.mdfx;
}