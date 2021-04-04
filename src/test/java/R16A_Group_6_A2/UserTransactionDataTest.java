package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.ArrayList;

public class UserTransactionDataTest {

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    @Test void userTransactionDataCreation() {
        UserTransactionData userTransaction = new UserTransactionData();
        assertNotNull(userTransaction, "constructor should not return null");
    }

    @Test void addUserTransaction() {
        UserTransactionData userTransaction = new UserTransactionData(false);

        ItemData itemData = new ItemData();
        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }
        Transaction successful = new Transaction(timestamp,itemNames,amount,0, Transaction.PaymentMethod.CARD);
        assertTrue(userTransaction.addUserTransaction("Artemis", successful), "should successfully input");
    }

    @Test void getLatest() {
        UserTransactionData userTransaction = new UserTransactionData(false);

        ItemData itemData = new ItemData();
        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }
        Transaction successful = new Transaction(timestamp,itemNames,amount,0, Transaction.PaymentMethod.CARD);
        assertTrue(userTransaction.addUserTransaction("Artemis", successful), "should successfully input");

        ArrayList<String> latestTest = new ArrayList<>();
        latestTest.add(itemData.getItems().get(0).getName());
        latestTest.add(itemData.getItems().get(1).getName());

        ArrayList<String> latest = userTransaction.getLatest("Artemis");

        assertEquals(latestTest, latest, "latest items should match");
    }

    @Test void multipleTransaction() {
        UserTransactionData userTransaction = new UserTransactionData(false);

        ItemData itemData = new ItemData();
        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }
        Transaction successful = new Transaction(timestamp,itemNames,amount,0, Transaction.PaymentMethod.CARD);
        assertTrue(userTransaction.addUserTransaction("Artemis", successful), "should successfully input");

        Timestamp newTime = new Timestamp(System.currentTimeMillis() + 50000);
        Transaction successful2 = new Transaction(newTime,itemNames,amount,0, Transaction.PaymentMethod.CARD);
        assertTrue(userTransaction.addUserTransaction("Artemis", successful2), "should successfully input");

        ArrayList<String> latestTest = new ArrayList<>();
        latestTest.add(itemData.getItems().get(0).getName());
        latestTest.add(itemData.getItems().get(1).getName());

        ArrayList<String> latest = userTransaction.getLatest("Artemis");

        assertEquals(latestTest, latest, "latest items should match");

    }

    @Test void duplicateTransaction() {
        UserTransactionData userTransaction = new UserTransactionData(false);

        ItemData itemData = new ItemData();
        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }
        Transaction successful = new Transaction(timestamp,itemNames,amount,0, Transaction.PaymentMethod.CARD);

        String username = "Artemis";
        assertTrue(userTransaction.addUserTransaction("Artemis", successful), "should successfully input");
        assertFalse(userTransaction.addUserTransaction("Artemis", successful), "should not input");

        ArrayList<String> latestTest = new ArrayList<>();
        latestTest.add(itemData.getItems().get(0).getName());
        latestTest.add(itemData.getItems().get(1).getName());

        ArrayList<String> latest = userTransaction.getLatest("Artemis");

        assertEquals(latestTest, latest, "latest items should match");
    }

    @Test void moreThanFiveItem() {
        UserTransactionData userTransaction = new UserTransactionData(false);

        ItemData itemData = new ItemData();
        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        itemsSold.put(itemData.getItems().get(2), 1);
        itemsSold.put(itemData.getItems().get(3), 1);
        itemsSold.put(itemData.getItems().get(4), 1);
        itemsSold.put(itemData.getItems().get(5), 1);
        itemsSold.put(itemData.getItems().get(6), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }
        Transaction successful = new Transaction(timestamp,itemNames,amount,0, Transaction.PaymentMethod.CARD);
        assertTrue(userTransaction.addUserTransaction("Artemis", successful), "should successfully input");

        ArrayList<String> latestTest = new ArrayList<>();
        latestTest.add(itemData.getItems().get(0).getName());
        latestTest.add(itemData.getItems().get(1).getName());
        latestTest.add(itemData.getItems().get(2).getName());
        latestTest.add(itemData.getItems().get(3).getName());
        latestTest.add(itemData.getItems().get(4).getName());

        ArrayList<String> latest = userTransaction.getLatest("Artemis");

        assertEquals(latestTest, latest, "latest items should match");
    }

    @Test void complexTest() { // This shows how the order of latest product bought work
        UserTransactionData userTransaction = new UserTransactionData(false);

        ItemData itemData = new ItemData();
        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(12), 2);
        itemsSold.put(itemData.getItems().get(10), 1);
        itemsSold.put(itemData.getItems().get(3), 1);
        itemsSold.put(itemData.getItems().get(2), 1);
        itemsSold.put(itemData.getItems().get(0), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }

        HashMap<Item, Integer> itemsSold2 = new HashMap<>();
        itemsSold2.put(itemData.getItems().get(7), 2);
        itemsSold2.put(itemData.getItems().get(9), 2);
        itemsSold2.put(itemData.getItems().get(12), 1);
        double amount2 = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames2 = new HashMap<>();
        for(Item i : itemsSold2.keySet()){
            itemNames2.put(i.getName(),itemsSold2.get(i));
        }

        Transaction successful = new Transaction(timestamp,itemNames,amount,0, Transaction.PaymentMethod.CARD);
        assertTrue(userTransaction.addUserTransaction("Artemis", successful), "should successfully input");

        Timestamp newTime = new Timestamp(System.currentTimeMillis() + 50000);
        Transaction successful2 = new Transaction(newTime,itemNames2,amount2,0, Transaction.PaymentMethod.CARD);
        assertTrue(userTransaction.addUserTransaction("Artemis", successful2), "should successfully input");

        ArrayList<String> latestTest = new ArrayList<>(); // The Latest Item bought prioritizes the timestamps
        latestTest.add(itemData.getItems().get(0).getName());   // Then the item ids which as you can see
        latestTest.add(itemData.getItems().get(2).getName());   // item id 7,9,12 from successful2 transaction is all there
        latestTest.add(itemData.getItems().get(7).getName());   // but only 0,2 from successful is there because the system
        latestTest.add(itemData.getItems().get(9).getName());   // requests 5 items not just 3, so the 2 lowest item ids from the
        latestTest.add(itemData.getItems().get(12).getName());  // previous transactions are used

        ArrayList<String> latest = userTransaction.getLatest("Artemis");

        assertEquals(latestTest, latest, "latest items should match");

    }

    @Test void testPersistentData() {
        UserTransactionData userTransaction = new UserTransactionData(false);
        ItemData itemData = new ItemData();
        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        itemsSold.put(itemData.getItems().get(2), 3);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }
        Transaction successful = new Transaction(timestamp,itemNames,amount,0, Transaction.PaymentMethod.CARD);
        Transaction duplicate = new Transaction(timestamp,itemNames,amount,0, Transaction.PaymentMethod.CARD);
        userTransaction.addUserTransaction("Artemis", successful);
        assertFalse(userTransaction.addUserTransaction("Artemis", duplicate), "duplicate transaction should not add");

        Timestamp newTime = new Timestamp(System.currentTimeMillis() + 50000);
        Transaction successful2 = new Transaction(newTime,itemNames,amount,0, Transaction.PaymentMethod.CARD);

        UserTransactionData loadTransaction = new UserTransactionData(true);

        assertEquals(userTransaction.getLatest("Artemis"), loadTransaction.getLatest("Artemis"), "latest product bought should match");
    }

}