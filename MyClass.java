class Deck {
    int[] create() {
        int[] cards = new int[52];
        int place = 0;
        for(int i = 0; i < 4; i++) {
            for(int j = 1; j < 14; j++) {
                cards[place] = j;
                place++;
            }
        }
        return cards;
    }
}

public class MyClass {
    public static void main(String args[]) {
        Deck newGame= new Deck();
        int cards[] = newGame.create();
        for(int i = 0; i < cards.length; i++){
            System.out.println(cards[i]);
        }
    }
}