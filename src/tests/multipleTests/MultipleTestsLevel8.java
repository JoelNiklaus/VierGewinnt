package tests.multipleTests;

import computerPlayerLevels.CPLevel8;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by joelniklaus on 03.12.16.
 */
public class MultipleTestsLevel8 extends AbstractComputerPlayerTest {

	@Before
	public void setUp() {
		this.opponent = new CPLevel8();
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
