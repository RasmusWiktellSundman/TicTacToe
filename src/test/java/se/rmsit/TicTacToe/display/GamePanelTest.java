package se.rmsit.TicTacToe.display;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.rmsit.TicTacToe.Game;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class GamePanelTest {
	private GamePanel gamePanel;

	@BeforeEach
	void setUp() {
		gamePanel = new GamePanel();
	}

	@Test
	void canCreateAndInitializeButtonsArray() {
		JButton[][] buttons = gamePanel.getButtons();
		for (int y = 0; y < Game.TILE_LENGTH; y++) {
			for (int x = 0; x < Game.TILE_LENGTH; x++) {
				assertInstanceOf(JButton.class, buttons[x][y]);
			}
		}
	}
}