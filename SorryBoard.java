/**
 * Created by Alex on 3/20/2016.
 */
public class SorryBoard {
    Piece[] Human;
    Piece[] Computer;

    Piece[] currentPlayer;
    Piece[] currentOpponent;

    public SorryBoard(){
        Human = new Piece[4];
        Computer = new Piece[4];

        for (int i=4;i>0;i--) {
            Human[i-1]= new Piece(true,4,i);
            Computer[i-1] = new Piece(false,19,i);
        }

        currentPlayer = Human;
        currentOpponent = Computer;

    }

    public void nextTurn(){
        Piece[] tempArr = currentPlayer;
        currentPlayer = currentOpponent;
        currentOpponent = tempArr;
    }

    public boolean checkWin(){
        boolean gameWon = true;
        for (Piece i: currentPlayer){
            if (!(i.getLocationI()==i.safeIndex && i.getLocationJ()==6)){
                gameWon = false;
            }
        }
        return gameWon;
    }

    public void makeMove(int[] choice){
        int piece = choice[0];
        int square = choice[1];

        if (0<square && square<60){
            currentPlayer[piece].setLocation(square,0);
        }else if(square>=60){
            //60 is indicator that piece goes to safezone
            currentPlayer[piece].setLocation(currentPlayer[piece].safeIndex,square-60);
        }

        //on swap, return piece to start
        for (int i=0;i<4;i++){
            if(currentPlayer[piece].getLocationI() == currentOpponent[i].getLocationI()&&currentOpponent[i].getLocationJ()==0){
                currentOpponent[i].setLocation(currentOpponent[0].startIndex,i+1);
            }
        }

        //check if piece is on slide triangle
        if (currentPlayer[piece].getLocationI() == currentOpponent[0].startIndex+5){
            //if any piece is on slide 1, bump it to start
            for(int i=0;i<4;i++){
                if(currentPlayer[i].getLocationI()>currentOpponent[0].startIndex+5 && currentPlayer[i].getLocationI()<currentOpponent[0].startIndex+10){
                    currentPlayer[i].setLocation(currentPlayer[0].startIndex,i+1);
                }
                if(currentOpponent[i].getLocationI()>currentOpponent[0].startIndex+5 && currentOpponent[i].getLocationI()<currentOpponent[0].startIndex+10){
                    currentOpponent[i].setLocation(currentOpponent[0].startIndex,i+1);
                }
            }
            currentPlayer[piece].setLocation(currentOpponent[0].startIndex+10,0);
        } else if (currentPlayer[piece].getLocationI() == currentOpponent[0].safeIndex-1){ //slide2 check
            for(int i=0;i<4;i++){
                if(currentPlayer[i].getLocationI()>currentOpponent[0].safeIndex-1 && currentPlayer[i].getLocationI()<currentOpponent[0].safeIndex+2){
                    currentPlayer[i].setLocation(currentPlayer[0].startIndex,i+1);
                }
                if(currentOpponent[i].getLocationI()>currentOpponent[0].safeIndex-1 && currentOpponent[i].getLocationI()<currentOpponent[0].safeIndex+2){
                    currentOpponent[i].setLocation(currentOpponent[0].startIndex,i+1);
                }
            }
            currentPlayer[piece].setLocation(currentOpponent[0].startIndex+2,0);
        }
    }


