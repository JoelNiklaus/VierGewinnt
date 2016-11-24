package neu;

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
		if (game.getCopyOfBoard()[col][row].equals(game.getCurrentPlayer().getToken()))
			return true;
		else
			return false;
	}

	private void move(int dcol, int drow) {
		col += dcol;
		row += drow;
	}
}
