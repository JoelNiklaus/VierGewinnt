package computerPlayerLevels;

import vierGewinnt.IPlayer;
import vierGewinnt.Token;

import java.util.ArrayList;

/* ************************************************************************* *\
 *                Programmierung 1 HS 2016 - Serie 4-1                         *
 \* ************************************************************************* */

/**
 * A not extremly stupid computer player any more
 */
public class CPLevel6 implements IPlayer {

	public static final int COLS = 7;
	public static final int ROWS = 6;
	public static final int WINNING_SCORE = 4;

	public static final int THREE_OF_FOUR_OWN = 20;
	public static final int THREE_OF_FOUR_OPPONENT = 10;

	private Token ownToken;
	private Token opponentToken = null;

	private boolean firstTurn = true;

	private int[] colRating = new int[COLS];

	private ArrayList<Integer> cols;

	private Token[][] board;

	/**
	 * Strategy is to chose a random column and select the next valid column to the right (the
	 * chosen included)
	 */
	@Override
	public int getNextColumn(Token[][] board) {
		this.board = board;

		opponentToken = getOtherToken();

		colRating[3] = 3;
		colRating[2] = 2;
		colRating[4] = 2;
		colRating[1] = 1;
		colRating[5] = 1;
		colRating[0] = 0;
		colRating[6] = 0;

		cols = deleteFullColumns();

		// TODO allgemein: zwei nebeneinander vom Gegner auf beiden seiten offen : daneben legen

		if (firstTurn) {
			// Starting Strategy
			int numberOfTokensPlaced = getNumberOfTokensPlaced(board);
			// I can start
			if (numberOfTokensPlaced == 0)
				return 3;
			// first Turn but the opponent started
			if (numberOfTokensPlaced == 1) {
				// if opponent placed token in the middle
				if (board[3][0] == opponentToken)
					return 4;
				else
					return 3;
			} else
				firstTurn = false;
		}

		// selbst siegen
		int column = checkImmidiateFour(ownToken, board);
		if (column >= 0)
			return column;

		// Gegner Sieg verhindern
		column = checkImmidiateFour(opponentToken, board);
		if (column >= 0)
			return column;

		// Gegner Sieg in nächster Runde verhindern -> diese Kolonne sperren
		destroyOpponentWinInNextRound(board);

		// Eigenen Sieg nicht verbauen -> diese Kolonne sperren
		checkWinInNextRound(ownToken, board);

		// Gegner Sieg in übernächster Runde verhindern -> diese Kolonne setzen
		for (int col : cols)
			if (colHeight(board, col) < ROWS) {
				Token[][] copiedBoard = insertToken(col, opponentToken, getCopyOfBoard(board));
				int opponentWinCol = checkImmidiateFour(opponentToken, copiedBoard);

				// if opponent could win, test this col
				if (opponentWinCol >= 0)
					if (colHeight(copiedBoard, opponentWinCol) < ROWS - 1) {
						copiedBoard = insertToken(opponentWinCol, ownToken, copiedBoard);
						copiedBoard = insertToken(opponentWinCol, opponentToken, copiedBoard);

						if (checkPossibleVierGewinnt(opponentWinCol,
								colHeight(copiedBoard, opponentWinCol) - 1, opponentToken,
								copiedBoard))
							return col;
					}
			}


		// if no col available just choose first one which is not full -> almost equal to resign
		if (cols.isEmpty())
			for (int col = 0; col < COLS; col++)
				if (!isColFull(board, col))
					return col;

		return getColWithBestRating(colRating, cols);
	}

	private int checkImmidiateFour(Token player, Token[][] board) {
		for (int col : cols)
			if (colHeight(board, col) < ROWS) {
				Token[][] copiedBoard = insertToken(col, player, getCopyOfBoard(board));
				if (checkPossibleVierGewinnt(col, colHeight(copiedBoard, col) - 1, player,
						copiedBoard))
					return col;
			}
		return -1;
	}

	private void checkThreeOfFour(Token player) {
		int ratingIncrease;
		if (player.equals(opponentToken))
			ratingIncrease = THREE_OF_FOUR_OPPONENT;
		else
			ratingIncrease = THREE_OF_FOUR_OWN;
		for (int col : cols)
			if (colHeight(board, col) < ROWS) {
				Token[][] copiedBoard = insertToken(col, player, getCopyOfBoard(board));
				if (checkPossibleGoodScore(col, colHeight(copiedBoard, col) - 1, player,
						copiedBoard))
					colRating[col] += ratingIncrease;
			}
	}

	private void checkWinInNextRound(Token player, Token[][] board) {
		int column = -1;
		for (int col : cols)
			if (colHeight(board, col) < ROWS - 1) {
				Token[][] copiedBoard = insertToken(col, getOtherToken(player), getCopyOfBoard(board));
				copiedBoard = insertToken(col, player, copiedBoard);
				if (checkPossibleVierGewinnt(col, colHeight(copiedBoard, col) - 1, player,
						copiedBoard))
					column = col;
			}
		if (column > -1)
			cols.remove(Integer.valueOf(column));
	}

	private Token getOtherToken(Token token) {
		Token otherToken;
		if (token.toString().equals("X"))
			otherToken = Token.player1;
		else
			otherToken = Token.player2;
		return otherToken;
	}

