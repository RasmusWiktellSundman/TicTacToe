package se.rmsit.TicTacToe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.rmsit.TicTacToe.managers.TileAndMoveManager;
import se.rmsit.TicTacToe.managers.VictoryManager;

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
	void canSetRunning() {
		assertFalse(engine.isRunning());
		engine.setRunning(true);
		assertTrue(engine.isRunning());
	}

	@Test
	void canGetMoveTileManager() {
		assertInstanceOf(TileAndMoveManager.class, engine.getTileAndMoveManager());
	}

	@Test
	void canGetVictoryManager() {
		assertInstanceOf(VictoryManager.class, engine.getVictoryManager());
	}

	@Test
	void canGetPlayerWhoStarted() {
		assertEquals('x', engine.getPlayerWhoStarted());
	}

	@Test
	void canUpdatePlayerWhoStarted() {
		assertEquals('x', engine.getPlayerWhoStarted());
		engine.updatePlayerWhoStarted();
		assertEquals('o', engine.getPlayerWhoStarted());
	}
}
