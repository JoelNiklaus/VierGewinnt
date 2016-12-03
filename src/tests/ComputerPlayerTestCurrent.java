package tests;

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
public class ComputerPlayerTestCurrent {
	IPlayer favorite = new ComputerPlayer();
	IPlayer opponent = new ComputerPlayer();

	// as firstPlayer
	@Test
	public void testDoesNotLoseAgainstCurrentasFirstPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertNotEquals(opponent, game.getWinner(favorite, opponent));
	}

	@Test
	public void testWinsAgainstCurrentasFirstPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertEquals(favorite, game.getWinner(favorite, opponent));
	}

	// as secondPlayer
	@Test
	public void testDoesNotLoseAgainstCurrentasSecondPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertNotEquals(opponent, game.getWinner(opponent, favorite));
	}

	@Test
	public void testWinsAgainstCurrentasSecondPlayer() throws Exception {
		VierGewinnt game = new VierGewinnt();

		assertEquals(favorite, game.getWinner(opponent, favorite));
	}

}