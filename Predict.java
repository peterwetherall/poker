import java.util.Arrays;

class Predict {
	
	public static String[] getPercent (String[] hand1, String[] hand2, String[] table, Deck deckObj) {
	
		String[] deck = deckObj.getDeck();
		double[] wins = { 0, 0 };
		
		//Pre-flop
		if (deck.length == 48) {
			wins[0] = 1;
			wins[1] = 1;
			/*for (int i = 0; i < deck.length; i++) {
				System.out.print("\n" + i + " | ");
				for (int j = 0; j < deck.length; j++) {
					System.out.print("=");
					for (int k = 0; k < deck.length; k++) {
						for (int l = 0; l < deck.length; l++) {
							for (int m = 0; m < deck.length; m++) {
								if (i != j && i != k && i != l && i != m && j != k && j != l && j != m && k != l && k != m && l != m) {
									String[] tempTable = { deck[i], deck[j], deck[k], deck[l], deck[m] };
									double p1Score = Score.scoreHand(hand1, tempTable);
									double p2Score = Score.scoreHand(hand2, tempTable);
									if (p1Score > p2Score) {
										wins[0] += 1;
									}
									if (p1Score < p2Score) {
										wins[1] += 1;
									}
								}
							}
						}
					}
				}
			}*/
		}
		
		//Pre-river
		if (deck.length == 45) {
			for (int i = 0; i < deck.length; i++) {
				for (int j = 0; j < deck.length; j++) {
					if (i != j) {
						String[] tempTable = { deck[i], deck[j], table[0], table[1], table[2] };
						double p1Score = Score.scoreHand(hand1, tempTable);
						double p2Score = Score.scoreHand(hand2, tempTable);
						if (p1Score > p2Score) {
							wins[0] += 1;
						}
						if (p1Score < p2Score) {
							wins[1] += 1;
						}
					}
				}
			}
		}
		
		//Pre-turn
		if (deck.length == 44) {
			for (int i = 0; i < deck.length; i++) {
				String[] tempTable = { deck[i], table[0], table[1], table[2], table[3] };
				double p1Score = Score.scoreHand(hand1, tempTable);
				double p2Score = Score.scoreHand(hand2, tempTable);
				if (p1Score > p2Score) {
					wins[0] += 1;
				}
				if (p1Score < p2Score) {
					wins[1] += 1;
				}
			}
		}
		
		String[] result = {
			String.format("%.2f", wins[0] * 100 / (wins[0] + wins[1])) + "%",
			String.format("%.2f", wins[1] * 100 / (wins[0] + wins[1])) + "%"
		};
		return result;
		
	}
	
}