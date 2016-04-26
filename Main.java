
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
import javafx.scene.text.TextAlignment;
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
                if(!pieces[i][j].inSafety() && !pieces[i][j].inStart()) {
                    int piecePos = pieces[i][j].getLocationI();
                    plPieces[i][j].setCenterX(border[piecePos].getX() + 25);
                    plPieces[i][j].setCenterY(border[piecePos].getY() + 25);
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
        window.setTitle("Sorry!");
        Group root = new Group();
        BoardFuncs.createBoard(root, border, safety, safetyZones, startZones);
        newGame.startGame();
        newGame.playerCard = null;
        Button drawCard = new Button("Draw Card!");
        Label cardFunc = new Label();
        root.getChildren().add(cardFunc);
        cardFunc.setTextAlignment(TextAlignment.CENTER);
        cardFunc.setTranslateX(280);
        cardFunc.setTranslateY(320);
        root.getChildren().add(drawCard);
        drawCard.setTranslateX(365);
        drawCard.setTranslateY(275);
        /*
        When player attempts to draw card, check if they already have a card out signalling that it is still their turn.
        If it is their turn to draw a card allow them to draw a card and display its function below the button.
        If their is no legal move for them to perform set it to computers turn and update the players piece's locations and set card to null.
         */
        drawCard.setOnMouseClicked(e -> {
            if(newGame.playerCard == null) {
                newGame.plDrawCard();
                cardFunc.setText("Card drawn was a " + newGame.playerCard.getValue() + "\n" + newGame.playerCard.getFunction());
            }
            if(!newGame.hasPosMove(newGame.getBoard().getMoves(newGame.playerCard))) {
                cardFunc.setText(cardFunc.getText() + "\n You have no possible moves, ending turn.");
                newGame.getBoard().nextTurn();
                newGame.compMove();
                updatePlayers(newGame.getBoard().getPlayers(), border, safety, plPieces);
                newGame.playerCard = null;
            }
        });

        /*
        Create each player's pieces and place them in their start zone.
         */
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

        //Sets the action to perform when the player clicks on one of their pieces
        for(Circle[] x : plPieces) {
            for(Circle y : x) {
                y.setOnMouseClicked(e -> {

                    //if their is already a selected piece set that piece's id to plOne as to not have
                    //two pieces with the selection highlighting
                    if(currSelection != null) {
                        currSelection.setId("plOne");
                    }

                    //check if the piece isn't already selected before performing any operation
                    if(((Circle) e.getSource()).getId() == "plOne") {
                        currSelection = ((Circle) e.getSource());

                        //get the piece number and the possible moves from that location
                        //with the given card and store them for later
                        possMoves = (newGame.getBoard().getMoves(newGame.playerCard))[BoardFuncs.getCurrPieceIndex(plPieces[0], currSelection)];
                        piece = BoardFuncs.getCurrPieceIndex(plPieces[0], currSelection);

                        //Highlight the possible movement locations from possMoves
                        for(int i = 0; i < border.length; i++) {
                            for(int z : possMoves) {
                                if(z == i ) {
                                    border[i].setId("PossMove");
                                }
                            }
                        }

                        //highlight the selected circle
                        currSelection.setId("plOneSel");
                    }
                });
                root.getChildren().add(y);
            }
        }

        //Action to be performed when the player clicks on any of the border tiles
        for(Rectangle x : border) {
            x.setOnMouseClicked(e -> {

                //Check if the selected location is one of the possible movement locations
                int moveIndex = 0;
                for(int i = 0; i < border.length; i++) {

                    //If the location isnt a possible move loc set it to fase else set to true.
                    if(border[i].equals((Rectangle) e.getSource())) {
                        moveIndex = i;
                        if(!BoardFuncs.contains(possMoves, i)) {
                            possibleMove = false;
                        } else {
                            possibleMove = true;
                        }
                    }
                }
                //actions to be performed if the move is possible with the currently selected piece
                if(currSelection != null && possibleMove) {
                    //clean up the board and make sure all pieces are unhighlighted
                    currSelection.setId("plOne");
                    currSelection = null;
                    for(Rectangle brdrID : border) {
                        brdrID.setId("Squares");
                    }
                    //Move the player in the game logic, get rid of their card, and update the pieces location on the gui.
                    newGame.playerMove(new int[] {piece, moveIndex});
                    newGame.playerCard = null;
                    updatePlayers(newGame.getBoard().getPlayers(), border, safety, plPieces);
                    //Player turn is over so run a computer move.
                    newGame.compMove();
                    updatePlayers(newGame.getBoard().getPlayers(), border, safety, plPieces);
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
