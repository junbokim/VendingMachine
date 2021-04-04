package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDataTest {

    @Test
    void itemDataCreation(){
        assertNotNull(new ItemData(), "Should return ItemData instance");
        assertNotNull(new ItemData(true), "Should return ItemData instance");
        assertNotNull(new ItemData(false), "Should return ItemData instance");
    }

    @Test
    void getItemsTest(){
        ItemData itemData = new ItemData(false);
        ArrayList<Item> items = itemData.getItems();
        assertNotNull(items);
        assertEquals(items.size(), 16);
        assertEquals(items.get(5).getName(), "Mars");
        assertEquals(items.get(7).getCode(), 8);
    }

    @Test
    void getCategoriesTest(){
        ItemData itemData = new ItemData(false);
        ArrayList<String> cats = itemData.getCategories();
        assertNotNull(cats);
        assertEquals(cats.size(), 4);
        assertEquals(cats.get(3), "Candies");
    }

    @Test
    void getItemNamesTest(){
        ItemData itemData = new ItemData(false);
        ArrayList<ArrayList<String>> names = itemData.getItemNames();
        assertNotNull(names);
        assertEquals(names.size(), 4);
    }

    @Test
    void getItemNamesIndexTest(){
        ItemData itemData = new ItemData(false);
        int wrongInput = itemData.getItemNamesIndex("Food");
        int drinks = itemData.getItemNamesIndex("Drinks");
        int choco = itemData.getItemNamesIndex("Chocolates");
        int chips = itemData.getItemNamesIndex("Chips");
        int candies = itemData.getItemNamesIndex("Candies");
        assertEquals(wrongInput, -1);
        assertEquals(drinks, 0);
        assertEquals(choco, 1);
        assertEquals(chips, 2);
        assertEquals(candies, 3);
    }

    @Test
    void inStockTest(){
        ItemData itemData = new ItemData(false);
        boolean inStock = itemData.inStock(itemData.getItems().get(3), 1);
        boolean notInStock = itemData.inStock(itemData.getItems().get(5), 8);
        assertEquals(inStock, true);
        assertEquals(notInStock, false);
    }

    @Test
    void changeItemNameTest(){
        ItemData itemData = new ItemData(false);
        boolean badInput = itemData.changeItemName(itemData.getItems().get(10),"Sprite");
        boolean goodInput = itemData.changeItemName(itemData.getItems().get(10),"Pringles Original");
        boolean goodInput1 = itemData.changeItemName(itemData.getItems().get(2),"Coke Zero");
        assertEquals(badInput, false);
        assertEquals(goodInput, true);
        assertEquals(goodInput1, true);
        assertEquals(itemData.getItemNames().get(2).get(1), "Pringles Original");
        assertEquals(itemData.getItems().get(10).getName(), "Pringles Original");
        assertEquals(itemData.getItemNames().get(0).get(2), "Coke Zero");
        assertEquals(itemData.getItems().get(2).getName(), "Coke Zero");


        //REVERT VALUES BACK TO DEFAULT
        boolean pringles = itemData.changeItemName(itemData.getItems().get(10),"Pringles");
        boolean coca_cola = itemData.changeItemName(itemData.getItems().get(2),"Coca Cola");
    }

    @Test
    void changeItemCodeTest(){
        ItemData itemData = new ItemData(false);
        boolean badInput = itemData.changeItemCode(itemData.getItems().get(2),1);
        boolean goodInput = itemData.changeItemCode(itemData.getItems().get(2),18);
        assertEquals(badInput, false);
        assertEquals(goodInput, true);
        assertEquals(itemData.getItems().get(2).getCode(), 18);
    }

    @Test
    void changeItemCategoryTest(){
        ItemData itemData = new ItemData(false);
        boolean badInput = itemData.changeItemCategory(itemData.getItems().get(15),"1");
        boolean goodInput = itemData.changeItemCategory(itemData.getItems().get(15),"Chocolates");
        assertEquals(badInput, false);
        assertEquals(goodInput, true);
        assertEquals(itemData.getItemNames().get(1).get(4), "Skittles");
        assertEquals(itemData.getItemNames().get(itemData.getItemNamesIndex("Candies")).size(), 2);
        assertEquals(itemData.getItems().get(15).getCategory(), "Chocolates");
    }

    @Test
    void changeItemQuantityTest(){
        ItemData itemData = new ItemData(false);
        boolean badInput = itemData.changeItemQuantity(itemData.getItems().get(13),-100);
        boolean badInput2 = itemData.changeItemQuantity(itemData.getItems().get(13),20);
        boolean goodInput = itemData.changeItemQuantity(itemData.getItems().get(13),12);
        assertEquals(badInput, false);
        assertEquals(badInput2, false);
        assertEquals(goodInput, true);
        assertEquals(itemData.getItems().get(13).getQuantity(),12);
    }

    @Test
    void changeItemPriceTest(){
        ItemData itemData = new ItemData(false);
        boolean badInput = itemData.changeItemPrice(itemData.getItems().get(4), -20);
        boolean goodInput = itemData.changeItemPrice(itemData.getItems().get(4), 20);
        assertEquals(badInput, false);
        assertEquals(goodInput, true);
        assertEquals(itemData.getItems().get(4).getPrice(), 20);
    }

    @Test
    void testPersistentData() { // Simplified changeItemCategoryTest, split over two
        ItemData original = new ItemData(false);
        boolean badInput = original.changeItemCode(original.getItems().get(2),1);
        boolean goodInput = original.changeItemCode(original.getItems().get(2),18);

        ItemData newData = new ItemData();
        assertEquals(newData.getItems().get(2).getCode(), 18);
    }

    @Test
    void createAvailableItemsReportTest(){
        ItemData itemData = new ItemData(false);
        itemData.getAvailableItemsReport();
        // Get the file
        File f = new File("reports/available_items_report.txt");

        // Check if the specified file
        // Exists or not
        assertTrue(f.exists());

        f.delete();
    }


}
