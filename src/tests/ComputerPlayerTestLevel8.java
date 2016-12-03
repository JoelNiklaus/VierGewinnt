package tests;

import computerPlayerLevels.CPLevel5;
import computerPlayerLevels.CPLevel6;
import computerPlayerLevels.CPLevel8;
import org.junit.Test;
import vierGewinnt.ComputerPlayer;
import vierGewinnt.IPlayer;
import vierGewinnt.VierGewinnt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the computerPlayer against CPLevel7
 */
public class ComputerPlayerTestLevel8 {
	IPlayer favorite = new ComputerPlayer();
	IPlayer opponent = new CPLevel8();

	// as firstPlayer
	@Test
	public void testDoesNotLoseAgainstLvl8asFirstPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertNotEquals(opponent, game.getWinner(favorite, opponent));
	}

	@Test
	public void testWinsAgainstLvl8asFirstPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertEquals(favorite, game.getWinner(favorite, opponent));
	}

	// as secondPlayer
	@Test
	public void testDoesNotLoseAgainstLvl8asSecondPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertNotEquals(opponent, game.getWinner(opponent, favorite));
	}

	@Test
	public void testWinsAgainstLvl8asSecondPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertEquals(favorite, game.getWinner(opponent, favorite));
	}

}