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
		nextPlayer = getOpposingPlayer(playerWhoStarted);
		getTileAndMoveManager().resetMoves();
		updatePlayerWhoStarted();
		setRunning(true);
	}

	/**
	 * Kollar om den angivna spelaren är giltig. Endast x och o är giltiga alternativ
	 * @param player Spelaren att kolla
	 * @return Om spelaren är giltig
	 */
	public boolean validatePlayer(char player) {
		return player == 'x' || player == 'o';
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
	 * @param player Spelaren du vill ha motståndare för
	 * @return players motståndare
	 */
	public char getOpposingPlayer(char player) {
		return player == 'x' ? 'o' : 'x';
	}

	// Getters och setters
	public char getNextPlayer() {
		return nextPlayer;
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
