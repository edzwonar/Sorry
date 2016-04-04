/**
 * Created by Alex on 3/20/2016.
 */
import java.util.ArrayList;

public class SorryBoard {
    Piece[] Human;
    Piece[] Computer;
    Deck Deck;

    Piece[][] squares = new Piece[60][7];

    public SorryBoard(){
        Human = new Piece[4];
        Computer = new Piece[4];

        for (int i=3;i>0;i--) {
            Human[i] = new Piece(true);
            squares[4][i+1] = Human[i];
        }

        for (int i=3;i>0;i--){
            Computer[i] = new Piece(false);
            squares[18][i+1] = Computer[i];
        }
    }
}
