package se.rmsit.TicTacToe;

import se.rmsit.TicTacToe.display.ResultPanel;
import se.rmsit.TicTacToe.exceptions.InvalidCoordinatesException;
import se.rmsit.TicTacToe.exceptions.InvalidPlayerTypeException;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;

public class GameEngine {
	// Information om vilka drag som gjorts på vilken position i koordinat systemet, där tiles[0][0] är högst upp till vänster
	private final char[][] tiles = new char[3][3];
	private char nextPlayer = 'x';
	// Används för att effektivisera draw. Genom att ha en variabel behöver draw metoden inte loopa igenom hela arrayen vid varje drag.
	private int totalMoves = 0;

	public void addMove(int x, int y, char player) throws TileOccupiedException {
		// Kollar så rätt spelartyp skickas med (x eller o)
		if(!validatePlayer(player)) {
			throw new InvalidPlayerTypeException();
		}
		if(!validCoordinates(x, y)) {
			throw new InvalidCoordinatesException();
		}
		if(isSquareOccupied(x, y)) {
			throw new TileOccupiedException();
		}
		tiles[y][x] = player;
		totalMoves++;

		handleVictory();
	}

	public char getPlayerOnTile(int x, int y) {
		return tiles[y][x];
	}

	private void handleVictory() {
		String victoryMessage;
		// Kolla om x har vunnit
		victoryMessage = hasVictory('x');
		if(victoryMessage != null) {
			ResultPanel.addResult('x', victoryMessage);
			return;
		}

		// Kolla om o har vunnit
		victoryMessage = hasVictory('o');
		if(victoryMessage != null) {
			ResultPanel.addResult('o', victoryMessage);
			return;
		}

		if(isDraw()) {
			ResultPanel.addDraw();
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
				if(getPlayerOnTile(x, y) == player)
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
				if(getPlayerOnTile(x, y) == player)
					tilesOccupiedOfPlayer++;
			}
			if(tilesOccupiedOfPlayer == Game.TILE_LENGTH)
				return "kolumn " + (x+1);
		}

		// kolla diagonaler
		// Steg 1, kolla diagonaler från vänster top till häger botten
		int tilesOccupiedOfPlayer = 0;
		for (int coordinate = 0; coordinate < Game.TILE_LENGTH; coordinate++) {
			if(getPlayerOnTile(coordinate, coordinate) == player)
				tilesOccupiedOfPlayer++;
		}
		if(tilesOccupiedOfPlayer == Game.TILE_LENGTH)
			return "diagonalen vänster top till häger botten";

		// Steg 2, kolla diagonaler från höger top till vänster botten
		tilesOccupiedOfPlayer = 0;
		for (int coordinate = 2; coordinate >= 0; coordinate--) {
			// När y är som mest måste y vara som minst, när y är ett mindre är x ett mer.
			// Tar minus 1 då Game.TILE_LENGTH är ett antal (där 0 inte representerar första)
			if(getPlayerOnTile((Game.TILE_LENGTH-1)-coordinate, coordinate) == player)
				tilesOccupiedOfPlayer++;
		}
		if(tilesOccupiedOfPlayer == Game.TILE_LENGTH)
			return "diagonalen höger top till vänster botten";

		return null;
	}

	public boolean isDraw() {
		return totalMoves == Game.TILE_LENGTH * Game.TILE_LENGTH;
	}

	private boolean validCoordinates(int x, int y) {
		if(x < 0 || x > Game.TILE_LENGTH - 1)
			return false;
		if(y < 0 || y > Game.TILE_LENGTH - 1)
			return false;
		return true;
	}

	private boolean isSquareOccupied(int x, int y) {
		// Kollar om char-arrayen innehåller chars default värde. Om den inte gör det, är den platsen upptagen
		return tiles[y][x] != '\u0000';
	}

	private boolean validatePlayer(char player) {
		// Endast x och o är giltiga alternativ.
		return player == 'x' || player == 'o';
	}

	// Getters och setters
	public char[][] getMoves() {
		return tiles;
	}

	public char getNextPlayer() {
		return nextPlayer;
	}

	public void updateNextPlayer() {
		nextPlayer = (nextPlayer == 'x' ? 'o' : 'x');
	}

	public void moveNextPlayer(int x, int y) throws TileOccupiedException {
			addMove(x, y, getNextPlayer());
			updateNextPlayer();
	}
}
