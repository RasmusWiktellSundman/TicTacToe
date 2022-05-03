package se.rmsit.TicTacToe;

import se.rmsit.TicTacToe.display.MainFrame;

public class Main {
	// Antal rutor på en rad
	public static final int TILE_LENGTH = 3;
	private static MainFrame mainFrame;
	private static GameEngine gameEngine;

	public static void main(String[] args) {
		mainFrame = new MainFrame("Tic Tac Toe");
		gameEngine = new GameEngine();
	}

	public static void setErrorMessage(String message) {
		mainFrame.setErrorMessage(message);
	}

	public static void removeErrorMessage() {
		mainFrame.setErrorMessage("");
	}

	// getters and setters
	public static GameEngine getGameEngine() {
		return gameEngine;
	}
}
