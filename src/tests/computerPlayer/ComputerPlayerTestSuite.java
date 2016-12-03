package tests.computerPlayer;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

/**
 * Runs all tests that compare the ComputerPlayer other to levels
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ComputerPlayerTestLevel1.class, ComputerPlayerTestLevel2.class, ComputerPlayerTestLevel3.class,
		ComputerPlayerTestLevel4.class, ComputerPlayerTestLevel5.class, ComputerPlayerTestLevel6.class, ComputerPlayerTestLevel7.class, ComputerPlayerTestLevel8.class, ComputerPlayerTestCurrent.class})
public class ComputerPlayerTestSuite {


}
