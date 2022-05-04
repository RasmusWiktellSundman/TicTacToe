package se.rmsit.TicTacToe.display;

import se.rmsit.TicTacToe.GameEngine;
import se.rmsit.TicTacToe.Game;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;

import javax.swing.*;
import java.awt.*;

public class GamePanel {
	private JPanel panelGame;
	JButton[][] buttons = new JButton[Game.TILE_LENGTH][Game.TILE_LENGTH];

	public GamePanel() {
		populateButtonsArray();
		addButtonListeners();
	}

	private void populateButtonsArray() {
		int x = 0;
		int y = 0;
		for (Component component : panelGame.getComponents()) {
			if(component.getName() != null && component.getName().contains("tile")) {
				buttons[y][x] = (JButton) component;
				x++;
				// Nollställ x när vi kommer till slutet av en rad och öka y
				if(x > Game.TILE_LENGTH - 1) {
					x = 0;
					y++;
				}
			}
		}
	}

	private void addButtonListeners() {
		for (int y = 0; y < Game.TILE_LENGTH; y++) {
			for (int x = 0; x < Game.TILE_LENGTH; x++) {
				// Variablerna behöver vara final för att kunna användas i event lyssnaren
				int finalX = x;
				int finalY = y;
				// Lägger till lyssnare för knapptryck
				buttons[y][x].addActionListener(e -> {
					GameEngine engine = Game.getGameEngine();
					// Tar bort gamla felmeddelanden
					Game.removeErrorMessage();
					// Sparar vilken spelare som gjorde draget, för att sätta rutan till den spelaren, ifall draget är giltigt.
					char player = engine.getNextPlayer();
					try {
						engine.getTileAndMoveManager().moveAndUpdateNextPlayer(finalX, finalY);
						Game.getMainFrame().updateNextPlayer(engine.getNextPlayer());

						JButton button = (JButton) e.getSource();
						button.setText(player + "");
					} catch (TileOccupiedException ex) {
						Game.setErrorMessage("Rutan är upptagen, välj en annan!");
					}
				});
			}
		}
	}

	public void restart() {
		// Sätter alla knappars text till tomt och aktiverar dem
		for (int y = 0; y < Game.TILE_LENGTH; y++) {
			for (int x = 0; x < Game.TILE_LENGTH; x++) {
				JButton button = buttons[y][x];
				button.setText("");
				button.setEnabled(true);
			}
		}
	}

	public void end() {
		// Avaktiverar alla knappar
		for (int y = 0; y < Game.TILE_LENGTH; y++) {
			for (int x = 0; x < Game.TILE_LENGTH; x++) {
				JButton button = buttons[y][x];
				button.setEnabled(false);
			}
		}
	}

	// Getters and setters
	public JButton[][] getButtons() {
		return buttons;
	}
}
