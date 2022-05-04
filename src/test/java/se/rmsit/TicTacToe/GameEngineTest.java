package se.rmsit.TicTacToe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.rmsit.TicTacToe.display.ResultPanel;
import se.rmsit.TicTacToe.exceptions.InvalidCoordinatesException;
import se.rmsit.TicTacToe.exceptions.InvalidPlayerTypeException;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {
	private GameEngine engine;

	@BeforeEach
	void setUp() {
		engine = new GameEngine();
	}

	@Test
	void canGetEmptyBoardArray() {
		assertArrayEquals(new char[3][3], engine.getMoves());
	}

	@Test
	void canAddMove() throws TileOccupiedException {
		char[][] expectedResult = new char[3][3];
		expectedResult[0][0] = 'x';
		engine.addMove(0, 0, 'x');
		assertArrayEquals(expectedResult, engine.getMoves());
		engine.addMove(1, 2, 'o');
		expectedResult[2][1] = 'o';
		assertArrayEquals(expectedResult, engine.getMoves());
	}

	@Test
	void validateMoveCoordinates() {
		// Kollar så man inte kan ange ogiltiga koordinater
		assertThrows(InvalidCoordinatesException.class, () -> {
			engine.addMove(3, 0, 'x');
		});
		assertThrows(InvalidCoordinatesException.class, () -> {
			engine.addMove(3, 3, 'x');
		});
		assertThrows(InvalidCoordinatesException.class, () -> {
			engine.addMove(-1, 0, 'x');
		});
		assertThrows(InvalidCoordinatesException.class, () -> {
			engine.addMove(0, -1, 'x');
		});
	}

	@Test
	void validatePlayerMoveType() {
		// Kollar så man inte kan ange annat än x, o
		assertThrows(InvalidPlayerTypeException.class, () -> {
			engine.addMove(0, 0, 'a');
		});
	}

	@Test
	void cantAddMoveToOccupiedTile() {
		try {
			engine.addMove(0, 0, 'x');
		} catch (TileOccupiedException e) {
			e.printStackTrace();
		}
		// Kollar så man inte kan ange använda samma ruta flera gånger
		assertThrows(TileOccupiedException.class, () -> {
			engine.addMove(0, 0, 'x');
		});
		assertThrows(TileOccupiedException.class, () -> {
			engine.addMove(0, 0, 'o');
		});
	}

	@Test
	void canGetNextPlayer() {
		assertEquals(engine.getNextPlayer(), 'x');
	}

	@Test
	void canUpdateNextPlayer() {
		assertEquals(engine.getNextPlayer(), 'x');
		engine.updateNextPlayer();
		assertEquals(engine.getNextPlayer(), 'o');
	}

	@Test
	void updatingNextPlayerOnNextPlayerMoveAndAddingMove() throws TileOccupiedException {
		char[][] expectedResult = new char[3][3];
		expectedResult[0][0] = 'x';
		engine.moveNextPlayer(0, 0);
		assertArrayEquals(expectedResult, engine.getMoves());
		engine.moveNextPlayer(1, 2);
		expectedResult[2][1] = 'o';
		assertArrayEquals(expectedResult, engine.getMoves());
	}

	@Test
	void canGetPlayerOnTile() throws TileOccupiedException {
		engine.addMove(0, 0, 'x');
		assertEquals('x', engine.getPlayerOnTile(0, 0));

		assertEquals('\u0000', engine.getPlayerOnTile(1, 0));

		engine.addMove(2, 2, 'o');
		assertEquals('o', engine.getPlayerOnTile(2, 2));
	}

	@Test
	void checkIf3InARowIsCalculatedCorrectly() throws TileOccupiedException {
		try {
			engine.addMove(0, 0, 'x');
			engine.addMove(1, 0, 'x');
			assertNull(engine.hasVictory('x'));

			engine.addMove(2, 0, 'x');
			assertNotNull(engine.hasVictory('x'));

			assertNull(engine.hasVictory('o'));

			engine = new GameEngine();
			engine.addMove(0, 1, 'o');
			engine.addMove(1, 1, 'o');
			engine.addMove(2, 1, 'o');
			assertNotNull(engine.hasVictory('o'));

			engine = new GameEngine();
			engine.addMove(0, 0, 'o');
			engine.addMove(0, 1, 'o');
			engine.addMove(0, 2, 'o');
			assertNotNull(engine.hasVictory('o'));

			engine = new GameEngine();
			engine.addMove(0, 0, 'o');
			engine.addMove(1, 1, 'o');
			engine.addMove(2, 2, 'o');
			assertNotNull(engine.hasVictory('o'));

			engine = new GameEngine();
			engine.addMove(2, 0, 'o');
			engine.addMove(1, 1, 'o');
			engine.addMove(0, 2, 'o');
			assertNotNull(engine.hasVictory('o'));

			engine = new GameEngine();
			engine.addMove(0, 0, 'o');
			engine.addMove(1, 1, 'x');
			engine.addMove(2, 2, 'o');
			assertNull(engine.hasVictory('o'));

			engine = new GameEngine();
			engine.addMove(0, 0, 'x');
			engine.addMove(2, 0, 'x');
			engine.addMove(0, 2, 'x');
			engine.addMove(2, 1, 'x');
			engine.addMove(1, 2, 'x');
			engine.addMove(1, 0, 'o');
			engine.addMove(0, 1, 'o');
			engine.addMove(1, 1, 'o');
			engine.addMove(2, 2, 'o');
			assertNull(engine.hasVictory('x'));
		} catch (NullPointerException exception) {
			// Ignorerar att Game- and ResultPanel-objekt inte är initierade
			if(!exception.getStackTrace()[0].getClassName().equals("se.rmsit.TicTacToe.Game") &&
					!exception.getStackTrace()[0].getClassName().contains("ResultPanel")) {
				throw exception;
			}
		}
	}

	@Test
	void canGetDraw() throws TileOccupiedException {
		try {
			// Behöver skapas för att addMove ska fungera, då den automatiskt kollar efter vinst och skriver dit
			new ResultPanel();
			// Lägger till drag för alla rutor förutom botten höger
			engine.addMove(0, 0, 'x');
			engine.addMove(1, 0, 'o');
			engine.addMove(2, 0, 'x');
			engine.addMove(0, 1, 'x');
			engine.addMove(1, 1, 'x');
			engine.addMove(2, 1, 'o');
			engine.addMove(0, 2, 'o');
			engine.addMove(1, 2, 'x');
			assertFalse(engine.isDraw());

			// Lägger till drag för sista rutan
			engine.addMove(2, 2, 'o');
			assertTrue(engine.isDraw());
		} catch (NullPointerException exception) {
			// Ignorerar att Game- and ResultPanel-objekt inte är initierade
			if(!exception.getStackTrace()[0].getClassName().equals("se.rmsit.TicTacToe.Game") &&
					!exception.getStackTrace()[0].getClassName().contains("ResultPanel")) {
				throw exception;
			}
		}
	}

	@Test
	void canSetRunning() {
		assertFalse(engine.isRunning());
		engine.setRunning(true);
		assertTrue(engine.isRunning());
	}

	@Test
	void canResetMovesAndTotalMoves() throws TileOccupiedException {
		engine.addMove(0, 0, 'x');

		engine.resetMoves();
		assertArrayEquals(new char[3][3], engine.getMoves());
		assertEquals(0, engine.getTotalMoves());
	}

}
