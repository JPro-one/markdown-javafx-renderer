package com.sandec.mdfx;

import com.sandec.mdfx.impl.AdaptiveImage;
import com.sandec.mdfx.impl.MDFXNodeHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import java.util.List;

public class MarkdownView extends VBox {

    private SimpleStringProperty mdString = new SimpleStringProperty("");

    public MarkdownView(String mdString) {
        this.mdString.set(mdString);
        this.mdString.addListener((p,o,n) -> updateContent());
        getStylesheets().add("/com/sandec/mdfx/mdfx.css");
        getDefaultStylesheets().forEach(s -> getStylesheets().add(s));
        updateContent();
    }
    public MarkdownView() {
        this("");
    }

    protected List<String> getDefaultStylesheets() {
        return List.of("/com/sandec/mdfx/mdfx-default.css");
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

            // The TextFlow does not limit the width of it's node based on the available width
            // As a workaround, we bind to the width of the MarkDownView.
            r.maxWidthProperty().bind(widthProperty());

            return r;
        }

    }
}
