package com.sandec.mdfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class MDFXNode extends VBox {

    SimpleStringProperty mdStringProperty = new SimpleStringProperty("");

    public MDFXNode(String mdString) {
        mdStringProperty.set(mdString);

        mdStringProperty.addListener((p,o,n) -> updateContent());
        updateContent();
    }
    public MDFXNode() {
        this("");
    }

    private void updateContent() {
        MDFXNodeHelper content = new MDFXNodeHelper(this, mdStringProperty.getValue());
        getChildren().clear();
        getChildren().add(content);
    }

    public boolean showChapter(int[] currentChapter) {
            return true;
    }

    public void setLink(Node node, String link, String description) {
        // TODO
        //com.jpro.web.Util.setLink(node, link, scala.Option.apply(description));
    }
}
