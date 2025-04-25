package delft;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.*;
import org.junit.jupiter.api.*;

class MainTesting {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    // --- helper definitions for dynamic input strings ---
    private static final String EXIT_CODE = "0\n";
    private static final String AUTH_PASS = "1234\n";
    private static final String AUTH_FAIL = "23051425\n";
    private static final String BOOK_SUBMENU = "1\n";
    private static final String MEMBER_SUBMENU = "2\n";
    private static final String FIND_BOOK_INFORMATION = "1\n";
    private static final String CHECKOUT_BOOK = "2\n";
    private static final String RETURN_BOOK = "3\n";
    private static final String ADD_BOOK = "4\n";
    private static final String REMOVE_BOOK = "5\n";
    private static final String UPDATE_BOOK_INFORMATION = "6\n";
    private static final String VIEW_CATALOG = "7\n";
    private static final String ADD_MEMBER = "1\n";
    private static final String REMOVE_MEMBER = "2\n";
    private static final String LIST_MEMBERS = "3\n";
    private static final String UPDATE_MEMBER_INFORMATION = "4\n";
    private static final String LIBRARY_SUBMENU = "3\n";


    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    // Unit Test
    // test for invalid input on main menu
    @Test
    void testInvalidInputHandling() {
        String simulatedInput = AUTH_PASS + "invalid\n" + EXIT_CODE; // Invalid input followed by exit
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertThat(output).contains("Invalid option. Please try again.");
    }

