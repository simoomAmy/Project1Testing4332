package delft;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


  

}
