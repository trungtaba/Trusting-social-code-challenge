package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestUtils {
    public static void assertTests(String expectedFile, String actualFile) {

        String expectedLine;
        try {
            BufferedReader expected = new BufferedReader(new FileReader(new File(expectedFile)));
            BufferedReader actual = new BufferedReader(new FileReader(new File(actualFile)));

            while ((expectedLine = expected.readLine()) != null) {
                String actualLine = actual.readLine();
                assertNotNull("ActualLine null", actualLine);
                assertEquals(expectedLine, actualLine);
            }
            assertNull("ActualLine not null", actual.readLine());
        } catch (IOException e) {
            System.out.println("Error: test util error");
            System.out.println(e.toString());
        }
    }
}
