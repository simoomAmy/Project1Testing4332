package delft;

import java.util.ArrayList;
import java.util.List;

public class Librarians {
    String name;
    int authCode;

    // full time 
    double salary;
    double totalSalaryWithdrawn; 
    List<Book> booksPurchased; 

    public Librarians(String name, int authCode) {
        this.name = name;
        this.authCode = authCode;
        this.salary = 39000.0; 
        this.totalSalaryWithdrawn = 0.0;
        this.booksPurchased = new ArrayList<>();
       
    }

    //get salary
    public double getSalary(){
        return salary;
    }
    //this it to add the salary withdrawn to the total salary withdrawn
    public void addTotalSalaryWithdrawn(double salary){
        totalSalaryWithdrawn += salary;
        this.salary -= salary;
    }
    //this function is to add a book that was bought succefully to the
    //librarians booksBought list
    public void addBookToBought(Book book){
        booksPurchased.add(book);
    }

}

