package delft;

import static org.mockito.Mockito.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

class LibraryTesting {

    @Test
    public void memberTesting() {
        //to give to the memeber testing
        String name = "amy";
        String email = "amy@gmail.com";
        int id = 21;
        //set up for list
        List<Book> borrowedBooks = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            String nameTitle = "Book" + i;
            String author = "Author" + i;
            int year = 1900 + i;
            String isbn = "ISBN" + i;
            int bookId = i + 1;
            boolean available = false;
            String genre = "Genre" + i;
            borrowedBooks.add(new Book(nameTitle, author, year, isbn, bookId, available, genre));
        }
        //initalized member instance
        Member memberTester = new Member(name, email, id, borrowedBooks);
        //to test the system out put
        PrintStream out = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        memberTester.printMemberInfo();

        System.out.flush();
        System.setOut(out);

        // Get the output
        String output = baos.toString();
        //test all was outputed as it should have
        assertThat(baos.toString()).contains(name);
        assertThat(baos.toString()).contains(email);
        assertThat(baos.toString()).contains(Integer.toString(id));
        for(Book book : borrowedBooks){
            assertThat(baos.toString()).contains(book.name);
        }


        Book boosk = new Book("title", "author", 200,"ISBN",1234, true,"genre");
        memberTester.addBorrowedBook(boosk);
        //to ensure the book was added
        assertThat(memberTester.borrowedBooks).contains(boosk);
        //to test if it will add a unavailable book
        Book boosk3 = new Book("title2", "author", 200,"ISBN",123, false,"genre");
        memberTester.addBorrowedBook(boosk3);
        assertThat(memberTester.borrowedBooks).doesNotContain(boosk3);


        memberTester.removedBorrowedBook(123);
        assertThat(memberTester.borrowedBooks).doesNotContain(boosk);

        memberTester.removedBorrowedBook(123);



        //the member instance i will use







    }
}


