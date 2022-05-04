package se.rmsit.TicTacToe;

import se.rmsit.TicTacToe.display.ResultPanel;
import se.rmsit.TicTacToe.managers.TileAndMoveManager;

public class GameEngine {
	// Information om vilka drag som gjorts på vilken position i koordinat systemet, där tiles[0][0] är högst upp till vänster
	private char nextPlayer = 'x';
	private boolean running = false;
	private TileAndMoveManager tileMoveManager;

	public GameEngine() {
		this.tileMoveManager = new TileAndMoveManager(this);
	}

	public void handleVictory() {
		String victoryMessage;
		// Kolla om x har vunnit
		victoryMessage = hasVictory('x');
		if(victoryMessage != null) {
			ResultPanel.addResult('x', victoryMessage);
			Game.endGame();
			return;
		}

		// Kolla om o har vunnit
		victoryMessage = hasVictory('o');
		if(victoryMessage != null) {
			ResultPanel.addResult('o', victoryMessage);
			Game.endGame();
			return;
		}

		if(isDraw()) {
			ResultPanel.addDraw();
			Game.endGame();
		}
	}

	/**
	 * Kollar om spelaren har vunnit och returnerar hur
	 * @param  player Spelaren att kolla vinst för
	 * @return Meddelande över hur vinsten skedde, null om ingen har vunnit
	 */
	public String hasVictory(char player) {
		// kolla rader
		// Loopar igenom varje rad
		for (int y = 0; y < Game.TILE_LENGTH; y++) {
			int tilesOccupiedOfPlayer = 0;
			// Loopar igenom alla rutor längst en rad och kollar så alla ägs av spelaren
			for (int x = 0; x < Game.TILE_LENGTH; x++) {
				if(tileMoveManager.getPlayerOnTile(x, y) == player)
					tilesOccupiedOfPlayer++;
			}
			if(tilesOccupiedOfPlayer == Game.TILE_LENGTH)
				return "rad " + (y+1);
		}

		// kolla kolumner
		// Samma fast loopar igenom kolumner
		for (int x = 0; x < Game.TILE_LENGTH; x++) {
			int tilesOccupiedOfPlayer = 0;
			for (int y = 0; y < Game.TILE_LENGTH; y++) {
				if(tileMoveManager.getPlayerOnTile(x, y) == player)
					tilesOccupiedOfPlayer++;
			}
			if(tilesOccupiedOfPlayer == Game.TILE_LENGTH)
				return "kolumn " + (x+1);
		}

		// kolla diagonaler
		// Steg 1, kolla diagonaler från vänster top till häger botten
		int tilesOccupiedOfPlayer = 0;
		for (int coordinate = 0; coordinate < Game.TILE_LENGTH; coordinate++) {
			if(tileMoveManager.getPlayerOnTile(coordinate, coordinate) == player)
				tilesOccupiedOfPlayer++;
		}
		if(tilesOccupiedOfPlayer == Game.TILE_LENGTH)
			return "diagonalen vänster top till häger botten";

		// Steg 2, kolla diagonaler från höger top till vänster botten
		tilesOccupiedOfPlayer = 0;
		for (int coordinate = 2; coordinate >= 0; coordinate--) {
			// När y är som mest måste y vara som minst, när y är ett mindre är x ett mer.
			// Tar minus 1 då Game.TILE_LENGTH är ett antal (där 0 inte representerar första)
			if(tileMoveManager.getPlayerOnTile((Game.TILE_LENGTH-1)-coordinate, coordinate) == player)
				tilesOccupiedOfPlayer++;
		}
		if(tilesOccupiedOfPlayer == Game.TILE_LENGTH)
			return "diagonalen höger top till vänster botten";

		return null;
	}

	public boolean isDraw() {
		return tileMoveManager.getTotalMoves() == Game.TILE_LENGTH * Game.TILE_LENGTH;
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
}
