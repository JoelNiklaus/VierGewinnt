package tests.multipleTests;

import computerPlayerLevels.CPLevel5;
import computerPlayerLevels.CPLevel6;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by joelniklaus on 03.12.16.
 */
public class MultipleTestsLevel6 extends AbstractComputerPlayerTest {

	@Before
	public void setUp() {
		this.opponent = new CPLevel6();
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
