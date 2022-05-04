package se.rmsit.TicTacToe;

import se.rmsit.TicTacToe.managers.TileAndMoveManager;
import se.rmsit.TicTacToe.managers.VictoryManager;

public class GameEngine {
	// Information om vilka drag som gjorts på vilken position i koordinat systemet, där tiles[0][0] är högst upp till vänster
	private char nextPlayer = 'x';
	private boolean running = false;
	private TileAndMoveManager tileMoveManager;
	private VictoryManager victoryManager;

	public GameEngine() {
		this.tileMoveManager = new TileAndMoveManager(this);
		this.victoryManager = new VictoryManager(this.tileMoveManager);
	}

	public boolean validatePlayer(char player) {
		// Endast x och o är giltiga alternativ.
		return player == 'x' || player == 'o';
	}

	// Getters och setters
	public char getNextPlayer() {
		return nextPlayer;
	}

	public void updateNextPlayer() {
		nextPlayer = (nextPlayer == 'x' ? 'o' : 'x');
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public TileAndMoveManager getTileAndMoveManager() {
		return tileMoveManager;
	}

	public VictoryManager getVictoryManager() {
		return victoryManager;
	}
}
