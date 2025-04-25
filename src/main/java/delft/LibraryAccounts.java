package delft;

import static delft.Purchasing.bookPrice;

public class LibraryAccounts {
    double cashBalance;
    public LibraryAccounts() {
        cashBalance = 39000.00;
    }

    public double getCashBalance() {
        return cashBalance;
    }


    //cash with the specified balance specified

    //this is to add the donations the checking for if full time or part time librarians
    //will be left to the CLI
    public boolean deposit(double amount) {
        if(amount < 0){
            return false;
        }else {
            cashBalance += amount;
            return true;
        }
    }

    //this if to withdraw the amount of the must not allow the balance to go below 0 since the library cant
    //spend more than it has and returns a boolean to check if the transaction was successful
    public boolean withdraw(double amount) {
        if((cashBalance - amount) >= 0) {
            cashBalance -= amount;
            return true;
        }else{
            return false;
        }
    }

    //this is for the librarians to withdraw their salary
    //the checking for full or volunteer is in the cli
    public boolean withdrawalSalary(Librarians librarian) {
        double salary = librarian.getSalary();//get salary
        boolean withdrawalDone = withdraw(salary);
        if(withdrawalDone) {//checks if withdraw was possible
            librarian.addTotalSalaryWithdrawn(salary);//add the salary withdrawn to the total of the librarian
            return true;
        }else {
            return false;//not enough money
        }
    }
    //this is to add buy the new book
    //the book will be created and added to the librarians library in the librarians class and be added to the list
    //the logic of wether the book is added to the list should be in the cli since
    //if there is not enough money and the purchase fails it will not add it to the
    //the catalog/list of books in library
    public boolean orderNewBook(Librarians librarians){
        double bookprice = bookPrice();
        boolean withdrawalDone = withdraw(bookprice);
        if(withdrawalDone) {
            return true;
        }else {
            return false;
        }
    }

}
