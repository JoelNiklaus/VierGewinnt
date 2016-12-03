package tests;

import computerPlayerLevels.CPLevel5;
import computerPlayerLevels.CPLevel6;
import org.junit.Test;
import vierGewinnt.ComputerPlayer;
import vierGewinnt.IPlayer;
import vierGewinnt.VierGewinnt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the computerPlayer against CPLevel6
 */
public class ComputerPlayerTestLevel6 {
	IPlayer favorite = new ComputerPlayer();
	IPlayer opponent = new CPLevel6();

	// as firstPlayer
	@Test
	public void testDoesNotLoseAgainstLvl6asFirstPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertNotEquals(opponent, game.getWinner(favorite, opponent));
	}

	@Test
	public void testWinsAgainstLvl6asFirstPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertEquals(favorite, game.getWinner(favorite, opponent));
	}

	// as secondPlayer
	@Test
	public void testDoesNotLoseAgainstLvl6asSecondPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertNotEquals(opponent, game.getWinner(opponent, favorite));
	}

	@Test
	public void testWinsAgainstLvl6asSecondPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertEquals(favorite, game.getWinner(opponent, favorite));
	}

}