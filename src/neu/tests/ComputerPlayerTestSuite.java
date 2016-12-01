package neu.tests;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;

/**
 * Runs all tests that compare the ComputerPlayer other to levels
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ComputerPlayerTestLevel1.class, ComputerPlayerTestLevel2.class, ComputerPlayerTestLevel3.class,
ComputerPlayerTestLevel4.class})
public class ComputerPlayerTestSuite {


}
