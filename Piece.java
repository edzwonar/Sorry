/**
 * Created by Alex on 3/20/2016.
 * @author Emilie on 4/8/2016.
 *
 */
public class Piece {

    boolean human;
    int startIndex;
    int safeIndex;

    int i;
    int j;

    /**
     * Constructor-takes in boolean identifying Piece
     * as belonging to a human player or Computer,
     * and two int values for it's initial location
     *
     */
    public Piece(boolean isHuman, int i, int j){

        // assign isHuman to true if Piece is human player's
        if(isHuman){
            human = true;
            startIndex = 4;
            safeIndex = 2;

            // assign isHuman to false if Piece is computer's
        } else {
            human = false;
            startIndex = 19;
            safeIndex = 17;
        }

        // initialize location of piece
        setLocation(i, j);
    }

    /**
     * Takes in ints i and j and
     * saves the piece's location as
     * these index values
     * @param int i
     * @param int j
     */
    public void setLocation(int i, int j){
        this.i = i;
        this.j = j;
    }

    /**
     * Method returns the border
     * index of the piece that
     * calls it
     */

    public int getLocationI(){
        return i;
    }

    /**
     * Method returns the secondary
     * index of the piece that
     * calls it
     */

    public int getLocationJ(){
        return j;
    }

    /**
     * Method that checks if a piece is in
     * it's safety zone, and returns
     * true/false accordingly
     */

    public boolean inSafety(){

        if(i == safeIndex & j > 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Method that checks if a piece is in
     * it's start zone, and returns
     * true/false accordingly
     */
    public boolean inStart(){
        if(i == startIndex && j > 0){
            return true;
        }
        else{
            return false;
        }
    }
}