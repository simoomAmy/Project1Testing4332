package delft;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class TestLibrary
{
    private Library library;
    private List<Book> allBooks;
    private List<Integer> availableBookIds;
    private List<Integer> loanedBookIds;
    private List<Member> members;

    @BeforeEach
    void setUp()
    {
        allBooks = new ArrayList<>();
        availableBookIds = new ArrayList<>();
        loanedBookIds = new ArrayList<>();
        members = new ArrayList<>();
        library = new Library(availableBookIds,allBooks, loanedBookIds, members);
    }

    // Stubbing to see if the book is added into the allBooks list
    @Test
    public void testAddBook()
    {
        String name = "Book";
        String author = "Author";
        int year = 2025;
        String isbn = "1234567890";
        int bookID = 1;
        boolean isAvailable = true;
        String genre = "Fiction";
        Book bok = new Book(name, author, year, isbn, bookID, isAvailable, genre);
        library.addBook(bok);
        assertEquals( 1, allBooks.size());

    }

    // Stubbing to see if the book is removed from the allBooks list
    @Test
    public void testRemoveBook()
    {
        Book book = new Book("Book1", "Author", 2025, "1234567890", 1, true, "Fiction");

        allBooks.add(book);

        availableBookIds.add(1);
        loanedBookIds.add(1);

        library.removeBook(book);

        assertFalse(allBooks.contains(book));
        assertEquals(1, availableBookIds.size());

    }

    // Stubbing to see if the book is checked out
    @Test
    public void testCheckoutBook()
    {
        Book book = new Book("Book1", "Author", 2025, "1234567890", 1, true, "Fiction");
        allBooks.add(book);
        availableBookIds.add(1);
        loanedBookIds.add(1);
        List<Book> borrowedBooks = new ArrayList<>();

    }

    // Stubbing to see if the new member is added into the list
    @Test
    public void testAddMember()
    {
        String name = "Member";
        String email = "Member@gmail.com";
        int memberID = 1;
        List<Book> borrowedBooks = new ArrayList<>();
        Member member = new Member(name, email, memberID, borrowedBooks);
        library.addMember(member);
        assertEquals(1, members.size());
    }

    //Stubbing to see if the member is removed from the list
    @Test
    public void testRevokeMember()
    {
        List<Book> borrowedBooks = new ArrayList<>();
        Member newMem = new Member("A","A@gmail.com",12345,borrowedBooks);
        library.members.add(newMem);
        library.revokeMembership(newMem);

        assertThat(library.members.size()).isEqualTo(0);
    }

    // Mocking to check if the book is available whether so
    @Test
    public void testBookAvailability()
    {
        Book availableBook = mock(Book.class);
        availableBook.name = "Book1";
        when(availableBook.checkAvailability()).thenReturn(true);
    }

    // Unit (Specification) Checking if the book is available and show the member who has it
    @Test
    public void testWhoHasBook()
    {
        Book book = new Book("Book", "Author", 2025, "1234567890", 1, true, "Fiction");
        library.addBook(book);
        Book book2 = new Book("Book", "Author", 2025, "1234567890", 1, true, "Fiction");
        library.addBook(book2);
        Member member = new Member("A","A@gmail.com",12345,allBooks);
        library.addMember(member);
        library.checkoutBook(member,1);
        assertThat(library.whoHasBook("Book")).isEqualTo("A");


    }

    // Unit (Specification) Gives the list of all the members in the library
    @Test
    public void testGetAllMembers()
    {
        List<Book> borrowedBooks = new ArrayList<>();
        Member member1 = new Member("Member", "Member@gmail.com", 1, borrowedBooks);
        members.add(member1);

        List<Member> result = library.getAllMembers();
        assertEquals(1, result.size());
    }

    // Unit (Specification) Checking the book through the book name and get the ID from it
    @Test
    public void testFindBookIdByName()
    {
        Book book1 = new Book("Book1", "Author", 2025, "1234567890", 1, true, "Fiction");
        allBooks.add(book1);

        int result = library.findBookIdByName("Book1");

        assertEquals(1, result);
    }

    // Stubbing to see if the book is returned and removed from the loaned list
    @Test
    public void testReturnBook()
    {

        Book book = new Book("Book1", "Author", 2025, "1234567890", 1, true, "Fiction");
        List<Book> borrowedBooks = new ArrayList<>();
        Member member = new Member("Member", "Member@gmail.com", 1, borrowedBooks);
        member.addBorrowedBook(book);
        allBooks.add(book);
        loanedBookIds.add(book.bookID);

        library.returnBook(member, book);

    }
}