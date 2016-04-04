import java.io.IOException;
import java.util.Scanner;

class Menu
{
    public static void main(String[] args) throws IOException
    {
     /* Try statement to catch exception */
        try
        {
         /* Initialize Flag to control loop */
            int stillMenu = 1;
         
         /* Initialize Scanner for input */
            Scanner keyboard = new Scanner(System.in);
         
         /*
         //FOR STATS
         // instantiate file object
         File file = new File("stats.txt");
       		
         // if file doesnt exists, then create it
         if(!file.exists())
       	   file.createNewFile();
       	 		
         // new fileWriter that allows file to be appended with true
          FileWriter fileWriter = new FileWriter(file.getName(),true);
       
         // new bufferedWriter for multiple writes
         BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
       
         // close the file
         bufferWriter.close();
         */
         
         /* Print the main menu system */
         /* Only Prints the menu once */
         /* Returning from game mode should not reprint menu */

            System.out.println("Welcome to Mastermind!");
            System.out.println("Type the following number to decide:");
            System.out.println("1:Player vs Computer");
            System.out.println("2:Statistics");
            System.out.println("3:Help");
            System.out.println("4:Exit");
         
         /* Loop controlling the main menu */
         /* Only accepts int 1-4, any other int is invalid */
         /* Non Int will kill program */

            while(stillMenu == 1)
            {
                int menuChoice = keyboard.nextInt();

                if (menuChoice == 1)
                {
                    String PA = "Y";
                    while(PlayAgain(PA))
                    {

                        //new Board
                        //loop till win
                        //Board.Turn^
                        //StatsDump

                        //Options  -- currently just sends player to menu
                        System.out.println("Play again? Y/N ");
                        PA = keyboard.next();
                    }
                }
                else if (menuChoice == 2)
                {
                /* Stat */
                    //System.out.println("Choice 3");
                    //Stats s = new Stats();
                }
                else if (menuChoice == 3)
                {
                    String s;
                    do {
                        System.out.print("Welcome to Mastermind!\n\n" +
                                "--o  enter this command for an overview of the game\n" +
                                "--p  enter this command for Player Mode instructions\n" +
                                "--s  enter this command for details on scoring\n\n" +
                                "--h  enter this command to return home\n");
                        s = keyboard.next();
                        if (MnHelp(s)!=null){
                            System.out.println();
                            System.out.println(MnHelp(s));
                            System.out.println("Done? Hit '/' and ENTER or '--h' for menu");
                            s = keyboard.next();}

                    } while (MnHelp(s)!=null);
                }
                else if (menuChoice == 4)
                {
                /* Exit */
                    System.out.println("Thanks for Playing!");
                    stillMenu = 0;
                }
             /* Statement for when the input isn't one of the choices */
                else
                {
                 /* Printed when invalid input entered */
                    System.out.println("Enter Valid Number");
                    System.out.println("");
                }
                //System.out.println("Welcome to Mastermind!");
                System.out.println();
                System.out.println("Type the following number to decide:");
                System.out.println("1:Player vs Computer");
                System.out.println("2:Statistics");
                System.out.println("3:Help");
                System.out.println("4:Exit");
            }
        }
     
     /* Catch statement for when a string or non int input is entered */
     /* Kills program */
        catch(java.util.InputMismatchException e)
        {
            System.out.println("dead");
        }
    }

    private static Boolean PlayAgain(String input){
        int i = 1;
        Scanner keyboard = new Scanner("");
        do {
            if (input.equals("Y") || input.equals("y")) {return true;}
            else if (input.equals("N") || input.equals("n")) {return false;}
            else {System.out.println("Invalid Input, please try again.");
                input = keyboard.next();}
        } while (i == 1);
        return false;
    }

    private static String MnHelp(String input) {
        if (input.equals("--o")) {
            return ("This is a software version of the\n" +
                    "code-breaking game called Mastermind from 1970,\n" +
                    "developed by Mordecai Meirowitz\n\n");
        } else if (input.equals("--p")) {
            return ("In Player Mode, the user makes a code\n" +
                    "and the computer attempts to break the code\nwithin 10 tries.\n\n" +
                    "To begin, the player chooses a four-pegged code for\n" +
                    "the computer to crack. There are six different colors\n" +
                    "to choose from, and repeats are allowed. The colors\n" +
                    "are: red, orange, yellow, green, blue, and purple.  The\n" +
                    "player enters the choice of colors one at a time as prompted,\n" +
                    "clicking “OK” after each entry.\n\n" +
                    "After each guess, a window will pop up telling whether the\n" +
                    "computer guessed correctly or not.\n");
        } else if (input.equals("--s")) {
            return ("Hi");
        } else if (input.equals("--h")) {
            return null;
        } else {
            return("Invalid Input, please try again.");
        }
    }
}  