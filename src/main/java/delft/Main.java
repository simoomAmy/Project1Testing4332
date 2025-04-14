//Jacob 

package delft;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Book testBook = new Book("Book 1", "Test", 2023, "ISBN1", 1, true, "Fiction");
        Book testBook2 = new Book("Book 2", "Test", 2023, "ISBN23", 2, true, "Fiction");
        
        // list set up (can change to have more books and members etc.)
        List<Integer> availableBookIds = new ArrayList<>(List.of(1));
        List<Book> allBooks = new ArrayList<>(List.of(testBook, testBook2));
        List<Integer> loanedBookIds = new ArrayList<>();
        List<Member> members = new ArrayList<>(List.of(new Member("Jacob", "test@gmail.com", 1, new ArrayList<>(List.of(testBook)))));

        Library library = new Library(availableBookIds, allBooks, loanedBookIds, members);

        
        boolean running = true;
        while (running) {

            // display menu for CLI
            System.out.println("=== Library Management CLI ===");
            System.out.println("1. Find Book Information");
            System.out.println("2. Checkout Book");
            System.out.println("3. Return Book");
            System.out.println("4. Show Full Catalog");
            System.out.println("6. Add Member");
            System.out.println("7. Remove Member");
            System.out.println("8. Add Book");
            System.out.println("9. Remove Book");
            System.out.println("10. List All Members");
            System.out.println("11. Update Book Information");
            System.out.println("12. Update Book");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
        
            // input reader switcher
            String input = scanner.nextLine();
            switch (input) {
                case "0":
                    System.out.println("Exiting Library Management CLI.");
                    running = false;
                    break;

                case "1": // Find Book Information
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

                case "2": // Checkout Book
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
                            library.checkoutBook(checkoutMember, bookToCheckout);
                            clearScreen();
                            System.out.println("Book: " + bookToCheckout.name + " checked out successfully to: " + checkoutMember.name);
                        } else {
                            System.out.println("Book not found.");
                        }
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;

                case "3": // Return Book
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

                case "4": // Show Full Catalog
                    System.out.println("Books in Library:");
                    // loop through allBooks list and print out book name and id
                    for (Book book : allBooks) {
                        System.out.println(book.name + " (ID: " + book.bookID + ")");
                    }
                    break;

                case "6": // Add Member
                    System.out.print("Enter member name: ");
                    String memberName = scanner.nextLine();
                    System.out.print("Enter member email: ");
                    String memberEmail = scanner.nextLine();
                    System.out.print("Enter member ID: ");
                    int newMemberId = Integer.parseInt(scanner.nextLine());
                    List<Book> borrowedBooks = new ArrayList<>();
                    library.addMember(memberName, memberEmail, newMemberId, borrowedBooks);
                    clearScreen();
                    System.out.println("Member: " + memberName + " added successfully.");
                    break;

                case "7": // Remove Member
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

                case "8": // Add Book
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
                    library.addBook(name, author, year, isbn, bookID, isBookAvailable, genre);
                    clearScreen();
                    System.out.println("Book: " + name + " added successfully.");
                    break;

                case "9": // Remove Book
                    System.out.print("Enter book ID to remove: ");
                    int bookIdToRemove = Integer.parseInt(scanner.nextLine());
                    Book bookToRemove = null;
                    // if book is found in the allBooks list
                    for (Book book : allBooks) {
                        if (book.bookID == bookIdToRemove) {
                            bookToRemove = book;
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

                case "10": // List All Members
                    System.out.println("Members in Library:");
                    // loop throgh the members and prints their info 
                    for (Member m : members) {
                        m.printMemberInfo();
                    }
                    break;
                    
                case "11": // Update Book Information
                    System.out.print("Enter book ID to update: ");
                    int bookIdToUpdate = Integer.parseInt(scanner.nextLine());
                    Book bookToUpdate = null;
                    // looks through allBooks list
                    for (Book book : allBooks) {
                        if (book.bookID == bookIdToUpdate) {
                            bookToUpdate = book;
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
                case "12":
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
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}

