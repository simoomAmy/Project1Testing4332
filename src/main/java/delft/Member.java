//Amy (Testing)

package delft;

import java.util.List;
import java.util.Scanner;

public class Member {
    public String name;
    public  String email;
    public int memberId;
    public List<Book> borrowedBooks;

    //added then got rid of getter methods due to my error during testing, I wont be using them.
    public Member(String name, String email, int memberID, List<Book> borrowedBooks) {
        this.name = name;
        this.email = email;
        this.memberId = memberID;
        this.borrowedBooks = borrowedBooks;
    }

    //Mocking Verifying string
    public void printMemberInfo(){
        System.out.println("____________________________");
        System.out.println("Name: " + this.name);
        System.out.println("Email: " + this.email);
        System.out.println("ID: "+ this.memberId);

        for(Book book : this.borrowedBooks){
            System.out.println(book.name + " ID: "+ book.bookID);
        }
        System.out.println("____________________________");

    }

    // Specification (Unit Test)
    public List<Book> getborrowedBookList(){
        return this.borrowedBooks;
    }

    // Stub (Adding the dummy list)
    public void addBorrowedBook(Book book){
        System.out.println();
        if(book.isAvailable == false){
            System.out.println("Book is not available and cannot add it to borrowed books");
        }else if(book.isAvailable == true){
            System.out.println("Book has been added to borrowed books");
            book.isAvailable = false;
            this.borrowedBooks.add(book);

        }
    }

    // Mock (Verifying user input)
    public void updateMemberInfo(){
        System.out.println("Please select a field to update");
        System.out.println("1. Name: " + this.name);
        System.out.println("2. Email: " + this.email);
        System.out.println("3. ID: " + this.memberId);

        Scanner scanner = new Scanner(System.in);
        switch(scanner.nextInt()){
            case 1:
                this.name = scanner.nextLine();
                System.out.println("Name Updated To: " + this.name);
            case 2:
                this.email = scanner.nextLine();
                System.out.println("Email Updated To: " + this.email);
            case 3:
                this.memberId = scanner.nextInt();
                System.out.println("ID Updated To: " + this.memberId);
        }
        scanner.close();
    }

    // Stub (Adding the dummy list) and testing if the book is available
    public void removedBorrowedBook(int bookID){
        for(Book book : this.borrowedBooks){
            if(book.bookID == bookID){
                book.isAvailable = true;
                this.borrowedBooks.remove(book);
            }
        }
    }

}
