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
    void withdrawGreaterThanAmounts(@ForAll @DoubleRange(min = 39000.01) double withdrawAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean withdraw = x.withdraw(withdrawAmount);
        assertThat(withdraw).isFalse();
        assertThat(x.getCashBalance()).isEqualTo(39000.00);
    }
    @Provide
    static Arbitrary<Double> falseTestCases() {
        return Arbitraries.oneOf(
                Arbitraries.doubles().greaterOrEqual(39000.01),
                Arbitraries.doubles().lessThan(0)
        );
    }

    @Property
    void withdrawLessThanAmounts(@ForAll("falseTestCases") Double withdrawAmount) {
        LibraryAccounts x = new LibraryAccounts();
        boolean withdraw = x.withdraw(withdrawAmount);
        assertThat(withdraw).isTrue();
        assertThat(x.getCashBalance()).isEqualTo(39000.00);
    }



    @Test
    public void withdrawTestTrue(){//simple test case where check to see that on the assumption
        //that the amount is less than that availbel will successfully be subtracted and
        //return true
        boolean result = libraryAccounts.withdraw(500.23);
        assertThat(libraryAccounts.getCashBalance()).isEqualTo(39000.00-500.23);
        assertThat(result).isEqualTo(true);
    }
    @Test
    public void withdrawTestFalse(){
        //this is to check that if the amount is greater than the cash available it will return false
        //and won't allow the the subtraction.
        boolean result = libraryAccounts.withdraw(1000000);
        assertThat(libraryAccounts.getCashBalance()).isEqualTo(39000.00);//checks that the balance stays untouched if the amount withdrawn is greater than it
        assertThat(result).isEqualTo(false);//will return false on the amount is greate than cashBalance
    }

}
