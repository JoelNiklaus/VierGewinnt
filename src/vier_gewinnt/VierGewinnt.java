package vier_gewinnt;

import java.util.Arrays;
import java.util.Scanner;

public class VierGewinnt {

	public static final int COLS = 7; // Standard: 7
	public static final int ROWS = 6; // Standard: 6
	public static final int WINNING_SCORE = 4;
	
	private Token[][] board = new Token[COLS][ROWS];
	private Player[] players = new Player[2];
	private Player currentPlayer;

	private Scanner scanner = new Scanner(System.in);
	
	public void start() {
		initPlayers();
		play();
	}

	/** initialize board and players and start the game */
	public void play() {
		initBoard();
		
		boolean solved = false;
		currentPlayer = players[(new java.util.Random()).nextInt(2)]; // choose randomly who begins
		
		System.out.println("current player: " + currentPlayer.getToken());
		int insertCol, insertRow; // starting from 0
		while (!solved && !this.isBoardFull()) {
			// get player's next "move"
			// note that we pass only a copy of the board as an argument,
			// otherwise the player would be able to manipulate the board and cheat!
			insertCol = currentPlayer.getNextColumn(getCopyOfBoard());
			// insert the token and get the row where it landed
			insertRow = this.insertToken(insertCol, currentPlayer.getToken());
			//  check if the game is over
			solved = this.checkVierGewinnt(insertCol, insertRow);
			// switch to other player
			if (!solved)
				if (players[0].equals(currentPlayer))
					currentPlayer = players[1];
				else
					currentPlayer = players[0];
		}
		System.out.println(displayBoard(this.board));
		if (solved)
			System.out.println("Player " + currentPlayer.getToken() + " wins!");
		else
			System.out.println("Draw! Game over.");
		again();
	}
	
	private void again() {
		System.out.print("Do you want to play again? (y / n) ");
		String again = scanner.nextLine().toLowerCase();
		if (again.equals("y"))
			play();
		else
			System.exit(0);
	}

	private void initPlayers() {
		players[0] = new HumanPlayer();
		System.out.print("Play against a human opponent? (y / n) ");
		String opponent = scanner.nextLine().toLowerCase();
		while ((1 != opponent.length()) || (-1 == ("yn".indexOf(opponent)))) {
			System.out
					.print("Can't understand your answer. Play against a human opponent? (y / n) ");
			opponent = scanner.nextLine().toLowerCase();
		}
		if (opponent.equals("y"))
			players[1] = new HumanPlayer();
		else
			players[1] = new ComputerPlayer();

		players[0].setToken(Token.player1);
		players[1].setToken(Token.player2);
	}
	
