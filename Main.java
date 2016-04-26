
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

import java.util.Arrays;

public class Main extends Application {

    Button button;
    Stage window;
    SorryGame newGame = new SorryGame();
    Rectangle[] border = new Rectangle[60];
    Rectangle[][] safety = new Rectangle[4][5];
    Polygon[] safetyZones = new Polygon[4];
    Polygon[] startZones = new Polygon[4];
    Circle[][] plPieces = new Circle[2][4];
    int[] possMoves;
    Circle currSelection;
    int piece;

    boolean canEnterSafety = true;
    boolean possibleMove = true;

    private void updatePlayers(Piece[][] pieces, Rectangle[] border, Rectangle[][] safety, Circle[][] plPieces) {
        for(int i = 0; i < plPieces.length; i++) {
            for(int j = 0; j < plPieces[i].length; j++) {
                if(pieces[i][j].getLocationJ() == 0 && !pieces[i][j].inStart()) {
                    plPieces[i][j].setCenterX(safety[i][pieces[i][j].getLocationI()].getX() + 25);
                    plPieces[i][j].setCenterY(safety[i][pieces[i][j].getLocationJ()].getY() + 25);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("I see demons");
        Group root = new Group();
        BoardFuncs.createBoard(root, border, safety, safetyZones, startZones);
        newGame.startGame();
        newGame.playerCard = null;
        Button drawCard = new Button("Draw Card!");
        drawCard.setOnMouseClicked(e -> {
            if(newGame.playerCard == null) {
                newGame.plDrawCard();
            }
            if(!newGame.hasPosMove(newGame.getBoard().getMoves(newGame.playerCard))) {
                System.out.println("You dont have any moves you dingus");
                newGame.getBoard().nextTurn();
                newGame.compMove();
                updatePlayers(newGame.getBoard().getPlayers(), border, safety, plPieces);
                newGame.playerCard = null;
            }
        });
        root.getChildren().add(drawCard);
        drawCard.setTranslateX(300);
        drawCard.setTranslateY(200);

        for(int i = 0; i < plPieces.length; i++) {
            Point2D currSfZn = BoardFuncs.getOrigin(startZones[i]);
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
                    if(currSelection != null) {
                        currSelection.setId("plOne");
                    }
                    if(((Circle) e.getSource()).getId() == "plOne") {
                        currSelection = ((Circle) e.getSource());
                        possMoves = (newGame.getBoard().getMoves(newGame.playerCard))[BoardFuncs.getCurrPieceIndex(plPieces[0], currSelection)];
                        int[][] testMoves = (newGame.getBoard().getMoves(newGame.playerCard));
                        piece = BoardFuncs.getCurrPieceIndex(plPieces[0], currSelection);
                        for(int i = 0; i < border.length; i++) {
                            for(int z : possMoves) {
                                if(z == i ) {
                                    border[i].setId("PossMove");
                                }
                            }
                        }
                        currSelection.setId("plOneSel");
                    }
                });
                root.getChildren().add(y);
            }
        }

        for(Rectangle x : border) {
            x.setOnMouseClicked(e -> {
                int moveIndex = 0;
                for(int i = 0; i < border.length; i++) {
                    if(border[i].equals((Rectangle) e.getSource())) {
                        moveIndex = i;
                        if(!BoardFuncs.contains(possMoves, i)) {
                            possibleMove = false;
                        } else {
                            possibleMove = true;
                        }
                    }
                }

                if(currSelection != null && possibleMove) {
                    double rX = ((Rectangle) e.getSource()).getX();
                    double rY = ((Rectangle) e.getSource()).getY();
                    currSelection.setCenterY(rY + 25);
                    currSelection.setCenterX(rX + 25);
                    currSelection.setId("plOne");
                    currSelection = null;
                    border[moveIndex].setId("Squares");
                    newGame.playerMove(new int[] {piece, moveIndex});
                    newGame.playerCard = null;
                    updatePlayers(newGame.getBoard().getPlayers(), border, safety, plPieces);
                    newGame.compMove();
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
        scene.getStylesheets().add("SorryBoard.css");
        window.setScene(scene);
        window.show();
    }
}
