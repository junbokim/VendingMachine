package R16A_Group_6_A2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {

    // HashMap used to store the items in the shopping cart, with the key being the item and the value being the quantity in the cart
    private HashMap<Item, Integer> items;
    private ItemData itemData;

    public ShoppingCart(ItemData itemData) {
        this.items = new HashMap<>();
        this.itemData = itemData;
    }

    public HashMap<Item, Integer> getItems() { return this.items; }

    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        List<Item> itemList = new ArrayList<>(this.items.keySet());
        for (int i = 0; i < this.items.size(); i++) {
            names.add(itemList.get(i).getName());
        }
        return names;
    }

    public Integer getQuantity(String name) {
        List<Item> itemList = new ArrayList<>(this.items.keySet());
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getName().equals(name)) {
                return this.items.get(itemList.get(i));
            }
        }
        return 0;
    }

    public void updateQuantity(String name, int quantity) {
        List<Item> itemList = new ArrayList<>(this.items.keySet());
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getName().equals(name)) {
                Item item = itemList.get(i);
                if (this.items.get(item) + quantity <= item.getQuantity()) {
                    this.items.put(item, this.items.get(item) + quantity);
                }
                // The user has exceeded the quantity of this item (how much of it is in stock), therefore it is set to the max
                else {
                    this.items.put(item, item.getQuantity());
                }
            }
        }

    }

    public void addItem(String name, int quantity) {
        if (quantity == 0) {
            return;
        }
        for (int i = 0; i < this.itemData.getItems().size(); i++) {
            if (this.itemData.getItems().get(i).getName().equals(name)) {
                Item item = this.itemData.getItems().get(i);
                // We are only putting the item in the cart. We do not remove it from the Item's quantity yet as the transaction has not taken place
                int newQuantity = quantity;
                if (this.items.containsKey(item)){
                    newQuantity = this.items.get(item) + quantity;
                }
                if (newQuantity <= item.getQuantity()) {
                    this.items.put(item, newQuantity);
                }
                else {
                    this.items.put(item, item.getQuantity());
                }
            }
        }
    }

    public void removeItem(String name) {
        List<Item> itemList = new ArrayList<>(this.items.keySet());
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getName().equals(name)) {
                this.items.remove(itemList.get(i), this.items.get(itemList.get(i)));
            }
        }
    }

    public double getPrice(){
        double totalPrice = 0;
        for( Item item : this.items.keySet()){
            totalPrice += item.getPrice() * this.items.get(item);
        }
        return totalPrice;
    }

}