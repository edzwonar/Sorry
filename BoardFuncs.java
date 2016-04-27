
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

/*
Class that contains commonly used functions of the gui to cut down on clutter
 */
public class BoardFuncs {

    /**
     * Creates a regular polygon
     * @param sides Number of sides the polygon has
     * @param radius Radius of the polygon
     * @param initAngle Initial angle of the polygon in degrees
     * @param origin Where to place the center of the polygon
     * @return
     */
    public static Polygon regularPolygon(int sides, double radius, double initAngle, Point2D origin) {
        Polygon regPoly;
        initAngle = (initAngle * Math.PI)/180;
        Point2D[] points = new Point2D[sides];

        /*
        Increment through the number of verticies and find their x and y locations by first finding their location on
        the unit circle and then multipling that by the radius and finally adding the offset of the origin
         */
        for(int i = 0; i < sides; i++) {
            points[i] = new Point2D(origin.getX() + radius*Math.cos((2 * Math.PI * i)/sides + initAngle), origin.getY() + radius * Math.sin((2 * Math.PI * i)/sides + initAngle));
        }

        //Convert the Array of Point2Ds to an array of double points in the format of (x1,y1,x2,y2,...)
        double[] polyPoints = new double[sides * 2];
        for(int i = 0; i < points.length; i++) {
            int j = i * 2;
            polyPoints[j] = points[i].getX();
            polyPoints[j + 1] = points[i].getY();
        }
        //create a polygon from the array of double points and return it
        regPoly = new Polygon(polyPoints);
        return regPoly;
    }

    /**
     * Given a polygon, finds and returns its origin location
     * @param poly
     * @return Point2D containing the polygon's origin
     */
    public static Point2D getOrigin(Polygon poly) {
        double oX = 0;
        double oY = 0;
        //Adda all the x's and y's of the given polygon
        for(int i = 0; i < poly.getPoints().size(); i+=2) {
            oX += poly.getPoints().get(i);
            oY += poly.getPoints().get(i + 1);
        }
        //Divide the sumed x's and y's by the number of verticies to find their average
        oX /= poly.getPoints().size() / 2;
        oY /= poly.getPoints().size() / 2;
        //Return a Point2D containing these averaged x's and y's
        return new Point2D(oX, oY);
    }

    /**
     * Create's a shape that is the slide symbol
     * @param dir 0-3 value of which direction the slide should be facing
     * @return The slide shape
     */
    public static Shape slide(int dir) {

        //Create all the seperate parts of the slide
        Polygon arrow = regularPolygon(3,25,0,new Point2D(0,0));
        Rectangle strip = new Rectangle(5,-7.5,150,15);
        Circle finish = new Circle(150,0,20);

        //Union the arrow and strip, then union the Arrow/Strip and the Finish to create the final slide shape
        Shape path = Shape.union(arrow,strip);
        path = Path.union(path, finish);

        //Rotate the slide appropriately
        path.setRotate(dir * 90);
        return path;
    }

    /**
     * Gets the piece number from Array of Circles and the currently selected circle
     * @param arr Array of the player's Circles
     * @param currSelection Currently selected Circle
     * @return What number the piece is
     */
    public static int getCurrPieceIndex(Circle[] arr, Circle currSelection) {
        for(int i =0; i < arr.length; i++) {
            if(arr[i].equals(currSelection)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Simple contains function to see if the given index is in an array
     * Used for checking if the given board location is in the array of possible moves
     * @param array
     * @param key
     * @return
     */
    public static boolean contains(final int[] array, final int key) {
        for (final int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initializes the board GUI
     * @param root Group element to add to
     * @param border All border rectangles
     * @param safety All safety rectangles
     * @param safetyZones Home Polygons
     * @param startZones Start Polygons
     */
    public static void createBoard(Group root, Rectangle[] border, Rectangle[][] safety, Polygon[] safetyZones, Polygon[] startZones) {
        //--------------------------------BEGINNING OF BORDER DECLARATIONS------------------------------------------
        //Top edge
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
        //Right edge
        for(int i = 16; i <= 30; i++) {
            border[i] = new Rectangle(50,50);
            border[i].setId("Squares");
            border[i].setX(border[15].getX());
            border[i].setY(border[i - 1].getY() + 50);
        }
        //Bottom Edge
        for(int i = 31; i <=45; i++) {
            border[i] = new Rectangle(50,50);
            border[i].setId("Squares");
            border[i].setY(border[30].getY());
            border[i].setX(border[i - 1].getX() - 50);
        }
        //Left Edge
        for(int i = 46; i < border.length; i++) {
            border[i] = new Rectangle(50, 50);
            border[i].setId("Squares");
            border[i].setX(border[45].getX());
            border[i].setY(border[i - 1].getY() - 50);
        }
        //-------------------------------------------------SAFETY DECLARATIONS--------------------------------
        for(int i = 0; i < safety.length; i++) {
            for(int j = 0; j < safety[i].length; j++) {
                if(i == 0) {
                    //player 1 safety
                    safety[i][j] = new Rectangle(50, 50);
                    safety[i][j].setX(border[2 + (15 * i)].getX());
                    safety[i][j].setY(border[2 + (15 * i)].getY() + (50 + (j * 50)));
                } else if(i == 1) {
                    //player 2 safety
                    safety[i][j] = new Rectangle(50, 50);
                    safety[i][j].setX(border[2 + (15 * i)].getX() - (50 + (j * 50)));
                    safety[i][j].setY(border[2 + (15 * i)].getY());
                } else if(i == 2) {
                    //player 3 safety
                    safety[i][j] = new Rectangle(50, 50);
                    safety[i][j].setX(border[2 + (15 * i)].getX());
                    safety[i][j].setY(border[2 + (15 * i)].getY() - (50 + (j * 50)));
                } else {
                    //player 4 safety
                    safety[i][j] = new Rectangle(50, 50);
                    safety[i][j].setX(border[2 + (15 * i)].getX() + (50 + (j * 50)));
                    safety[i][j].setY(border[2 + (15 * i)].getY());
                }
            }
        }
        //set safety id
        for(int i = 0; i < safety.length; i++) {
            for(int j = 0; j < safety[i].length; j++) {
                safety[i][j].setId("Squares");
                root.getChildren().add(safety[i][j]);
            }
        }
        //add to root
        for(int i = 0; i <  border.length; i++) {
            root.getChildren().add(border[i]);
        }
        //------------------------SAFETY DECLARATION------------------------
        safetyZones[0] = regularPolygon(8, 65.3281 , 22.5, new Point2D(safety[0][4].getX() + 25, safety[0][4].getY() + 60.3553  + 50));
        safetyZones[1] = regularPolygon(8, 65.3281 , 22.5, new Point2D(safety[1][4].getX() - 60.3553, safety[1][4].getY() + 25));
        safetyZones[2] = regularPolygon(8, 65.3281 , 22.5, new Point2D(safety[2][4].getX() + 25, safety[2][4].getY() - 60.3553));
        safetyZones[3] = regularPolygon(8, 65.3281 , 22.5, new Point2D(safety[3][4].getX() + 60.3553 + 50, safety[3][4].getY() + 25));
        //------------------------HOME DECLARATION---------------------------
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
