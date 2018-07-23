package com.sandec.mdfx;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.*;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.parser.Parser;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class MDFXNodeHelper extends VBox {
  String mdString;

  MDFXNode parent;

  final static String ITALICE_CLASS_NAME = "markdown-italic";
  final static String BOLD_CLASS_NAME = "markdown-bold";

  List<String> elemStyleClass = new LinkedList<String>();

  List<Consumer<Pair<Node,String>>> elemFunctions = new LinkedList<Consumer<Pair<Node,String>>>();

  Boolean nodePerWord = false;

  List<String> styles = new LinkedList<String>();

  VBox root = new VBox();

  GridPane grid = null;
  int gridx = 0;
  int gridy = 0;
  TextFlow flow = null;

  int[] currentChapter = new int[6];

  public boolean shouldShowContent() {
    return parent.showChapter(currentChapter);
  }

  public void newParagraph() {
    TextFlow newFlow = new TextFlow();
    newFlow.getStyleClass().add("markdown-normal-flow");
    root.getChildren().add(newFlow);
    flow = newFlow;
  }

  public MDFXNodeHelper(MDFXNode parent, String mdstring) {
    this.parent = parent;

    getStylesheets().add("/com/sandec/mdfx/mdfx.css");

    root.getStyleClass().add("markdown-paragraph-list");
    root.setFillWidth(true);

    LinkedList<Extension> extensions = new LinkedList();
    extensions.add(TablesExtension.create());
    Parser parser = Parser.builder().extensions(extensions).build();
    org.commonmark.node.Node node = parser.parse(mdstring);
    node.accept(new MDParser());

    this.getChildren().add(root);
  }


  class MDParser extends AbstractVisitor {
    public void visit(org.commonmark.node.Code code) {

      Label label = new Label(code.getLiteral());
      label.getStyleClass().add("markdown-code");

      Region bgr1 = new Region();
      bgr1.setManaged(false);
      bgr1.getStyleClass().add("markdown-code-background");
      label.boundsInParentProperty().addListener((p,oldV,newV) -> {
        bgr1.setTranslateX(newV.getMinX() + 2);
        bgr1.setTranslateY(newV.getMinY() - 2);
        bgr1.resize(newV.getWidth() - 4, newV.getHeight() + 4);
      });

      flow.getChildren().add(bgr1);
      flow.getChildren().add(label);

      super.visit(code);
    }

    public void visit(org.commonmark.node.CustomBlock customBlock) {
      flow.getChildren().add(new Text("\n\n"));
      super.visit(customBlock);
    }


    public void visit(org.commonmark.node.CustomNode customNode) {
      if(customNode instanceof TableHead){
        TextFlow oldFlow = flow;
        grid = new GridPane();
        grid.getStyleClass().add("markdown-table-table");
        int gridx = 0;
        int gridy = -1;
        root.getChildren().add(grid);

        super.visit(customNode);


        IntStream.rangeClosed(1, gridx).forEach( i -> {
          ColumnConstraints constraint = new ColumnConstraints();
          if(i == gridx) {
            constraint.setPercentWidth(100.0 * (2.0 / (gridx+1.0)));
          }
          grid.getColumnConstraints().add(constraint);
        });

        flow = oldFlow;
        newParagraph();
        //flow.styleClass ::= "markdown-normal-flow"
      } else if(customNode instanceof TableBody) {
        super.visit(customNode);
      //} else if(customNode instanceof TableBlock) {
      //  super.visit(customNode);
      } else if(customNode instanceof TableRow) {
        gridx = 0;
        gridy += 1;
        super.visit(customNode);
      } else if(customNode instanceof TableCell) {
        TextFlow oldFlow = flow;
        flow = new TextFlow();
        flow.getStyleClass().add("markdown-normal-flow");
        TextFlow container = flow;
        //  println("grid: " + grid)
        //  javafx.scene.layout.GridPane.setHgrow(container,Priority.ALWAYS)
        flow.setPrefWidth(9999);
        flow.getStyleClass().add("markdown-table-cell");
        if(gridy == 0) {
          flow.getStyleClass().add("markdown-table-cell-top");
        }
        if(gridy % 2 == 0) {
          flow.getStyleClass().add("markdown-table-odd");
        } else {
          flow.getStyleClass().add("markdown-table-even");
        }
        grid.add(container,gridx,gridy);
        gridx += 1;
        super.visit(customNode);
      }
    }

    public void visit(org.commonmark.node.Document document) {
      super.visit(document);
    }

    public void visit(org.commonmark.node.Emphasis emphasis) {
      elemStyleClass.add(ITALICE_CLASS_NAME);
      super.visit(emphasis);
      elemStyleClass.remove(ITALICE_CLASS_NAME);
    }

    public void visit(org.commonmark.node.StrongEmphasis strongEmphasis) {
      elemStyleClass.add(BOLD_CLASS_NAME);
      super.visit(strongEmphasis);
      elemStyleClass.remove(BOLD_CLASS_NAME);
    }

    public void visit(org.commonmark.node.FencedCodeBlock fencedCodeBlock) {

      Label label = new Label(fencedCodeBlock.getLiteral());
      label.getStyleClass().add("markdown-codeblock");
      VBox vbox = new VBox(label);
      vbox.getStyleClass().add("markdown-codeblock-box");

      root.getChildren().add(vbox);
      //flow.styleClass ::= "markdown-normal-flow"
      //flow <++ new Text("\n")
      super.visit(fencedCodeBlock);
    }

    public void visit(org.commonmark.node.SoftLineBreak softLineBreak) {
      //flow <++ new Text("\n")
      addText(" ","");
      super.visit(softLineBreak);
    }

    public void visit(org.commonmark.node.HardLineBreak hardLineBreak) {
      flow.getChildren().add(new Text("\n"));
      super.visit(hardLineBreak);
    }


    public void visit(org.commonmark.node.Heading heading) {

      if(heading.getLevel() == 1 || heading.getLevel() == 2) {
        currentChapter[heading.getLevel()] += 1;

        IntStream.rangeClosed(heading.getLevel()+1, currentChapter.length-1).forEach( i -> {
          currentChapter[i] = 0;
        });
      }

      if(shouldShowContent()) {
        newParagraph();

        flow.getStyleClass().add("markdown-heading-" + heading.getLevel());
        flow.getStyleClass().add("markdown-heading");

        super.visit(heading);
      }
    }


    public void visit(org.commonmark.node.ListItem listItem) {
      // add new listItem
      VBox oldRoot = root;

      VBox newRoot = new VBox();
      newRoot.getStyleClass().add("markdown-vbox1");
      newRoot.getStyleClass().add("markdown-paragraph-list");
      newRoot.setFillWidth(true);

      Label label = new Label(" • ");
      label.setMinWidth(20);

      HBox hbox = new HBox();
      hbox.getStyleClass().add("markdown-hbox1");
      hbox.getChildren().add(label);
      hbox.setAlignment(Pos.TOP_LEFT);
      hbox.getChildren().add(newRoot);


      oldRoot.getChildren().add(hbox);

      root = newRoot;

      super.visit(listItem);
      root = oldRoot;
    }

    public void visit(org.commonmark.node.BulletList bulletList) {
      VBox oldRoot = root;
      root = new VBox();
      oldRoot.getChildren().add(root);
      newParagraph();
      flow.getStyleClass().add("markdown-normal-flow");
      super.visit(bulletList);
      root = oldRoot;
    }

    public void visit(org.commonmark.node.OrderedList orderedList) {
      VBox oldRoot = root;
      root = new VBox();
      oldRoot.getChildren().add(root);
      newParagraph();
      flow.getStyleClass().add("markdown-normal-flow");
      super.visit(orderedList);
      root = oldRoot;
    }

    public void visit(org.commonmark.node.Paragraph paragraph) {
      newParagraph();
      flow.getStyleClass().add("markdown-normal-flow");
      super.visit(paragraph);
    }

    public void visit(org.commonmark.node.Image image) {
      flow.getChildren().add(new ImageView(new Image(image.getDestination())));
      super.visit(image);
    }

    public void visit(org.commonmark.node.Link link) {

      LinkedList<Node> nodes = new LinkedList<>();

      Consumer<Pair<Node,String>> addProp = (pair) -> {
        Node node = pair.getKey();
        String txt = pair.getValue();
        nodes.add(node);

        node.getStyleClass().add("markdown-link");
        parent.setLink(node,link.getDestination(),txt);
      };
      Platform.runLater(() -> {
        BooleanProperty lastValue = new SimpleBooleanProperty(false);
        Runnable updateState = () -> {
          boolean isHover = nodes.stream().map(node -> node.isHover()).collect(Collectors.toList()).contains(true);
          if(isHover != lastValue.get()) {
            lastValue.set(isHover);
            nodes.stream().forEach(node -> {
              if(isHover) {
                node.getStyleClass().add("markdown-link-hover");
              } else {
                node.getStyleClass().remove("markdown-link-hover");
              }

            });
          }

        };

        nodes.stream().forEach(node -> {
          node.hoverProperty().addListener((p,o,n) -> updateState.run());
        });
        updateState.run();
      });

      boolean oldNodePerWord = nodePerWord;
      nodePerWord = true;
      elemFunctions.add(addProp);
      super.visit(link);
      nodePerWord = oldNodePerWord;
      elemFunctions.remove(addProp);
    }

    public void visit(org.commonmark.node.Text text) {
      super.visit(text);

      String wholeText = text.getLiteral();
      String[] textsSplitted = null;
      if(nodePerWord) {
        textsSplitted = text.getLiteral().split(" ");
      } else {
        textsSplitted = new String[1];
        textsSplitted[0] = text.getLiteral();
      }
      final String[] textsSplittedFinal = textsSplitted;

      IntStream.rangeClosed(0,textsSplitted.length - 1).forEach(i -> {
        if(i == 0) {
          addText(textsSplittedFinal[i], wholeText);
        } else {
          addText(" " + textsSplittedFinal[i], wholeText);
        }
      });
    }

    public void addText(String text, String wholeText) {
      if(!text.isEmpty()) {
        System.out.println("text: '" + text + "'");

        Text toAdd = new Text(text);

        toAdd.getStyleClass().add("markdown-text");
        elemStyleClass.stream().forEach(elemStyleClass -> {
          toAdd.getStyleClass().add(elemStyleClass);
        });
        elemFunctions.stream().forEach(f -> {
          f.accept(new Pair(toAdd,wholeText));
        });

        flow.getChildren().add(toAdd);
      }
    }
  }
}
