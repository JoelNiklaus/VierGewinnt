package vier_gewinnt;

import java.util.Scanner;

/** A human player class */
public class HumanPlayer extends AbstractPlayer {

	private Scanner scan = new Scanner(System.in);
	private static int humanCounts = 0;
	
	public HumanPlayer() {
		assert humanCounts <= 2;
		humanCounts++;
		System.out.println("Hi, what is your name, " + humanCounts + "-th human player?");
		name = scan.nextLine();
		if (0 == name.length())
			name = "Human Player No " + humanCounts;
	}
	
	@Override
	public int getNextColumn(Token[][] board) {
		System.out.println("\n" + VierGewinnt.displayBoard(board));
		int column = -1;
		while (column < 0 || column >= board.length) {
			System.out.print("Player " + this.token.toString() + " choose a column between 1 and "
					+ board.length + ": ");
			try {
				column = Integer.parseInt(scan.nextLine()) - 1;
			} catch (NumberFormatException e) {
				column = -1;
				System.out.println("That is not even a number!");
			}
			if (column >= 0 && column < board.length)
				if (isColFull(column, board)) {
					System.out.println("This column is full!");
					column = -1;
				}
		}
		return column;
	}
	
	/** returns true if the column col is already full and false otherwise. */
	private boolean isColFull(int col, Token[][] board) {
		int topRow = board[0].length - 1;
		if (board[col][topRow] != Token.empty)
			return true;
		else
			return false;
	}
}
