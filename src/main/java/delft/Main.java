//Jacob 

package delft;


import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void clearScreen() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        try {
            if (operatingSystem.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix/Linux/Mac
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing screen");
        }
    }

    public static void main(String[] args) {
        Book testBook = new Book("Brandon", "Amelia", 2024, "AID91", 0, true, "Fiction");
        List<Book> Membooks = new ArrayList<>();
        Member mem = new Member("Kayla", "kayla@fakeEmai.com", 1234, Membooks);
        mem.addBorrowedBook(testBook);
        testBook.updateBookInfo();
    }


}