	private void initBoard() {
		for (Token[] column : this.board)
			Arrays.fill(column, Token.empty);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Inserts the token at the specified column (if possible)
	 *
	 * @param column
	 *            the column to insert the token
	 * @param token
	 *            the players token
	 * @return the row where the token landed
	 */
	private int insertToken(int column, Token tok) {
		if ((column < 1) || (column > 7))
			System.out.println("Please choose a column between 1 and " + COLS + "!");
		else if (isColFull(column) == true)
			System.out.println("Please choose a column which is not already full!");
		
		int freeRow = colHeight(column);
		board[column][freeRow] = tok;
		
		return freeRow;
	}
	
	/**
	 * @returns true if the column col is already full and false otherwise.
	 */
	private boolean isColFull(int col) {
		return isColFull(this.board, col);
	}

	/**
	 * @returns true if the column col is already full and false otherwise.
	 */
	public static boolean isColFull(Token[][] board, int col) {
		int topRow = board[0].length - 1;
		if (board[col][topRow] != Token.empty)
			return true;
		else
			return false;
	}
	
	/**
	 * @returns the lowest Row which is free of a column
	 */
	private int colHeight(int col) {
		return colHeight(this.board, col);
	}

	/**
	 * @returns the lowest Row which is free of a column
	 */
	public static int colHeight(Token[][] board, int col) {
		int freeRow = 0;
		
		while (freeRow < ROWS) {
			if (board[col][freeRow] == Token.empty)
				break;
			freeRow++;
		}
		return freeRow;
	}
	
	/**
	 * Checks if every position is occupied
	 *
	 * @returns true, if the board is full.
	 */
	private boolean isBoardFull() {
		return isBoardFull(this.board);
	}
	
	/**
	 * Checks if every position is occupied
	 *
	 * @returns true, if the board is full.
	 */
	public static boolean isBoardFull(Token[][] board) {
		for (Token[] row : board)
			for (Token field : row)
				if (field == Token.empty)
					return false;
		return true;
	}
	
	/**
	 * Checks for at least four equal tokens in a row in either direction, starting from the given
	 * position.
	 */
	private boolean checkVierGewinnt(int col, int row) {
		// Solution with Runner
		Runner runner = new Runner(this, col, row);
		// horizontal
		if (runner.run(0, 1) >= WINNING_SCORE)
			return true;
		// vertical
		if (runner.run(1, 0) >= WINNING_SCORE)
			return true;
		// diagonal
		if (runner.run(1, 1) >= WINNING_SCORE)
			return true;
		
		return false;
		
		//
		// // Old Solution Token player = board[col][row]; if ((horizontal(col, row, player) ==
		// true) || (vertical(col, row, player) == true) || (diagonalLeft(col, row, player) == true)
		// || (diagonalRight(col, row, player) == true)) { return true; }
		//
		// return false;
		//
	}
	
	//
	// /**
	// * Checks for at least four equal tokens in horizontal direction
	// */
	// private boolean horizontal(int col, int row, Token currentPlayer) {
	// int counter = 1;
	// for (int i = 1; col + i <= COLS - 1; i++)
	// if (board[col + i][row] == currentPlayer)
	// counter++;
	// else
	// break;
	// for (int i = -1; col + i >= 0; i--)
	// if (board[col + i][row] == currentPlayer)
	// counter++;
	// else
	// break;
	//
	// if ((counter >= WINNING_SCORE))
	// return true;
	// return false;
	// }
	//
	// /**
	// * Checks for at least four equal tokens in vertical direction
	// */
	// private boolean vertical(int col, int row, Token currentPlayer) {
	// int counter = 1;
	// for (int i = 1; row - i >= 0; i++)
	// if (board[col][row - i] == currentPlayer)
	// counter++;
	// else
	// break;
	//
	// if ((counter >= WINNING_SCORE))
	// return true;
	// return false;
	// }
	//
	// /**
	// * Checks for at least four equal tokens in diagonalLeft direction
	// */
	// private boolean diagonalLeft(int col, int row, Token currentPlayer) {
	// /**/
	// int counter = 1;
	// // go left up
	// for (int i = 1; row + i <= ROWS - 1 && col - i > 0; i++)
	// if (board[col - i][row + i] == currentPlayer)
	// counter++;
	// else
	// break;
	// // go right down
	// for (int i = 1; (row - i >= 0) && (col + i <= COLS - 1); i++)
	// if (board[col + i][row - i] == currentPlayer)
	// counter++;
	// else
	// break;
	//
	// if ((counter >= WINNING_SCORE))
	// return true;
	// return false;
	// }
	//
	// /**
	// * Checks for at least four equal tokens in diagonalRight direction
	// */
	// private boolean diagonalRight(int col, int row, Token currentPlayer) {
	// int counter = 1;
	// // go right up
	// for (int i = 1; (row + i <= ROWS - 1) && (col + i <= COLS - 1); i++)
	// if (board[col + i][row + i] == currentPlayer)
	// counter++;
	// else
	// break;
	// // go left down
	// for (int i = 1; (row - i >= 0) && (col - i > 0); i++)
	// if (board[col - i][row - i] == currentPlayer)
	// counter++;
	// else
	// break;
	//
	// if ((counter >= WINNING_SCORE))
	// return true;
	// return false;
	// }
	
	/** Returns a (deep) copy of the board array */
	public Token[][] getCopyOfBoard() {
		Token[][] copiedBoard = new Token[COLS][ROWS];
		for (int i = 0; i < copiedBoard.length; i++)
			for (int j = 0; j < copiedBoard[i].length; j++)
				copiedBoard[i][j] = this.board[i][j];
		return copiedBoard;
	}
	
	/** returns a graphical representation of the board */
	public static String displayBoard(Token[][] myBoard) {
		String rowDelimiter = "+";
		String rowNumbering = " ";
		for (int col = 0; col < myBoard.length; col++) {
			rowDelimiter += "---+";
			rowNumbering += " " + (col + 1) + "  ";
		}
		rowDelimiter += "\n";
		
		String rowStr;
		String presentation = rowDelimiter;
		for (int row = myBoard[0].length - 1; row >= 0; row--) {
			rowStr = "| ";
			for (int col = 0; col < myBoard.length; col++)
				rowStr += myBoard[col][row].toString() + " | ";
			presentation += rowStr + "\n" + rowDelimiter;
		}
		presentation += rowNumbering;
		return presentation;
	}
	
	/** main method, starts the program */
	public static void main(String args[]) {
		new VierGewinnt().start();
	}
}
