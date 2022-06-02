package se.rmsit.TicTacToe.managers;

import se.rmsit.TicTacToe.Game;
import se.rmsit.TicTacToe.GameEngine;
import se.rmsit.TicTacToe.exceptions.InvalidCoordinatesException;
import se.rmsit.TicTacToe.exceptions.InvalidPlayerTypeException;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;

/**
 * Hanterar allt relaterat till rutor och drag. Exempelvis vart spelare befinner sig och nya drag
 */
public class TileAndMoveManager {
	// Information om vilka drag som gjorts på vilken position i koordinat systemet, där tiles[0][0] är högst upp till vänster
	// Structure: char[x][y]
	private char[][] tiles = new char[Game.TILE_LENGTH][Game.TILE_LENGTH];
	// Används för att effektivisera draw. Genom att ha en variabel behöver draw metoden inte loopa igenom hela arrayen vid varje drag.
	private int totalMoves = 0;
	private final GameEngine gameEngine;

	public TileAndMoveManager(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	/**
	 * Lägger till draget för nästa spelare, uppdaterar vem som är nästa spelare och kollar efter vinst.
	 * @param x Rutans x-koordinat (från 0)
	 * @param y Rutans y-koordinat (från 0)
	 * @throws TileOccupiedException
	 * @see #addMove(int, int, char)
	 */
	public void moveAndUpdateNextPlayer(int x, int y) throws TileOccupiedException {
		addMove(x, y, gameEngine.getNextPlayer());
		gameEngine.updateNextPlayer();
	}

	/**
	 * Lägger till ett drag och kallar på {@link VictoryManager#handleVictory()}.
	 * @param x Rutans x-koordinat (från 0)
	 * @param y Rutans y-koordinat (från 0)
	 * @param player Spelaren som gör draget
	 * @throws TileOccupiedException
	 */
	public void addMove(int x, int y, char player) throws TileOccupiedException {
		// Kollar så rätt spelartyp skickas med (x eller o)
		if(!gameEngine.validatePlayer(player)) {
			throw new InvalidPlayerTypeException();
		}
		if(!validCoordinates(x, y)) {
			throw new InvalidCoordinatesException();
		}
		if(isSquareOccupied(x, y)) {
			throw new TileOccupiedException();
		}
		tiles[x][y] = player;
		totalMoves++;

		gameEngine.getVictoryManager().handleVictory();
	}

	public char getPlayerOnTile(int x, int y) {
		return tiles[x][y];
	}

	/**
	 * Kollar om angivna koordinater är giltiga.
	 * Endast positiva koordinater som är mindre än {@link Game#TILE_LENGTH} - 1 är giltiga
	 * @param x Rutans x-koordinat (från 0)
	 * @param y Rutans y-koordinat (från 0)
	 * @return Om koordinaterna är giltiga
	 */
	private boolean validCoordinates(int x, int y) {
		if(x < 0 || x > Game.TILE_LENGTH - 1)
			return false;
		if(y < 0 || y > Game.TILE_LENGTH - 1)
			return false;
		return true;
	}

	private boolean isSquareOccupied(int x, int y) {
		// Kollar om char-arrayen innehåller chars default värde. Om den inte gör det, är den platsen upptagen
		return tiles[x][y] != '\u0000';
	}

	public void resetMoves() {
		tiles = new char[Game.TILE_LENGTH][Game.TILE_LENGTH];
		totalMoves = 0;
	}

	public char[][] getMoves() {
		return tiles;
	}

	public int getTotalMoves() {
		return totalMoves;
	}
}
