//Nathan (Testing)

package delft;

import java.util.List;

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
    public void addBook(Book newBook)
    {
        // Makes a new book object for the book that is being added


        // Adds the new book to the list allBooks
        allBooks.add(newBook);

        // Since added books cannot already be checked out, we add the book to the available list
        availableBookIds.add(newBook.bookID);
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
    public void checkoutBook(Member member, int bookID) {
        for(Book book : allBooks){
            if(bookID == book.bookID){
                loanedBookIds.add(bookID);
                availableBookIds.remove(bookID);

                member.addBorrowedBook(book);
                break;
            }
        }


    }

    // Stub (Verify after book is added)
    public void addMember(Member member)
    {
        // Makes a new member object for the member that is being added

        // Adds the new member to the list of members
        this.members.add(member);

    }

    public void revokeMembership(Member member)
    {
        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);
            if (currentMember.equals(member)) {
                this.members.remove(currentMember);
            } else {
                System.out.println("No Member found");
            }
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
        for (int i=0; i < members.size();i++) {
            Member currentMember = members.get(i);
            for(int z = 0; i< currentMember.getborrowedBookList().size();z++){
                Book currentBook = currentMember.getborrowedBookList().get(z);
                if(bookName.equals(currentBook.name)){
                    return currentMember.name;
                }
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
        book.isAvailable = true;
        for(int x = 0; x < loanedBookIds.size();x++){
            if(loanedBookIds.get(x)==book.bookID){
                loanedBookIds.remove(x);
                break;
            }
        }


        availableBookIds.add(book.bookID);
        member.removedBorrowedBook(book.bookID);
    }


}