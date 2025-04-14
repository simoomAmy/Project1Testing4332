// Brandon Walton

package delft;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.io.*;
import org.junit.jupiter.api.*;

class BookTest {

    private Scanner scan;
    private Book book;


    @BeforeEach
    void setup() {
        book = new Book("Brandon", "John Doe", 2008, "012345678", 0, true, "Testing");
    }

    // [Specification Test] - Verifies that the checkAvailability method returns true or false (based on the availability)
    @Test
    void testingAvailability() {
        assertTrue(book.checkAvailability());
        book.isAvailable = false;
        assertFalse(book.checkAvailability());
    }

    @Test
    void testingGetBookInfo(){
        // Setting up the output capture for System.out to redirect printed content
        PrintStream out = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        System.setOut(ps);

        book.getBookInfo();

        // Restore the original System.out after capturing the output
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
    void testUpdateField() {
        String input = "Jane Doe";
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call the method with actual System.in
        String option = "Author";
        String prev = book.author;
        String result = Book.updateField(new Scanner(System.in), option, prev);

        assertEquals(input.trim(), result);
        System.setIn(originalSystemIn);
    }

    @Test
    void testBookConstructorWithInvalidFields() {
        // Test when the name is empty
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "John Doe", 2008, "012345678", 1, true, "Fiction");
        });

        // Test when the author is empty
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("The Great Book", "", 2008, "012345678", 1, true, "Fiction");
        });

        // Test when the genre is empty
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("The Great Book", "John Doe", 2008, "012345678", 1, true, "");
        });

        // Test when the name is null
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(null, "John Doe", 2008, "012345678", 1, true, "Fiction");
        });

        // Test when the author is null
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("The Great Book", null, 2008, "012345678", 1, true, "Fiction");
        });

        // Test when the genre is null
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("The Great Book", "John Doe", 2008, "012345678", 1, true, null);
        });
    }








}
