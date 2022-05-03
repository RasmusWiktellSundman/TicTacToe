package se.rmsit.TicTacToe;

import se.rmsit.TicTacToe.exceptions.InvalidCoordinatesException;
import se.rmsit.TicTacToe.exceptions.InvalidPlayerTypeException;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;

public class GameEngine {
	// Information om vilka drag som gjorts på vilken position i koordinat systemet, där tiles[0][0] är högst upp till vänster
	private final char[][] tiles = new char[3][3];
	private char nextPlayer = 'x';

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
		tiles[x][y] = player;
	}

	private boolean validCoordinates(int x, int y) {
		if(x < 0 || x > 2)
			return false;
		if(y < 0 || y > 2)
			return false;
		return true;
	}

	private boolean isSquareOccupied(int x, int y) {
		// Kollar om char-arrayen innehåller chars default värde. Om den inte gör det, är den platsen upptagen
		return tiles[x][y] != '\u0000';
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

	public void moveNextPlayer(int x, int y) {
		try {
			addMove(x, y, getNextPlayer());
		} catch (TileOccupiedException e) {
			// Todo: Show error message on screen
		}
		updateNextPlayer();
	}
}
