package delft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.*;

public class LibrarianTest {
    
    @Test
    // Specification Testing: Simple test to ensure that the Librarians salary is returned (Easy)
    public void testGetSalary(){
        Librarians test = new Librarians("Brandon", 101,false);
        double salary = test.getSalary();
        assertEquals(salary, 39000.0);
    }
    
    @Test
    // Specification Testing: Simple test that when a librarian's salary is withdrawn (Easy)
    public void addTotalSalaryWithdrawn(){
        Librarians test = new Librarians("Brandon", 101,false);
        double currentSalary = test.getSalary();
        test.addTotalSalaryWithdrawn(10000.0);
        double expected = currentSalary - 10000.0;
        assertEquals(expected, currentSalary);
    }

    @Test
    // Specification Testing: Simple test to see if it adds a book that was bought succefully book list
    void testAddBookToBought() {
        Librarians librarian = new Librarians("Brandon", 101, false);
        Book book = new Book("Bought Book", "Author", 2025, "ISBN3", 1, true, "Fiction");

        assertNotNull(librarian.booksPurchased);
        assertTrue(librarian.booksPurchased.isEmpty());

        librarian.addBookToBought(book);

        assertEquals(1, librarian.booksPurchased.size());
    }

  

}
