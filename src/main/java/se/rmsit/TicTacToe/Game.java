package se.rmsit.TicTacToe;

import se.rmsit.TicTacToe.display.MainFrame;

public class Game {
	/** Antal rutor på en rad */
	public static final int TILE_LENGTH = 3;
	private static MainFrame mainFrame;
	private static GameEngine gameEngine;

	public static void main(String[] args) {
		gameEngine = new GameEngine();
		mainFrame = new MainFrame("Tic Tac Toe");
	}

	public static void startGame() {
		gameEngine.startGame();
		mainFrame.startGame();
	}

	public static void endGame() {
		gameEngine.setRunning(false);
		mainFrame.endGame();
	}

	// getters and setters
	public static GameEngine getGameEngine() {
		return gameEngine;
	}

	public static MainFrame getMainFrame() {
		return mainFrame;
	}
}
