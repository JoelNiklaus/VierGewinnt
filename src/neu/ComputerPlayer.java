package neu;

import java.util.ArrayList;

/* ************************************************************************* *\
 *                Programmierung 1 HS 2016 - Serie 4-1                         *
 \* ************************************************************************* */

/** A very stupid computer player */
public class ComputerPlayer implements IPlayer {

	public static final int COLS = 7;
	public static final int ROWS = 6;
	public static final int WINNING_SCORE = 4;
	public static final int GOOD_SCORE = 4;

	private Token ownToken;

	private Token[][] board;
	
	/**
	 * Strategy is to chose a random column and select the next valid column to the right (the
	 * chosen included)
	 */
	@Override
	public int getNextColumn(Token[][] board) {
		this.board = board;
		Token opponentToken = getOtherToken();
		int[] colRating = new int[COLS];
		colRating[3] = 10;

		ArrayList<Integer> cols = new ArrayList<Integer>();
		// Volle Kolonnen streichen
		for (int col = 0; col < COLS; col++)
			if (!isColFull(col))
				cols.add(col);
		
		// selbst siegen
		for (int col : cols) {
			Token[][] copiedBoard = getCopyOfBoard();
			copiedBoard = insertToken(col, ownToken, copiedBoard);
			if (checkPossibleVierGewinnt(col, colHeight(col), ownToken, copiedBoard))
				return col;
		}

		// Gegner Sieg verhindern
		for (int col : cols) {
			Token[][] copiedBoard = getCopyOfBoard();
			copiedBoard = insertToken(col, opponentToken, copiedBoard);
			if (checkPossibleVierGewinnt(col, colHeight(col), opponentToken, copiedBoard))
				return col;
		}

		// Gegner Sieg in nächster Runde verhindern -> diese Kolonne sperren
		for (int col : cols) {
			Token[][] copiedBoard = getCopyOfBoard();
			copiedBoard = insertToken(col, ownToken, copiedBoard);
			for (int col2 : cols) {
				Token[][] copiedBoard2 = getCopyOfBoard();
				copiedBoard2 = insertToken(col2, opponentToken, copiedBoard2);
				if (checkPossibleVierGewinnt(col2, colHeight(col2), opponentToken, copiedBoard2))
					colRating[col] = -1000;
			}
		}
		
		// 3 nacheinander versuchen
		for (int col : cols) {
			Token[][] copiedBoard = getCopyOfBoard();
			copiedBoard = insertToken(col, ownToken, copiedBoard);
			if (checkPossibleGoodScore(col, colHeight(col), ownToken, copiedBoard))
				colRating[col] += 10;
		}

		int currentBestRating = Integer.MIN_VALUE;
		int currentBestCol = 3;
		for (int col : cols)
			if (colRating[col] > currentBestRating) {
				currentBestCol = col;
				currentBestRating = colRating[col];
			}

		return currentBestCol;
	}
	
	private Token getOtherToken() {
		Token otherToken;
		if (ownToken.toString().equals("X"))
			otherToken = Token.player1;
		else
			otherToken = Token.player2;
		return otherToken;
	}
	
	private Token[][] getCopyOfBoard() {
		Token[][] copiedBoard = new Token[COLS][ROWS];
		for (int i = 0; i < copiedBoard.length; i++)
			for (int j = 0; j < copiedBoard[i].length; j++)
				copiedBoard[i][j] = this.board[i][j];
		return copiedBoard;
	}
	
	private int insertToken(int column, Token token) {
		if ((column < 1) || (column > 7))
			System.out.println("Please choose a column between 1 and " + COLS + "!");
		else if (isColFull(column) == true)
			System.out.println("Please choose a column which is not already full!");
		
		int freeRow = colHeight(column);
		board[column][freeRow] = token;
		
		return freeRow;
	}
	
	private Token[][] insertToken(int column, Token token, Token[][] board) {
		if ((column < 1) || (column > 7))
			System.out.println("Please choose a column between 1 and " + COLS + "!");
		else if (isColFull(column) == true)
			System.out.println("Please choose a column which is not already full!");
		
		int freeRow = colHeight(column);
		board[column][freeRow] = token;
		
		return board;
	}
	
	private boolean isColFull(int col) {
		return isColFull(this.board, col);
	}
	
	private static boolean isColFull(Token[][] board, int col) {
		int topRow = board[0].length - 1;
		if (board[col][topRow] != Token.empty)
			return true;
		else
			return false;
	}

	private int colHeight(int col) {
		return colHeight(this.board, col);
	}
	
	private static int colHeight(Token[][] board, int col) {
		int freeRow = 0;

		while (freeRow < ROWS) {
			if (board[col][freeRow] == Token.empty)
				break;
			freeRow++;
		}
		return freeRow;
	}
	
	private boolean checkPossibleVierGewinnt(int col, int row, Token player, Token[][] board) {
		Runner runner = new Runner(board, col, row);
		// horizontal
		if (runner.run(0, 1, player) >= WINNING_SCORE)
			return true;
		// vertical
		if (runner.run(1, 0, player) >= WINNING_SCORE)
			return true;
		// diagonal
		if (runner.run(1, 1, player) >= WINNING_SCORE)
			return true;
		
		return false;
	}
	
	private boolean checkPossibleGoodScore(int col, int row, Token player, Token[][] board) {
		Runner runner = new Runner(board, col, row);
		// horizontal
		if (runner.run(0, 1, player) >= GOOD_SCORE)
			return true;
		// vertical
		if (runner.run(1, 0, player) >= GOOD_SCORE)
			return true;
		// diagonal
		if (runner.run(1, 1, player) >= GOOD_SCORE)
			return true;
		
		return false;
	}
	
	/**
	 * @return true if the column col is already full and false otherwise.
	 */
	private boolean isColFull(int col, Token[][] board) {
		int topRow = board[col].length - 1;
		return (board[col][topRow] != Token.empty);
	}

	public class Runner {

		Token[][] board;
		int homeCol, homeRow;
		int col = 0, row = 0;
		
		public Runner(Token[][] board, int homeCol, int homeRow) {
			this.board = board;
			this.homeCol = homeCol;
			this.homeRow = homeRow;
		}
		
		public int run(int dcol, int drow, Token player) {
			int score = 1;
			this.goHome();
			score += this.forwardRun(dcol, drow, player);
			this.goHome();
			score += this.reverseRun(dcol, drow, player);
			return score;
		}

		private void goHome() {
			col = homeCol;
			row = homeRow;
		}

		private int reverseRun(int dcol, int drow, Token player) {
			return this.forwardRun(-dcol, -drow, player);
		}

		private int forwardRun(int dcol, int drow, Token player) {
			this.move(dcol, drow);
			if (outOfBounds())
				return 0;
			if (this.samePlayer(player))
				return 1 + this.forwardRun(dcol, drow, player);
			else
				return 0;
		}

		private boolean outOfBounds() {
			if (col < 0 || row < 0 || col >= VierGewinnt.COLS || row >= VierGewinnt.ROWS)
				return true;
			return false;
		}

		private boolean samePlayer(Token player) {
			if (board[col][row].equals(player))
				return true;
			else
				return false;
		}

		private void move(int dcol, int drow) {
			col += dcol;
			row += drow;
		}
	}
	
	@Override
	public void setToken(Token token) {
		this.ownToken = token;
	}
	
	@Override
	public Token getToken() {
		return this.ownToken;
	}
	
	@Override
	public String getPlayersName() {
		return "Random Player";
	}
}
