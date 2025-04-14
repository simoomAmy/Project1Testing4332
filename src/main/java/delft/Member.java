//Amy (Testing)

package delft;

import java.util.List;

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
        }
        if(book.isAvailable == true){
            System.out.println("Book has been added to borrowed books");
            book.isAvailable = false;
            this.borrowedBooks.add(book);

        }
    }

    // Mock (Verifying user input)
    public void updateMemberInfo(int option, String change){
        if(option == 1){
            this.name = change;
        }else if(option == 2){
            this.email = change;

        }else if(option == 3){
            int memberID = Integer.parseInt(change);
            this.memberId = memberID;
        }
    }

    // Stub (Adding the dummy list) and testing if the book is available
    public void removedBorrowedBook(int bookID){
        for(int i = 0; i < borrowedBooks.size(); i++){
            Book booker = borrowedBooks.get(i);
            if(booker.bookID == bookID){
                booker.isAvailable = true;
                borrowedBooks.remove(i);
            }
        }
    }

}