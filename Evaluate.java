import java.util.Arrays;

class Evaluate {

	public static String describeHand(String[] hand, String[] table) {
		
		//Combine hands into one array
		String[] cards = new String[7];
		int pointer = 0;
		for (String card : hand) {
			cards[pointer] = card;
			pointer++;
		}
		for (String card : table) {
			cards[pointer] = card;
			pointer++;
		}
		
		//Count card values
		int[] cardValues = new int[13];
		Arrays.fill(cardValues, 0);
		for (String card : cards) {
			switch (card.substring(0, 1)) {
				case "A": cardValues[12] += 1; break;
				case "K": cardValues[11] += 1; break;
				case "Q": cardValues[10] += 1; break;
				case "J": cardValues[9] += 1; break;
				case "1": cardValues[8] += 1; break;
				case "9": cardValues[7] += 1; break;
				case "8": cardValues[6] += 1; break;
				case "7": cardValues[5] += 1; break;
				case "6": cardValues[4] += 1; break;
				case "5": cardValues[3] += 1; break;
				case "4": cardValues[2] += 1; break;
				case "3": cardValues[1] += 1; break;
				case "2": cardValues[0] += 1; break;
			}
		}
		
		//Count card suits
		int[] cardSuits = new int[4];
		Arrays.fill(cardSuits, 0);
		for (String card : cards) {
			switch (card.substring(card.length() - 1)) {
				case "H": cardSuits[0] += 1; break;
				case "C": cardSuits[1] += 1; break;
				case "D": cardSuits[2] += 1; break;
				case "S": cardSuits[3] += 1; break;
			}
		}
		
		
		//Check for royal and straight flush
		for (int i = 0; i < 4; i++) {
			if (cardSuits[i] > 4) {
				//Count cards of that suit
				int[] suitValues = new int[13];
				Arrays.fill(suitValues, 0);
				for (String card : cards) {
					if (card.substring(card.length() - 1) == Card.suitName(i).substring(0, 1)) {
						switch (card.substring(0, 1)) {
							case "A": suitValues[12] += 1; break;
							case "K": suitValues[11] += 1; break;
							case "Q": suitValues[10] += 1; break;
							case "J": suitValues[9] += 1; break;
							case "1": suitValues[8] += 1; break;
							case "9": suitValues[7] += 1; break;
							case "8": suitValues[6] += 1; break;
							case "7": suitValues[5] += 1; break;
							case "6": suitValues[4] += 1; break;
							case "5": suitValues[3] += 1; break;
							case "4": suitValues[2] += 1; break;
							case "3": suitValues[1] += 1; break;
							case "2": suitValues[0] += 1; break;
						}
					}
				}
				int straightCount = 0;
				for (int j = 12; j >= 0; j--) {
					if (suitValues[j] > 0) {
						straightCount++;
						if (straightCount == 5) {
							if (Card.cardName(j) == "10") {
								return "Royal flush (" + Card.suitName(i) + ")";
							}
							return "Straight flush (" + Card.suitName(i) + ": " + Card.cardName(j) + " to " + Card.cardName(j + 4) + ")";
						}
						if (straightCount == 4 && j == 0 && suitValues[12] > 0) {
							return "Straight flush (" + Card.suitName(i) + ": " + Card.cardName(12) + " to " + Card.cardName(j + 3) + ")";
						}
					} else {
						straightCount = 0;
					}
				}
			}
		}
		
		//Check for four of a kind
		for (int i = 12; i >= 0; i--) {
			if (cardValues[i] == 4) {
				return "Four of a kind (" + Card.cardName(i) + ")";
			}
		}
		
		//Check for three of a kind
		for (int i = 12; i >= 0; i--) {
			if (cardValues[i] == 3) {
				//Check for full house
				for (int j = 12; j >= 0; j--) {
					if (cardValues[j] == 2) {
						return "Full house (Triple " + Card.cardName(i) + " and double " + Card.cardName(j) + ")";
					}
				}
				//Check for flush
				for (int j = 0; j < 4; j++) {
					if (cardSuits[j] > 4) {
						return "Flush (" + Card.suitName(j) + ")";
					}
				}
				//Check for straight
				int straightCount = 0;
				for (int j = 12; j >= 0; j--) {
					if (cardValues[j] > 0) {
						straightCount++;
						if (straightCount == 5) {
							return "Straight (" + Card.cardName(j) + " to " + Card.cardName(j + 4) + ")";
						}
						if (straightCount == 4 && j == 0 && cardValues[12] > 0) {
							return "Straight (" + Card.cardName(12) + " to " + Card.cardName(j + 3) + ")";
						}
					} else {
						straightCount = 0;
					}
				}
				return "Three of a kind (" + Card.cardName(i) + ")";
			}
		}
		
		//Re-check for flush
		for (int i = 0; i < 4; i++) {
			if (cardSuits[i] > 4) {
				return "Flush (" + Card.suitName(i) + ")";
			}
		}
		//Re-check for straight
		int straightCount = 0;
		for (int j = 12; j >= 0; j--) {
			if (cardValues[j] > 0) {
				straightCount++;
				if (straightCount == 5) {
					return "Straight (" + Card.cardName(j) + " to " + Card.cardName(j + 4) + ")";
				}
				if (straightCount == 4 && j == 0 && cardValues[12] > 0) {
					return "Straight (" + Card.cardName(12) + " to " + Card.cardName(j + 3) + ")";
				}
			} else {
				straightCount = 0;
			}
		}
		
		//Check for one pair
		for (int i = 12; i >= 0; i--) {
			if (cardValues[i] == 2) {
				//Check for two pairs
				for (int j = 12; j >= 0; j--) {
					if (j != i && cardValues[j] == 2) {
						return "Two pairs (" + Card.cardName(i) + " and " + Card.cardName(j) + ")";
					}
				}
				return "One pair (" + Card.cardName(i) + ")";
			}
		}
		
		//Find high card
		for (int i = 12; i >= 0; i--) {
			if (cardValues[i] > 0) {
				return "High card (" + Card.cardName(i) + ")";
			}
		}
		
		return "N/A";
	}

}