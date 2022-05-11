package se.rmsit.TicTacToe.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.rmsit.TicTacToe.GameEngine;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;

import static org.junit.jupiter.api.Assertions.*;

class VictoryManagerTest {
	private GameEngine engine;
	private TileAndMoveManager tileMoveManager;
	private VictoryManager victoryManager;

	@BeforeEach
	void setUp() {
		engine = new GameEngine();
		tileMoveManager = engine.getTileAndMoveManager();
		victoryManager = engine.getVictoryManager();
	}

	@Test
	void checkIf3InARowIsCalculatedCorrectly() throws TileOccupiedException {
		try {
			tileMoveManager.addMove(0, 0, 'x');
			tileMoveManager.addMove(1, 0, 'x');
			assertNull(victoryManager.hasVictory('x'));

			tileMoveManager.addMove(2, 0, 'x');
			assertNotNull(victoryManager.hasVictory('x'));

			assertNull(victoryManager.hasVictory('o'));

			tileMoveManager = new TileAndMoveManager(new GameEngine());
			victoryManager = new VictoryManager(tileMoveManager);
			tileMoveManager.addMove(0, 1, 'o');
			tileMoveManager.addMove(1, 1, 'o');
			tileMoveManager.addMove(2, 1, 'o');
			assertNotNull(victoryManager.hasVictory('o'));

			tileMoveManager = new TileAndMoveManager(new GameEngine());
			victoryManager = new VictoryManager(tileMoveManager);
			tileMoveManager.addMove(0, 0, 'o');
			tileMoveManager.addMove(0, 1, 'o');
			tileMoveManager.addMove(0, 2, 'o');
			assertNotNull(victoryManager.hasVictory('o'));

			tileMoveManager = new TileAndMoveManager(new GameEngine());
			victoryManager = new VictoryManager(tileMoveManager);
			tileMoveManager.addMove(0, 0, 'o');
			tileMoveManager.addMove(1, 1, 'o');
			tileMoveManager.addMove(2, 2, 'o');
			assertNotNull(victoryManager.hasVictory('o'));

			tileMoveManager = new TileAndMoveManager(new GameEngine());
			victoryManager = new VictoryManager(tileMoveManager);
			tileMoveManager.addMove(2, 0, 'o');
			tileMoveManager.addMove(1, 1, 'o');
			tileMoveManager.addMove(0, 2, 'o');
			assertNotNull(victoryManager.hasVictory('o'));

			tileMoveManager = new TileAndMoveManager(new GameEngine());
			victoryManager = new VictoryManager(tileMoveManager);
			tileMoveManager.addMove(0, 0, 'o');
			tileMoveManager.addMove(1, 1, 'x');
			tileMoveManager.addMove(2, 2, 'o');
			assertNull(victoryManager.hasVictory('o'));

			tileMoveManager = new TileAndMoveManager(new GameEngine());
			victoryManager = new VictoryManager(tileMoveManager);
			tileMoveManager.addMove(0, 0, 'x');
			tileMoveManager.addMove(2, 0, 'x');
			tileMoveManager.addMove(0, 2, 'x');
			tileMoveManager.addMove(2, 1, 'x');
			tileMoveManager.addMove(1, 2, 'x');
			tileMoveManager.addMove(1, 0, 'o');
			tileMoveManager.addMove(0, 1, 'o');
			tileMoveManager.addMove(1, 1, 'o');
			tileMoveManager.addMove(2, 2, 'o');
			assertNull(victoryManager.hasVictory('x'));
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
			// Lägger till drag för alla rutor förutom botten höger
			tileMoveManager.addMove(0, 0, 'x');
			tileMoveManager.addMove(1, 0, 'o');
			tileMoveManager.addMove(2, 0, 'x');
			tileMoveManager.addMove(0, 1, 'x');
			tileMoveManager.addMove(1, 1, 'x');
			tileMoveManager.addMove(2, 1, 'o');
			tileMoveManager.addMove(0, 2, 'o');
			tileMoveManager.addMove(1, 2, 'x');
			assertFalse(victoryManager.isDraw());

			// Lägger till drag för sista rutan
			tileMoveManager.addMove(2, 2, 'o');
			assertTrue(victoryManager.isDraw());
		} catch (NullPointerException exception) {
			// Ignorerar att Game- and ResultPanel-objekt inte är initierade
			if(!exception.getStackTrace()[0].getClassName().equals("se.rmsit.TicTacToe.Game") &&
					!exception.getStackTrace()[0].getClassName().contains("ResultPanel")) {
				throw exception;
			}
		}
	}

	@Test
	void updatingPlayerWhoStartedOnDraw() throws TileOccupiedException {
		try {
			assertEquals('x', engine.getPlayerWhoStarted());

			// Skapar en oavgjord ställning
			tileMoveManager.addMove(0, 0, 'x');
			tileMoveManager.addMove(1, 0, 'o');
			tileMoveManager.addMove(2, 0, 'x');
			tileMoveManager.addMove(0, 1, 'x');
			tileMoveManager.addMove(1, 1, 'x');
			tileMoveManager.addMove(2, 1, 'o');
			tileMoveManager.addMove(0, 2, 'o');
			tileMoveManager.addMove(1, 2, 'x');
			tileMoveManager.addMove(2, 2, 'o');

			assertEquals('o', engine.getPlayerWhoStarted());
		} catch (NullPointerException exception) {
			// Ignorerar att Game- and ResultPanel-objekt inte är initierade
			if(!exception.getStackTrace()[0].getClassName().equals("se.rmsit.TicTacToe.Game") &&
					!exception.getStackTrace()[0].getClassName().contains("ResultPanel")) {
				throw exception;
			}
		}
	}

}