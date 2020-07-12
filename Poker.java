import java.io.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Arrays;

class Poker extends JFrame implements ActionListener {
	
	JPanel pnl = new JPanel();
	JButton next = new JButton("Next");
	JLabel p1outcome = new JLabel("<html><div style='padding-top: 25px; text-align: left; width: 550px;'></div></html>");
	JLabel p2outcome = new JLabel("<html><div style='padding-top: 25px; text-align: left; width: 550px;'></div></html>");
	//Create new deck of cards
	Deck deck = new Deck();
	//Player 1's cards
	String[] p1Hand = { deck.draw(), deck.draw() };
	//Cards on the table
	JLabel[] tableLabels = new JLabel[5];
	String[] table = { "back", "back", "back", "back", "back" };
	//Player 2's cards
	String[] p2Hand = { deck.draw(), deck.draw() };
	//State
	int state = 0;
	
	public static void main(String[] args) {
		Poker window = new Poker();
		try {
			window.setIconImage(ImageIO.read(new File("img/favicon.png")));
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}
	
	public Poker() {
		//Create and setup GUI window
		super("Poker Project");
		setSize(800, 850);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Display player 1's cards
		pnl.add(new JLabel("<html><div style='padding-top: 25px; text-align: center; width: 10000px;'>Player 1's hand:</div></html>"));
		pnl.add(new JLabel(Card.getCard(p1Hand[0])));
		pnl.add(new JLabel(Card.getCard(p1Hand[1])));
		//Display cards on the table
		pnl.add(new JLabel("<html><div style='padding-top: 25px; text-align: center; width: 10000px;'>On the table:</div></html>"));
		for (int i = 0; i < 5; i++) {
			tableLabels[i] = new JLabel(Card.getCard(table[i]));
			pnl.add(tableLabels[i]);
		}
		//Display player 2's cards
		pnl.add(new JLabel("<html><div style='padding-top: 25px; text-align: center; width: 10000px;'>Player 2's hand:</div></html>"));
		pnl.add(new JLabel(Card.getCard(p2Hand[0])));
		pnl.add(new JLabel(Card.getCard(p2Hand[1])));
		
		//Progression button
		next.addActionListener(this);
		pnl.add(new JLabel("<html><div style='padding-top: 25px; width: 10000px;'></div></html>"));
		pnl.add(next);
		
		pnl.add(p1outcome);
		pnl.add(p2outcome);
		
		//Predict who will win (first time)
		String[] percent = Predict.getPercent(p1Hand, p2Hand, table, deck);
		p1outcome.setText("<html><div style='text-align: left; width: 550px;'>Player 1 prediction: " + percent[0] + "</div></html>");
		p2outcome.setText("<html><div style='text-align: left; width: 550px;'>Player 2 prediction: " + percent[1] + "</div></html>");
		
		add(pnl);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == next) {
			if (state == 0) {
				for (int i = 0; i < 3; i++) {
					table[i] = deck.draw();
					tableLabels[i].setIcon(Card.getCard(table[i]));
				}
				//Predict who will win (second time)
				String[] percent = Predict.getPercent(p1Hand, p2Hand, table, deck);
				p1outcome.setText("<html><div style='text-align: left; width: 550px;'>Player 1 prediction: " + percent[0] + "</div></html>");
				p2outcome.setText("<html><div style='text-align: left; width: 550px;'>Player 2 prediction: " + percent[1] + "</div></html>");
			} else if (state == 1) {
				table[3] = deck.draw();
				tableLabels[3].setIcon(Card.getCard(table[3]));
				//Predict who will win (third time)
				String[] percent = Predict.getPercent(p1Hand, p2Hand, table, deck);
				p1outcome.setText("<html><div style='text-align: left; width: 550px;'>Player 1 prediction: " + percent[0] + "</div></html>");
				p2outcome.setText("<html><div style='text-align: left; width: 550px;'>Player 2 prediction: " + percent[1] + "</div></html>");
			} else if (state == 2) {
				table[4] = deck.draw();
				tableLabels[4].setIcon(Card.getCard(table[4]));
				//Display calculated score
				String p1Desc = Evaluate.describeHand(p1Hand, table);
				String p2Desc = Evaluate.describeHand(p2Hand, table);
				p1outcome.setText("<html><div style='text-align: left; width: 550px;'>Player 1 outcome: " + p1Desc + "</div></html>");
				p2outcome.setText("<html><div style='text-align: left; width: 550px;'>Player 2 outcome: " + p2Desc + "</div></html>");
				//Display winner
				double p1Score = Score.scoreHand(p1Hand, table);
				double p2Score = Score.scoreHand(p2Hand, table);
				String winner = "No one wins!";
				if (p1Score > p2Score) {
					winner = "Player 1 wins.";
				} else if (p1Score < p2Score) {
					winner = "Player 2 wins.";
				}
				pnl.add(new JLabel("<html><div style='text-align: left; width: 550px; color: green;'>" + winner + "</div></html>"));
			}
			state += 1;
		}
	}
	
}