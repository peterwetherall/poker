import java.util.Arrays;

class Score {

	public static double scoreHand(String[] hand, String[] table) {
		
		double score = 0;
		int base = 13;
	
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
								for (int k = 0; k < 5; k++) {
									cardValues[j + k] -= 1;
									score += Math.pow(2, base * 10 + (k + j));
								}
							} else {
								for (int k = 0; k < 5; k++) {
									cardValues[j + k] -= 1;
									score += Math.pow(2, base * 9 + (k + j));
								}
							}
						}
						if (straightCount == 4 && j == 0 && suitValues[12] > 0) {
							cardValues[12] -= 1;
							score += Math.pow(2, base * 9);
							for (int k = 0; k < 4; k++) {
								cardValues[k] -= 1;
								score += Math.pow(2, base * 9 + k);
							}
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
				cardValues[i] -= 4;
				score += Math.pow(2, base * 8 + i) * 4;
			}
		}
		
		//Check for three of a kind
		for (int i = 12; i >= 0; i--) {
			if (cardValues[i] == 3) {
				boolean otherFound = false;
				//Check for full house
				for (int j = 12; j >= 0; j--) {
					if (cardValues[j] == 2) {
						otherFound = true;
						cardValues[i] -= 3;
						cardValues[j] -= 2;
						score += Math.pow(2, base * 7 + i) * 3;
						score += Math.pow(2, base * 6 + j) * 2;
					}
				}
				//Check for flush
				for (int j = 0; j < 4; j++) {
					if (cardSuits[j] > 4) {
						otherFound = true;
						for (String card : cards) {
							if (card.substring(card.length() - 1).equals(Card.suitName(j).substring(0, 1))) {
								switch (card.substring(0, 1)) {
									case "A": cardValues[12] -= 1; score += Math.pow(2, base * 5 + 12); break;
									case "K": cardValues[11] -= 1; score += Math.pow(2, base * 5 + 11); break;
									case "Q": cardValues[10] -= 1; score += Math.pow(2, base * 5 + 10); break;
									case "J": cardValues[9] -= 1; score += Math.pow(2, base * 5 + 9); break;
									case "1": cardValues[8] -= 1; score += Math.pow(2, base * 5 + 8); break;
									case "9": cardValues[7] -= 1; score += Math.pow(2, base * 5 + 7); break;
									case "8": cardValues[6] -= 1; score += Math.pow(2, base * 5 + 6); break;
									case "7": cardValues[5] -= 1; score += Math.pow(2, base * 5 + 5); break;
									case "6": cardValues[4] -= 1; score += Math.pow(2, base * 5 + 4); break;
									case "5": cardValues[3] -= 1; score += Math.pow(2, base * 5 + 3); break;
									case "4": cardValues[2] -= 1; score += Math.pow(2, base * 5 + 2); break;
									case "3": cardValues[1] -= 1; score += Math.pow(2, base * 5 + 1); break;
									case "2": cardValues[0] -= 1; score += Math.pow(2, base * 5); break;
								}
							}
						}
					}
				}
				//Check for straight
				int straightCount = 0;
				for (int k = 12; k >= 0; k--) {
					if (cardValues[k] > 0) {
						straightCount++;
						if (straightCount == 5) {
							otherFound = true;
							for (int j = 0; j < 5; j++) {
								cardValues[k + j] -= 1;
								score += Math.pow(2, base * 4 + (j + k));
							}
						}
						if (straightCount == 4 && k == 0 && cardValues[12] > 0) {
							otherFound = true;
							cardValues[12] -= 1;
							score += Math.pow(2, base * 4);
							for (int j = 0; j < 4; j++) {
								cardValues[j] -= 1;
								score += Math.pow(2, base * 4 + j);
							}
						}
					} else {
						straightCount = 0;
					}
				}
				if (otherFound == false) {
					cardValues[i] -= 3;
					score += Math.pow(2, base * 3 + i) * 3;
				}
			}
		}
		
		//Re-check for flush
		for (int i = 0; i < 4; i++) {
			if (cardSuits[i] > 4) {
				for (String card : cards) {
					if (card.substring(card.length() - 1).equals(Card.suitName(i).substring(0, 1))) {
						switch (card.substring(0, 1)) {
							case "A": cardValues[12] -= 1; score += Math.pow(2, base * 5 + 12); break;
							case "K": cardValues[11] -= 1; score += Math.pow(2, base * 5 + 11); break;
							case "Q": cardValues[10] -= 1; score += Math.pow(2, base * 5 + 10); break;
							case "J": cardValues[9] -= 1; score += Math.pow(2, base * 5 + 9); break;
							case "1": cardValues[8] -= 1; score += Math.pow(2, base * 5 + 8); break;
							case "9": cardValues[7] -= 1; score += Math.pow(2, base * 5 + 7); break;
							case "8": cardValues[6] -= 1; score += Math.pow(2, base * 5 + 6); break;
							case "7": cardValues[5] -= 1; score += Math.pow(2, base * 5 + 5); break;
							case "6": cardValues[4] -= 1; score += Math.pow(2, base * 5 + 4); break;
							case "5": cardValues[3] -= 1; score += Math.pow(2, base * 5 + 3); break;
							case "4": cardValues[2] -= 1; score += Math.pow(2, base * 5 + 2); break;
							case "3": cardValues[1] -= 1; score += Math.pow(2, base * 5 + 1); break;
							case "2": cardValues[0] -= 1; score += Math.pow(2, base * 5); break;
						}
					}
				}
			}
		}
		
		//Re-check for straight
		int straightCount = 0;
		for (int i = 12; i >= 0; i--) {
			if (cardValues[i] > 0) {
				straightCount++;
				if (straightCount == 5) {
					for (int j = 0; j < 5; j++) {
						cardValues[i + j] -= 1;
						score += Math.pow(2, base * 4 + (j + i));
					}
				}
				if (straightCount == 4 && i == 0 && cardValues[12] > 0) {
					cardValues[12] -= 1;
					score += Math.pow(2, base * 4);
					for (int j = 0; j < 4; j++) {
						cardValues[j] -= 1;
						score += Math.pow(2, base * 4 + j);
					}
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
						//Two pairs
						cardValues[j] -= 2;
						score += Math.pow(2, base * 2 + j) * 2;
					}
				}
				//One pair
				cardValues[i] -= 2;
				score += Math.pow(2, base * 1 + i) * 2;
			}
		}
		
		//Calculate base "high-card" score
		for (int i = 0; i < 13; i++) {
			score += Math.pow(2, base * 0 + i) * cardValues[i];
		}
		
		return score;
	
	}
	
}