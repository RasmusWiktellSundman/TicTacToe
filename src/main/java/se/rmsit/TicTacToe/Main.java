package se.rmsit.TicTacToe;

import se.rmsit.TicTacToe.display.MainFrame;

public class Main {
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame("Tic Tac Toe");
		GameEngine engine = new GameEngine();
	}
}
