import java.util.Scanner;

/**
 * Created by Alex on 4/4/2016.
 */
public class SorryGame {
    SorryBoard Board;
    Deck Deck;

    public void startGame(){
        Board = new SorryBoard();
        Deck = new Deck();
        Deck.shuffle();
        this.turns();
        //playagain?
    }

    public void turns(){
        boolean humanPlayer = true; //Human player starts
        boolean gameWon = false;
        int round = 0;


        while(gameWon == false) {

            if(humanPlayer){System.out.println("\nHuman turn--");}
            else {System.out.println("\nComputer turn--");}
            round++;
            System.out.println("Round: " + round);

            Card drawnCard = Deck.drawCard();
            System.out.println("Card drawn was a " + drawnCard.getValue());


            int[][] moves = Board.getMoves(drawnCard);
            int[] choice;
            if(!(moves[0][0]==0&&moves[1][0]==0&&moves[2][0]==0&&moves[3][0]==0)) {
                if(humanPlayer) {
                    choice = getCompChoice(moves);
                } else {
                    choice = getCompChoice(moves);
                }
                Board.makeMove(choice);
            }
            gameWon = Board.checkWin();

            if(Deck.numLeft == 0){
                Deck.freshDeck();
            }

            humanPlayer = !humanPlayer;
            Board.nextTurn();
        }
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