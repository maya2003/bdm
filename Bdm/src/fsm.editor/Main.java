/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

//!!
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import java.lang.Math;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.beans.value.*;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class Main extends Application
{
  @Override
  public void start(Stage primaryStage)
  {
    Group g = new Group();


    TextArea _ = new TextArea();
    _.setText("*");
    _.relocate(-120, -120);
    _.setPrefWidth(40);
    _.setPrefHeight(40);
    g.getChildren().add(_);


////////////////////////////////////////
////////////////////////////////////////
////////////////////////////////////////
////////////////////////////////////////


    Group transitionG = new Group();


    Line arrow1 = new Line();
    arrow1.setStartX(0);
    arrow1.setStartY(-20);
    arrow1.setEndX(0);
    arrow1.setEndY(-10);
    transitionG.getChildren().add(arrow1);

    Polygon arrow2 = new Polygon(-10, -10, 10, -10, 0, 10);
    arrow2.setFill(Color.RED);
    transitionG.getChildren().add(arrow2);

    TextArea event = new TextArea();
    event.setPromptText("event");
    event.setText("event");
    event.setStyle("-fx-text-box-border: transparent;");
    event.relocate(-95, -2);
  event.setPrefWidth(100);
  event.setPrefHeight(30);
    transitionG.getChildren().add(event);

    g.getChildren().add(new DebugStroke(event));

    Line arrow3 = new Line();
    arrow3.setStartX(-45);
    arrow3.setStartY(10);
    arrow3.setEndX(-35);
    arrow3.setEndY(10);
    transitionG.getChildren().add(arrow3);

    Polygon arrow4 = new Polygon(-35, 0, -15, 10, -35, 20);
    arrow4.setFill(Color.RED);
    transitionG.getChildren().add(arrow4);

    Line transition = new Line();
    transition.setStartX(-10);
    transition.setStartY(10);
    transition.setEndX(10);
    transition.setEndY(10);
    transitionG.getChildren().add(transition);

    TextArea condition = new TextArea();
    condition.setPromptText("condition");
    condition.setText("condition");
    condition.setStyle("-fx-text-box-border: transparent;");
    condition.relocate(20, -20);
  condition.setPrefWidth(100);
  condition.setPrefHeight(30);
    transitionG.getChildren().add(condition);

   g.getChildren().add(new DebugStroke(condition));

    Line separator = new Line();
    separator.setStartX(20);
    separator.setStartY(10);
    separator.setEndX(100);
    separator.setEndY(10);
    transitionG.getChildren().add(separator);

    TextArea action = new TextArea();
    action.setPromptText("action");
    action.setText("action");
    action.setStyle("-fx-text-box-border: transparent;");
    action.relocate(20, 20);
  action.setPrefWidth(100);
  action.setPrefHeight(30);
    transitionG.getChildren().add(action);

   g.getChildren().add(new DebugStroke(action));

   g.getChildren().add(new DebugStroke(transitionG));


////////////////////////////////////////
////////////////////////////////////////
////////////////////////////////////////
////////////////////////////////////////

  g.getChildren().add(transitionG);

  CubicCurve cubicCurve = new CubicCurve(); //CubicCurveTo
  cubicCurve.setStartX(0);
  cubicCurve.setStartY(10);
  cubicCurve.setControlX1(0);
  cubicCurve.setControlY1((10+250)/2);
  cubicCurve.setControlX2(100);
  cubicCurve.setControlY2((10+250)/2);
  cubicCurve.setEndX(100);
  cubicCurve.setEndY(250);
  cubicCurve.setStroke(Color.PURPLE);
  cubicCurve.setFill(null);
  g.getChildren().add(cubicCurve);

  Circle state = new Circle();
  state.setCenterX(100);
  state.setCenterY(300);
  state.setRadius(50);
  state.setFill(Color.RED);
  g.getChildren().add(state);

    TextArea name = new TextArea();
    name.setPromptText("name");
    name.setText("name");
    name.setStyle("-fx-text-box-border: transparent;");
    name.relocate(82, 304);
  name.setPrefWidth(100);
  name.setPrefHeight(30);
    g.getChildren().add(name);

   g.getChildren().add(new DebugStroke(name));



  StackPane root = new StackPane();
  root.getChildren().add(g);
  Scene scene = new Scene(root, 1000, 550);
  primaryStage.setScene(scene);
  primaryStage.show();
  }

  public static void main(String[] args)
  {
    launch(args);
  }

}

