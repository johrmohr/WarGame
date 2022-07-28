import java.util.Random; //Random number generator
import java.util.Arrays; //Copy array
import java.util.Scanner; //Input

class DeckOfCards { //Method involving the deck of cards used in the game

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

class War extends DeckOfCards {
    
    static int playerWins; //Amount of times dealer and player have won
    static int dealerWins;
    static int balance; //Balance for player
    static int warGames;
    
    War(int startingCash) {
        
        balance = startingCash;
        playerWins = 0; //Wins start at zero
        dealerWins = 0;
        warGames = 0; //Number of wars starts at zero
    }
    
    void showCards(int[] p, int[] d) { //Show the cards played in the round
    
        int i = p.length - 1;
        int j = d.length - 1;
        
        String Pcard = ""; 
        String Dcard = "";
        
        if(p[i] == 14) Pcard = "A"; //Change face cards to show as letters for player
        else if(p[i] == 13) Pcard = "K";
        else if(p[i] == 12) Pcard = "Q";
        else if(p[i] == 11) Pcard = "J";
        else Pcard = String.valueOf(p[i]);
        
        if(d[j] == 14) Dcard = "A"; //Change to face cards for dealer
        else if(d[j] == 13) Dcard = "K";
        else if(d[j] == 12) Dcard = "Q";
        else if(d[j] == 11) Dcard = "J";
        else Dcard = String.valueOf(d[j]);
        
        System.out.println();
        System.out.println("Dealer's card: " + Dcard); //Show dealer's card
        
        if(playerWins == 0 && dealerWins == 0) System.out.println("Press ENTER to reveal your card");
        
        Scanner scanner = new Scanner(System.in); //Take ENTER input to reveal card
        String show = scanner.nextLine();
        
        System.out.println("Your card: " + Pcard); //Show player's card
        System.out.println();
    }
    
    void whoWon(int[] p, int[] d, int bet) { //Determine who won the round and change balance according to wager
        
        int i = p.length - 1;
        int j = d.length - 1;
        
        if(d[j] > p[i]) { //If the dealer has a higher card value
                
            System.out.println("You lost.");
            balance -= bet; //Subtract the bet from balance
            System.out.println("New balance: " + balance); //Show balance
            System.out.println();
            dealerWins++; //Iterate dealer wins
        }
        
        else if(p[i] > d[j]) { //If the player has a higher card value
            
            System.out.println("You win!");
            balance += bet; //Add bet to balance
            System.out.println("New balance: " + balance); //Show balance
            System.out.println();
            playerWins++; //Iterate player wins
        }
        
        else battle(d, p, bet); //If card values are equal, go to war
    }
    
    private void battle(int[] d, int[] p, int bet) {
        
        System.out.println("Tie! Surrender? (type 's') or go to War? (type 'w')"); //Option to go to war or surrender
        
        int valid = 0;
        int choice = 0;
        int i = p.length - 3;
        int j = d.length - 3;
        
        do {
            
            if(valid > 0) System.out.println("Type 's' to surrender or 'w' to go to war.");
            
            Scanner scanner = new Scanner(System.in); //Take user input
            String choiceIn = scanner.nextLine();
            String surrender = "s";
            String war = "w";
            
            if(choiceIn.equals(surrender)) { //Translate input for switch statement
                choice = 1;
                valid++;
            }
            
            else if(choiceIn.equals(war)) {
                choice = 2;
                valid++;
            }
            
            else System.out.println("Invalid input, try again."); //In case of error
            
        } while(valid == 0);
        
        switch(choice) {
            
            case 1: //If the player surrenders
                
                System.out.println("You surrendered.");
                bet /= 2;
                balance -= bet; //Subtract the bet from balance
                System.out.println("New balance: " + balance); //Show new balance
                System.out.println();
                
                break;
                        
            case 2: //If the player chooses war
                
                bet *= 2;
                System.out.println("You chose war! ");
                System.out.println();
                System.out.println("Dealer's cards: " + d[j] + " X");
                System.out.println("Your cards: " + p[i] + " X");
                
                if(d[j] > p[i]) { //If the dealer wins the war
                
                    System.out.println("You lost.");
                    balance -= bet; //Subtract the bet from balance
                    System.out.println("New balance: " + balance); //Show new balance
                    System.out.println();
                    dealerWins++; //Iterate dealer wins
                    warGames++;
                }
                
                else if(p[i] > d[j]) { //If the player wins the war
            
                    System.out.println("You win!");
                    balance += bet; //Add the bet to balance
                    System.out.println("New balance: " + balance); //Show new balance
                    System.out.println();
                    playerWins++; //Iterate player wins
                    warGames++;
                }
                
                else { //If its a tie the dealer wins
                    
                    System.out.println("Another tie! You lose. (house advantage)");
                    balance -= bet; //Subtract the bet from balance
                    System.out.println("New balance: " + balance); //Show new balance
                    System.out.println();
                    dealerWins++; //Iterate dealer wins
                    warGames++;
                }
                
                break;
                
            default: //In case of error
                
                System.out.println("ERROR");
        }
    }
}

class startGame extends War { //startGame inherits War class
    
