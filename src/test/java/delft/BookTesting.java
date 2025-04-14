// Brandon Walton

package delft;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;
import java.io.*;
import org.junit.jupiter.api.*;

class BookTest {

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

        // Setting up the terminal for the strings
        PrintStream out = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        System.setOut(ps);

        book.getBookInfo();

        //
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


}
