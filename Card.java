import java.io.*;
import javax.swing.*;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


class Card {

	//Return a JLabel containing an image of the specified card
	public static ImageIcon getCard(String cardType) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("img/" + cardType + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(100, -1, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		return imageIcon;
	}

	//Convert card number into card name
	public static String cardName(int cardNumber) {
		String result = "";
		switch (cardNumber) {
			case 12:
				result = "Ace";
				break;
			case 11:
				result = "King";
				break;
			case 10:
				result = "Queen";
				break;
			case 9:
				result = "Jack";
				break;
			case 8:
				result = "10";
				break;
			case 7:
				result = "9";
				break;
			case 6:
				result = "8";
				break;
			case 5:
				result = "7";
				break;
			case 4:
				result = "6";
				break;
			case 3:
				result = "5";
				break;
			case 2:
				result = "4";
				break;
			case 1:
				result = "3";
				break;
			case 0:
				result = "2";
				break;
		}
		return result;
	}
	
	//Convert suit number into suit name
	public static String suitName(int suitNumber) {
		String result = "";
		switch (suitNumber) {
			case 0:
				result = "Hearts";
				break;
			case 1:
				result = "Clubs";
				break;
			case 2:
				result = "Diamonds";
				break;
			case 3:
				result = "Spades";
				break;
		}
		return result;
	}
}