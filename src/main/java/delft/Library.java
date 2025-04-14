//Nathan (Testing)

package delft;

import java.util.List;
import java.util.Iterator;

public class Library {

    public List<Integer> availableBookIds;
    public List<Book> allBooks;
    public List<Integer> loanedBookIds; // changed from List<Book> to List<Integer> to match availableBookIds
    public List<Member> members;

    public Library(List<Integer> availableBookIds,
                   List<Book> allBooks,
                   List<Integer> loanedBookIds,
                   List<Member> members
    )
    {
        this.availableBookIds = availableBookIds;
        this.allBooks = allBooks;
        this.loanedBookIds = loanedBookIds;
        this.members = members;
    }

    // Stub (Verify after book is added)
    public void addBook(String name,
                        String author,
                        int year,
                        String isbn,
                        int bookID,
                        boolean isAvailable,
                        String genre)
    {
        // Makes a new book object for the book that is being added
        Book newBook = new Book(name, author, year, isbn, bookID, isAvailable, genre);

        // Adds the new book to the list allBooks
        allBooks.add(newBook);

        // Since added books cannot already be checked out, we add the book to the available list
        availableBookIds.add(bookID);
    }

    // Stub (Verify after book is added)
    public void removeBook(Book book) {
        // When the library removes a book, we want to remove it from the allBooks list:
        allBooks.remove(book);

        int bookId = findBookIdByName(book.name);

        // and the available list if it is there:
        if (availableBookIds.contains(bookId)) {
            availableBookIds.remove(bookId);
        }

        // and the loaned list if it is there:
        if (loanedBookIds.contains(bookId)) {
            loanedBookIds.remove(bookId);
        }

    }

    // Stub (Verify after book is added)
    public void checkoutBook(Member member, Book book) {
        // We check if the book is available:
        if (book.checkAvailability() == true) {

            int bookId = findBookIdByName(book.name);

            // When a book is checked out, we want to remove it from the available list:
            availableBookIds.remove(Integer.valueOf(bookId));

            // and add it to the loaned list:
            loanedBookIds.add(bookId);

            // link the book to the member:
            member.addBorrowedBook(book);
        }
        else
        {
            System.out.println("Book is not available.");
        }



    }

    // Stub (Verify after book is added)
    public void addMember(String name, String email, int memberID, List<Book> borrowedBooks)
    {
        // Makes a new member object for the member that is being added
        Member newMember = new Member(name, email, memberID, borrowedBooks);

        // Adds the new member to the list of members
        this.members.add(newMember);
    }

    public void revokeMembership(Member member) {
        Iterator<Member> iterator = this.members.iterator();
        boolean memberFound = false;

        while (iterator.hasNext()) {
            Member currentMember = iterator.next();
            if (currentMember.equals(member)) {
                iterator.remove(); // Safely remove the member
                memberFound = true;
                System.out.println("Member removed successfully.");
                break;
            }
        }

        if (!memberFound) {
            System.out.println("No Member found");
        }
    }

    // Checks if the book is available and prints if it's available or not
    public void bookAvailability(Book book)
    {
        if (book.checkAvailability()) {
            System.out.println("Book \"" + book.name + "\" is available.");
        } else {
            System.out.println("Book \"" + book.name + "\" is not available.");
        }
    }

    // Unit (Specification)
    public String whoHasBook(String bookName)
    {
        // Check whether if the book is in the loaned list
        for (int bookId : loanedBookIds) {
            if (bookName.equals(allBooks.get(bookId).name)) {
                // Returns the member if the book is in the loaned list
                return allBooks.get(bookId).name;
            }
        }
        // If the book not found in loaned list
        return null;

    }

    // Unit (Specification)
    public List<Member> getAllMembers()
    {
       return this.members;
    }


    // Unit (Specification)
    public int findBookIdByName(String bookName)
    {
        for (Book book : allBooks)
        {
            if (book.name.equals(bookName))
            {
                return book.bookID;
            }
        }
        return -1; // Book not found
    }

    // Stub or Mock
    public void returnBook(Member member, Book book){

        int bookId = findBookIdByName(book.name);

        // When a book is returned, we want to remove it from the loaned list:
        loanedBookIds.remove(Integer.valueOf(bookId));

        // and add it to the available list:
        availableBookIds.add(bookId);

        // and remove it from the member's borrowed books:
        member.removedBorrowedBook(bookId);
    }


}