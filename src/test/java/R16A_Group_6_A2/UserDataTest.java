package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDataTest {
    @Test void userDataCreation(){
        UserData.setTestingFilepath();
        UserData userData = new UserData(true);
        assertNotNull(userData, "User constructor should not return null");
    }


    @Test void addUser(){
        UserData.setTestingFilepath();
        UserData userData = new UserData(false);
        userData.addUser("Artemis","thehuntress");
        assertNotNull(userData, "Basically, this shouldn't crash");
    }

    @Test void removeUser() {
        UserData.setTestingFilepath();
        UserData userData = new UserData(false);
        userData.addUser("Test", "Password");
        assertTrue(userData.getUserLookup().containsKey("Test"));
        userData.removeUser(new User("owner", "password", User.UserType.OWNER), "Test");
        assertFalse(userData.getUserLookup().containsKey("Test"));
    }

    @Test void addDoubleUser(){
        UserData.setTestingFilepath();
        UserData userData = new UserData(false);
        assertTrue(userData.addUser("Artemis","thehuntress"),"First user should succeed");
        assertFalse(userData.addUser("Artemis","ofthestars"), "Second user should not add");

        User loggedUser = userData.tryLogIn("Artemis", "thehuntress");
        assertEquals(loggedUser.getUsername(), "Artemis", "Logged in user should match");
        assertTrue(loggedUser.isCustomer(), "User should be customer");

        loggedUser = userData.tryLogIn("Artemis", "ofthestars");
        assertNull(loggedUser, "Second user should not be added");
    }


    @Test void tryLogin(){
        UserData.setTestingFilepath();
        UserData userData = new UserData(false);
        userData.addUser("Artemis","thehuntress");
        userData.addUser("Asteria","ofthestars");

        // Valid logins
        User loggedUser = userData.tryLogIn("Artemis", "thehuntress");
        assertEquals(loggedUser.getUsername(), "Artemis", "Logged in user should match");
        assertTrue(loggedUser.isCustomer(), "User should be customer");

        loggedUser = userData.tryLogIn("Asteria", "ofthestars");
        assertEquals(loggedUser.getUsername(), "Asteria", "Logged in user should match");
        assertTrue(loggedUser.isCustomer(), "User should be customer");

        

    }

    @Test void tryInvalidUsers(){
        UserData.setTestingFilepath();
        UserData userData = new UserData(false);
        userData.addUser("Artemis","thehuntress");
        userData.addUser("Asteria","ofthestars");

        User nullUser = userData.tryLogIn("Asteria", "thehuntress");
        assertNull(nullUser, "No valid user should be returned");

        nullUser = userData.tryLogIn("Jenkins", "theannoying");
        assertNull(nullUser, "No valid user should be returned");
    }

    @Test void tryLoginAsGuest(){
        UserData.setTestingFilepath();
        UserData userData = new UserData(false);
        User guestUser = userData.getGuest();
        assertNotNull(guestUser, "Guest user should be returned");
    }

    @Test void changeType(){
        UserData.setTestingFilepath();
        UserData userData = new UserData();
        String username = "Artemis";
        String password = "thehuntress";
        userData.addUser(username, password);
        User loggedUser = userData.tryLogIn(username, password);
        assertTrue(loggedUser.isCustomer(), "User should be customer");

        User owner = new User("Bob", "thebuilder", User.UserType.OWNER);
        assertFalse(userData.changeType(owner, "NotUser", User.UserType.CASHIER), "User not found");

        assertFalse(userData.changeType(owner, username, User.UserType.GUEST), "Cannot change User into GUEST");
        assertFalse(userData.changeType(owner, "guest", User.UserType.CUSTOMER), "Cannot change the Type of GUEST");




        userData.changeType(owner, username, User.UserType.CASHIER);
        assertTrue(loggedUser.isCashier(), "User should be cashier");



        User superuser = new User("Vulcan", "theforgemaster", User.UserType.SUPERUSER);

        userData.changeType(superuser, username, User.UserType.SELLER);
        assertTrue(loggedUser.isSeller(), "User should be seller");
        assertTrue(userData.changeType(owner, username, User.UserType.SUPERUSER),"User should change into Superuser");
        assertFalse(userData.changeType(owner, username, User.UserType.CUSTOMER), "Cannot change User into Superuser");
    }

    @Test void testReportGeneration(){
        UserData userData = new UserData(false);
        userData.addUser("Artemis","thehuntress");
        userData.addUser("Hypatia","thealchemist");
        User owner = new User("Bob", "thebuilder", User.UserType.OWNER);
        userData.changeType(owner, "Hypatia", User.UserType.SUPERUSER);

        userData.createUserReport();

        File file = new File("reports/users.txt");
        if(file.exists() && !file.isDirectory()) {
            try {
                Scanner reader = new Scanner(file);
                assertEquals(reader.nextLine(), "username,role");
                assertEquals(reader.nextLine(), "Hypatia,SUPERUSER");
                assertEquals(reader.nextLine(), "Artemis,CUSTOMER");

                reader.close();
            } catch (FileNotFoundException e) {
                //System.out.println("An error occurred.");
                //e.printStackTrace();
            }
        }
    }

    @Test void setCard() {
        UserData.setTestingFilepath();
        UserData userData = new UserData(false);
        String username = "Artemis";
        String password = "thehuntress";
        assertFalse(userData.setCard(username, "Artemis", "12345"), "User not found");
        userData.addUser(username, password);
        assertTrue(userData.setCard(username, "Artemis", "12345"), "Card details successfully inputted");
    }

    @Test void getCard() {
        UserData.setTestingFilepath();
        UserData userData = new UserData(false);
        assertNull(userData.getCard("Artemis"), "User does not exist");

        String username = "Artemis";
        String password = "thehuntress";
        userData.addUser(username, password);
        assertNull(userData.getCard(username), "User does not have any saved cards");

        String cardName = "Artemis";
        String cardNumber = "12345";
        assertTrue(userData.setCard(username, cardName, cardNumber), "Card details successfully inputted");

        CreditCard card = new CreditCard(cardName, cardNumber);
        assertEquals(userData.getCard(username).getName(), card.getName(), "Card name should match");
        assertEquals(userData.getCard(username).getNumber(), card.getNumber(), "Card number should match");


    }
  
    @Test void testPersistentData(){
        UserData.setTestingFilepath();
        UserData userData = new UserData(false);
        userData.addUser("Artemis","thehuntress");

        UserData newUserData = new UserData();
        assertNotNull(newUserData.tryLogIn("Artemis","thehuntress"));
    }

}