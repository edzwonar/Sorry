/**
 * This class represents a card from
 * the Sorry deck
 * Possible values: 0 (Sorry),1,2,3,4,5,7,8,10,11,12
 *
 * getValue() returns card's integer value
 * getFunction() returns the rule associated with the card
 * as a string
 *
 * getImg() returns the path to the image file
 *
 * @author Emilie Dzwonar
 */
public class Card{

    private int value;
    private String function;

    public Card(int value){

        this.value = value;
        generateFunction();

    }

    public void generateFunction(){

        switch(value){

            case 0:
                function = "SORRY! Must take one man from your start, place it on any square that is \n occupied by any opponent, and return that opponent's man to its start. \n If there is no man on your start or no opponent's man is on any square your move is forfeited.";

            case 1:
                function = "Must either start a man out \nor move one man forward 1 square.";
                break;

            case 2:
                function = "Must either start a man out \nor move one man forward 2 squares. Draw Again.";
                break;

            case 3:
                function = "Must move one man forward 3 squares.";
                break;

            case 4:
                function = "Must move one man backward 4 squares.";
                break;

            case 5:
                function = "Must move one man forward 5 squares.";
                break;

            case 7:
                function = "Must either move one man forward 7 squares \nor split the move between any two men.";
                break;

            case 8:
                function = "Must move one man forward 8 squares.";
                break;

            case 10:
                function = "Must either move one man forward 10 squares\nor move one man backward 1 square.";
                break;

            case 11:
                function = "Move one man forward 11 squares or any one of your men may change places with any one man of any opponent./n Note: Forfeit move if you do not wish to change places and it is impossible to go forward 11 squares.";
                break;

            case 12:
                function = "Must move one man forward 12 squares.";
                break;

            default:
                function = "Invalid function";
                break;


        }

    }

    public int getValue(){
        return value;
    }

    public String getFunction(){
        return function;
    }

    public String getImg(){
        return "images/"+value+".jpg";
    }
}
