package se.rmsit.TicTacToe.display;

import se.rmsit.TicTacToe.Game;

import javax.swing.*;

public class MainFrame extends JFrame {

	private JPanel panelMain;
	private JButton startGameButton;
	private JLabel labelError;
	private GamePanel gamePanel;

	public MainFrame(String title) {
		super(title);
		this.setContentPane(this.panelMain);
		this.setSize(500, 300);
		// center window
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addListeners();
	}

	private void addListeners() {
		startGameButton.addActionListener(e -> {
			// Anropar startGame från Game-klassen för att även GameEngine ska informeras.
			// Game-klassen anropar sedan startGame() i denna klass.
			Game.startGame();
		});
	}

	public void setErrorMessage(String message) {
		labelError.setText(message);
	}

	public void startGame() {
		startGameButton.setEnabled(false);
		gamePanel.restart();
	}

	public void endGame() {
		startGameButton.setEnabled(true);
		gamePanel.end();
	}
}
