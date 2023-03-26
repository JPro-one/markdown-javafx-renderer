package com.sandec.mdfx;

import com.sandec.mdfx.impl.MDFXNodeHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MarkdownView extends VBox {

    private SimpleStringProperty mdString = new SimpleStringProperty("");

    public MarkdownView(String mdString) {
        this.mdString.set(mdString);
        this.mdString.addListener((p,o,n) -> updateContent());
        getStylesheets().add("/com/sandec/mdfx/mdfx.css");
        updateContent();
    }
    public MarkdownView() {
        this("");
    }

    private void updateContent() {
        MDFXNodeHelper content = new MDFXNodeHelper(this, mdString.getValue());
        getChildren().clear();
        getChildren().add(content);
    }

    public StringProperty mdStringProperty() {
        return mdString;
    }

    public void setMdString(String mdString) {
        this.mdString.set(mdString);
    }

    public String getMdString() {
        return mdString.get();
    }

    public boolean showChapter(int[] currentChapter) {
            return true;
    }

    public void setLink(Node node, String link, String description) {
        // TODO
        //com.jpro.web.Util.setLink(node, link, scala.Option.apply(description));
    }

    public Node generateImage(String url) {
        if(url.isEmpty()) {
            return new Group();
        } else {
            Image img = new Image(url, false);
            AdaptiveImage r = new AdaptiveImage(img);

            // The StackPane is just a workaround.
            // Otherwise the TextFlow doesn't get rendered properly, when the image is loaded.
            StackPane p = new StackPane();

            img.widthProperty().addListener((p1,o,n) -> {
                if(n.intValue() > 0) {
                    p.getChildren().clear();
                    p.getChildren().add(r);
                }
            });

            // The TextFlow does not limit the width of it's node based on the available width
            // As a workaround, we bind to the width of the MarkDownView.
            r.maxWidthProperty().bind(widthProperty());

            return r;
        }

    }
}
