package cs2110;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PegCountsTest {

    @DisplayName("WHEN the correct code is guessed, THEN the returned array should have the "
            + "code length at index 0 (all red pegs) and 0 at index 1 (no white pegs).")
    @Test
    void testCorrectGuess() {
        assertArrayEquals(new int[]{4, 0}, Mastermind.pegCounts("1234", "1234"));
        assertArrayEquals(new int[]{6, 0}, Mastermind.pegCounts("123456", "123456"));
    }

    @DisplayName("WHEN the guess does not share any digits with the code, THEN the returned array "
            + "should contain 0s at both indices.")
    @Test
    void testNoPegs() {
        assertArrayEquals(new int[]{0, 0}, Mastermind.pegCounts("1234", "5566"));
        assertArrayEquals(new int[]{0, 0}, Mastermind.pegCounts("121256", "343434"));
    }


    @DisplayName("WHEN the guess shares one symbol in the same position with the code and disagrees "
            + "on all other symbols, THEN the returned array should have 1 at index 0 (one red peg) "
            + "and 0 at index 1 (no white pegs).")
    @Test
    void testOneRedPeg() {
        assertArrayEquals(new int[]{1, 0}, Mastermind.pegCounts("1234", "1566"));
        assertArrayEquals(new int[]{1, 0}, Mastermind.pegCounts("1234", "5266"));
        assertArrayEquals(new int[]{1, 0}, Mastermind.pegCounts("1234", "5536"));
        assertArrayEquals(new int[]{1, 0}, Mastermind.pegCounts("1234", "5564"));
    }

    @DisplayName("WHEN the guess results in one red peg and one white peg, THEN the correct peg "
            + "counts array is returned.")
    @Test
    void testAllColors() {
        assertArrayEquals(new int[]{1, 1}, Mastermind.pegCounts("1234", "1562"));
        assertArrayEquals(new int[]{1, 1}, Mastermind.pegCounts("1134", "1561"));
    }

    @DisplayName("WHEN the guess has more copies of a character than there are in the code, THEN "
            + "the number of white pegs given is limited by the number of copies of the "
            + "character in the code")
    @Test
    void testTooManyWhitePegs() {
        assertArrayEquals(new int[]{0, 2}, Mastermind.pegCounts("111166", "453211"));
        assertArrayEquals(new int[]{0, 3}, Mastermind.pegCounts("111122", "453211"));
        assertArrayEquals(new int[]{0, 4}, Mastermind.pegCounts("111222", "226116"));
    }

}
