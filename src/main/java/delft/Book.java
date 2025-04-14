//Brandon (Testing)

package delft;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Book {

    public String name;
    public String author;
    public int year;
    public String isbn;
    public int bookID;
    public boolean isAvailable;
    public String genre;

    public Book(String name,
                String author,
                int year,
                String isbn,
                int bookID,
                boolean isAvailable,
                String genre
    )
    {
        this.name = name;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
        this.bookID = bookID;
        this.isAvailable = isAvailable;
        this.genre = genre;
    }

    // Specification (Unit Test)
    public boolean checkAvailability() {
        return this.isAvailable;
    }


    // Mocking: Verifying changed field 
    public void updateBookInfo(){
        Scanner scan = new Scanner(System.in);
        boolean running = true;
        while (running) {
            List<String> options = new ArrayList<>(Arrays.asList(
                    String.format("[1]: Author -> (%s)", this.author),
                    String.format("[2]: Genre -> (%s)", this.genre),
                    String.format("[3]: Year -> (%d)", this.year),
                    String.format("[4]: ISBN -> (%s)", this.isbn),
                    String.format("[5]: BookID -> (%s)", this.bookID),
                    String.format("[6]: Availability -> (%s)", this.isAvailable ? "Available" : "Not Available"),
                    "[7]: Exit"
            ));

            // Display the options
            for (String item : options) {
                System.out.println(item);
            }

            System.out.print("Select a field below to update: ");

            // Get the user input
            int option = Integer.parseInt(scan.next());

            // Handle the user's choice
            switch (option) {
                case 1:
                    String newAuthor = updateField(scan, "Author", this.author);
                    this.author = newAuthor;
                    break;
                case 2:
                    String newGenre = updateField(scan, "Genre", this.genre);
                    this.genre = newGenre;
                    break;
                case 3:
                    String newStringYear = updateField(scan, "Year", String.valueOf(this.year));
                    int newYear = Integer.parseInt(newStringYear);
                    this.year = newYear;
                    break;
                case 4:
                    String newISBN = updateField(scan, "ISBN", this.isbn);
                    this.isbn = newISBN;
                case 5:
                    String newStringID = updateField(scan, "BookID", String.valueOf(this.bookID));
                    int newID = Integer.parseInt(newStringID);
                    this.bookID = newID;
                    break;
                case 6:
                    boolean prev = this.isAvailable;
                    this.isAvailable = !prev;
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
        scan.close();
    }

    // Mock: Verify 
    public void getBookInfo(){
        String info = String.format(
                "1. Name: %s\n" +
                "2. Author: %s\n" +
                "3. Genre: %s\n" +
                "4. Year: %d\n" +
                "5. ISBN: %s\n" +
                "6. BookID: %s\n" +
                "7. Is Book Available: %s\n",
                this.name,
                this.author,
                this.genre,
                this.year,
                this.isbn,
                this.bookID,
                this.checkAvailability()
        );
        System.out.println(info);
    }
    

    // Specification
    public static String updateField(Scanner scan, String option, String prev){
        Main.clearScreen();
        System.out.printf("Updating %s%n", option);
        System.out.printf("  - Old Value: %s%n", prev);
        System.out.printf("  - Insert New Value: ");
        String newValue = scan.next();
        return newValue;
    }
}