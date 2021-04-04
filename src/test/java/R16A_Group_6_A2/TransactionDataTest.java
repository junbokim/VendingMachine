package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDataTest {
    @Test
    void transactionDataCreationTest() {
        ItemData itemData = new ItemData(false);
        TransactionData transactionData = new TransactionData(itemData, false);
        assertNotNull(transactionData);
        assertNotNull(new TransactionData(itemData));
    }

    @Test
    void createSuccessfulTransactionTest() throws InterruptedException {
        ItemData itemData = new ItemData(false);
        TransactionData transactionData = new TransactionData(itemData, false);

        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;

        transactionData.createSuccessfulTransaction(itemsSold,amount,5,Transaction.PaymentMethod.CASH);
        Transaction t = transactionData.getSuccessful().get(0);

        assertEquals(transactionData.getSuccessful().size(), 1);
        assertEquals(transactionData.getUnsuccessful().size(), 0);

        Thread.sleep(100);

        assertTrue(t.getDate().before(new Timestamp(System.currentTimeMillis())));
        assertEquals(itemData.getItems().get(0).getQuantity(), 5);
        assertEquals(itemData.getItems().get(1).getQuantity(), 6);
    }

    @Test
    void createUnsuccessfulTransactionGuestTest() throws InterruptedException {
        ItemData itemData = new ItemData(false);
        TransactionData transactionData = new TransactionData(itemData, false);

        UserData userData = new UserData();
        User guest = userData.getGuest();

        transactionData.createUnsuccessfulTransaction(guest,"Insufficient funds");
        Transaction t = transactionData.getUnsuccessful().get(0);

        assertEquals(transactionData.getSuccessful().size(), 0);
        assertEquals(transactionData.getUnsuccessful().size(), 1);

        Thread.sleep(100);

        assertTrue(t.getDate().before(new Timestamp(System.currentTimeMillis())));
        assertEquals(t.getUsername(), "anonymous");
    }

    @Test
    void createUnsuccessfulTransactionCustomerTest() throws InterruptedException {
        ItemData itemData = new ItemData(false);
        TransactionData transactionData = new TransactionData(itemData, false);

        User user = new User("Jo", "jo", User.UserType.CUSTOMER);

        transactionData.createUnsuccessfulTransaction(user,"Insufficient funds");
        Transaction t = transactionData.getUnsuccessful().get(0);

        assertEquals(transactionData.getSuccessful().size(), 0);
        assertEquals(transactionData.getUnsuccessful().size(), 1);

        Thread.sleep(100);

        assertTrue(t.getDate().before(new Timestamp(System.currentTimeMillis())));
        assertEquals(t.getUsername(), "Jo");
    }

    @Test
    void persistentDataTest() throws InterruptedException {
        ItemData itemData = new ItemData(false);
        TransactionData transactionData = new TransactionData(itemData, false);

        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;

        transactionData.createSuccessfulTransaction(itemsSold,amount,5,Transaction.PaymentMethod.CASH);
        Transaction t = transactionData.getSuccessful().get(0);

        ItemData newItemData = new ItemData(true);
        TransactionData newTransactionData = new TransactionData(itemData, true);

        assertEquals(newTransactionData.getSuccessful().size(), 1);
        assertEquals(newTransactionData.getUnsuccessful().size(), 0);

        Thread.sleep(100);

        assertTrue(t.getDate().before(new Timestamp(System.currentTimeMillis())));
        assertEquals(newItemData.getItems().get(0).getQuantity(), 5);
        assertEquals(newItemData.getItems().get(1).getQuantity(), 6);

        //REVERT VALUES BACK TO DEFAULT
        boolean changeBack = newItemData.changeItemQuantity(newItemData.getItems().get(0), 7);
        boolean changeBack1 = newItemData.changeItemQuantity(newItemData.getItems().get(1), 7);
    }

    @Test
    void createSuccessfulReportTest(){
        ItemData itemData = new ItemData(false);
        TransactionData transactionData = new TransactionData(itemData, false);

        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;

        transactionData.createSuccessfulTransaction(itemsSold,amount,5,Transaction.PaymentMethod.CASH);
        transactionData.getSuccessfulReport();
        // Get the file
        File f = new File("reports/successful_transactions_report.txt");

        // Check if the specified file
        // Exists or not
        assertTrue(f.exists());
        f.delete();
    }

    @Test
    void createUnsuccessfulReportTest(){
        ItemData itemData = new ItemData(false);
        TransactionData transactionData = new TransactionData(itemData, false);

        User user = new User("Jo", "jo", User.UserType.CUSTOMER);

        transactionData.createUnsuccessfulTransaction(user,"Insufficient funds");
        transactionData.getUnsuccessfulReport();
        // Get the file
        File f = new File("reports/unsuccessful_transactions_report.txt");

        // Check if the specified file
        // Exists or not
        assertTrue(f.exists());
        f.delete();
    }

    @Test
    void createItemDetailsTest() throws InterruptedException {
        ItemData itemData = new ItemData(false);
        TransactionData transactionData = new TransactionData(itemData, false);

        transactionData.getItemDetailsReport();
        // Get the file
        File f = new File("reports/item_details_report.txt");

        // Check if the specified file
        // Exists or not
        assertTrue(f.exists());
        f.delete();
    }
}

