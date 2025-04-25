package delft;

import java.util.ArrayList;
import java.util.List;

public class Librarians {
    String name;
    boolean partTime;//to be able to know whether they are full or part

    //below are only applicable if the librarian is full
    double salary;//the salary if they are fulltimeint authCode;
    double totalSalaryWithdrawn;
    int authCode;
    List<Book> booksBought;
    Librarians(String name, int authCode, boolean partTime) {
        this.name = name;
        this.authCode = authCode;
        this.totalSalaryWithdrawn = 0;
        booksBought = new ArrayList<>();
        partTime = false;

    }
    //get salary
    public double getSalary(){
        return salary;
    }
    //this it to add the salary withdrawn to the total salary withdrawn
    public void addTotalSalaryWithdrawn(double salary){
        totalSalaryWithdrawn += salary;
    }
    //this function is to add a book that was bought succefully to the
    //librarians booksBought list
    public void addBookToBought(Book book){
        booksBought.add(book);
    }







}