    public int[][] getMoves(Card currCard){
        int move;
        int[][] possMoves = new int[4][7];
        int value = currCard.getValue();

        System.out.println("Player 1 Pieces: " + Human[0].getLocationI() + ", " + Human[1].getLocationI() + ", " + Human[2].getLocationI() + ", " + Human[3].getLocationI());
        System.out.println("Player 2 Pieces: " + Computer[0].getLocationI() + ", " + Computer[1].getLocationI() + ", " + Computer[2].getLocationI() + ", " + Computer[3].getLocationI());
        System.out.println();

        if (value == 3 || value == 5 || value == 8 || value == 12){ //MOVE FORWARD THE VALUE
            for (int i=0;i<4;i++){
                int j = 0;
                if (!currentPlayer[i].inStart()) {
                    move = adjustMove(currentPlayer[i], value);
                    if (checkMove(move)) {
                        possMoves[i][j] = move;
                        j++;
                    }
                }

            }
        } else if (value == 1 || value == 2){                     //START or MOVE FORWARD VALUE
            for (int i=0;i<4;i++){
                int j = 0;
                if (currentPlayer[i].inStart()) {
                    move = currentPlayer[i].startIndex;
                    if (checkMove(move)) {
                        possMoves[i][j] = move;
                        j++;
                    }
                } else {
                    possMoves[i][j] = adjustMove(currentPlayer[i], value);
                    j++;
                }

            }
        } else if (value == 0){                                  //SWAP START PIECE WITH OPPONENT ONBOARD PIECE
            for (int i=0;i<4;i++){
                int j =0;
                if (currentPlayer[i].inStart()) {
                    for (Piece k : currentOpponent) {
                        if (!(k.inStart()||k.inSafety())) {//*2
                            move = k.getLocationI();
                            if (checkMove(move)) {
                                possMoves[i][j] = move;
                                j++;
                            }
                        }
                    }
                }

            }
        } else if (value == 4){                                  //MOVE BACK 4
            for (int i=0;i<4;i++){
                int j =0;
                if (!(currentPlayer[i].inStart()||currentPlayer[i].inSafety())) {
                    move = adjustMove(currentPlayer[i], -4);
                    if (checkMove(move)) {
                        possMoves[i][j] = move;
                        j++;
                    }
                }

            }
        } else if (value == 10){                                 //MOVE BACK 1 or MOVE FORWARD 10
            for (int i=0;i<4;i++){
                int j=0;
                if (!currentPlayer[i].inStart()) {
                    move = adjustMove(currentPlayer[i], -1);
                    if (checkMove(move)) {
                        possMoves[i][j] = move;
                        j++;
                    }
                    move = adjustMove(currentPlayer[i], 10);
                    if (checkMove(move)) {
                        possMoves[i][j] = move;
                        j++;
                    }
                }

            }
        } else if (value == 11){                                 //SWAP ONBOARD PIECE WITH OPPONENTS ONBOARD PIECE or MOVE FORWARD 11
            for (int i=0;i<4;i++){
                int j = 0;
                if (currentPlayer[i].inStart()) {    //*2
                    for (Piece k : currentOpponent) {
                        if (!(k.inStart()||k.inSafety())) {//*2
                            possMoves[i][j] = k.getLocationI();
                            j++;
                        }
                    }
                } else if (!(currentPlayer[i].inStart()||currentPlayer[i].inSafety())) {
                    move = adjustMove(currentPlayer[i], 11);
                    if (checkMove(move)) {
                        possMoves[i][j] = move;
                        j++;
                    }
                }

            }
        } else if (value == 7){                                  //MOVE TWO PIECES ANY SPLIT OF 7
            for (int i=0;i<4;i++){
                int j =0;
                if (!currentPlayer[i].inStart()) {
                    for (int k = 7; k > 0; k--) {  //check every split of the 7
                        move = adjustMove(currentPlayer[i], k);
                        if (checkMove(move)) {
                            possMoves[i][j] = move;
                            j++;
                        }
                    }
                }

            }
        }
        return possMoves;

    }

    //checks for own pawns on possible move
    public boolean checkMove(int possLand){
        if(possLand==60){
            return true;
        }
        //checks if own piece is already on possible land
        for (int i=0;i<4;i++){
            if(currentPlayer[i].getLocationI()==possLand&&currentPlayer[i].getLocationJ()==0){
                return false;
            }
        }
        return true;
    }

    int adjustMove(Piece pawn, int distance){  //** mod move by 60, send unique output for safezone
        int start = pawn.getLocationI();
        int destination = (((start+distance % 60) + 60) % 60);   //has to be funny because java modulus returns a remainder, wouldnt work for negatives on 4&10

        for (int i = distance;i>0;i--){
            if ((start+i) %60==pawn.safeIndex && (distance-i)<=6 && (distance-i)>=0){
                return distance-i + 60; //Board should only have 0-59 as possible spots, if makeMove gets 60, it knows to turn to safezone, any value over is the jindex
            }
        }

        //when start in safety or on turn, see if move is possible
        if(pawn.inSafety()||start==pawn.safeIndex){
            if(pawn.getLocationJ() + distance <= 6 && pawn.getLocationJ() + distance>0) {
                destination = pawn.getLocationJ() + distance + 60;
            } else {
                destination = 0;
            }
        }

        return destination;
    }
}
