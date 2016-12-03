package tests.multipleTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all tests that compare the ComputerPlayer other to levels
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({MultipleTestsLevel1.class, MultipleTestsLevel2.class, MultipleTestsLevel3.class, MultipleTestsLevel4.class, MultipleTestsLevel5.class, MultipleTestsLevel6.class, MultipleTestsLevel7.class, MultipleTestsLevel8.class, MultipleTestsCurrent.class})
public class MultipleTestsTestSuite {


}