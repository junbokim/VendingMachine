package R16A_Group_6_A2;

import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ShoppingCartTest {

    private ShoppingCart cart;
    private ItemData itemData;

    public ShoppingCartTest() {
        this.itemData = new ItemData(false);
        this.cart = new ShoppingCart(this.itemData);
        this.cart.addItem("Coca cola", 4);
        this.cart.addItem("Bounty", 3);
    }

    @Test
    public void testConstruction() {
        assertNotNull(this.cart);
    }

    @Test
    public void testGetItems() {
        HashMap<Item, Integer> items = new HashMap<>();
        assertEquals(0, items.size());
        items = this.cart.getItems();
        assertEquals(2, items.size());
        assertEquals(11,cart.getPrice());
    }

    @Test
    public void testGetNames() {
        List<String> names = this.cart.getNames();
        assertTrue(names.contains("Coca cola"));
        assertTrue(names.contains("Bounty"));
    }

    @Test
    public void testGetQuantity() {
        assertEquals(4, this.cart.getQuantity("Coca cola"));
        assertEquals(3, this.cart.getQuantity("Bounty"));
    }

    @Test
    public void testGetEmptyCartQuantity() {
        ShoppingCart emptyCart = new ShoppingCart(this.itemData);
        assertEquals(0, emptyCart.getQuantity("Coca cola"));
    }

    @Test
    public void testUpdateQuantity() {
        assertEquals(4, this.cart.getQuantity("Coca cola"));
        this.cart.updateQuantity("Coca cola", 1);
        assertEquals(5, this.cart.getQuantity("Coca cola"));
    }

    @Test
    public void testLargeUpdateQuantity() {
        assertEquals(3, this.cart.getQuantity("Bounty"));
        this.cart.updateQuantity("Bounty", 20);
        assertEquals(7, this.cart.getQuantity("Bounty"));
    }

    @Test
    public void testAddItem() {
        assertEquals(2, this.cart.getItems().size());
        this.cart.addItem("Sour Patch", 4);
        assertEquals(3, this.cart.getItems().size());
        assertEquals(4, this.cart.getQuantity("Sour Patch"));
        this.cart.addItem("Sour Patch", 1);
        assertEquals(5, this.cart.getQuantity("Sour Patch"));
    }
    
    @Test
    public void testAddItemLargeQuantity() {
        assertEquals(2, this.cart.getItems().size());
        this.cart.addItem("Sour Patch", 100);
        assertEquals(3, this.cart.getItems().size());
        assertEquals(7, this.cart.getQuantity("Sour Patch"));
    }

    @Test
    public void testRemoveItem() {
        assertEquals(2, this.cart.getItems().size());
        this.cart.removeItem("Bounty");
        assertEquals(1, this.cart.getItems().size());
        List<String> itemList = this.cart.getNames();
        assert itemList.contains("Coca cola");
        assertFalse(itemList.contains("Bounty"));
    }
}
