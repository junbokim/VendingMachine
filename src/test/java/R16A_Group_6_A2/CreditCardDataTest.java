package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardDataTest {

    @Test void creditCardDataCreation() {
        CreditCardData creditCardData = new CreditCardData();
        assertNotNull(creditCardData, "should return creditCardData instance");
    }

    @Test void incorrectCard() {
        CreditCardData creditCardData = new CreditCardData();
        assertFalse(creditCardData.verifyCard("Charles", "54321"), "incorrect card number provided");
        assertFalse(creditCardData.verifyCard("Robert", "40691"), "incorrect cardholder name provided");
    }

    @Test void correctCard() {
        CreditCardData creditCardData = new CreditCardData();
        assertTrue(creditCardData.verifyCard("Charles", "40691"), "correct cardholder name and number provided");
    }

    @Test void addCard() {
        CreditCardData creditCardData = new CreditCardData();
        assertTrue(creditCardData.addCard("Athena", "13498"));
        assertTrue(creditCardData.verifyCard("Athena", "13498"), "Card details should match");
        creditCardData.removeCard("13498");
    }

    @Test void removeCard() {
        CreditCardData creditCardData = new CreditCardData();
        creditCardData.removeCard("40691");
        assertFalse(creditCardData.verifyCard("Charles", "40691"), "Card details should not match");
        assertFalse(creditCardData.removeCard("40691"),"Should not be able to remove card *twice*");

        // Fix everything back up again
        creditCardData.addCard("Charles", "40691");
    }

    @Test void testDataStorage() {

        CreditCardData creditCardData = new CreditCardData();
        creditCardData.addCard("Diana", "71969");

        CreditCardData loaded = new CreditCardData();
        assertTrue(loaded.verifyCard("Diana", "71969"), "Card details should match");
        loaded.removeCard("71969");
    }

}