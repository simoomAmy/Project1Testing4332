package delft;

import net.jqwik.api.Property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



class PurchasingTest 
{
    @Property
    void bookPrice() 
    {
        double price = Purchasing.bookPrice();
        assertTrue(price >= 10.0 && price < 100.0);

        long cents = Math.round(price * 100);
        assertEquals(cents / 100.0, price);
    }
    
}

