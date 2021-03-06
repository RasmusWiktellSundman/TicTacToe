package se.rmsit.TicTacToe.display;

import se.rmsit.TicTacToe.GameEngine;
import se.rmsit.TicTacToe.Game;
import se.rmsit.TicTacToe.exceptions.TileOccupiedException;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
	JButton[][] buttons = new JButton[Game.TILE_LENGTH][Game.TILE_LENGTH];
	private Cursor blockCursor;
	private final GameEngine engine = Game.getGameEngine();

	public GamePanel() {
		setupLayout();
		setupButtons();
		addButtonListeners();
		createBlockCursor();
	}

	private void setupLayout() {
		GridLayout layout = new GridLayout(Game.TILE_LENGTH, Game.TILE_LENGTH);
		// Sätter mellanrum mellan knapparna
		layout.setHgap(10);
		layout.setVgap(10);
		// Sätter panelens layout till att använda GridLayout
		setLayout(layout);
	}

	private void setupButtons() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < Game.TILE_LENGTH * Game.TILE_LENGTH; i++) {
			JButton button = new JButton();
			// designa knapp
			button.setMinimumSize(new Dimension(50, 50));
			// Tar bort kant runt text vid focus
			button.setFocusPainted(false);
			button.setName("tile" + i);
			button.setBackground(Color.GRAY);

			// Lägg till i panelen
			add(button);

			// Lägg in i buttons array
			buttons[x][y] = button;
			x++;
			// Nollställ x när vi kommer till slutet av en rad och öka y
			if(x > Game.TILE_LENGTH - 1) {
				x = 0;
				y++;
			}
		}
	}

	private void addButtonListeners() {
		for (int y = 0; y < Game.TILE_LENGTH; y++) {
			for (int x = 0; x < Game.TILE_LENGTH; x++) {
				// Variablerna behöver vara final för att kunna användas i event lyssnaren
				final int finalX = x;
				final int finalY = y;
				// Lägger till lyssnare för knapptryck
				// När en spelare trycker på en knapp hanteras draget
				buttons[y][x].addActionListener(e -> {
					if(!engine.isRunning()) {
						Game.getMainFrame().setErrorMessage("Du måste starta spelet.");
						return;
					}

					// Tar bort gamla felmeddelanden
					Game.getMainFrame().removeErrorMessage();

					// Sparar vilken spelare som gjorde draget, för att sätta rutan till den spelaren, ifall draget är giltigt.
					char player = engine.getNextPlayer();

					try {
						engine.getTileAndMoveManager().moveAndUpdateNextPlayer(finalX, finalY);
						// Uppdaterar vems tur det är grafiskt
						Game.getMainFrame().setNextPlayer(engine.getNextPlayer());

						JButton button = (JButton) e.getSource();
						if(player == 'x')
							setX(button);
						else
							setO(button);
					} catch (TileOccupiedException ex) {
						Game.getMainFrame().setErrorMessage("Rutan är upptagen, välj en annan!");
					}
				});
			}
		}
	}

	public void restart() {
		// Återställer knappar till ursprungsläge (tar bort X och O, med mera)
		for (int y = 0; y < Game.TILE_LENGTH; y++) {
			for (int x = 0; x < Game.TILE_LENGTH; x++) {
				JButton button = buttons[y][x];
				button.setText("");
				button.setBackground(Color.WHITE);
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}

	public void end() {
		// Gör knapparnas bakgrundsfärg mörkare och ändrar muspekare.
		for (int y = 0; y < Game.TILE_LENGTH; y++) {
			for (int x = 0; x < Game.TILE_LENGTH; x++) {
				JButton button = buttons[y][x];
				button.setCursor(blockCursor);
				// Gör bakgrunden mörkare
				button.setBackground(button.getBackground().darker());
			}
		}
	}

	private void setX(JButton button) {
		button.setText("X");
		button.setBackground(Color.RED);
		button.setForeground(Color.WHITE);
		button.setCursor(blockCursor);
	}

	private void setO(JButton button) {
		button.setText("O");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.setCursor(blockCursor);
	}

	/**
	 * Skapar en {@link Cursor} med en blockeringssymbol som motiv.
	 */
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
