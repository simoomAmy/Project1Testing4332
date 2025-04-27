package delft;

import net.jqwik.api.*;
import net.jqwik.api.constraints.DoubleRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LibraryAccountsTest {
    private LibraryAccounts libraryAccounts;//
    private double initialCashBalance;

    /*small set up to save time
    libraryAccounts - to allow the a quick setup
    initialCashBalance - to give a basis to compare to the normal cash balance when initalized
    */
    @BeforeEach
    void setUp() {
        libraryAccounts = new LibraryAccounts(); // initialize the field, not a local variable
        initialCashBalance = 39000.00;//to have the basis to compare
    }
/*
depositPositive - Property testing because it deals with a critical system of accounting
this is to test the partition of any value that can should be added to the deposit meaning any positive value including zero
I ran a property test for any double from zero and up
the assertions ensure that the deposit return true meaning the code was hit and it was added sucussfuly
the other assertions also ensure that the deposit was done correctly by checking the checkbalance and ensuring it was updated
with the amount that was deposited
 */
    @Property
    //here the range is made to be
    void depositPositive(@ForAll @DoubleRange(min = 0.0) double depositAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean deposited = x.deposit(depositAmount);


        assertThat(deposited).isTrue();
        assertThat(x.getCashBalance()).isEqualTo((depositAmount + 39000.00));
    }
    /*
    depositNegative - Property testing because it deals with a critical system of accounting
this is to test the partition of any value that can be negative which could affect the cashbalance by ensuring the range is any negative value which results in false
the assertions ensure that the deposit return false meaning the cashbalance isnt touched
the other assertions also ensure that the deposit was done correctly by checking the checkbalance and ensuring it was the same
as initailizied
     */

    @Property
    void depositNegative(@ForAll @DoubleRange(min = -10000, max = -0.01) double depositAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean deposited = x.deposit(depositAmount);
        assertThat(deposited).isFalse();
        assertThat(x.getCashBalance()).isEqualTo(39000.00);
    }
/*
    withdrawLessThanZeroAmounts - Property testing because it deals with a critical system of accounting
this is to test the partition of any value that can be negative which could skew the balance which should be stopped and return false
the assertions ensure that the withdraw return false meaning the cashbalance isnt touched
the other assertions also ensure that the withdraw was stopped correctly of stopping the access to balance by checking the checkbalance and ensuring it was the same
as initailizied
     */
    @Property
    void withdrawLessThanZeroAmounts(@ForAll @DoubleRange(min = -10000,max = -0.01) Double withdrawAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean withdraw = x.withdraw(withdrawAmount);
        assertThat(withdraw).isFalse();
        assertThat(x.getCashBalance()).isEqualTo(39000.00);
    }
    /*
    withdrawGreaterAmount - Property testing because it deals with a critical system of accounting
this is to test the partition of any value that can be is greater than the balance and should be stopped and return false
the assertions ensures that withdraw returns false
the other assertions also ensure that the withdraw was stopped correctly of stopping the access to balance by checking
the checkbalance and ensuring it was the same as initailizied
     */
    @Property
    void withdrawGreaterAmount(@ForAll@DoubleRange(min = 39000.01) Double withdrawAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean withdraw = x.withdraw(withdrawAmount);
        assertThat(withdraw).isFalse();
        assertThat(x.getCashBalance()).isEqualTo(39000.00);
    }
    /*
       withdrawLessThanMaxAmounts - Property testing because it deals with a critical system of accounting
   this is to test the partition of any value that is within the scope of the balance
   the assertions ensures that withdraw returns true
   the other assertions is to make sure that the amount was properly updated by checking the initail amount minus the withdrawl amount
   the checkbalance and ensuring it was the same as initailizied
        */
    @Property
    void withdrawLessThanMaxAmounts(@ForAll @DoubleRange(min = 0.0, max = 39000.00) double withdrawAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean withdraw = x.withdraw(withdrawAmount);
        assertThat(withdraw).isTrue();
        assertThat(x.getCashBalance()).isEqualTo(39000.00-withdrawAmount);
    }
    /*
           withdrawSalaryStayInBalanceOnce - because of the withdraw already test as the critical system i only need to ensure through specification testing
           this partition is to test that if the salary is called once it will pass
           this is a simple test
           fist assertion is that it returns true on the assumption that the salary is within the balance
           second assertion is to ensure the totalSalary was update correctly by ensuring that the normal salray was added
           third assertion is to ensure the balance was properly updated with the withdraw of the salary
            */
    @Test
    void withdrawSalaryStayInBalanceOnce(){
        LibraryAccounts x = new LibraryAccounts();
        Librarians xzyLibrarian = new Librarians("Becy",1234,false);

        boolean expected = x.withdrawalSalary(xzyLibrarian);
        assertThat(expected).isTrue();
        assertThat(xzyLibrarian.totalSalaryWithdrawn).isEqualTo(39000.00);
        assertThat(x.getCashBalance()).isEqualTo(39000.00-xzyLibrarian.getSalary());

    }
    /*
          withdrawSalaryStayInBalanceOnce - because of the withdraw already test as the critical system i only need to ensure through specification testing
          this partition is to test that if the salary is called more than once out of the balance it will be denied and not put the balance into negative
          This test for multiple librarians accessing the withdrawSalary
          fist assertion is that it returns true on the assumption that the salary is within the balance
          second assertion is that is returns false since the balance would be 0 and should not allow for the balance to be withdrawn
          third assertion is that it returns false since the balance would be 0 and should not allow for the balance to be withdrawn
          fourth assertion is to ensure the totalSalary was update correctly by ensuring that the normal salray was added
          fifth assertion is to ensure the totalSalary was update not touched for that librarian since the balance is lower
          sixth assertion is to ensure the totalSalary was update not touched for that librarian since the balance is lower

          seventh assertion is to ensure the balance was properly updated with the withdraw of the one salary and didnt go negative
           */
    @Test
    void withdrawSalaryOutOfBalanceMultiple(){
        LibraryAccounts x = new LibraryAccounts();
        Librarians abcLibrarian = new Librarians("Becy",1234,false);
        Librarians zxyLibrarian = new Librarians("Giggy",1234,false);
        Librarians asdfLibrarian = new Librarians("John",1234,false);

        boolean expected = x.withdrawalSalary(abcLibrarian);
        boolean expected2 = x.withdrawalSalary(zxyLibrarian);
        boolean expected3 = x.withdrawalSalary(asdfLibrarian);
        assertThat(expected).isTrue();
        assertThat(expected2).isFalse();
        assertThat(expected3).isFalse();
        assertThat(abcLibrarian.totalSalaryWithdrawn).isEqualTo(39000.00);
        assertThat(zxyLibrarian.totalSalaryWithdrawn).isEqualTo(0);
        assertThat(asdfLibrarian.totalSalaryWithdrawn).isEqualTo(0);
        assertThat(x.getCashBalance()).isEqualTo(39000.00-abcLibrarian.getSalary());
    }

    /*
          withdrawSalaryStayInBalanceOnce - because of the withdraw already test as the critical system i only need to ensure through specification testing
          this partition is to test that if the salary is called more than once out of the balance it will be denied and not put the balance into negative
          This test for one single librarian accessing the withdrawSalary
          fist assertion is that it returns true on the assumption that the salary is within the balance
          second assertion is that is returns false since the balance would be 0 and should not allow for the balance to be withdrawn
          third assertion is that it returns false since the balance would be 0 and should not allow for the balance to be withdrawn
          fourth assertion is to ensure the totalSalary was update correctly by ensuring that the normal salray was added and the other extra withdraws were not adding to it

          seventh assertion is to ensure the balance was properly updated with the withdraw of the one salary and didnt go negative
           */

    @Test
    void withdrawSalaryOutOfBalanceMultiTimes(){
        LibraryAccounts x = new LibraryAccounts();
        Librarians abcLibrarian = new Librarians("Becy",1234,false);

        boolean expected = x.withdrawalSalary(abcLibrarian);
        boolean expected2 = x.withdrawalSalary(abcLibrarian);
        boolean expected3 = x.withdrawalSalary(abcLibrarian);
        assertThat(expected).isTrue();
        assertThat(expected2).isFalse();
        assertThat(expected3).isFalse();
        assertThat(abcLibrarian.totalSalaryWithdrawn).isEqualTo(39000.00);
        assertThat(x.getCashBalance()).isEqualTo(39000.00-abcLibrarian.getSalary());
    }
    /*
    this is to test the specification of a book being able to go through the withdraw on the checking that the balance will be large enough
     since books can only be 10 to 100 the intial balance is big enough and only must be tested once.
     first assertion is that to ensure if a book within the balance is purchased it will return true
     second assertion is that the cash balance will reflect the purchase of the book since the value return cant be acessed it
     will be checked by ensuring the inital balance is less than when initalized
     */
    @Test
    void orderbookONCEstayBelowCap(){
        LibraryAccounts x = new LibraryAccounts();

        boolean expexted = x.orderNewBook();
        assertThat(expexted).isTrue();
        assertThat(x.getCashBalance()).isLessThan(39000.00);
    }
    /*
    this is to test the specification of a book being able to go through the withdraw on the checking that the balance will be large enough
     since books can only be 10 to 100 the intial balance is big enough and to make it test the negative value then it will be called untill the balance is low enough to
     stop being able to purchase.
     first assertion is that to ensure if a book within the balance is purchased it will return true
     second assertion is that the cash balance will reflect the purchase of the book since the value return cant be acessed it
     will be checked by ensuring the inital balance is less than when initalized
     */
    @Test
    void orderBookAboveCap(){
        LibraryAccounts x = new LibraryAccounts();
        boolean expexted = true;
        double balance = x.getCashBalance();
        while(expexted){
            expexted = x.orderNewBook();
            balance = x.getCashBalance();
        }
        assertThat(expexted).isFalse();
        assertThat(balance).isLessThan(39000.00);
    }




}
