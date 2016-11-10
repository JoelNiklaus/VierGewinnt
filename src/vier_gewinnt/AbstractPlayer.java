package vier_gewinnt;

public abstract class AbstractPlayer implements Player {
	
	protected Token token;
	protected String name;

	@Override
	public void setToken(Token token) {
		this.token = token;
	}

	@Override
	public Token getToken() {
		return this.token;
	}

	@Override
	public String getPlayersName() {
		return name;
	}

}
