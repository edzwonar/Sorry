package sample;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by bobman on 3/30/2016.
 */
public class BoardFuncs {
    public static Polygon regularPolygon(int sides, double radius, double initAngle, Point2D origin) {
        Polygon regPoly;
        initAngle = (initAngle * Math.PI)/180;
        Point2D[] points = new Point2D[sides];
        for(int i = 0; i < sides; i++) {
            points[i] = new Point2D(origin.getX() + radius*Math.cos((2 * Math.PI * i)/sides + initAngle), origin.getY() + radius * Math.sin((2 * Math.PI * i)/sides + initAngle));
        }
        double[] polyPoints = new double[sides * 2];
        for(int i = 0; i < points.length; i++) {
            int j = i * 2;
            polyPoints[j] = points[i].getX();
            polyPoints[j + 1] = points[i].getY();
        }
        regPoly = new Polygon(polyPoints);
        return regPoly;
    }

    public static void addOneVert(Polygon poly) {
        double oX = 0;
        double oY = 0;
        double r = 0;
        double s = Math.sqrt(Math.pow((poly.getPoints().get(2) - poly.getPoints().get(0)),2) + Math.pow((poly.getPoints().get(3) - poly.getPoints().get(1)), 2));
        for(int i = 0; i < poly.getPoints().size(); i+=2) {
            oX += poly.getPoints().get(i);
            oY += poly.getPoints().get(i + 1);
        }
        oX /= poly.getPoints().size() / 2;
        oY /= poly.getPoints().size() / 2;
        r = s/(2 * Math.sin(Math.PI/(poly.getPoints().size()/2)));
        poly.getPoints().add(0,0.0);
        poly.getPoints().add(1,0.0);

        for(int i = 0; i < poly.getPoints().size() / 2; i++) {

            poly.getPoints().set(i * 2, oX + r*Math.cos((2 * Math.PI * i)/(poly.getPoints().size() / 2)));
            poly.getPoints().set(i * 2 + 1, oY + r * Math.sin((2 * Math.PI * i)/(poly.getPoints().size() / 2)));
        }
    }

    public static void decOneVert(Polygon poly) {
        double oX = 0;
        double oY = 0;
        double r = 0;
        double s = Math.sqrt(Math.pow((poly.getPoints().get(2) - poly.getPoints().get(0)),2) + Math.pow((poly.getPoints().get(3) - poly.getPoints().get(1)), 2));
        for(int i = 0; i < poly.getPoints().size(); i+=2) {
            oX += poly.getPoints().get(i);
            oY += poly.getPoints().get(i + 1);
        }
        oX /= poly.getPoints().size() / 2;
        oY /= poly.getPoints().size() / 2;
        r = s/(2 * Math.sin(Math.PI/(poly.getPoints().size()/2)));
        poly.getPoints().remove(0);
        poly.getPoints().remove(0);
        System.out.println(poly.getPoints().size());

        for(int i = 0; i < poly.getPoints().size() / 2; i++) {

            poly.getPoints().set(i * 2, oX + r*Math.cos((2 * Math.PI * i)/(poly.getPoints().size() / 2)));
            poly.getPoints().set(i * 2 + 1, oY + r * Math.sin((2 * Math.PI * i)/(poly.getPoints().size() / 2)));
        }
    }

    public static Point2D getOrigin(Polygon poly) {
        double oX = 0;
        double oY = 0;
        for(int i = 0; i < poly.getPoints().size(); i+=2) {
            oX += poly.getPoints().get(i);
            oY += poly.getPoints().get(i + 1);
        }
        oX /= poly.getPoints().size() / 2;
        oY /= poly.getPoints().size() / 2;
        return new Point2D(oX, oY);
    }

    public static Shape slide(int dir) {
        Polygon arrow = regularPolygon(3,25,0,new Point2D(0,0));
        Rectangle strip = new Rectangle(5,-7.5,150,15);
        Circle finish = new Circle(150,0,20);
        Shape path = Shape.union(arrow,strip);
        path = Path.union(path, finish);
        path.setRotate(dir * 90);
        return path;
    }

