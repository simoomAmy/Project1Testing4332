//Jacob 

package delft;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Scanner is used for user input 
        Scanner scanner = new Scanner(System.in);

        // Book Setup
        Book testBook = new Book("Book 1", "Test", 2023, "ISBN1", 1, true, "Fiction");
        Book testBook2 = new Book("Book 2", "Test", 2023, "ISBN23", 2, true, "Fiction");

        // ---- Librarian Setup ---- 

        // Making Librarians
        Librarians librarianOne = new Librarians("Jacob", 1234, false);
        Librarians librarianTwo = new Librarians("Tyler", 4321, false);
        Librarians librarianThree = new Librarians("Dennis", 5678, false);

        // Adding librarians To A List
        List<Librarians> librarianList = new ArrayList<>(List.of(librarianOne, librarianTwo, librarianThree));
        
        
        // list set up (can change to have more books and members etc.)
        List<Integer> availableBookIds = new ArrayList<>(List.of(1));
        List<Book> allBooks = new ArrayList<>(List.of(testBook, testBook2));
        List<Integer> loanedBookIds = new ArrayList<>();
        List<Member> members = new ArrayList<>(List.of(new Member("Jacob", "test@gmail.com", 1, new ArrayList<>(List.of(testBook)))));

        Library library = new Library(availableBookIds, allBooks, loanedBookIds, members);

        boolean running = true; // bool for CLI running

            // calls the authorization method to authorize user
            // if returns false, the CLI wont continue to run
            if (authorizeLibrarian(librarianList, scanner) == false)
            {
                running = false; 
            }
        
        
        while (running) {

            

            // display menu for CLI
            System.out.println("=== Library Management CLI ===");
            System.out.println("1. Book Submenu");
            System.out.println("2. Member Submenu");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
        
            // input reader switcher
            String submenuInput = scanner.nextLine();
            switch (submenuInput) {
                case "0":
                    System.out.println("Exiting Library Management CLI.");
                    running = false;
                    break;

                case "1": // Book Submenu
                    clearScreen();
                    System.out.println("=== Book Submenu ===");
                    System.out.println("1. Find Book Information");
                    System.out.println("2. Checkout Book");
                    System.out.println("3. Return Book");
                    System.out.println("4. Add Book");
                    System.out.println("5. Remove Book");
                    System.out.println("6. Update Book Information");
                    System.out.println("7. Show Book Catalog");
                    System.out.println("0. Exit");
                    System.out.print("Select an option: ");

                    String bookMenuInput = scanner.nextLine();

                    switch (bookMenuInput) {
                        //exit case
                        case "0":
                        System.out.println("Exiting Library Management CLI.");
                        running = false;
                        break;

                        //Find Book Information                        
                        case "1":
                            clearScreen();
                            System.out.println("=== Find A Book ===");
                            System.out.print("Enter book name to find: ");
                            String bookNameToFind = scanner.nextLine();
                            int bookIdToFind = library.findBookIdByName(bookNameToFind);
                            if (bookIdToFind != -1) {
                                for (Book book : allBooks) {
                                    if (book.bookID == bookIdToFind) {
                                        clearScreen();
                                        book.getBookInfo();
                                    }
                                }
                            } else {
                                System.out.println("Book not found.");
                            }
                            break;
                            
                        // Checkout Book
                        case "2":
                            clearScreen();
                            System.out.println("=== Checkout A Book ===");
                            System.out.print("Enter member ID: ");
                            int memberIdCheckout = Integer.parseInt(scanner.nextLine());
                            Member checkoutMember = null;
                            
                            // search for member in member id 
                            for (Member m : members) {
                                if (m.memberId == memberIdCheckout) {
                                    checkoutMember = m;
                                    break;
                                }
                            }
                            // if the member exist search for book id in allBooks list 
                            if (checkoutMember != null) {
                                System.out.print("Enter book ID to checkout: ");
                                int bookIdToCheckout = Integer.parseInt(scanner.nextLine());
                                Book bookToCheckout = null;
                                for (Book book : allBooks) {
                                    if (book.bookID == bookIdToCheckout) {
                                        // checks if the book is available
                                        if (book.checkAvailability() == true)
                                        {
                                            bookToCheckout = book;
                                        }
                                        break;
                                    }
                                }
        
                                //if book exist and can be checked out, check it out
                                if (bookToCheckout != null) {
                                    library.checkoutBook(checkoutMember, bookIdToCheckout);
                                    clearScreen();
                                    System.out.println("Book: " + bookToCheckout.name + " checked out successfully to: " + checkoutMember.name);
                                } else {
                                    System.out.println("Book not found.");
                                }
                            } else {
                                System.out.println("Member not found.");
                            }
                            break;

                        // Return Book
                        case "3":
                            clearScreen();  
                            System.out.println("=== Return A Book ===");  
                            System.out.print("Enter member ID: ");
                            int memberIdReturn = Integer.parseInt(scanner.nextLine());
                            Member returnMember = null;
                            // search for member in members
                            for (Member m : members) {
                                if (m.memberId == memberIdReturn) {
                                    returnMember = m;
                                    break;
                                }
                            }
                            // if member is found
                            if (returnMember != null) {
                                System.out.print("Enter book ID to return: ");
                                int bookIdToReturn = Integer.parseInt(scanner.nextLine());
                                Book bookToReturn = null;
                                // looks through allBooks list
                                for (Book book : allBooks) {
                                    if (book.bookID == bookIdToReturn) {
                                        bookToReturn = book;
                                        break;
                                    }
                                }
                                // if book is found
                                if (bookToReturn != null) {
                                    library.returnBook(returnMember, bookToReturn);
                                    clearScreen();
                                    System.out.println("Book: " + bookToReturn.name + " returned successfully by: " + returnMember.name);
                                } else {
                                    System.out.println("Book not found.");
                                }
                            } else {
                                System.out.println("Member not found.");
                            }
                            break;
                        
                        // Add Book
                        case "4":
                            clearScreen();
                            System.out.println("=== Add A Book ===");
                            System.out.print("Enter book name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter author: ");
                            String author = scanner.nextLine();
                            System.out.print("Enter publication year: ");
                            int year = Integer.parseInt(scanner.nextLine());
                            System.out.print("Enter ISBN: ");
                            String isbn = scanner.nextLine();
                            System.out.print("Enter book ID: ");
                            int bookID = Integer.parseInt(scanner.nextLine());
                            boolean isBookAvailable = true; // books should be available by default
                            System.out.print("Enter genre: ");
                            String genre = scanner.nextLine();
                            Book book = new Book(name, author, year, isbn, bookID, isBookAvailable, genre);
                            library.addBook(book);
                            clearScreen();
                            System.out.println("Book: " + name + " added successfully.");
                            break;

                        // Remove Book
                        case "5":
                            clearScreen();
                            System.out.println("=== Remove A Book ===");
                            System.out.print("Enter book ID to remove: ");
                            int bookIdToRemove = Integer.parseInt(scanner.nextLine());
                            Book bookToRemove = null;
                            // if book is found in the allBooks list
                            for (Book booker : allBooks) {
                                if (booker.bookID == bookIdToRemove) {
                                    bookToRemove = booker;
                                    break;
                                }
                            }
                            // removes book if found 
                            if (bookToRemove != null) {
                                library.removeBook(bookToRemove);
                                clearScreen();
                                System.out.println("Book with ID " + bookIdToRemove + " removed successfully.");
                            } else {
                                System.out.println("Book not found.");
                            }
                            break;
                            
                        // Update Book Information    
                        case "6":
                            clearScreen();
                            System.out.println("=== Update Book Information ===");
                            System.out.print("Enter book ID to update: ");
                            int bookIdToUpdate = Integer.parseInt(scanner.nextLine());
                            Book bookToUpdate = null;
                            // looks through allBooks list
                            for (Book bookey : allBooks) {
                                if (bookey.bookID == bookIdToUpdate) {
                                    bookToUpdate = bookey;
                                    break;
                                }
                            }
                            // if book is found, update it 
                            if (bookToUpdate != null) {
                                bookToUpdate.updateBookInfo();
                                clearScreen();
                                System.out.println("Book with ID " + bookIdToUpdate + " updated successfully.");
                            } else {
                                System.out.println("Book not found.");
                            }
                            break;
                        
                        //Show Book Catalog
                        case "7":
                            clearScreen();
                            System.out.println("=== Library Book Catalog ===");
                            System.out.println("Books in Library:");
                            // loop through allBooks list and print out book name and id
                            for (Book booker : allBooks) {
                                System.out.println(booker.name + " (ID: " + booker.bookID + ")");
                            }
                            break;

                    // default book submenu
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                    }

                    break; 

                case "2": // Member Submenu
                    clearScreen();
                    System.out.println("=== Member Submenu ===");
                    System.out.println("1. Add Member");
                    System.out.println("2. Remove Member");
                    System.out.println("3. List All Members");
                    System.out.println("4. Update Member Information");
                    System.out.println("0. Exit");
                    System.out.print("Select an option: ");

                    String memberMenuInput = scanner.nextLine();

                    switch (memberMenuInput) {
                        
                        // exit case
                        case "0":
                        System.out.println("Exiting Library Management CLI.");
                            running = false;
                        break;
                        
                        // Add Member
                        case "1":
                            System.out.print("Enter member name: ");
                            String memberName = scanner.nextLine();
                            System.out.print("Enter member email: ");
                            String memberEmail = scanner.nextLine();
                            System.out.print("Enter member ID: ");
                            int newMemberId = Integer.parseInt(scanner.nextLine());
                            List<Book> borrowedBooks = new ArrayList<>();
                            Member member = new Member(memberName, memberEmail, newMemberId, borrowedBooks);
                            library.addMember(member);
                            clearScreen();
                            System.out.println("Member: " + memberName + " added successfully.");
                            break;
                        
                        // Remove Member
                        case "2":
                            System.out.print("Enter member ID to remove: ");
                            int memberIdToRemove = Integer.parseInt(scanner.nextLine());
                            Member memberToRemove = null;
                            for (Member m : members) {
                                if (m.memberId == memberIdToRemove) {
                                    memberToRemove = m;
                                    break;
                                }
                            }
                            if (memberToRemove != null) {
                                // Use library's revokeMembership or simply remove from the list.
                                library.revokeMembership(memberToRemove);
                                clearScreen();
                                System.out.println("Member with ID " + memberIdToRemove + " removed successfully.");
                            } else {
                                System.out.println("Member not found.");
                            }
                            break;

                        // List All Members
                        case "3":
                            clearScreen();
                            System.out.println("Members in Library:");
                            // loop throgh the members and prints their info 
                            for (Member m : members) {
                                m.printMemberInfo();
                            }
                            break;

                        // Update Member Information
                        case "4":
                            System.out.println("Please Provide the Member ID of the Member information you wish to update: ");
                            int memberIDToUpdate = Integer.parseInt(scanner.nextLine());
                            for (Member m : members) {
                                if(m.memberId == memberIDToUpdate) {
                                    System.out.println("Member ID: " + m.memberId + " selected.");
                                    System.out.print("Please enter the option you wish to update:\n" +
                                            "1. Name - Currently : "+m.name +"\n" +
                                            "2. Email - Currently: "+m.email+"\n"+
                                            "3. MemberID - Currently : "+memberIDToUpdate+"\n");
                                    int option = Integer.parseInt(scanner.nextLine());
                                    switch (option) {
                                        case 1:
                                            System.out.print("Please input the new Name: \n");
                                            String newName = scanner.nextLine();
                                            m.updateMemberInfo(option, newName);
                                        case 2:
                                            System.out.print("Please input the new Email: \n");
                                            String newEmail = scanner.nextLine();
                                            m.updateMemberInfo(option, newEmail);
                                        case 3:
                                            System.out.print("Please input the new MemberID: \n");
                                            String intNum = scanner.nextLine();
                                            m.updateMemberInfo(option,intNum);
                                        default:
                                            System.out.println("Invalid option");

                                    }

                                }
                            }
                                break;
                    // default member submenu
                    default:
                    System.out.println("Invalid option. Please try again.");
                        
                }
                
                // default CLI menu 
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    // Helper function used to clear the screen. This makes the CLI much cleaner
    public static void clearScreen() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        try {
            if (operatingSystem.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix/Linux/Mac
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing screen");
        }
    }

    // function that authorizes the FT Librarian 
    public static boolean authorizeLibrarian( List<Librarians> librarianList, Scanner scanner) {
     
        int inputCount = 0; // initalizes inputCount to 0

        while (true) 
        {

        System.out.println("Insert Auth Code: ");
        
        int authCode = 0; // initalizes authCode to 0
       
        // input handler
        try {
            authCode = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid auth code.");
            continue; 
        }
        
        // loops through librarian list to check if authCode is valid
        for (Librarians librarian : librarianList) {    
            if (librarian.authCode == authCode) {
                System.out.println("Access Granted.");
                return true;
            }
        }
        
        // authCode not found, increment inputCount
        inputCount++;
        System.out.println("Auth Code Not Found. Access Denied.");
        
        // if inputCount is 3, exit the program
        if (inputCount >= 3) {
            System.out.println("Too many failed attempts. Exiting program.");
            return false; // exit the program
    }
}
}
}

