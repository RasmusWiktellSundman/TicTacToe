package se.rmsit.TicTacToe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;
import se.rmsit.TicTacToe.managers.TileAndMoveManager;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {
	private GameEngine engine;

	@BeforeEach
	void setUp() {
		engine = new GameEngine();
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
	void checkIf3InARowIsCalculatedCorrectly() throws TileOccupiedException {
		try {
			TileAndMoveManager moveManager = engine.getTileAndMoveManager();

			moveManager.addMove(0, 0, 'x');
			moveManager.addMove(1, 0, 'x');
			assertNull(engine.hasVictory('x'));

			moveManager.addMove(2, 0, 'x');
			assertNotNull(engine.hasVictory('x'));

			assertNull(engine.hasVictory('o'));

			engine = new GameEngine();
			moveManager.addMove(0, 1, 'o');
			moveManager.addMove(1, 1, 'o');
			moveManager.addMove(2, 1, 'o');
			assertNotNull(engine.hasVictory('o'));

			engine = new GameEngine();
			moveManager.addMove(0, 0, 'o');
			moveManager.addMove(0, 1, 'o');
			moveManager.addMove(0, 2, 'o');
			assertNotNull(engine.hasVictory('o'));

			engine = new GameEngine();
			moveManager.addMove(0, 0, 'o');
			moveManager.addMove(1, 1, 'o');
			moveManager.addMove(2, 2, 'o');
			assertNotNull(engine.hasVictory('o'));

			engine = new GameEngine();
			moveManager.addMove(2, 0, 'o');
			moveManager.addMove(1, 1, 'o');
			moveManager.addMove(0, 2, 'o');
			assertNotNull(engine.hasVictory('o'));

			engine = new GameEngine();
			moveManager.addMove(0, 0, 'o');
			moveManager.addMove(1, 1, 'x');
			moveManager.addMove(2, 2, 'o');
			assertNull(engine.hasVictory('o'));

			engine = new GameEngine();
			moveManager.addMove(0, 0, 'x');
			moveManager.addMove(2, 0, 'x');
			moveManager.addMove(0, 2, 'x');
			moveManager.addMove(2, 1, 'x');
			moveManager.addMove(1, 2, 'x');
			moveManager.addMove(1, 0, 'o');
			moveManager.addMove(0, 1, 'o');
			moveManager.addMove(1, 1, 'o');
			moveManager.addMove(2, 2, 'o');
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
			TileAndMoveManager moveManager = engine.getTileAndMoveManager();
			// Lägger till drag för alla rutor förutom botten höger
			moveManager.addMove(0, 0, 'x');
			moveManager.addMove(1, 0, 'o');
			moveManager.addMove(2, 0, 'x');
			moveManager.addMove(0, 1, 'x');
			moveManager.addMove(1, 1, 'x');
			moveManager.addMove(2, 1, 'o');
			moveManager.addMove(0, 2, 'o');
			moveManager.addMove(1, 2, 'x');
			assertFalse(engine.isDraw());

			// Lägger till drag för sista rutan
			moveManager.addMove(2, 2, 'o');
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
}
