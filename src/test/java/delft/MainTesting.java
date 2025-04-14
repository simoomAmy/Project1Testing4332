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

    // test for invalid input on main menu
    @Test
    void testInvalidInputHandling() {
        String simulatedInput = "invalid\n0\n"; // Invalid input followed by exit
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertThat(output).contains("Invalid option. Please try again.");
    }

    // Test the user adds a book
    // Also test the catalog function
    @Test
    void testAddBookFunction() {
        // user input
        String simulatedInput = "8\nDinosaurs\nMr Dino Man\n2012\n9752\n40\nSci-Fi\n4\n0\n"; // Added exit option (0)
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("=== Library Management CLI ===");
        assertThat(output).contains("Books in Library:");
        assertThat(output).contains("Book 1 (ID: 1)");
        assertThat(output).contains("Book 2 (ID: 2)");
        assertThat(output).contains("Dinosaurs (ID: 40)");
    }

    // Test remove book
    @Test
    void testRemoveBookFunction() {
        // user input
        String simulatedInput = "9\n2\n4\n0\n"; // Added exit option (0)
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("=== Library Management CLI ===");
        assertThat(output).contains("Books in Library:");
        assertThat(output).contains("Book 1 (ID: 1)");
        assertThat(output).doesNotContain("Book 2 (ID: 2)");
    }

    // test empty library
    @Test
    void testEmptyLibraryScenario() {
        String simulatedInput = "9\n2\n9\n1\n4\n0\n"; // View catalog in an empty library, then exit
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertThat(output).contains("Books in Library:").doesNotContain("Book 1 (ID: 1)");
    }

    // test removing non-existent book
    @Test
    void testRemovingNonExistentBook() {
        String simulatedInput = "9\n99\n4\n0\n"; // Attempt to remove a non-existent book, then exit
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertThat(output).contains("Book not found.");
    }

    // testing exiting the program
    @Test
    void testExitProgram() {
        String simulatedInput = "0\n"; // Exit immediately
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertThat(output).contains("Exiting Library Management CLI.");
    }

    @Test
    void testingGetBookInfo() {
        // user input
        String simulatedInput = "1\nBook 1\n0\n"; // book info, book id 1
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("1. Name: Book 1");
        assertThat(output).contains("2. Author: Test");
        assertThat(output).contains("3. Genre: Fiction");
        assertThat(output).contains("4. Year: 2023");
        assertThat(output).contains("5. ISBN: ISBN1");
        assertThat(output).contains("6. BookID: 1");
        assertThat(output).contains("7. Is Book Available: true");
    }
    
    @Test
    void testingListAllMembers() {
        // user input
        String simulatedInput = "10\n0\n"; // prints all members and exits
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Name: Jacob");
        assertThat(output).contains("Email: test@gmail.com");
        assertThat(output).contains("ID: 1");
        assertThat(output).contains("Book 1 ID: 1");
    }

    @Test
    void testingReturnBook() {
        // user input
        String simulatedInput = "3\n1\n1\n0\n"; // returns book 1 from member 1
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Book: Book 1 returned successfully by: Jacob");
    }

    @Test
    void testingReturnBookNotAvailable() {
        // user input
        String simulatedInput = "3\n1\n456\n0\n"; // tries to remove book that doesn't exist
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Book not found.");
    }
}
