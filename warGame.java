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
}

public class warGame {
    public static void main(String args[]) {
        
        DeckOfCards newGame= new DeckOfCards();
        int cards[] = newGame.create();
        cards = newGame.shuffle(cards);
        for(int i = 0; i < cards.length; i++){
            System.out.println(cards[i]);
        }
    }
}