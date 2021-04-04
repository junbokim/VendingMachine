package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CashTest {
    @Test void testCreation() { // Test if it actually makes
        Cash payment = new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,1,0,0,5,0,0,0)));
        assertNotNull(payment, "Cash object should generate");
    }

    @Test void testCashAmount() { // Test if it actually makes
        Cash payment = new Cash(new ArrayList<>(Arrays.asList(1,1,1,1,1,1,1,1,1,1,1)));
        assertEquals(payment.getValue(), 188.85, "Cash value should match");

        payment.updateAmounts(new ArrayList<>(Arrays.asList(0,1,0,1,0,1,0,1,0,1,2)));
        assertEquals(payment.getValue(), 262.6, "Cash value should match");
    }

    @Test void testCashSubtraction() { // Test if it actually makes
        Cash cash1 = new Cash(new ArrayList<>(Arrays.asList(1,1,1,1,1,1,1,1,1,1,1)));
        Cash cash2 = new Cash(new ArrayList<>(Arrays.asList(0,1,0,1,0,1,0,1,0,1,0)));

        assertEquals(Cash.subtract(cash1, cash2).getValue(), 126.25, "Cash value should match");
    }

    @Test void testCashAddition() { // Test if it actually makes
        Cash cash1 = new Cash(new ArrayList<>(Arrays.asList(1,1,1,1,1,1,1,1,1,1,1)));
        Cash cash2 = new Cash(new ArrayList<>(Arrays.asList(0,1,0,1,0,1,0,1,0,1,0)));

        assertEquals(Cash.add(cash1, cash2).getValue(), 251.45, "Cash value should match");
    }

    @Test void testChangeCalculation() { // Test if it actually makes
        ArrayList<Integer> changeAmounts = new ArrayList<>(Arrays.asList(1,0,1,0,1,0,1,0,1,0,1));
        double changeValue = 126.25;

        Cash change = Cash.calculateChange(new Cash(new ArrayList<>(Arrays.asList(5,5,5,5,5,5,5,5,5,5,5))), changeValue);
        assertEquals(change.getAmounts(), changeAmounts, "Cash value should match");
    }

    @Test void testInsufficientChange() { // Test if it actually makes
        ArrayList<Integer> changeAmounts = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,2,0,0,0));
        double changeValue = 25;

        Cash change = Cash.calculateChange(new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,5,0,5,5))), changeValue);
        assertEquals(change.getAmounts(), changeAmounts, "Cash value should match");
    }

    @Test void testStringFormatting() { // Test if it actually makes
        ArrayList<Integer> amounts = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11));
        Cash cash = new Cash(amounts);
        assertEquals(cash.formatForExport(), "1,2,3,4,5,6,7,8,9,10,11", "Strings should match");
    }

    @Test void testStringRestoration() { // Test if it actually makes
        ArrayList<Integer> amounts = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11));
        Cash cash = new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,2,0,0,0)));
        cash.setFromString("1,2,3,4,5,6,7,8,9,10,11");
        cash.setFromString("1,2,3,4,5,6,9,8,7,10");
        assertEquals(cash.getAmounts(), amounts, "Strings should match");
    }



}

