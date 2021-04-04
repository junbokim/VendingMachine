package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test void userCreation(){
        User user = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        assertNotNull(user, "User constructor should return user instance");
    }


    @Test void isCustomer(){
        User customer = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        User cashier = new User("Artemis","thehuntress", User.UserType.CASHIER);
        User seller = new User("Artemis","thehuntress", User.UserType.SELLER);
        User owner = new User("Artemis","thehuntress", User.UserType.OWNER);

        assertTrue(customer.isCustomer(), "User should be customer");
        assertFalse(cashier.isCustomer(), "User should not be customer");
        assertFalse(seller.isCustomer(), "User should not be customer");
        assertFalse(owner.isCustomer(), "User should not be customer");
    }

    @Test void isCashier(){
        User customer = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        User cashier = new User("Artemis","thehuntress", User.UserType.CASHIER);
        User seller = new User("Artemis","thehuntress", User.UserType.SELLER);
        User owner = new User("Artemis","thehuntress", User.UserType.OWNER);

        assertFalse(customer.isCashier(), "User should not be cashier");
        assertTrue(cashier.isCashier(), "User should be cashier");
        assertFalse(seller.isCashier(), "User should not be cashier");
        assertFalse(owner.isCashier(), "User should not be cashier");
    }

    @Test void isSeller(){
        User customer = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        User cashier = new User("Artemis","thehuntress", User.UserType.CASHIER);
        User seller = new User("Artemis","thehuntress", User.UserType.SELLER);
        User owner = new User("Artemis","thehuntress", User.UserType.OWNER);

        assertFalse(customer.isSeller(), "User should not be seller");
        assertFalse(cashier.isSeller(), "User should not be seller");
        assertTrue(seller.isSeller(), "User should be seller");
        assertFalse(owner.isSeller(), "User should not be seller");
    }

    @Test void isOwner(){
        User customer = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        User cashier = new User("Artemis","thehuntress", User.UserType.CASHIER);
        User seller = new User("Artemis","thehuntress", User.UserType.SELLER);
        User owner = new User("Artemis","thehuntress", User.UserType.OWNER);

        assertFalse(customer.isOwner(), "User should not be owner");
        assertFalse(cashier.isOwner(), "User should not be owner");
        assertFalse(seller.isOwner(), "User should not be owner");
        assertTrue(owner.isOwner(), "User should be owner");
    }

    @Test void isGuest(){
        User customer = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        User cashier = new User("Artemis","thehuntress", User.UserType.CASHIER);
        User seller = new User("Artemis","thehuntress", User.UserType.SELLER);
        User owner = new User("Artemis","thehuntress", User.UserType.OWNER);
        User guest = new User("Artemis","thehuntress", User.UserType.GUEST);

        assertFalse(customer.isGuest(), "User should not be guest");
        assertFalse(cashier.isGuest(), "User should not be guest");
        assertFalse(seller.isGuest(), "User should not be guest");
        assertFalse(owner.isGuest(), "User should not be guest");
        assertTrue(guest.isGuest(), "User should be guest");
    }

    @Test void isSuperUser(){
        User customer = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        User cashier = new User("Artemis","thehuntress", User.UserType.CASHIER);
        User seller = new User("Artemis","thehuntress", User.UserType.SELLER);
        User owner = new User("Artemis","thehuntress", User.UserType.OWNER);
        User superuser = new User("Artemis","thehuntress", User.UserType.SUPERUSER);

        assertFalse(customer.isGuest(), "User should not be superuser");
        assertFalse(cashier.isGuest(), "User should not be superuser");
        assertFalse(seller.isGuest(), "User should not be superuser");
        assertFalse(owner.isGuest(), "User should not be superuser");
        assertTrue(superuser.isSuperUser(), "User should be superuser");
    }

    @Test void attemptIncorrectLogin(){
        User user = new User("Artemis","thehuntress", User.UserType.CUSTOMER);

        assertFalse(
                user.tryLogIn("Artemis","ofthestars"),
                "User should not be able to login on incorrect credentials");

        assertFalse(
                user.tryLogIn("Asteria","thehuntress"),
                "User should not be able to login on incorrect credentials");
    }

    @Test void attemptCorrectLogin(){
        User user = new User("Artemis","thehuntress", User.UserType.CUSTOMER);

        assertTrue(
                user.tryLogIn("Artemis","thehuntress"),
                "User should be able to login on correct credentials");
    }

    @Test void getUsername(){
        String usernameTest = "Artemis";
        User user = new User(usernameTest,"thehuntress", User.UserType.CUSTOMER);

        assertEquals(user.getUsername(), usernameTest, "Usernames should match");
    }

    @Test void changeUserType(){
        User changeToCashier = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        changeToCashier.changeType(User.UserType.CASHIER);
        assertTrue(changeToCashier.isCashier(), "User should be cashier");
    }

    @Test void getCard() {
        User user = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        assertNull(user.getCard(), "Card should not exist");
    }

    @Test void setCard(){
        User user = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        assertNull(user.getCard(), "Card should not exist");
        String cardName = "Artemis";
        String cardNumber = "12345";
        user.setCard(cardName, cardNumber);
        
        CreditCard card = new CreditCard(cardName, cardNumber);
        assertEquals(user.getCard().getName(), card.getName(), "Card name should match");
        assertEquals(user.getCard().getNumber(), card.getNumber(), "Card number should match");

    }
  
    @Test void testStringFormatting() {
        User user = new User("Artemis","thehuntress", User.UserType.CUSTOMER);
        assertEquals(user.formatForExport(), "Artemis,thehuntress,CUSTOMER","String export should match");

        User user2 = new User("Artemis","thehuntress", User.UserType.CUSTOMER, "Artemis", "12345");
        assertEquals(user2.formatForExport(), "Artemis,thehuntress,CUSTOMER,Artemis,12345","String export should match");
    }

    @Test void testStringRestoration() {
        User user = new User("alpha","beta", User.UserType.OWNER);
        user.setFromString("Artemis,thehuntress,CUSTOMER");
        user.setFromString("alpha,beta,gamma,delta");
        assertTrue(user.isCustomer());
        assertTrue(user.tryLogIn("Artemis","thehuntress"));

        User user2 = new User("Bob","thebuilder", User.UserType.OWNER);
        user2.setFromString("Artemis,thehuntress,CUSTOMER,Artemis,12345");
        assertTrue(user2.isCustomer());
        assertTrue(user2.tryLogIn("Artemis","thehuntress"));

        CreditCard card = new CreditCard("Artemis", "12345");
        assertEquals(user2.getCard().getName(), card.getName(), "Card name should match");
        assertEquals(user2.getCard().getNumber(), card.getNumber(), "Card number should match");
    }
}