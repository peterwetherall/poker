import java.util.Arrays;
import java.util.Random;

class Deck {
	
	private static String[] deck = new String[52];
	
	public Deck() {
		reset();
		shuffle(1);
	}
	
	//Return deck as array
	public static String[] getDeck() {
		return deck;
	}
	
	//Configure and setup the deck
	public static void reset() {
		//Setup deck of cards two-dimensional array
		for (int i = 0; i < deck.length; i++) {
			//Determine card value
			int cardValue = (i % 13) + 1;
			switch (cardValue) {
				case 1:
					deck[i] = "Ace".substring(0, 1);
					break;
				case 11:
					deck[i] = "Jack".substring(0, 1);
					break;
				case 12:
					deck[i] = "Queen".substring(0, 1);
					break;
				case 13:
					deck[i] = "King".substring(0, 1);
					break;
				default:
					deck[i] = Integer.toString(cardValue);
			}
			//Determine card suit
			int cardSuit = i / 13;
			switch (cardSuit) {
				case 0:
					deck[i] += "Hearts".substring(0, 1);
					break;
				case 1:
					deck[i] += "Clubs".substring(0, 1);
					break;
				case 2:
					deck[i] += "Diamonds".substring(0, 1);
					break;
				case 3:
					deck[i] += "Spades".substring(0, 1);
					break;
			}
		}
	}
	
	//Remove and return the top card from the deck
	public static String draw() {
		String topCard = deck[0];
		String[] newDeck = new String[deck.length - 1];
		for (int i = 0; i < deck.length - 1; i++) {
			newDeck[i] = deck[i + 1];
		}
		deck = newDeck;
		return topCard;
	}
	
	//Shuffle the deck
	public static void shuffle(int iterations) {
		System.out.println("Shuffling deck ...");
		for (int i = 0; i < deck.length * iterations; i++) {
			Random rand = new Random();
			int r1 = rand.nextInt(deck.length);
			int r2 = rand.nextInt(deck.length);
			String temp = deck[r1];
			deck[r1] = deck[r2];
			deck[r2] = temp;
		}
	}
	
	//Display the deck in the console
	public static void log() {
		System.out.print("Current deck: ");
		for (int i = 0; i < deck.length; i++) {
			System.out.print(deck[i] + " ");
		}
		System.out.print("\n");
	}
}