    public static void createBoard(Group root, Rectangle[] border, Rectangle[][] safety, Polygon[] safetyZones, Polygon[] startZones) {
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
            border[i] = new Rectangle(50, 50);
            border[i].setId("Squares");
            border[i].setX(border[45].getX());
            border[i].setY(border[i - 1].getY() - 50);
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
                root.getChildren().add(safety[i][j]);
            }
        }

        for(int i = 0; i <  border.length; i++) {
            root.getChildren().add(border[i]);
        }
        safetyZones[0] = regularPolygon(8, 65.3281 , 22.5, new Point2D(safety[0][4].getX() + 25, safety[0][4].getY() + 60.3553  + 50));
        safetyZones[1] = regularPolygon(8, 65.3281 , 22.5, new Point2D(safety[1][4].getX() - 60.3553, safety[1][4].getY() + 25));
        safetyZones[2] = regularPolygon(8, 65.3281 , 22.5, new Point2D(safety[2][4].getX() + 25, safety[2][4].getY() - 60.3553));
        safetyZones[3] = regularPolygon(8, 65.3281 , 22.5, new Point2D(safety[3][4].getX() + 60.3553 + 50, safety[3][4].getY() + 25));

        startZones[0] = regularPolygon(8, 65.3281 , 22.5, new Point2D(border[4].getX() + 25, border[4].getY() + 60.3553  + 50));
        startZones[1] = regularPolygon(8, 65.3281 , 22.5, new Point2D(border[19].getX() - 60.3553, border[19].getY() + 25));
        startZones[2] = regularPolygon(8, 65.3281 , 22.5, new Point2D(border[34].getX() + 25, border[34].getY() - 60.3553));
        startZones[3] = regularPolygon(8, 65.3281 , 22.5, new Point2D(border[49].getX() + 60.3553 + 50, border[49].getY() + 25));

        root.getChildren().addAll(safetyZones[0], safetyZones[1], safetyZones[2], safetyZones[3], startZones[0], startZones[1], startZones[2], startZones[3]);
        //----------------slides----------------------------
        Shape sd1 = BoardFuncs.slide(0);
        sd1.setTranslateX(75);
        sd1.setTranslateY(75);
        Shape sd1sld2 = BoardFuncs.slide(0);
        sd1sld2.setTranslateX(525);
        sd1sld2.setTranslateY(75);
        sd1 = Shape.union(sd1, sd1sld2);
        sd1.setId("sd1");
        sd1.setDisable(true);
        Shape sd2 = new Path(((Path) sd1).getElements());
        sd2.setRotate(90);
        sd2.setTranslateY(350);
        sd2.setTranslateX(396);
        sd2.setId("sd2");
        sd2.setDisable(true);
        Shape sd3 = new Path(((Path) sd1).getElements());
        sd3.setRotate(180);
        sd3.setTranslateY(750);
        sd3.setTranslateX(-3);
        sd3.setId("sd3");
        sd3.setDisable(true);
        Shape sd4 = new Path(((Path) sd2).getElements());
        sd4.setRotate(-90);
        sd4.setTranslateY(350);
        sd4.setTranslateX(-353);
        sd4.setId("sd4");
        sd4.setDisable(true);
        root.getChildren().addAll(sd1, sd2, sd3, sd4) ;
        //-------------end of slides----------------------------
        //-------------peripherals------------------------------
        Text sorry2 = new Text(275,475, "Sorry!");
        sorry2.setFont(Font.font("Bauhaus 93", FontWeight.BOLD, 100));
        sorry2.setId("Sorry2");
        Text sorry = new Text(275,475, "Sorry!");
        sorry.setFont(Font.font("Bauhaus 93", FontWeight.BOLD, 100));
        sorry.setId("Sorry");


        System.out.println(javafx.scene.text.Font.getFamilies());
        root.getChildren().addAll(sorry, sorry2);
        //-------------end of peripherals-----------------------

        for(int i = 0; i < safetyZones.length; i++) {
            safetyZones[i].setId("Squares");
            startZones[i].setId("Squares");
        }
    }
}
