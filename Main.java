package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application {

    Button button;
    Stage window;
    Rectangle[] border = new Rectangle[60];
    Rectangle[][] safety = new Rectangle[4][5];
    Polygon[] safetyZones = new Polygon[4];
    Polygon[] startZones = new Polygon[4];
    Circle[][] plPieces = new Circle[2][4];
    Circle currSelection;

    boolean canEnterSafety = true;
    boolean possibleMove = true;

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("I see demons");
        Group root = new Group();
        BoardFuncs.createBoard(root, border, safety, safetyZones, startZones);

        for(int i = 0; i < plPieces.length; i++) {
            Point2D currSfZn = BoardFuncs.getOrigin(safetyZones[i]);
            for(int j = 0; j < plPieces[i].length; j++) {
                if(i == 0) {
                    plPieces[i][j] = new Circle(currSfZn.getX(), currSfZn.getY(), 20);
                    plPieces[i][j].setId("plOne");
                } else {
                    plPieces[i][j] = new Circle(currSfZn.getX(), currSfZn.getY(), 20);
                    plPieces[i][j].setId("plTwo");
                }
            }
        }
        for(Circle[] x : plPieces) {
            for(Circle y : x) {
                y.setOnMouseClicked(e -> {
                    if(((Circle) e.getSource()).getId() == "plOne") {
                        currSelection = ((Circle) e.getSource());
                        currSelection.setId("plOneSel");
                    }
                });
                root.getChildren().add(y);
            }
        }

        for(Rectangle x : border) {
            x.setOnMouseClicked(e -> {
                if(currSelection != null && possibleMove) {
                    double rX = ((Rectangle) e.getSource()).getX();
                    double rY = ((Rectangle) e.getSource()).getY();
                    currSelection.setCenterY(rY + 25);
                    currSelection.setCenterX(rX + 25);
                    currSelection.setId("plOne");
                    currSelection = null;
                }
            });
        }
        for(Rectangle x : safety[0]) {
            x.setOnMouseClicked(e -> {
                if(currSelection != null && canEnterSafety == true) {
                    double rX = ((Rectangle) e.getSource()).getX();
                    double rY = ((Rectangle) e.getSource()).getY();
                    currSelection.setCenterY(rY + 25);
                    currSelection.setCenterX(rX + 25);
                    currSelection.setId("plOne");
                    currSelection = null;
                }
            });
        }

        Scene scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add("sample/SorryBoard.css");
        window.setScene(scene);
        window.show();
    }
}
