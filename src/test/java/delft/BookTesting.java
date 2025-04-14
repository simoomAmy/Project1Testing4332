package delft;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;
import org.junit.jupiter.api.*;

class BookTest {


    private Book book;

    @BeforeEach
    void setup() {
        // Global book (Ensures consistency with needed valid books)
        book = new Book("Brandon", "John Doe", 2008, "012345678", 0, true, "Testing");
    }

    @Test
    // Testing the availability of a book
    void testingAvailability() {
        assertTrue(book.checkAvailability());
        book.isAvailable = false;
        assertFalse(book.checkAvailability());
    }

    @Test
    // Simulating User Input and ensuring the fields are updated
    void testUpdateBookInfo() {
        // Simulate user input: selecting options 1 to 6, with new values for each field
        String simulatedInput = String.join("\n",
                "1", "New Author",
                "2", "New Genre",
                "3", "2000",
                "4", "2222",
                "5", "42",
                "6",
                "7"
        );

        // Simulating User Input
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        book.updateBookInfo();

        assertEquals("New Author", book.author);
        assertEquals("New Genre", book.genre);
        assertEquals(2000, book.year);
        assertEquals("2222", book.isbn);
        assertEquals(42, book.bookID);
        assertFalse(book.checkAvailability());
    }

    @Test

    // Testing when a user provides an integer value below the threshold
    void testingUserInsertsBelowMinimum() {
        // Simulates invalid user input: "0" (not a valid option 1-7)
        String invalidInput = "0";
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(invalidInput.getBytes()));
        assertThrows(IllegalArgumentException.class, book::updateBookInfo);
        System.setIn(originalSystemIn);
    }

    @Test
    // Testing when a user provides an integer value above the threshold
    void testingUserInsertsAboveMaximum() {
        // Simulates invalid user input: "8" (not a valid option 1-7)
        String invalidInput = "8";
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(invalidInput.getBytes()));
        assertThrows(IllegalArgumentException.class, book::updateBookInfo);
        System.setIn(originalSystemIn);
    }

    @Test
    // Testing when a user updates the author field
    void testUpdateAuthor() {
        // Simulating User Input
        String input = "Jane Doe";
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call the method with actual System.in
        String option = "Author";
        String prev = book.author;
        String result = book.updateField(new Scanner(System.in), option, prev);

        assertEquals(input.trim(), result);
        System.setIn(originalSystemIn);
    }

    @Test
    // Testing when a user updates the genre field
    void testUpdateGenre() {
        // Simulating User Input
        String input = "Fiction";
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String option = "Genre";
        String prev = book.author;
        String result = book.updateField(new Scanner(System.in), option, prev);

        assertEquals(input.trim(), result);
        System.setIn(originalSystemIn);
    }

    @Test
    // Testing when a user updates the isbn field
    void testUpdateISBN() {
        // Simulating User Input
        String input = "0123";
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String option = "ISBN";
        String prev = book.author;
        String result = book.updateField(new Scanner(System.in), option, prev);

        assertEquals(input.trim(), result);
        System.setIn(originalSystemIn);
    }

    @Test
    // Testing when a user updates the bookID field
    void testUpdateBookID() {
        // Simulating User Input
        String input = "1";
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String option = "BookID";
        String prev = book.author;
        String result = book.updateField(new Scanner(System.in), option, prev);

        assertEquals(input.trim(), result);
        System.setIn(originalSystemIn);
    }

    @Test
    // Testing that the book correctly displays the desired format and correct book
    void testingGetBookInfo() {
        PrintStream out = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        System.setOut(ps);

        book.getBookInfo();

        System.out.flush();
        System.setOut(out);
        String output = bos.toString();

        // String matching and ensuring that the output contains the desired information
        assertThat(output).contains(book.name);
        assertThat(output).contains(book.author);
        assertThat(output).contains(book.genre);
        assertThat(output).contains(Integer.toString(book.year));
        assertThat(output).contains(book.isbn);
        assertThat(output).contains(Integer.toString(book.bookID));
        assertThat(output).contains(Boolean.toString(book.checkAvailability()));
    }

    @Test
    // Testing Invalid fields when creating a book
    void testInvalidBookFields() {
        String name = "user";
        String author = "John Doe";
        int year = 2000;
        String isbn = "012345678";
        String genre = "Testing";
        boolean checkAvailability = true;
        int bookID = 1;

        // Test when the name is empty
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("", author, year, isbn, 1, true, genre );
        });

        // Test when the author is empty
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(name, "", year, isbn, 1, true, genre );
        });

        // Test when the genre is empty
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(name, author, year, isbn, 1, true, "" );
        });

        // Test when the name is null
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(null, author, year, isbn, 1, true, genre );
        });

        // Test when the author is empty
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(name, null, year, isbn, 1, true, genre );
        });

        // Test when the genre is empty
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(name, author, year, isbn, 1, true, null);
        });
    }
}
