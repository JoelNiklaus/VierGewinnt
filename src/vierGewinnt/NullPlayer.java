package vierGewinnt;
/* ************************************************************************* *\
*                Programmierung 1 HS 2016 - Serie 4-1                         * 
\* ************************************************************************* */


/**
 * Used to handle null Players in draw. Represents No Winner
 */
public class NullPlayer implements IPlayer {

	@Override
	public String getPlayersName() {
		return "Null Player";
	}

	@Override
	public int getNextColumn(Token[][] board) {
		return 0;
	}

	@Override
	public void setToken(Token token) {

	}

	@Override
	public Token getToken() {
		return null;
	}
}
