import java.util.Random; //Random number generator
import java.util.Arrays; //Copy array

class DeckOfCards { //Method involving the deck of cards used in the game

    int kept1, kept2, kept3; //Cards user keeps if they win a round
    
    DeckOfCards() {
        
        kept1 = 0; //Initialize values
        kept2 = 0;
        kept3 = 0;
    }

    int[] create() { //Returns an array of 52 cards, 4 suits
    
        int[] cards = new int[52];
        int place = 0;
        
        for(int i = 0; i < 4; i++) { //Iterate through the 4 suits
            for(int j = 1; j < 14; j++) { //and 13 cards per suit
                cards[place] = j;
                place++;
            }
        }
        return cards;
    }
    
    int[] shuffle(int[] deck) { //Shuffles the deck
        
        Random random = new Random();
        int shufs = deck.length * 4; //Number of times deck is shuffled
        
        for(int i = 0; i < shufs; i++){ //Find two random positions
            int n = random.nextInt(52); //and switch values
            int s = random.nextInt(52);
            int temp = deck[n];
            deck[n] = deck[s];
            deck[s] = temp;
        }
        return deck;
    }
    
    int[] Split(int[] mixDeck, boolean next) { //Returns half of deck for player and computer
    
        if(next) { //If true, assign the first half of the deck
            
            int[] firstHalf = new int[26]; //Move first half of deck to one array
            
            for(int i = 0; i < 26; i++) {
                firstHalf[i] = mixDeck[i];
            }
            return firstHalf;
        }
        
        else { //If not true, assign second half of the deck
            
            int[] secondHalf = new int[26]; //Move other half of deck to another array
            
            for(int i = 26; i < 52; i++) {
                secondHalf[i-26] = mixDeck[i];
            }
            return secondHalf;
        }
    }
    
    int[] winDeck(int[] deck, int c1, int c2, int c3, boolean war) { //Adds card to deck if user wins
    
        if(war) { //Add 3 cards if user wins war
        
            int newDeck[] = new int[deck.length + 3];
            newDeck[0] = deck[deck.length - 1]; //Send the cards kept to the back of the deck rotation
            newDeck[1] = deck[deck.length - 2];
            newDeck[2] = deck[deck.length - 3];
            deck = loseDeck(deck, true);
            newDeck[3] = c1; //Send the cards won to the back of the deck rotation
            newDeck[4] = c2;
            newDeck[5] = c3;
            
            for(int i = 0; i < deck.length; i++) {
                newDeck[i + 6] = deck[i];
            }
            return newDeck;
        }
        
        else { //Add 1 card for normal round
        
            int newDeck[] = new int[deck.length + 1];
            newDeck[0] = deck[deck.length - 1]; //Send the cards kept to the back of the deck rotation
            deck = loseDeck(deck, false);
            newDeck[1] = c1; //Send the cards won to the back of the deck rotation
            
            for(int i = 0; i < deck.length; i++) {
                newDeck[i + 2] = deck[i];
            }
            return newDeck;
        }
    }
    
    int[] loseDeck(int[] deck, boolean war) { //Subtracts card from deck if user loses
    
        if(war) { //Subtract 3 cards if user loses war
        
            int[] newDeck = Arrays.copyOf(deck, deck.length - 3);
            return newDeck;
        }
        
        else { //Subtract 1 card for normal round
        
            int[] newDeck = Arrays.copyOf(deck, deck.length - 1);
            return newDeck;
        }
    }
}

class Wager { //Method involving bets made in the game
    
    int startingCash;
    int balance;
    
    Wager() { //Constructor
        
        startingCash = 500; //Player begins with $500
        balance = startingCash;
    }
    
    int showBalance() { //Returns the players current balance
        
        return balance;
    }
    
    void lose(int bet) { //Subtract bet if player loses
        
        balance -= bet;
    }
    
    void win(int bet) { //Add bet if players wins
        
        balance += bet;
    }
    
    void surrender(int bet) { //Subtract half the bet if player surrenders war
        
        bet /= 2;
        balance -= bet;
    }
}

public class warGame {
    public static void main(String args[]) {
        
        DeckOfCards newGame = new DeckOfCards(); //Create reference variable
        Wager wager = new Wager();
        int cards[] = newGame.create(); //Call method that creates deck of cards
        cards = newGame.shuffle(cards); //Call method that shuffles the deck
        
        int player[] = newGame.Split(cards, true); //Assign half of the deck to
        int dealer[] = newGame.Split(cards, false); //the player and dealer
        
        System.out.println("balance: " + wager.showBalance());
        wager.surrender(50);
        System.out.println("balance now: " + wager.showBalance());
        
    }
}