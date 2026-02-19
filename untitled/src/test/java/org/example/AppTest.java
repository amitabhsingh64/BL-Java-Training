package org.example;

// 1. Import the modern Jupiter annotations and assertions
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
class AppTest { // 2. No need to extend TestCase, and it doesn't need to be public!

    // 3. The constructor and suite() methods are completely deleted.

    /**
     * Rigorous Test :-)
     */
    @Test // 4. This annotation tells JUnit this is a test method
    void testApp() {
        assertTrue(true);
    }
}