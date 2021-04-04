package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ItemTest {

    @Test void itemCreation(){
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        assertNotNull(item, "Should return item instance");
    }

    @Test void getNameTest(){
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        assertEquals(item.getName(), "Mineral Water");
    }

    @Test void getCodeTest(){
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        assertEquals(item.getCode(), 1);
    }

    @Test void getCategoryTest(){
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        assertEquals(item.getCategory(), "Drinks");
    }

    @Test void getQuantityTest(){
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        assertEquals(item.getQuantity(), 7);
    }

    @Test void getPriceTest(){
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        assertEquals(item.getPrice(), 2.00);
    }

    @Test void setNameTest(){
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        item.setName("Water");
        assertEquals(item.getName(), "Water");
    }

    @Test void setCodeTest(){
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        item.setCode(2);
        assertEquals(item.getCode(), 2);
    }

    @Test void setCategoryTest(){
        Item item = new Item("Mineral Water", 1, "Chocolate", 7, 2.00);
        item.setCategory("Drinks");
        assertEquals(item.getCategory(), "Drinks");
    }

    @Test void setQuantityTest(){
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        item.setQuantity(item.getQuantity()-2);
        assertEquals(item.getQuantity(), 5);
    }

    @Test void setPriceTest(){
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        item.setPrice(100.00);
        assertEquals(item.getPrice(), 100.00);
    }

    @Test void testStringFormatting() {
        Item item = new Item("Mineral Water", 1, "Drinks", 7, 2.00);
        assertEquals(item.formatForExport(), "Mineral Water,1,Drinks,7,2.0");
    }

    @Test void testStringRestoration() {
        Item item = new Item("Omega Snacks", 72, "Exotic Material", 1, 100.0);
        item.setFromString("Mineral Water,1,Drinks,7,2.00");
        item.setFromString("alpha,beta,gamma,delta");
        assertEquals(item.getName(), "Mineral Water");
        assertEquals(item.getCode(), 1);
        assertEquals(item.getCategory(), "Drinks");
        assertEquals(item.getQuantity(), 7);
        assertEquals(item.getPrice(), 2.0);
    }

}
