package se.rmsit.TicTacToe;

import se.rmsit.TicTacToe.display.MainFrame;

public class Main {
	private static MainFrame mainFrame;
	private static GameEngine gameEngine;

	public static void main(String[] args) {
		mainFrame = new MainFrame("Tic Tac Toe");
		gameEngine = new GameEngine();
	}

	public static void setErrorMessage(String message) {
		mainFrame.setErrorMessage(message);
	}
}