    // Unit Test
    // Test the user adds a book
    // Also test the catalog function
    @Test
    void testAddBookFunction() {
        // user input
        String simulatedInput = AUTH_PASS + BOOK_SUBMENU + ADD_BOOK + "Dinosaurs\nMr Dino Man\n2012\n9752\n40\nSci-Fi\n4\n" + EXIT_CODE; 
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

    // Unit Test
    @Test
    void testRemoveBookFunction() {
        String BOOK_ID = "2\n"; // ID of the book to be removed
        // user input
        String simulatedInput = AUTH_PASS + BOOK_SUBMENU + REMOVE_BOOK + BOOK_ID + BOOK_SUBMENU + VIEW_CATALOG + EXIT_CODE; 
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

    // Unit Test
    @Test
    void testEmptyLibraryScenario() {
        // book ids to be removed
        String REMOVE_BOOK_ID_1 = "1\n";
        String REMOVE_BOOK_ID_2 = "2\n";
        String simulatedInput = AUTH_PASS + BOOK_SUBMENU + REMOVE_BOOK + REMOVE_BOOK_ID_1 + BOOK_SUBMENU + REMOVE_BOOK + REMOVE_BOOK_ID_2 + BOOK_SUBMENU + VIEW_CATALOG + EXIT_CODE; // View catalog in an empty library, then exit
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertThat(output).contains("Books in Library:").doesNotContain("Book 1 (ID: 1)");
    }

    // Unit Test
    @Test
    void testRemovingNonExistentBook() {
        String FAKE_BOOK_ID = "999\n"; // ID of a non-existent book
        // Attempt to remove a non-existent book, then exit
        String simulatedInput = AUTH_PASS + BOOK_SUBMENU + REMOVE_BOOK + FAKE_BOOK_ID + BOOK_SUBMENU + VIEW_CATALOG + EXIT_CODE; // Attempt to remove a non-existent book
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertThat(output).contains("Book not found.");
    }

    // Unit Test
    @Test
    void testExitProgram() {
        String simulatedInput = AUTH_PASS + EXIT_CODE; // Exit immediately
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertThat(output).contains("Exiting Library Management CLI.");
    }

    // Unit Test
    @Test
    void testingGetBookInfo() {
        String BOOK_NAME = "Book 1\n"; // Name of the book to be retrieved
        String simulatedInput = AUTH_PASS + BOOK_SUBMENU + FIND_BOOK_INFORMATION + BOOK_NAME + EXIT_CODE; // book info, book id 1
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
    
    // Unit Test
    @Test
    void testingListAllMembers() {
        // user input
        String simulatedInput = AUTH_PASS + MEMBER_SUBMENU + LIST_MEMBERS + EXIT_CODE; // prints all members and exits
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
    
    // Unit Test
    @Test
    void testingReturnBook() {
        String BOOK_ID = "1\n"; // ID of the book to be returned
        String MEMBER_ID = "1\n"; // ID of the member returning the book
        // user input
        String simulatedInput = AUTH_PASS + BOOK_SUBMENU + RETURN_BOOK + MEMBER_ID + BOOK_ID + EXIT_CODE; // returns book 1 from member 1
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Book: Book 1 returned successfully by: Jacob");
    }

    // Unit Test
    @Test
    void testingReturnBookNotAvailable() {

        String BOOK_ID = "456\n"; // ID of the book to be returned
        String MEMBER_ID = "1\n"; // ID of the member returning the book
        // user input
        String simulatedInput = AUTH_PASS + BOOK_SUBMENU + RETURN_BOOK + MEMBER_ID + BOOK_ID + EXIT_CODE; // tries to remove book that doesn't exist
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Book not found.");
    }

    // Unit Test
    @Test
    void testingCheckingOutWithBook() {
        String BOOK_ID = "2\n"; // ID of the book to be checked out
        String MEMBER_ID = "1\n"; // ID of the member returning the book
        
        // user input
        String simulatedInput = AUTH_PASS + BOOK_SUBMENU + CHECKOUT_BOOK + MEMBER_ID + BOOK_ID + EXIT_CODE; 
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Book: Book 2 checked out successfully to: Jacob");
        assertThat(output).contains("Book 2 ID: 2");
        assertThat(output).doesNotContain("Book not found.");
    }

    // Unit Test
    @Test
    void testingCheckingOutWithoutBook() {
        // user input
        String simulatedInput = "2\n1\n345\n10\n0\n"; 
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Book not found.");
        assertThat(output).doesNotContain("ID: 345");
    }

    // Unit Test
    @Test
    void testingAddMember() {
        // user input
        String NAME = "Emily\n"; // Name of the new member
        String EMAIL = "eshapi@cox.net\n"; // Email of the new member
        String ID = "10\n"; // ID of the new member
        String simulatedInput = AUTH_PASS + MEMBER_SUBMENU + ADD_MEMBER + NAME + EMAIL + ID + EXIT_CODE; // adds new member than exits
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Member: Emily added successfully.");
        
    }

    // Unit Test
    @Test
    void testingRemoveMember() {

        String NAME = "Emily\n"; // Name of the new member
        String EMAIL = "eshapi@cox.net\n"; // Email of the new member
        String ID = "10\n"; // ID of the new member
        // user input
        String simulatedInput = AUTH_PASS + MEMBER_SUBMENU + ADD_MEMBER + NAME + EMAIL + ID + MEMBER_SUBMENU + REMOVE_MEMBER + ID + EXIT_CODE; // adds new member then removes member
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Member with ID 10 removed successfully.");
        
    }


    // ---------- Test Written in Proj2 ----------
    
    // Test to make sure that a Librarian in the system can access the CLI
    @Test
    void authorizeLibrarianPass() {

        // user input
        String simulatedInput = AUTH_PASS + EXIT_CODE; // Authorizes Librarian, then exits
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Insert Auth Code: ");
        assertThat(output).contains("Access Granted.");
        assertThat(output).contains("=== Library Management CLI ===");
        assertThat(output).doesNotContain("Auth Code Not Found. Access Denied.");
        
    }

    // Test to make sure that soneone without the proper authcode cannot access the CLI
    @Test
    void authorizeLibrarianFail() {

        // user input
        String simulatedInput = AUTH_FAIL + AUTH_FAIL + AUTH_FAIL; // Authorization Failed
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Insert Auth Code: ");
        assertThat(output).contains("Auth Code Not Found. Access Denied.");
        assertThat(output).contains("Too many failed attempts. Exiting program.");
    }

    // Test to make sure that you can access CLI if accidentally enter the wrong code once
    @Test
    void authorizeLibrarianPassAfterIncorrectCode() {

        // user input
        String simulatedInput = AUTH_FAIL + AUTH_PASS + EXIT_CODE; 
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // main function call
        Main.main(new String[]{});

        // captures output
        String output = outputStream.toString();

        // verifies output contains expected
        assertThat(output).contains("Insert Auth Code: ");
        assertThat(output).contains("Auth Code Not Found. Access Denied.");
        assertThat(output).contains("=== Library Management CLI ===");
        assertThat(output).doesNotContain("Too many failed attempts. Exiting program.");
    }

}