    startGame() {
        
        super(500); //Start balance at $500
    }
    
    void gameLoop(int[] p, int[] d) {
        
        if(dealerWins == 0 && playerWins == 0) { //Show at start of game
            
            System.out.println("Welcome to Casino War Game in Java!");
            System.out.println("Your current balance is: $" + balance);
        }
        
        System.out.print("Place your bet: $"); //Input the player's wager for the round
        Scanner scan = new Scanner(System.in);
        int bet = scan.nextInt();
        
        int compare = playerWins;
        int ifWar = warGames;
        
        showCards(p, d);
        whoWon(p, d, bet);
        
        if(compare == playerWins) { //If dealer wins, change decks accordingly
            
            if(ifWar == warGames) { //Determine if a war game was played
                
                d = winDeck(d, p[p.length - 1], p[p.length - 2], p[p.length - 3], false);
                p = loseDeck(p, false);
            }
            
            else {
                
                d = winDeck(d, p[p.length - 1], 0, 0, true);
                p = loseDeck(p, true);
            }
        }
        
        else { //If player wins, change decks accordingly
            
            if(ifWar == warGames) { //Determine if a war game was played
                
                p = winDeck(p, d[d.length - 1], d[d.length - 2], d[d.length - 3], false);
                d = loseDeck(d, false);
            }
            
            else {
                
                p = winDeck(p, d[d.length - 1], 0, 0, true);
                d = loseDeck(d, true);
            }
        }
        
        System.out.println("Press 1 to play another round, press 2 to end game: "); //User input to end game or continue
        int input = scan.nextInt();
        System.out.println();
        if(input == 1) gameLoop(p, d); //Recursion, loop game until user inputs to end
    }
    
    int dWins() { return dealerWins; } //Return the amount of times the dealer has won
    
    int pWins() { return playerWins; } //Return the amount of times the player has won
    
    int bal() { return balance; } //Return the final balance
}

public class warGame {
    public static void main(String args[]) {
        
        DeckOfCards deck = new DeckOfCards(); //Create reference variables
        startGame start = new startGame();
        int cards[] = deck.create(); //Call method that creates deck of cards
        cards = deck.shuffle(cards); //Call method that shuffles the deck
        
        int player[] = deck.Split(cards, true); //Assign half of the deck to
        int dealer[] = deck.Split(cards, false); //the player and dealer
        
        start.gameLoop(player, dealer); //Begin the game
        
        System.out.println("Thank for playing!"); //End of game message
        System.out.println("Your wins: " + start.dWins());
        System.out.println("Dealer's wins: " + start.pWins());
        int change = start.bal() - 500; //Determine change in balance
        if(change >= 0) System.out.println("Change in money: +$" + change); //If positive
        else System.out.println("Change in money: " + change); //If negative
    }
}