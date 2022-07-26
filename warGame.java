import java.util.Random; //Random number generator

class DeckOfCards { //Method involving the deck of cards used in the game

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
}

public class warGame {
    public static void main(String args[]) {
        
        DeckOfCards newGame = new DeckOfCards(); //Create reference variable
        int cards[] = newGame.create(); //Call method that creates deck of cards
        cards = newGame.shuffle(cards); //Call method that shuffles the deck
        
        int player[] = newGame.Split(cards, true); //Assign half of the deck to
        int computer[] = newGame.Split(cards, false); //the player and computer
        
        
        for(int i = 0; i < 26; i++){
            //System.out.println(player[i]);
            System.out.println(computer[i]);
        }
        
    }
}


