package tests.multipleTests;

import computerPlayerLevels.CPLevel1;
import computerPlayerLevels.CPLevel6;
import org.junit.Test;
import vierGewinnt.ComputerPlayer;
import vierGewinnt.IPlayer;
import vierGewinnt.VierGewinnt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the computerPlayer against different Computer Players
 */
public abstract class AbstractComputerPlayerTest {
	protected IPlayer favorite = new ComputerPlayer();
	protected IPlayer opponent = null;

	protected VierGewinnt game = new VierGewinnt();

	public static final int NUMBER_OF_TESTS = 2000;
	public static final double WINNING_PERCENTAGE = 0.5;
	public static final double LOSING_PERCENTAGE = 0.01;

	protected int numberOfWins = 0;
	protected int numberOfDraws = 0;
	protected int numberOfLosses = 0;

	@Test
	public void testComputerPlayerFirstPlayer() {
		resetNumbers();
		for (int i = 0; i < NUMBER_OF_TESTS; i++) {
			IPlayer winner = game.getWinner(favorite, opponent);
			checkWinner(winner);
		}
		assertStatements();
	}

	@Test
	public void testComputerPlayerSecondPlayer() {
		resetNumbers();
		for (int i = 0; i < NUMBER_OF_TESTS; i++) {
			IPlayer winner = game.getWinner(opponent, favorite);
			checkWinner(winner);
		}
		assertStatements();
	}

	private void assertStatements() {
		assertEquals(numberOfWins + numberOfLosses + numberOfDraws, NUMBER_OF_TESTS);
		double winPercentage = (double) numberOfWins / NUMBER_OF_TESTS;
		double lossPercentage = (double) numberOfLosses / NUMBER_OF_TESTS;
		double drawPercentage = (double) numberOfDraws / NUMBER_OF_TESTS;
		System.out.println("Win  Percentage: " + winPercentage + "\nLoss Percentage: " + lossPercentage + "\nDraw Percentage: " + drawPercentage);
		assertTrue(winPercentage > WINNING_PERCENTAGE);
		assertTrue(lossPercentage < LOSING_PERCENTAGE);
		assertTrue(drawPercentage < (1 - WINNING_PERCENTAGE));
	}

	private void checkWinner(IPlayer winner) {
		if (winner.equals(favorite))
			numberOfWins++;
		else if (winner.equals(opponent))
			numberOfLosses++;
		else
			numberOfDraws++;
	}

	private void resetNumbers() {
		numberOfWins = 0;
		numberOfDraws = 0;
		numberOfLosses = 0;
	}

}