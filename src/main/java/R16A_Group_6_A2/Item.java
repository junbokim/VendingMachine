package R16A_Group_6_A2;

import java.util.Arrays;
import java.util.List;

public class Item {
    private String name;
    private int code;
    private String category;
    private int quantity;
    private double price;

    public Item(String name, int code, String category, int quantity, double price){
        this.name = name;
        this.code = code;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public int getCode(){
        return code;
    }

    public String getCategory(){
        return category;
    }

    public int getQuantity(){
        return quantity;
    }

    public double getPrice(){
        return price;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCode(int code){
        this.code = code;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String formatForExport(){
        StringBuilder exportString = new StringBuilder();
        exportString.append(name);
        exportString.append(",");
        exportString.append(code);
        exportString.append(",");
        exportString.append(category);
        exportString.append(",");
        exportString.append(quantity);
        exportString.append(",");
        exportString.append(price);
        return exportString.toString();
    }

    public boolean setFromString(String inputString){
        String[] splits = inputString.split(",");
        List<String> strings = Arrays.asList(splits);
        if (strings.size() == 5){
            name = strings.get(0);
            code = Integer.parseInt(strings.get(1));
            category = strings.get(2);
            quantity = Integer.parseInt(strings.get(3));
            price = Double.parseDouble(strings.get(4));
            return true;
        }
        return false;

    }
}