	private void destroyOpponentWinInNextRound(Token[][] board) {
		int column = -1;
		for (int col : cols)
			if (colHeight(board, col) < ROWS - 1) {
				Token[][] copiedBoard = insertToken(col, ownToken, getCopyOfBoard(board));
				copiedBoard = insertToken(col, opponentToken, copiedBoard);
				if (checkPossibleVierGewinnt(col, colHeight(copiedBoard, col) - 1, opponentToken,
						copiedBoard))
					column = col;
			}
		if (column > -1)
			cols.remove(Integer.valueOf(column));
	}

	private int getNumberOfTokensPlaced(Token[][] board) {
		int numberOfTokensPlaced = 0;
		for (Token[] row : board)
			for (Token field : row)
				if (field != Token.empty)
					numberOfTokensPlaced++;
		return numberOfTokensPlaced;
	}

	private int getColWithBestRating(int[] colRating, ArrayList<Integer> cols) {
		int currentBestRating = Integer.MIN_VALUE;
		int currentBestCol = 3;
		for (int col : cols)
			if (colRating[col] > currentBestRating) {
				currentBestCol = col;
				currentBestRating = colRating[col];
			}
		for (int col : cols)
			if (colRating[col] == currentBestRating)
				if (Math.random() < 0.5)
					return col;
		return currentBestCol;
	}

	private ArrayList<Integer> deleteFullColumns() {
		ArrayList<Integer> cols = new ArrayList<Integer>();
		for (int col = 0; col < COLS; col++)
			if (!isColFull(board, col))
				cols.add(col);
		return cols;
	}

	private Token getOtherToken() {
		Token otherToken;
		if (ownToken.toString().equals("X"))
			otherToken = Token.player1;
		else
			otherToken = Token.player2;
		return otherToken;
	}

	private Token[][] getCopyOfBoard(Token[][] board) {
		Token[][] copiedBoard = new Token[COLS][ROWS];
		for (int i = 0; i < copiedBoard.length; i++)
			for (int j = 0; j < copiedBoard[i].length; j++)
				copiedBoard[i][j] = board[i][j];
		return copiedBoard;
	}

	private Token[][] insertToken(int column, Token token, Token[][] board) {
		if ((column < 0) || (column > COLS - 1))
			System.out.println("Please choose a column between 1 and " + COLS + "!");
		else if (isColFull(board, column) == true)
			System.out.println("Please choose a column which is not already full!");

		int freeRow = colHeight(board, column);
		board[column][freeRow] = token;

		return board;
	}

	/**
	 * @return true if the column col is already full and false otherwise.
	 */
	private static boolean isColFull(Token[][] board, int col) {
		int topRow = board[0].length - 1;
		return (board[col][topRow] != Token.empty);
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
		Runner runner = new Runner(board, col, row, player);
		// vertical
		if (runner.zeroEmptyRun(0, 1) >= WINNING_SCORE)
			return true;
		// horizontal
		if (runner.zeroEmptyRun(1, 0) >= WINNING_SCORE)
			return true;
		// diagonal right up
		if (runner.zeroEmptyRun(1, 1) >= WINNING_SCORE)
			return true;
		// diagonal left up
		if (runner.zeroEmptyRun(1, -1) >= WINNING_SCORE)
			return true;

		return false;
	}

	private boolean checkPossibleGoodScore(int col, int row, Token player, Token[][] board) {
		Runner runner = new Runner(board, col, row, player);
		// vertical
		if (runner.oneEmptyRun(0, 1) >= WINNING_SCORE || runner.oneEmptyRun(0, -1) >= WINNING_SCORE)
			return true;
		// horizontal
		if (runner.oneEmptyRun(1, 0) >= WINNING_SCORE || runner.oneEmptyRun(-1, 0) >= WINNING_SCORE)
			return true;
		// diagonal right up
		if (runner.oneEmptyRun(1, 1) >= WINNING_SCORE
				|| runner.oneEmptyRun(-1, -1) >= WINNING_SCORE)
			return true;
		// diagonal left up
		if (runner.oneEmptyRun(1, -1) >= WINNING_SCORE
				|| runner.oneEmptyRun(-1, 1) >= WINNING_SCORE)
			return true;

		return false;
	}

	public class Runner {

		Token[][] board;
		int homeCol, homeRow;
		int col = 0, row = 0;
		Token player;
		int emptyScore = 0;

		public Runner(Token[][] board, int homeCol, int homeRow, Token player) {
			this.board = board;
			this.homeCol = homeCol;
			this.homeRow = homeRow;
			this.player = player;
		}

		// three player's tokens and one empty token
		public int oneEmptyRun(int dcol, int drow) {
			emptyScore = 0;
			int score = 1;
			this.goHome();
			score += this.forwardRun(dcol, drow);
			this.goHome();
			score += this.reverseRun(dcol, drow);
			return score;
		}

		// only player's token
		public int zeroEmptyRun(int dcol, int drow) {
			emptyScore = 1;
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

			if (this.emptyField() && emptyScore == 0) {
				emptyScore++;
				return 1 + this.forwardRun(dcol, drow);
			} else
				return 0;
		}

		private boolean outOfBounds() {
			return (col < 0 || row < 0 || col >= COLS || row >= ROWS);
		}

		private boolean samePlayer() {
			return (board[col][row].equals(player));
		}

		private boolean emptyField() {
			return (board[col][row].equals(Token.empty));
		}

		private void move(int dcol, int drow) {
			col += dcol;
			row += drow;
		}
	}

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
		return "Captain Awesome";
	}
}