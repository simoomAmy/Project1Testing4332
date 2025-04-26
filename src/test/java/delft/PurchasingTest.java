package delft;

import net.jqwik.api.Property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PurchasingTest 
{
    // Randomly generates a book price between $10 and $100 too se if the price is within the range of $10 to $100
    @Property
    void bookPrice() 
    {
        double price = Purchasing.bookPrice();
        // Sees if the price range is at least $10 but is also less than $100
        assertTrue(price >= 10.0 && price < 100.0);

        long cents = Math.round(price * 100);
        // Converts back to dollars to match the original price
        assertEquals(cents / 100.0, price);
    }
    
}

