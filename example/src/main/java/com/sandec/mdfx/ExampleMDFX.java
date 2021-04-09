package com.sandec.mdfx;

import com.sandec.mdfx.MDFXNode;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;

public class ExampleMDFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        fr.brouillard.oss.cssfx.CSSFX.start();

        String mdfxTxt = IOUtils.toString(getClass().getResourceAsStream("/com/sandec/mdfx/sample.md"), "UTF-8");

        MDFXNode mdfxNode = new MDFXNode(mdfxTxt) {
            //@Override
            //public boolean showChapter(int[] currentChapter) {
            //    return currentChapter[1] == 1;
            //}


            @Override
            public void setLink(Node node, String link, String description) {
                System.out.println("setLink: " + link);
                node.setCursor(Cursor.HAND);
                node.setOnMouseClicked(e -> {
                    System.out.println("link: " + link);
                });
            }

            @Override
            public Node generateImage(String url) {
                if(url.equals("node://colorpicker")) {
                    return new ColorPicker();
                } else {
                    return super.generateImage(url);
                }
            }
        };

        TextArea textArea = new TextArea(mdfxTxt);

        mdfxNode.mdStringProperty().bind(textArea.textProperty());
        mdfxNode.getStylesheets().add("/com/sandec/mdfx/mdfx-sample.css");

        ScrollPane content = new ScrollPane(mdfxNode);

        content.setFitToWidth(true);

        textArea.setMinWidth(350);
        HBox root = new HBox(textArea,content);

        Scene scene = new Scene(root, 700,700);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}