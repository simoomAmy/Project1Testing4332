package delft;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.*;
import org.junit.jupiter.api.*;

class MainTesting {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testMainFunction() {
        // Simulate user input for the main menu
        String simulatedInput = "4\n0\n"; // Option 4 (Show Full Catalog), then exit (Option 0)
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Call the main function
        Main.main(new String[]{});

        // Capture the output
        String output = outputStream.toString();

        // Verify the output contains expected text
        assertThat(output).contains("=== Library Management CLI ===");
        assertThat(output).contains("Books in Library:");
        assertThat(output).contains("Book 1 (ID: 1)");
        assertThat(output).contains("Book 2 (ID: 2)");
    }
}
