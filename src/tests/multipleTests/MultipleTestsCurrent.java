package tests.multipleTests;

import org.junit.Before;
import org.junit.Test;
import vierGewinnt.ComputerPlayer;

/**
 * Created by joelniklaus on 03.12.16.
 */
public class MultipleTestsCurrent extends AbstractComputerPlayerTest {

	@Before
	public void setUp() {
		this.opponent = new ComputerPlayer();
	}

	@Test
	public void testComputerPlayerFirstPlayer() {
		super.testComputerPlayerFirstPlayer();
	}

	@Test
	public void testComputerPlayerSecondPlayer() {
		super.testComputerPlayerSecondPlayer();
	}
}
