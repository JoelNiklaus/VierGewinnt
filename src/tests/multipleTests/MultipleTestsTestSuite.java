package tests.multipleTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all tests that compare the ComputerPlayer other to levels
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({MultipleTestsLevel1.class, MultipleTestsLevel7.class, MultipleTestsLevel8.class, MultipleTestsCurrent.class})
public class MultipleTestsTestSuite {


}
