package test;

import main.Config;
import main.PhoneProcessing;
import org.junit.Test;
import static test.utils.TestUtils.assertTests;

public class PhoneProcessingTest {
    private Config config= Config.getInstance();

    @Test
    public void test_1() {
        new PhoneProcessing("test_1.csv").processing();
        assertTests(config.getExpected()+"test_1_result.csv", config.getOutputDir()+"test_1_output.csv");
    }

    @Test
    public void test_2() {
        new PhoneProcessing("test_2.csv").processing();
        assertTests(config.getExpected()+"test_2_result.csv", config.getOutputDir()+"test_2_output.csv");
    }

    @Test
    public void test_3() {
        new PhoneProcessing("test_3.csv").processing();
        assertTests(config.getExpected()+"test_3_result.csv", config.getOutputDir()+"test_3_output.csv");
    }

    /*
    default
     */
    @Test
    public void test4() {
        new PhoneProcessing("test_4.csv").processing();
        assertTests(config.getExpected()+"test_4_result.csv", config.getOutputDir()+"test_4_output.csv");
    }

    /*
    single entry
     */
    @Test
    public void test5() {
        new PhoneProcessing("test_5.csv").processing();
        assertTests(config.getExpected()+"test_5_result.csv", config.getOutputDir()+"test_5_output.csv");
    }

    /*
    single entry without deactivation date
     */
    @Test
    public void test6() {
        new PhoneProcessing("test_6.csv").processing();
        assertTests(config.getExpected()+"test_6_result.csv", config.getOutputDir()+"test_6_output.csv");
    }

    /*
    single entry with deactivation daate out of range
     */
    @Test
    public void test7() {
        new PhoneProcessing("test_7.csv").processing();
        assertTests(config.getExpected()+"test_7_result.csv",config.getOutputDir()+ "test_7_output.csv");
    }

    /*
    test random
     */
    @Test
    public void test8() {
        new PhoneProcessing("test_8.csv").processing();
        assertTests(config.getExpected()+"test_8_result.csv",config.getOutputDir()+ "test_8_output.csv");
    }

    /*
    test random 2
     */
    @Test
    public void test9() {
        new PhoneProcessing("test_9.csv").processing();
        assertTests(config.getExpected()+"test_9_result.csv",config.getOutputDir()+ "test_9_output.csv");
    }
}
