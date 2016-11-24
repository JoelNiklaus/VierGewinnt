package neu;

/* ************************************************************************* *\
 *                Programmierung 1 HS 2016 - Serie 4-1                         *
 \* ************************************************************************* */

import java.util.Arrays;
import java.util.Scanner;

public class VierGewinnt {
	
	public static final int COLS = 7;
	public static final int ROWS = 6;
	public static final int WINNING_SCORE = 4;
	
	private Token[][] board = new Token[COLS][ROWS]; // 7 columns with 6 fields each
	private IPlayer[] players = new IPlayer[2]; //  two players

	private int currentPlayer;
	
	/** initialize board and players and start the game */
	public void play() {
		// initialize the board
		for (Token[] column : this.board)
			Arrays.fill(column, Token.empty);
		
		/* initialize players */
		players[0] = new HumanPlayer();
		System.out.print("Play against a human opponent? (y / n) ");
		String opponent = new Scanner(System.in).nextLine().toLowerCase();
		while ((1 != opponent.length()) || (-1 == ("yn".indexOf(opponent)))) {
			System.out
					.print("Can't understand your answer. Play against a human opponent? (y / n) ");
			opponent = new Scanner(System.in).nextLine().toLowerCase();
		}
		if (opponent.equals("y"))
			players[1] = new HumanPlayer();
		else
			players[1] = new ComputerPlayer();
		players[0].setToken(Token.player1);
		players[1].setToken(Token.player2);
		
		/* play... */
		boolean solved = false;
		currentPlayer = (new java.util.Random()).nextInt(2); // choose randomly who begins
		System.out.println("current player: " + currentPlayer);
		int insertCol, insertRow; // starting from 0
		while (!solved && !this.isBoardFull()) {
			// get player's next "move"
			// note that we pass only a copy of the board as an argument,
			// otherwise the player would be able to manipulate the board and cheat!
			insertCol = players[currentPlayer].getNextColumn(getCopyOfBoard());
			// insert the token and get the row where it landed
			insertRow = this.insertToken(insertCol, players[currentPlayer].getToken());
			//  check if the game is over
			solved = this.checkVierGewinnt(insertCol, insertRow);
			// switch to other player
			if (!solved)
				currentPlayer = (currentPlayer + 1) % 2;
		}
		System.out.println(displayBoard(this.board));
		if (solved)
			System.out.println("Player " + players[currentPlayer].getToken() + " wins!");
		else
			System.out.println("Draw! Game over.");
	}
	
	/**
	 * Inserts the token at the specified column (if possible) <<<<<<< HEAD
	 *
	 * @param column
	 *            the column to insert the token
	 * @param token
	 *            the players token
	 * @return the row where the token landed =======
	 * @param column
	 *            the column to insert the token
	 * @param tok
	 *            the players token
	 * @return the row where the token landed >>>>>>> 9428b1405d73578c7e008901b97cccb1b1ba4c56
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
	}
	
	/** Returns a (deep) copy of the board array */
	private Token[][] getCopyOfBoard() {
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
		VierGewinnt game = new VierGewinnt();
		game.play();
	}

	public class Runner {

		VierGewinnt game;
		int homeCol, homeRow;
		int col = 0, row = 0;
		
		public Runner(VierGewinnt game, int homeCol, int homeRow) {
			this.game = game;
			this.homeCol = homeCol;
			this.homeRow = homeRow;
		}
		
		public int run(int dcol, int drow) {
			int score = 1;
			this.goHome();
			score += this.forwardRun(dcol, drow);
			this.goHome();
			score += this.reverseRun(dcol, drow);
			return score;
		}

		private void goHome() {
			col = homeCol;
			row = homeRow;
		}

		private int reverseRun(int dcol, int drow) {
			return this.forwardRun(-dcol, -drow);
		}

		private int forwardRun(int dcol, int drow) {
			this.move(dcol, drow);
			if (outOfBounds())
				return 0;
			if (this.samePlayer())
				return 1 + this.forwardRun(dcol, drow);
			else
				return 0;
		}

		private boolean outOfBounds() {
			if (col < 0 || row < 0 || col >= VierGewinnt.COLS || row >= VierGewinnt.ROWS)
				return true;
			return false;
		}

		private boolean samePlayer() {
			if (game.getCopyOfBoard()[col][row].equals(players[currentPlayer].getToken()))
				return true;
			else
				return false;
		}

		private void move(int dcol, int drow) {
			col += dcol;
			row += drow;
		}
	}
}
