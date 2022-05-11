package se.rmsit.TicTacToe.managers;

import se.rmsit.TicTacToe.Game;
import se.rmsit.TicTacToe.display.ResultPanel;

public class VictoryManager {
	private final TileAndMoveManager tileMoveManager;

	public VictoryManager(TileAndMoveManager tileAndMoveManager) {
		this.tileMoveManager = tileAndMoveManager;
	}

	/**
	 * Kollar om en vinst har skett. Om en vinst har skett uppdateras {@link ResultPanel} och spelet avslutas
	 * @see #hasVictory(char)
	 */
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
	 * Kollar om spelaren har vunnit och returnerar hur spelaren vann
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
		// Bröt inte ut koden till en egen metod då den blev mindre läsbar och optimiserad än att duplicera.
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
			return "diagonalen vänster top till höger botten";

		// Steg 2, kolla diagonaler från höger top till vänster botten
		tilesOccupiedOfPlayer = 0;
		for (int coordinate = 2; coordinate >= 0; coordinate--) {
			// När x är som mest måste y vara som minst, när y är ett mindre är x ett mer.
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
}
