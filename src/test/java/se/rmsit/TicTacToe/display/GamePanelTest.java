package se.rmsit.TicTacToe.display;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.rmsit.TicTacToe.Main;

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
		for (int x = 0; x < Main.TILE_LENGTH; x++) {
			for (int y = 0; y < Main.TILE_LENGTH; y++) {
				assertInstanceOf(JButton.class, buttons[x][y]);
			}
		}
	}
}