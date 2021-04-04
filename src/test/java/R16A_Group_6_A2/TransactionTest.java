package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class TransactionTest {
    @Test
    void successfulTransactionTest() {
        ItemData itemData = new ItemData();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }
        Transaction successful = new Transaction(timestamp,itemNames,amount,0, Transaction.PaymentMethod.CARD);
        assertNotNull(successful);
        assertEquals(successful.getDate(),timestamp);
        assertEquals(successful.getItemsSold(), itemNames);
        assertEquals(successful.getItemsSold().get("Mineral Water"), 2);
        assertEquals(successful.getAmount(), amount);
        assertEquals(successful.getReturnedChange(), 0);
        assertEquals(successful.getPaymentMethod(), Transaction.PaymentMethod.CARD);
    }

    @Test
    void unsuccessfulTransactionTest() {
        ItemData itemData = new ItemData();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User("Jo", "jo", User.UserType.CUSTOMER);
        Transaction unsuccessful = new Transaction(timestamp,user.getUsername(), "transaction timed out");
        assertNotNull(unsuccessful);
        assertEquals(unsuccessful.getDate(),timestamp);
        assertEquals(unsuccessful.getUsername(), "Jo");
        assertEquals(unsuccessful.getReason(), "transaction timed out");
    }

    @Test
    void convertToString() {
        ItemData itemData = new ItemData();
        Timestamp timestamp = new Timestamp(1604969509033L);
        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }
        Transaction successful = new Transaction(timestamp,itemNames,amount,0, Transaction.PaymentMethod.CARD);
        assertEquals(successful.formatForExport(),"1604969509033,6.0,0.0,CARD,null,null:Mineral Water,2;Sprite,1");
    }

    @Test
    void badStringRestore() {
        Transaction successful = new Transaction(null,null,13.5,0, Transaction.PaymentMethod.CARD);
        assertFalse(successful.setFromString("1604969509033,6.0,0.0,CARD:Mineral Water,2;Sprite,1"));
    }

    @Test
    void restoreFromString() {
        Transaction successful = new Transaction(null,null,2.3,0, Transaction.PaymentMethod.CASH);
        successful.setFromString("1604969509033,6.0,0.0,CARD,null,null:Mineral Water,2;Sprite,1");

        ItemData itemData = new ItemData();
        HashMap<Item, Integer> itemsSold = new HashMap<>();
        itemsSold.put(itemData.getItems().get(0), 2);
        itemsSold.put(itemData.getItems().get(1), 1);
        double amount = itemData.getItems().get(0).getPrice() * 2 + itemData.getItems().get(1).getPrice() * 1;
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }

        assertNotNull(successful);
        assertEquals(successful.getDate().getTime(),1604969509033L);
        assertEquals(successful.getItemsSold(), itemNames);
        assertEquals(successful.getItemsSold().get("Mineral Water"), 2);
        assertEquals(successful.getAmount(), amount);
        assertEquals(successful.getReturnedChange(), 0);
        assertEquals(successful.getPaymentMethod(), Transaction.PaymentMethod.CARD);
    }


}
