package se.rmsit.TicTacToe.display;

import se.rmsit.TicTacToe.GameEngine;
import se.rmsit.TicTacToe.Game;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;

import javax.swing.*;
import java.awt.*;

public class GamePanel {
	private JPanel panelGame;
	JButton[][] buttons = new JButton[Game.TILE_LENGTH][Game.TILE_LENGTH];
	private Cursor blockCursor;

	public GamePanel() {
		populateButtonsArray();
		addButtonListeners();
		createBlockCursor();
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
						button.setCursor(blockCursor);

						if(player == 'x')
							setX(button);
						else
							setO(button);
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
				button.setBackground(Color.WHITE);
				button.setEnabled(true);
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}

	public void end() {
		// Avaktiverar alla knappar
		for (int y = 0; y < Game.TILE_LENGTH; y++) {
			for (int x = 0; x < Game.TILE_LENGTH; x++) {
				JButton button = buttons[y][x];
				button.setEnabled(false);
				button.setCursor(blockCursor);
			}
		}
	}

	private void setX(JButton button) {
		button.setText("X");
		button.setBackground(Color.RED);
		button.setForeground(Color.WHITE);
	}

	private void setO(JButton button) {
		button.setText("O");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
	}

	private void createBlockCursor() {
		// Används för att skapa awt Image-objekt och muspekare
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		// Initierar Image-objektet med bilden från resources mappen
		Image image = toolkit.getImage(getClass().getResource("/images/block-cursor.png"));
		// Skapar ny muspekare, använder 16, 16 för att centrera den.
		blockCursor = toolkit.createCustomCursor(image, new Point(16,16), "block-cursor");
	}

	// Getters and setters
	public JButton[][] getButtons() {
		return buttons;
	}
}
