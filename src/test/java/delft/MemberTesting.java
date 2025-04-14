package delft;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

class MemberTesting {

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



        //test all was outputed as it should have
        assertThat(baos.toString()).contains(name);
        assertThat(baos.toString()).contains(email);
        assertThat(baos.toString()).contains(Integer.toString(id));
        for(Book book : borrowedBooks){
            assertThat(baos.toString()).contains(book.name);
        }

        System.out.flush();
        System.setOut(out);

        Book boosk = new Book("title", "author", 200,"ISBN",1234, true,"genre");
        memberTester.addBorrowedBook(boosk);
        //to ensure it is set to false so no one can check it out
        assertThat(boosk.isAvailable).isFalse();
        //to ensure the book was added
        assertThat(memberTester.borrowedBooks).contains(boosk);
        //to test if it will add a unavailable book
        Book boosk3 = new Book("title2", "author", 200,"ISBN",123, false,"genre");
        memberTester.addBorrowedBook(boosk3);
        assertThat(memberTester.borrowedBooks).doesNotContain(boosk3);

        memberTester.removedBorrowedBook(boosk.bookID);
        assertThat(memberTester.borrowedBooks).doesNotContain(boosk);

        assertEquals(memberTester.getborrowedBookList(), borrowedBooks);

        memberTester.updateMemberInfo(1,"newName");
        assertEquals(memberTester.name, "newName");
        memberTester.updateMemberInfo(2, "newEmail");
        assertEquals(memberTester.email, "newEmail");
        memberTester.updateMemberInfo(3, "123678");
        assertEquals(memberTester.memberId,123678);

        //the member instance i will use

    }
}


