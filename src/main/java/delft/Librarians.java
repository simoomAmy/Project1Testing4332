package delft;

import java.util.ArrayList;
import java.util.List;

public class Librarians {
    String name;
    boolean isVolunteer;
    //below are only applicable if the librarian is full
    double salary;//the salary if they are fulltimeint authCode;
    double totalSalaryWithdrawn;
    int authCode;
    List<Book> booksPurchased;

    public Librarians(String name, int authCode, boolean isVolunteer) {
        this.name = name;
        this.isVolunteer = isVolunteer;
        this.authCode = authCode;

        if (!isVolunteer) {
            this.salary = 39000.0;
            this.totalSalaryWithdrawn = 0.0;
            this.booksPurchased = new ArrayList<>();
        } else {
            this.salary = 0.0;
            this.totalSalaryWithdrawn = 0.0;
            this.booksPurchased = null;
        }
    }

    //get salary

    public double getSalary() {
        return salary;
    }

    //this it to add the salary withdrawn to the total salary withdrawn
    public void addTotalSalaryWithdrawn(double salary){
        totalSalaryWithdrawn += salary;
        salary -= salary; //Amy- this seems to not follow the idea of Librarian as LibraryAccounts is the one that should edit any amounts of the accounts
    }
    //this function is to add a book that was bought succefully to the
    //librarians booksBought list
    public void addBookToBought(Book book){
        booksPurchased.add(book);
    }

}




