package delft;

import java.util.Random;

public class Purchasing {
    public static double bookPrice(){
        Random rand = new Random();
        Double price = (rand.nextDouble()* (100-10)+10);
        price = (int)(price*100)/100.0;
        return price;
    }

}
