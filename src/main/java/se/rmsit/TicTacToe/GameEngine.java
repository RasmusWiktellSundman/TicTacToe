package se.rmsit.TicTacToe;

import se.rmsit.TicTacToe.managers.TileAndMoveManager;
import se.rmsit.TicTacToe.managers.VictoryManager;

public class GameEngine {
	private char nextPlayer = 'x';
	// Spelaren som startade rundan (uppdateras vid ny runda)
	private char playerWhoStarted = 'x';
	private boolean running = false;
	private TileAndMoveManager tileMoveManager;
	private VictoryManager victoryManager;

	public GameEngine() {
		this.tileMoveManager = new TileAndMoveManager(this);
		this.victoryManager = new VictoryManager(this.tileMoveManager);
	}

	public void startGame() {
		updatePlayerWhoStarted();
		nextPlayer = playerWhoStarted;
		setRunning(true);
		getTileAndMoveManager().resetMoves();
	}

	public boolean validatePlayer(char player) {
		// Endast x och o är giltiga alternativ.
		return player == 'x' || player == 'o';
	}

	// Getters och setters
	public char getNextPlayer() {
		return nextPlayer;
	}

	/**
	 * Ändrar vem som är nästa spelare, till den andra spelaren
	 * @see #getOpposingPlayer
	 */
	public void updateNextPlayer() {
		nextPlayer = getOpposingPlayer(nextPlayer);
	}

	/**
	 * Ändrar startspelare till den andra spelaren
	 * @see #getOpposingPlayer
	 */
	public void updatePlayerWhoStarted() {
		playerWhoStarted = getOpposingPlayer(playerWhoStarted);
	}

	/**
	 * Returnerar den andra spelaren, t.ex anges x returneras o
	 * @param player Spelaren du vill utgå ifrån
	 * @return players motståndare
	 */
	public char getOpposingPlayer(char player) {
		return player == 'x' ? 'o' : 'x';
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

	public char getPlayerWhoStarted() {
		return playerWhoStarted;
	}
}
