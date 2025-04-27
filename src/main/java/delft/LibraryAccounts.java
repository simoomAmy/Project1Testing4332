package delft;

import static delft.Purchasing.bookPrice;

public class LibraryAccounts {
    double cashBalance;
    public LibraryAccounts() {
        cashBalance = 39000.00;//the cash balance is initialized when a class is opened
    }

    public double getCashBalance() {
        return this.cashBalance; //returns the current cash balance
    }


    //cash with the specified balance specified

    //this is to add the donations the checking for if full time or part time librarians
    //will be left to the CLI
    public boolean deposit(double amount) {//add the given amount to an account and returns a boolean to confirm if the deposit was made
        if(amount < 0){//this checks the amount is negative and if it is make it false
            return false;
        }else {//if it is positive then it will be added to the cash balance
            cashBalance = cashBalance + amount;
            return true;//returns true to confirm it is added
        }
    }

    //this if to withdraw the amount of the must not allow the balance to go below 0 since the library cant
    //spend more than it has and returns a boolean to check if the transaction was successful
    //also negative values will be considered as null
    public boolean withdraw(double amount) {//this is to take out the given amount from the cash balance without allowing it to go into negative
        if(amount < 0){//this checks the amount is positive
            return false;
        }
        if((cashBalance - amount) >= 0) {//this checks that if the amount is taken out if it wont go below zero
            this.cashBalance = this.cashBalance - amount;//if the balance is still not negative then take out from the balance
            return true;//return true that the balance will withdraw
        }else{//if the balance would go negative then it will return false
            return false;
        }
    }

//    this is for the librarians to withdraw their salary
//    the checking for full or volunteer is in the cli
    public boolean withdrawalSalary(Librarians librarian) { //this will be to withdraw salary from the cash balance and add it to their total withdrawn
        double salary = librarian.getSalary();//get salary
        boolean withdrawalDone = withdraw(salary);
        if(withdrawalDone) {//checks if withdraw was possible
            librarian.addTotalSalaryWithdrawn(salary);//add the salary withdrawn to the total of the librarian
            return true;
        }else {
            return false;//not enough money
        }
    }
//    this is to add buy the new book
//    the book will be created and added to the librarians library in the librarians class and be added to the list
//    the logic of wether the book is added to the list should be in the cli since
//    if there is not enough money and the purchase fails it will not add it to the
//    the catalog/list of books in library
    public boolean orderNewBook(){//this to allow the book be purchased
        double bookprice = bookPrice();//get the price of the book from purchaseNewBook
        boolean withdrawalDone = withdraw(bookprice);//this will go throught the process of the withdraw to see if the balance is enough to purchase the book
        return withdrawalDone;//returns if the book was bought or not
    }

}
