package se.rmsit.TicTacToe.managers;

import se.rmsit.TicTacToe.Game;
import se.rmsit.TicTacToe.GameEngine;
import se.rmsit.TicTacToe.exceptions.InvalidCoordinatesException;
import se.rmsit.TicTacToe.exceptions.InvalidPlayerTypeException;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;

/**
 * Hanterar allt relaterat till rutor. Till exempel vart spelare befinner sig och nya drag
 */
public class TileAndMoveManager {
	private char[][] tiles = new char[3][3];
	// Används för att effektivisera draw. Genom att ha en variabel behöver draw metoden inte loopa igenom hela arrayen vid varje drag.
	private int totalMoves = 0;
	private GameEngine gameEngine;

	public TileAndMoveManager(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public void moveAndUpdateNextPlayer(int x, int y) throws TileOccupiedException {
		addMove(x, y, gameEngine.getNextPlayer());
		gameEngine.updateNextPlayer();
	}

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
		tiles[y][x] = player;
		totalMoves++;

		gameEngine.handleVictory();
	}

	public char getPlayerOnTile(int x, int y) {
		return tiles[y][x];
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
