import java.util.Scanner;

/**
 * Created by Alex on 4/4/2016.
 */
public class SorryGame {
    SorryBoard Board;
    Deck Deck;
    Card playerCard;
    int round;
    boolean playerTurn;
    boolean gameWon;

    public void startGame(){
        Board = new SorryBoard();
        Deck = new Deck();
        Deck.shuffle();
        round = 0;
        playerTurn = true;
        gameWon = false;
    }

    public SorryBoard getBoard() {
        return Board;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void compMove() {
        System.out.println("\nComputer turn--");
        round++;
        System.out.println("Round: " + round);
        Card drawnCard = Deck.drawCard();
        System.out.println("Card drawn was a " + drawnCard.getValue());
        int[][] moves = Board.getMoves(drawnCard);
        int[] choice;
        if(hasPosMove(moves)) {
            choice = getCompChoice(moves);
            Board.makeMove(choice);
        }
        gameWon = Board.checkWin();

        if(Deck.numLeft == 0){
            Deck.freshDeck();
        }
        System.out.println("Dicks");
        Board.nextTurn();
        playerTurn = !playerTurn;
    }

    public boolean hasPosMove(int[][] moves) {
        if (!(moves[0][0] == 99 && moves[1][0] == 99 && moves[2][0] == 99 && moves[3][0] == 99)) {
            return true;
        }
        return false;
    }

    public void playerMove(int[] plMove) {
        if (gameWon == false) {

            if (playerTurn) {
                System.out.println("\nHuman turn--");
            }
            round++;
            System.out.println("Round: " + round);

            int[][] moves = Board.getMoves(playerCard);
            int[] choice;
            if (!(moves[0][0] == 99 && moves[1][0] == 99 && moves[2][0] == 99 && moves[3][0] == 99)) {
                choice = plMove;

                Board.makeMove(choice);
            }
            gameWon = Board.checkWin();

            if (Deck.numLeft == 0) {
                Deck.freshDeck();
            }
            Board.nextTurn();
            playerTurn = !playerTurn;
        }
    }

    public void plDrawCard() {
        playerCard = Deck.drawCard();
        System.out.println("Card drawn was a " + playerCard.getValue());
        System.out.println(playerCard.getFunction());
    }

    int[] getHumanChoice(int[][] moves){
        Scanner input = new Scanner(System.in);
        int[] choice = new int[2];
        int pieceRow;
        int destinationCollumn;
        System.out.println("Possible squares are as follows: ");
        for (int i=0;i<4;i++){
            System.out.print("Piece " + (i+1) + " can move to : ");
            for (int j : moves[i]){
                System.out.print(j + ", ");
            }
            System.out.println();
        }
        do {
            System.out.print("Enter piece to move: ");
            pieceRow = input.nextInt();
            System.out.print(pieceRow);
        } while (pieceRow<0 || pieceRow >5);
        do {
            System.out.print("Enter which position your choice takes in the display: ");
            destinationCollumn = input.nextInt();
            System.out.println();
        } while (destinationCollumn<0 || destinationCollumn>8);

        choice[0] = pieceRow-1;
        choice[1] = moves[pieceRow-1][destinationCollumn-1];
        return choice;
    }

    int[] getCompChoice(int[][] moves){
        int[] choice = new int[2];
        int piecetoMove=-1;
        int pieceMax=-1;
        int moveMin = 60;

        for (int i=0;i<4;i++){
            System.out.print("Piece " + (i+1) + " can move to : ");
            for (int j : moves[i]){
                System.out.print(j + ", ");

                //if roll takes pawn to HOME, make the move
                if(j==66||j==61){
                    choice[0] = i;
                    choice[1] = j;
                    return choice;
                }

                //take max
                if(j>0 && pieceMax==-1 || ((j+Board.currentPlayer[0].safeIndex)%60)>pieceMax &&j>0&& pieceMax!=-1){
                    pieceMax=j;
                }

            }
            if((pieceMax!=-1&&piecetoMove==-1||((pieceMax+Board.currentPlayer[0].safeIndex)%60)<moveMin&&piecetoMove!=-1)){
                moveMin = pieceMax;  //moves the piece that is furthest from safety as far as it can go
                piecetoMove = i;
            }
            System.out.println();
        }

        //always enter safezone

        choice[0] = piecetoMove;
        choice[1] = moveMin;
        return choice;
    }

}