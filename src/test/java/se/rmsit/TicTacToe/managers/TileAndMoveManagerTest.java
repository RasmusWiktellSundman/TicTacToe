package se.rmsit.TicTacToe.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.rmsit.TicTacToe.GameEngine;
import se.rmsit.TicTacToe.exceptions.InvalidCoordinatesException;
import se.rmsit.TicTacToe.exceptions.InvalidPlayerTypeException;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;

import static org.junit.jupiter.api.Assertions.*;

class TileAndMoveManagerTest {
	private TileAndMoveManager manager;

	@BeforeEach
	void setUp() {
		manager = new TileAndMoveManager(new GameEngine());
	}

	@Test
	void canGetEmptyBoardArray() {
		assertArrayEquals(new char[3][3], manager.getMoves());
	}

	@Test
	void canAddMove() throws TileOccupiedException {
		char[][] expectedResult = new char[3][3];
		expectedResult[0][0] = 'x';
		manager.addMove(0, 0, 'x');
		assertArrayEquals(expectedResult, manager.getMoves());
		manager.addMove(1, 2, 'o');
		expectedResult[1][2] = 'o';
		assertArrayEquals(expectedResult, manager.getMoves());
	}

	@Test
	void validateMoveCoordinates() {
		// Kollar så man inte kan ange ogiltiga koordinater
		assertThrows(InvalidCoordinatesException.class, () -> {
			manager.addMove(3, 0, 'x');
		});
		assertThrows(InvalidCoordinatesException.class, () -> {
			manager.addMove(3, 3, 'x');
		});
		assertThrows(InvalidCoordinatesException.class, () -> {
			manager.addMove(-1, 0, 'x');
		});
		assertThrows(InvalidCoordinatesException.class, () -> {
			manager.addMove(0, -1, 'x');
		});
	}

	@Test
	void cantAddInvalidPlayerTypeToTile() {
		// Kollar så man inte kan ange annat än x, o
		assertThrows(InvalidPlayerTypeException.class, () -> {
			manager.addMove(0, 0, 'a');
		});
	}

	@Test
	void cantAddMoveToOccupiedTile() {
		try {
			manager.addMove(0, 0, 'x');
		} catch (TileOccupiedException e) {
			e.printStackTrace();
		}
		// Kollar så man inte kan ange använda samma ruta flera gånger
		assertThrows(TileOccupiedException.class, () -> {
			manager.addMove(0, 0, 'x');
		});
		assertThrows(TileOccupiedException.class, () -> {
			manager.addMove(0, 0, 'o');
		});
	}

	@Test
	void addingMoveToRightPlayerOnMoveNextPlayer() throws TileOccupiedException {
		char[][] expectedResult = new char[3][3];
		expectedResult[0][0] = 'x';
		manager.moveAndUpdateNextPlayer(0, 0);
		assertArrayEquals(expectedResult, manager.getMoves());
		manager.moveAndUpdateNextPlayer(1, 2);
		expectedResult[1][2] = 'o';
		assertArrayEquals(expectedResult, manager.getMoves());
	}

	@Test
	void canGetPlayerOnTile() throws TileOccupiedException {
		manager.addMove(0, 0, 'x');
		assertEquals('x', manager.getPlayerOnTile(0, 0));

		assertEquals('\u0000', manager.getPlayerOnTile(1, 0));

		manager.addMove(2, 2, 'o');
		assertEquals('o', manager.getPlayerOnTile(2, 2));
	}

	@Test
	void canResetMovesAndTotalMoves() throws TileOccupiedException {
		manager.addMove(0, 0, 'x');

		manager.resetMoves();
		assertArrayEquals(new char[3][3], manager.getMoves());
		assertEquals(0, manager.getTotalMoves());
	}
}