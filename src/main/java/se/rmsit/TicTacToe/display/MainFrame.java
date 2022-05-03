package se.rmsit.TicTacToe.display;

import javax.swing.*;

public class MainFrame extends JFrame {

	private JPanel panelMain;
	private JButton startGameButton;

	public MainFrame(String title) {
		super(title);
		this.setContentPane(this.panelMain);
		this.setSize(500, 300);
		// center window
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
