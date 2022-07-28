import java.util.Random; //Random number generator
import java.util.Arrays; //Copy array
import java.util.Scanner; //Input

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
            for(int j = 2; j < 15; j++) { //and 13 cards per suit
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

class War {
    
    static int playerWins; //Amount of times dealer and player have won
    static int dealerWins;
    static int balance; //Balance for player
    
    War(int startingCash) {
        
        balance = startingCash;
        playerWins = 0; //Wins start at zero
        dealerWins = 0;
    }
    
    void showCards(int[] p, int[] d) { //Show the cards played in the round
    
        int i = p.length - 1;
        int j = d.length - 1;
        
        System.out.println();
        System.out.println("Dealer's card: " + d[j]);
        System.out.println("Your card: " + p[i]);
    }
    
    void whoWon(int[] p, int[] d, int bet) {
        
        int i = p.length - 1;
        int j = d.length - 1;
        
        if(d[j] > p[i]) { //If the dealer has a higher card value
                
            System.out.println("You lost.");
            balance -= bet;
            System.out.println("New balance: " + balance);
            dealerWins++; //Iterate dealer wins
        }
        
        else if(p[i] > d[j]) { //If the player has a higher card value
            
            System.out.println("You win!");
            balance += bet;
            System.out.println("New balance: " + balance);
            playerWins++; //Iterate player wins
        }
        
        else battle(d, p, bet); //If card values are equal, go to war
    }
    
    void battle(int[] d, int[] p, int bet) {
        
        System.out.println("Tie! Surrender? (type 's') or go to War? (type 'w')");
        
        int valid = 0;
        int choice = 0;
        int i = p.length - 3;
        int j = d.length - 3;
        
        do {
            
            if(valid > 0) System.out.println("Type 's' to surrender or 'w' to go to war.");
            
            Scanner scanner = new Scanner(System.in);
            String choiceIn = scanner.nextLine();
            String surrender = "s";
            String war = "w";
            
            if(choiceIn.equals(surrender)) {
                choice = 1;
                valid++;
            }
            
            else if(choiceIn.equals(war)) {
                choice = 2;
                valid++;
            }
            
            else System.out.println("Invalid input, try again.");
            
        } while(valid == 0);
        
        switch(choice) {
            
            case 1:
                
                System.out.println("You surrendered.");
                bet /= 2;
                balance -= bet;
                System.out.println("New balance: " + balance);
                
                break;
                        
            case 2:
                
                bet *= 2;
                System.out.println("You chose war! ");
                System.out.println();
                System.out.println("Dealer's cards: " + d[j] + " X");
                System.out.println("Your cards: " + p[i] + " X");
                
                if(d[j] > p[i]) { //If the dealer has a higher card value
                
                    System.out.println("You lost.");
                    balance -= bet;
                    System.out.println("New balance: " + balance);
                    dealerWins++; //Iterate dealer wins
                }
                
                else if(p[i] > d[j]) { //If the player has a higher card value
            
                    System.out.println("You win!");
                    balance += bet;
                    System.out.println("New balance: " + balance);
                    playerWins++; //Iterate player wins
                }
                
                else {
                    
                    System.out.println("Another tie! You lose. (house advantage)");
                    balance -= bet;
                    System.out.println("New balance: " + balance);
                    dealerWins++; //Iterate dealer wins
                }
                
                break;
                
            default:
                
                System.out.println("ERROR");
        }
    }
}

public class warGame {
    public static void main(String args[]) {
        
        DeckOfCards deck = new DeckOfCards(); //Create reference variable
        War newGame = new War(500);
        int cards[] = deck.create(); //Call method that creates deck of cards
        cards = deck.shuffle(cards); //Call method that shuffles the deck
        
        int player[] = deck.Split(cards, true); //Assign half of the deck to
        int dealer[] = deck.Split(cards, false); //the player and dealer
        
        player[player.length - 1] = 11;
        dealer[dealer.length - 1] = 11;
        
        newGame.showCards(player, dealer);
        newGame.whoWon(player, dealer, 50);
        System.out.println();
        
    }
}