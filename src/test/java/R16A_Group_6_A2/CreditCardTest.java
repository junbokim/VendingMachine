package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    @Test void creditCardCreation() {
        CreditCard card = new CreditCard("John", "12345");
        assertNotNull(card, "should return card instance");
    }

    @Test void incorrectCard() {
        CreditCard card = new CreditCard("John", "12345");
        assertFalse(card.verify("Jane", "12345"), "incorrect name is provided");
        assertFalse(card.verify("John", "54321"), "incorrect number is provided");
    }

    @Test void correctCard() {
        CreditCard card = new CreditCard("John", "12345");
        assertTrue(card.verify("John", "12345"), "correct name and number is provided");
    }

    @Test void getName() {
        CreditCard card = new CreditCard("John", "12345");
        assertEquals(card.getName(), "John", "username should be the same");
    }

    @Test void getNumber() {
        CreditCard card = new CreditCard("John", "12345");
        assertEquals(card.getNumber(), "12345", "number should be the same");
    }

}