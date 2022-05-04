package se.rmsit.TicTacToe.display;

import javax.swing.*;

public class ResultPanel {
	private JTextArea txtAreaResult;
	private JPanel panelResult;
	// Singleton, resultat ska bara hanteras av en instans.
	private static ResultPanel instance;
	private int xVictories = 0;
	private int oVictories = 0;

	public ResultPanel() {
		instance = this;
	}

	public static void addResult(char player, String message) {
		if(player == 'x')
			instance.xVictories++;
		else if(player == 'o')
			instance.oVictories++;
		instance.txtAreaResult.append("Player " + Character.toUpperCase(player) + " win on " + message + "\n");
		instance.txtAreaResult.append(getScoreMessage() + "\n");
	}

	public static String getScoreMessage() {
		return "Score(X:O) " + getXVictories() + ":" + getOVictories();
	}

	// getters and setters
	public static int getXVictories() {
		return instance.xVictories;
	}

	public static int getOVictories() {
		return instance.oVictories;
	}
}
