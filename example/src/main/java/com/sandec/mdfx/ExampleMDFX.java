package com.sandec.mdfx;

import com.sandec.mdfx.MDFXNode;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;

public class ExampleMDFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        String mdfxTxt = IOUtils.toString(getClass().getResourceAsStream("/com/sandec/mdfx/sample.md"), "UTF-8");

        MDFXNode mdfxNode = new MDFXNode(mdfxTxt);

        ScrollPane root = new ScrollPane(mdfxNode);

        root.setFitToWidth(true);
        root.getStylesheets().add("/com/sandec/mdfx/mdfx-default.css");

        Scene scene = new Scene(root, 700,700);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}