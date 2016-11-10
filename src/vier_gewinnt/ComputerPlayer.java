package vier_gewinnt;

/** A more or less smart computer player */
public class ComputerPlayer extends AbstractPlayer {

	public ComputerPlayer() {
		this.name = "Computer Player";
	}
	
	/**
	 * Strategy: Wenn Computer gewinnen kann -> gewinnen Wenn Gegner gewinnen kann -> verhindern
	 * sonst zufällige Kolonne wählen
	 */
	@Override
	public int getNextColumn(Token[][] board) {
		// selber gewinnen, wenn möglich
		if (horizontal(board, Token.player2) >= 0)
			return horizontal(board, Token.player2);
		if (vertical(board, Token.player2) >= 0)
			return vertical(board, Token.player2);
		if (diagonalLeft(board, Token.player2) >= 0)
			return diagonalLeft(board, Token.player2);
		if (diagonalRight(board, Token.player2) >= 0)
			return diagonalRight(board, Token.player2);
		
		// Gewinnen des Gegners verhindern, wenn nötig
		if (horizontal(board, Token.player1) >= 0)
			return horizontal(board, Token.player1);
		if (vertical(board, Token.player1) >= 0)
			return vertical(board, Token.player1);
		if (diagonalLeft(board, Token.player1) >= 0)
			return diagonalLeft(board, Token.player1);
		if (diagonalRight(board, Token.player1) >= 0)
			return diagonalRight(board, Token.player1);
		
		// sonst, zufällige Kolonne wählen
		return chooseRandomColumn(board);
	}
	
	/**
	 * Choose a random column and select the next valid column to the right (the chosen included)
	 */
	private int chooseRandomColumn(Token[][] board) {
		java.util.Random generator = new java.util.Random();
		int col = generator.nextInt(board.length);
		while (VierGewinnt.isColFull(board, col))
			col = (col + 1) % board.length;
		return col;
	}
	
	// /**
	// * @return true if the column col is already full and false otherwise.
	// */
	// private boolean isColFull(int col, Token[][] board) {
	// int topRow = board[col].length - 1;
	// return (board[col][topRow] != Token.empty);
	// }
	//
	// /**
	// * @returns the lowest Row which is free of a column
	// */
	// private int colHeight(int col, Token[][] board) {
	// int freeRow = 0;
	//
	// while (freeRow < VierGewinnt.ROWS) {
	// if (board[col][freeRow] == Token.empty)
	// break;
	// freeRow++;
	// }
	// if (freeRow < VierGewinnt.ROWS)
	// return freeRow;
	// else
	// return VierGewinnt.ROWS - 1;
	// }

	/**
	 * Prüft für jede Kolonne ob eine horizontale Reihe von mindestens vier erreicht wird, wenn der
	 * Stein dorthin gesetzt würde
	 *
	 * @returns col or -1
	 */
	private int horizontal(Token[][] board, Token player) {
		for (int col = 0; col < VierGewinnt.COLS; col++) {
			int counter = 1;
			for (int i = 1; col + i <= VierGewinnt.COLS - 1; i++)
				if (board[col + i][VierGewinnt.colHeight(board, col)] == player)
					counter++;
				else
					break;
			for (int i = -1; col + i >= 0; i--)
				if (board[col + i][VierGewinnt.colHeight(board, col)] == player)
					counter++;
				else
					break;

			// System.err.println(col);
			if ((counter >= 3) && !VierGewinnt.isColFull(board, col))
				return col;
		}

		return -1;
	}

	/**
	 * Prüft für jede Kolonne ob eine vertikale Reihe von mindestens vier erreicht wird, wenn der
	 * Stein dorthin gesetzt würde
	 *
	 * @returns col or -1
	 */
	private int vertical(Token[][] board, Token player) {
		for (int col = 0; col < VierGewinnt.COLS; col++) {
			int counter = 1;
			for (int i = 1; VierGewinnt.colHeight(board, col) - i >= 0; i++)
				if (board[col][VierGewinnt.colHeight(board, col) - i] == player)
					counter++;
				else
					break;

			// System.err.println(col);
			if ((counter >= 3) && !VierGewinnt.isColFull(board, col))
				return col;
		}

		return -1;
	}

	/**
	 * Prüft für jede Kolonne ob eine diagonale nach links oben verlaufende Reihe von mindestens
	 * vier erreicht wird, wenn der Stein dorthin gesetzt würde
	 *
	 * @returns col or -1
	 */
	private int diagonalLeft(Token[][] board, Token player) {
		for (int col = 0; col < VierGewinnt.COLS; col++) {
			int counter = 1;
			// go left up
			for (int i = 1; VierGewinnt.colHeight(board, col) + i <= VierGewinnt.ROWS - 1
					&& col - i > 0; i++)
				if (board[col - i][VierGewinnt.colHeight(board, col) + i] == player)
					counter++;
				else
					break;
			// go right down
			for (int i = 1; (VierGewinnt.colHeight(board, col) - i >= 0)
					&& (col + i <= VierGewinnt.COLS - 1); i++)
				if (board[col + i][VierGewinnt.colHeight(board, col) - i] == player)
					counter++;
				else
					break;

			// System.err.println(col);
			if ((counter >= 3) && !VierGewinnt.isColFull(board, col))
				return col;
		}

		return -1;
	}

	/**
	 * Prüft für jede Kolonne ob eine diagonale nach rechts oben verlaufende Reihe von mindestens
	 * vier erreicht wird, wenn der Stein dorthin gesetzt würde
	 *
	 * @returns col or -1
	 */
	private int diagonalRight(Token[][] board, Token player) {
		for (int col = 0; col < VierGewinnt.COLS; col++) {
			int counter = 1;
			// go right up
			for (int i = 1; (VierGewinnt.colHeight(board, col) + i <= VierGewinnt.ROWS - 1)
					&& (col + i <= VierGewinnt.COLS - 1); i++)
				if (board[col + i][VierGewinnt.colHeight(board, col) + i] == player)
					counter++;
				else
					break;
			// go left down
			for (int i = 1; (VierGewinnt.colHeight(board, col) - i >= 0) && (col - i > 0); i++)
				if (board[col - i][VierGewinnt.colHeight(board, col) - i] == player)
					counter++;
				else
					break;
			// System.err.println(col);
			if ((counter >= 3) && !VierGewinnt.isColFull(board, col))
				return col;
		}

		return -1;
	}
}
