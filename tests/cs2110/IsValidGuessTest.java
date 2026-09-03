package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IsValidGuessTest {

    /* *******************************************************************************************
     * The code at the top of this file is used to capture the console output, so we can check   *
     * that it is correct.                                                                       *
     ******************************************************************************************* */

    /**
     * The original `System.out`.
     */
    PrintStream systemOut;

    /**
     * Replacement for `System.out` during test execution.
     */
    PrintStream out;
    ByteArrayOutputStream outBytes;

    @BeforeEach
    void setUpSimulator() {
        outBytes = new ByteArrayOutputStream();
        out = new PrintStream(outBytes);
        systemOut = System.out;
        System.setOut(out);
        clearOutputStream();
    }

    /**
     * Resets the output stream so we can capture the print output from processing one command
     */
    void clearOutputStream() {
        out.flush();
        outBytes.reset();
    }

    @AfterEach
    void restoreOutput() {
        out.close();
        System.setOut(systemOut);
    }

    /**
     * Asserts that the line captured in the OutputStream is equal to `expected`, including a 
     * trailing newline character.
     */
    void assertOutput(String expected) {
        out.flush();
        assertEquals(expected + System.lineSeparator(), outBytes.toString());
        outBytes.reset();
    }

    /**
     * Asserts that no console output has been captured in the OutputStream.
     */
    void assertNoOutput() {
        out.flush();
        assertEquals("", outBytes.toString());
        outBytes.reset();
    }

    /* *******************************************************************************************
     * Here is where the tests begin.
     ******************************************************************************************* */

    @DisplayName("WHEN a valid guess is made with `codeLength == 4` and `alphabetSize == 6`, THEN "
            + "`isValidGuess()` returns `true` and nothing is printed.")
    @Test
    void testValidGuessDefaultParams() {
        boolean b = Mastermind.isValidGuess("1234", 4, 6);
        assertTrue(b);
        assertNoOutput();
    }

    @DisplayName("WHEN a guess is made with too few symbols, THEN `isValidGuess()` returns "
            + "`false` and prints the correct error message.")
    @Test
    void testGuessTooShort() {
        boolean b = Mastermind.isValidGuess("123", 4, 6);
        assertFalse(b);
        assertOutput("Your guess must have 4 symbols. Try again.");
    }

    @DisplayName("WHEN a guess is made with a non-digit symbol, THEN `isValidGuess()` returns "
            + "`false` and prints the correct error message.")
    @Test
    void testGuessNonDigit() {
        boolean b = Mastermind.isValidGuess("12E4", 4, 6);
        assertFalse(b);
        assertOutput("Your guess cannot include the symbol 'E'. Try again.");
    }

        // TODO 2: Add additional test cases to cover the specifications of the `isValidGuess()`
        //  method. All of your tests should include descriptive @DisplayNames and method names.
    // We created an extra 6 tests.


    @DisplayName("WHEN a guess is made with a letter symbol in the first position, "
            + "THEN `isValidGuess()` returns `false` and prints the correct error message.")
    @Test
        // This first test checks that another different english character and in a different spot does not succeed.
    void testGuessNonDigit2() {
        boolean b = Mastermind.isValidGuess("G434", 4, 6);
        assertFalse(b);
        assertOutput("Your guess cannot include the symbol 'G'. Try again.");
    }
    @DisplayName("WHEN a guess is made with a special character that is <48, "
            + "THEN `isValidGuess()` returns `false` and prints the correct error message.")
    @Test
        // This test tests a non-english character, and also makes sure that if the unicode is less than the numbers,
        // the code still identifies it false.
    void testSpecialChar() {
        boolean b = Mastermind.isValidGuess("333*45", 6, 6);
        assertFalse(b);
        assertOutput("Your guess cannot include the symbol '*'. Try again.");
    }
    @DisplayName("WHEN a guess is made with multiple non-numbers, "
            + "THEN `isValidGuess()` returns `false` and prints the correct error message regarding first.")
    @Test
        // This test makes sure the first illegal character is the one displayed in the error message.
    void catchFirstIllegal() {
        boolean b = Mastermind.isValidGuess("12(39)]", 7, 8);
        assertFalse(b);
        assertOutput("Your guess cannot include the symbol '('. Try again.");
    }
    @DisplayName("WHEN a guess is made with 0 and alphabetSize is not 10, "
            + "THEN `isValidGuess()` returns `false` and prints the correct error message with 0 as the issue.")
    @Test
        // This test makes sure that if alphabetSize is not 10, then 0 is not an allowed symbol.
    void checkForZeroFalse() {
        boolean b = Mastermind.isValidGuess("120", 3, 5);
        assertFalse(b);
        assertOutput("Your guess cannot include the symbol '0'. Try again.");
    }
    @DisplayName("WHEN a guess is made with 0 and alphabetSize is 10, "
            + "THEN `isValidGuess()` returns `true`.")
    @Test
        // This test makes sure that if alphabetSize is 10, then 0 is an allowed symbol.
    void checkForZeroTrue() {
        boolean b = Mastermind.isValidGuess("0102000", 7, 10);
        assertTrue(b);
    }
    @DisplayName("WHEN a valid guess is made with a number equal to alphabetSize and `alphabetSize == 9`,"
            + " THEN isValidGuess()` returns `true` and nothing is printed.")
    @Test
        // This test checks that if the guess number equals the alphabetSize (for alphabetSize <10), then
        // the code works.
    void testNumberEqualToAlphabetSizeLessThan10() {
        boolean b = Mastermind.isValidGuess("9", 1, 9);
        assertTrue(b);
    }
    @DisplayName("WHEN a valid guess is made with a number greater than alphabetSize and `alphabetSize == 8`,"
            + " THEN isValidGuess()` returns `true` and nothing is printed.")
    @Test
        //This test checks that if the guess number is one greater than the alphabet size, then the code fails.
    void testNumberGreaterThanAlphabetSize() {
        boolean b = Mastermind.isValidGuess("9", 1, 8);
        assertFalse(b);
    }
}

}
