package R16A_Group_6_A2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ItemData {
    private ArrayList<Item> items;
    private ArrayList<String> categories;

    private ArrayList<ArrayList<String>> itemNames;

    public ItemData(){

        clearStructures();

        fillDefaults();

        loadPersistentData();
    }

    public ItemData(boolean load){

        clearStructures();

        fillDefaults();
        if (load) {
            loadPersistentData();
        }
    }

    private void clearStructures(){
        this.itemNames = new ArrayList<>();

        // Categories
        this.categories = new ArrayList<>();
        this.addCategory("Drinks");
        this.addCategory("Chocolates");
        this.addCategory("Chips");
        this.addCategory("Candies");

        this.items = new ArrayList<>();
    }

    private void fillDefaults(){
        this.addItem("Mineral Water", 1, "Drinks", 7, 2.00);
        this.addItem("Sprite", 2, "Drinks", 7, 2.00);
        this.addItem("Coca cola", 3, "Drinks", 7, 2.00);
        this.addItem("Pepsi", 4, "Drinks", 7, 2.00);
        this.addItem("Juice", 5, "Drinks", 7, 2.50);

        this.addItem("Mars", 6, "Chocolates", 7, 1.00);
        this.addItem("M&M", 7, "Chocolates", 7, 1.00);
        this.addItem("Bounty", 8, "Chocolates", 7, 1.00);
        this.addItem("Snickers", 9, "Chocolates", 7, 2.00);

        this.addItem("Smiths", 10, "Chips", 7, 3.50);
        this.addItem("Pringles", 11, "Chips", 7, 5.00);
        this.addItem("Kettle", 12, "Chips", 7, 4.00);
        this.addItem("Thins", 13, "Chips", 7, 4.50);

        this.addItem("Mentos", 14, "Candies", 7, 1.00);
        this.addItem("Sour Patch", 15, "Candies", 7, 1.50);
        this.addItem("Skittles", 16, "Candies", 7, 1.00);
    }

    private void addItem(String name, int code, String category, int quantity, double price){ // This is INTERNAL - needs connection to persisdat if made external
        Item newItem = new Item(name, code, category, quantity, price);
        items.add(newItem);
        itemNames.get(getItemNamesIndex(category)).add(name);

    }

    private void addItem(Item item){ // This is INTERNAL - needs connection to persisdat if made external
        items.add(item);
        itemNames.get(getItemNamesIndex(item.getCategory())).add(item.getName());

    }

    private void addCategory(String name){ // This is INTERNAL - needs connection to persisdat if made external
        categories.add(name);
        itemNames.add(new ArrayList<>());
    }

    public ArrayList<Item> getItems(){
        return this.items;
    }

    public ArrayList<String> getCategories(){
        return this.categories;
    }

    public ArrayList<ArrayList<String>> getItemNames(){
        return this.itemNames;
    }

    public int getItemNamesIndex(String category){
        return categories.indexOf(category);
    }

    // Called when a customer enters a quantity for their purchase of an item
    public boolean inStock(Item item, int quantity){
        if(item.getQuantity()-quantity >= 0){
            return true;
        }

        return false;
    }

    // returns true if name change was successful
    // returns false if there is already an item with the same name
    public boolean changeItemName(Item item, String newName){
        for(Item i: items){
            if(i.getName().equals(newName)){
                return false;
            }
        }
        int index = getItemNamesIndex(item.getCategory());
        for(int n = 0; n < itemNames.get(index).size(); n++ ){
            if(itemNames.get(index).get(n).equals(item.getName())){
                itemNames.get(index).set(n, newName);
                break;
            }
        }
        item.setName(newName);

        storePersistentData();

        return true;
    }

    // returns true if code change was successful
    // returns false if there is already an item with the same code
    public boolean changeItemCode(Item item, int newCode){
        for(Item i: items){
            if(i.getCode() == newCode){
                return false;
            }
        }
        item.setCode(newCode);
        storePersistentData();
        return true;
    }

    // returns true if code change was successful
    // returns false if the category entered is invalid
    public boolean changeItemCategory(Item item, String category){
        for(String c: categories){
            if(c.toLowerCase().equals(category.toLowerCase())){
                itemNames.get(getItemNamesIndex(item.getCategory())).remove(item.getName());
                itemNames.get(getItemNamesIndex(category)).add(item.getName());
                item.setCategory(c);

                storePersistentData();

                return true;
            }
        }
        return false;
    }

    // When customer buys something, in order to remove from inventory
    // the quantity parameter would be item.getQuantity - customerBuyQuantity
    // When Seller updates inventory, quantity parameter would just be the new quantity.
    public boolean changeItemQuantity(Item item, int quantity){
        if(quantity < 0 || quantity > 15){
            return false;
        }
        item.setQuantity(quantity);
        storePersistentData();
        return true;
    }

    public boolean changeItemPrice(Item item, double newPrice){
        if(newPrice <= 0){
            return false;
        }
        item.setPrice(newPrice);
        storePersistentData();
        return true;
    }

    public void getAvailableItemsReport(){
        try {
            File file = new File("reports/available_items_report.txt");
            new File("reports").mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);

            writer.println("Available Items Report");
            writer.println("------------------------------");

            for (int i=0; i<items.size();i++) {
                writer.print("Product name: ");
                writer.println(items.get(i).getName());
                writer.print("Product code: ");
                writer.println(items.get(i).getCode());
                writer.print("Category: ");
                writer.println(items.get(i).getCategory());
                writer.print("Quantity: ");
                writer.println(items.get(i).getQuantity());
                writer.print("Price: $");
                writer.println(items.get(i).getPrice());
                writer.print("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

    private void storePersistentData(){
        try {
            File file = new File("data/items.txt");
            new File("data").mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            for (Item item : items) {
                writer.println(item.formatForExport());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

    private void loadPersistentData(){

        File file = new File("data/items.txt");
        if(file.exists() && !file.isDirectory()) {

            try {
                clearStructures(); // Clear it out to load data

                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()){
                    String nextLine = reader.nextLine();
                    Item newItem = new Item("",0,"",0,0.0);
                    newItem.setFromString(nextLine);
                    addItem(newItem);

                }
                reader.close();
            } catch (FileNotFoundException e) {
                //System.out.println("An error occurred.");
                //e.printStackTrace();
            }
        }

    }

}
