package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    Button button;
    Stage window;
    Rectangle[] border = new Rectangle[60];
    Rectangle[][] safety = new Rectangle[4][5];

    public static void main(String[] args) {
        launch(args);
    }

    public static Polygon createOctagon(int radius, Point2D origin) {
        Polygon oct;
        Point2D[] points = new Point2D[8];
        for(int i = 0; i < 8; i++) {
            points[i] = new Point2D(origin.getX() + radius*Math.cos((2 * Math.PI * i)/8), origin.getY() + radius * Math.sin((2 * Math.PI * i)/8));
        }
        double[] polyPoints = new double[16];
        for(int i = 0; i < points.length; i += 2) {
            polyPoints[i] = points[i].getX();
            polyPoints[i + 1] = points[i].getY();
        }
        for(Point2D x : points) {
            System.out.println(x);
        }
        oct = new Polygon(polyPoints);
        return oct;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("I see demons");
        Group root = new Group();

        for(int i = 0; i <= 15; i++) {
            border[i] = new Rectangle(50, 50);
            border[i].setId("Squares");
            border[i].setY(50);
            if(i == 0) {
                border[i].setX(0);
            } else {
                border[i].setX(border[i - 1].getX() + 50);
            }
        }
        for(int i = 16; i <= 30; i++) {
            border[i] = new Rectangle(50,50);
            border[i].setId("Squares");
            border[i].setX(border[15].getX());
            border[i].setY(border[i - 1].getY() + 50);
        }
        for(int i = 31; i <=45; i++) {
            border[i] = new Rectangle(50,50);
            border[i].setId("Squares");
            border[i].setY(border[30].getY());
            border[i].setX(border[i - 1].getX() - 50);
        }
        for(int i = 46; i < border.length; i++) {
            border[i] = new Rectangle(50,50);
            border[i].setId("Squares");
            border[i].setX(border[45].getX());
            border[i].setY(border[i - 1].getY() - 50);
        }
        for(int i = 0; i < border.length; i++) {
            int index = i;
            int side = index / 15;
            int relLoc = index % 15;
            border[i].setOnMouseClicked(event -> System.out.println("Index: " + index + "\nSide: " + side + "\nRelative Location: " + relLoc));
        }
        for(int i = 0; i < safety.length; i++) {
            for(int j = 0; j < safety[i].length; j++) {
                if(i == 0) {
                    safety[i][j] = new Rectangle(50, 50);
                    safety[i][j].setX(border[2 + (15 * i)].getX());
                    safety[i][j].setY(border[2 + (15 * i)].getY() + (50 + (j * 50)));
                } else if(i == 1) {
                    safety[i][j] = new Rectangle(50, 50);
                    safety[i][j].setX(border[2 + (15 * i)].getX() - (50 + (j * 50)));
                    safety[i][j].setY(border[2 + (15 * i)].getY());
                } else if(i == 2) {
                    safety[i][j] = new Rectangle(50, 50);
                    safety[i][j].setX(border[2 + (15 * i)].getX());
                    safety[i][j].setY(border[2 + (15 * i)].getY() - (50 + (j * 50)));
                } else {
                    safety[i][j] = new Rectangle(50, 50);
                    safety[i][j].setX(border[2 + (15 * i)].getX() + (50 + (j * 50)));
                    safety[i][j].setY(border[2 + (15 * i)].getY());
                }
            }
        }
        for(int i = 0; i < safety.length; i++) {
            for(int j = 0; j < safety[i].length; j++) {
                safety[i][j].setId("Squares");
                int side, index;
                side = i;
                index = j;
                safety[i][j].setOnMouseClicked(e -> System.out.println("On team " + side + "'s safety zone and at index " + index));
                root.getChildren().add(safety[i][j]);
            }
        }

        for(int i = 0; i <  border.length; i++) {
            root.getChildren().add(border[i]);
        }



        Scene scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add("sample/SorryBoard.css");
        window.setScene(scene);
        window.show();
    }
}
