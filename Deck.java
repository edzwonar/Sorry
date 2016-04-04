/**
 * This class simulates a standard Sorry deck of 45 cards  
 * including methods for creating a fresh deck, dealing
 * a card, and shuffling
 * 
 * @author Emilie Dzwonar
 * CS 205
 */
 
import java.util.*;

public class Deck 
{
   //initialize constant for number of cards in standard deck
   
   final int NUM_CARDS = 45;

   // initialize array list to hold cards
   private ArrayList<Card> deck;
   
   /**
    * Constructs a regular 52-card deck.  Initially, the cards
    * are in a sorted order.  The shuffle() method can be called to
    * randomize the order.  
    */
   public Deck()
   {
            freshDeck();
   }
   /**
    * Creates a sorted deck of 52 cards
    */
   public void freshDeck()
   {
      deck = new ArrayList<Card>();
   
      for(int ones = 0; ones < 5; ones++){
      deck.add(new Card(1));
      }
   
       for(int others = 0; others < 4; others++){
         deck.add(new Card(0));
         deck.add(new Card(2));
         deck.add(new Card(3));
         deck.add(new Card(4));
         deck.add(new Card(5));
         deck.add(new Card(7));
         deck.add(new Card(8));
         deck.add(new Card(10));
         deck.add(new Card(11));
         deck.add(new Card(12));
      
      }
   }
  
   /** 
      Remove and return the top Card on the Deck
      @return the top card of the deck
    */
   public Card drawCard()
   {
      // remove card
      Card c = deck.remove(0);
      
      // return card
      return c;
   }
   
   
   /** 
     * Randomize the order of Cards in Deck   
     */

   public void shuffle()
   {
      int randNum;
      Card temp;
      Random r = new Random();
      for (int i = 0; i < deck.size(); i++)
      {
         randNum = r.nextInt(deck.size());
         temp = deck.get(i);
         deck.set(i,deck.get(randNum));
         deck.set(randNum,temp);
      }      
   }
   

}

