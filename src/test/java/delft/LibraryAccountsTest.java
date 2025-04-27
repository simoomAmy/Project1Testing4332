package delft;

import net.jqwik.api.*;
import net.jqwik.api.constraints.DoubleRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LibraryAccountsTest {
    private LibraryAccounts libraryAccounts;
    private double initialCashBalance;

    @BeforeEach
    void setUp() {
        libraryAccounts = new LibraryAccounts(); // initialize the field, not a local variable
        initialCashBalance = 39000.00;//to have the basis to compare
    }

    @Property
    //here the range is made to be
    void depositPostive(@ForAll @DoubleRange(min = 0.0) double depositAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean deposited = x.deposit(depositAmount);

        assertThat(deposited).isTrue();
        assertThat(x.getCashBalance()).isEqualTo((depositAmount + 39000.00));
    }
    @Property
    void depositNegative(@ForAll @DoubleRange(min = -10000, max = -0.01) double depositAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean deposited = x.deposit(depositAmount);
        assertThat(deposited).isFalse();
        assertThat(x.getCashBalance()).isEqualTo(39000.00);
    }
    /// /////////////////////////////// ////



    @Property
    void withdrawLessThanZeroAmounts(@ForAll @DoubleRange(min = -10000,max = -0.01) Double withdrawAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean withdraw = x.withdraw(withdrawAmount);
        assertThat(withdraw).isFalse();
        assertThat(x.getCashBalance()).isEqualTo(39000.00);
    }
    @Property
    void withdrawGreaterAmount(@ForAll@DoubleRange(min = 39000.01) Double withdrawAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean withdraw = x.withdraw(withdrawAmount);
        assertThat(withdraw).isFalse();
        assertThat(x.getCashBalance()).isEqualTo(39000.00);
    }

    @Property
    void withdrawLessThanMaxAmounts(@ForAll @DoubleRange(min = 0.0, max = 39000.00) double withdrawAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean withdraw = x.withdraw(withdrawAmount);
        assertThat(withdraw).isTrue();
        assertThat(x.getCashBalance()).isEqualTo(39000.00-withdrawAmount);
    }

    @Test
    void withdrawSalaryStayInBalanceOnce(){
        LibraryAccounts x = new LibraryAccounts();
        Librarians xzyLibrarian = new Librarians("Becy",1234,false);

        boolean expected = x.withdrawalSalary(xzyLibrarian);
        assertThat(expected).isTrue();
        assertThat(xzyLibrarian.totalSalaryWithdrawn).isEqualTo(39000.00);
        assertThat(x.getCashBalance()).isEqualTo(39000.00-xzyLibrarian.getSalary());

    }
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
    @Test
    void orderbookONCEstayBelowCap(){
        LibraryAccounts x = new LibraryAccounts();

        boolean expexted = x.orderNewBook();
        assertThat(expexted).isTrue();
        assertThat(x.getCashBalance()).isLessThan(39000.00);
    }
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
