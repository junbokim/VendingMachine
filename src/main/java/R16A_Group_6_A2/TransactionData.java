package R16A_Group_6_A2;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class TransactionData {
    private ItemData itemData;
    private ArrayList<Transaction> successful;
    private ArrayList<Transaction> unsuccessful;

    public TransactionData(ItemData itemData){
        this.itemData = itemData;
        this.successful = new ArrayList<>();
        this.unsuccessful = new ArrayList<>();

        loadPersistentData();
    }

    public TransactionData(ItemData itemData, boolean load){
        this.itemData = itemData;
        this.successful = new ArrayList<>();
        this.unsuccessful = new ArrayList<>();

        if (load){
            loadPersistentData();
        }

    }

    public void createSuccessfulTransaction(HashMap<Item, Integer> itemsSold,double amount, double returnedChange, Transaction.PaymentMethod paymentMethod){
        HashMap<String, Integer> itemNames = new HashMap<>();
        for(Item i : itemsSold.keySet()){
            itemNames.put(i.getName(),itemsSold.get(i));
        }
        successful.add(new Transaction(new Timestamp(System.currentTimeMillis()), itemNames, amount, returnedChange, paymentMethod));
        for(Item i : itemsSold.keySet()){
            itemData.changeItemQuantity(i,i.getQuantity()-itemsSold.get(i));
        }

        storePersistentData();
    }

    public void createUnsuccessfulTransaction(User user, String reason){
        if(user.isGuest()){
            unsuccessful.add(new Transaction(new Timestamp(System.currentTimeMillis()), "anonymous", reason));
        }
        else{
            unsuccessful.add(new Transaction(new Timestamp(System.currentTimeMillis()), user.getUsername(), reason));
        }

        storePersistentData();

    }

    public ArrayList<Transaction> getSuccessful(){
        return this.successful;
    }

    public ArrayList<Transaction> getUnsuccessful(){
        return this.unsuccessful;
    }

    public void getSuccessfulReport(){
        try {
            File file = new File("reports/successful_transactions_report.txt");
            new File("reports").mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);

            writer.println("Successful Transactions Report");
            writer.println("------------------------------");

            for (int i=0; i<successful.size();i++) {
                Date date = successful.get(i).getDate();
                writer.print("Date: ");
                writer.println(date);
                writer.print("Items sold (Item name, Quantity): ");
                boolean firstHashItem = true;
                for (HashMap.Entry<String, Integer> entry : successful.get(i).getItemsSold().entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    if (firstHashItem) {
                        firstHashItem = false;
                    } else {
                        writer.print("; ");
                    }
                    writer.print(key);
                    writer.print(", ");
                    writer.print(value);

                }
                writer.print("\nAmount Paid: ");
                writer.println(successful.get(i).getAmount());
                writer.print("Change Returned: ");
                writer.println(successful.get(i).getReturnedChange());
                writer.print("Payment Method: ");
                writer.println(successful.get(i).getPaymentMethod());
                writer.print("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

    public void getUnsuccessfulReport(){
        try {
            File file = new File("reports/unsuccessful_transactions_report.txt");
            new File("reports").mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);

            writer.println("Unsuccessful Transactions Report");
            writer.println("--------------------------------");

            for (int i=0; i<unsuccessful.size();i++) {
                Date date = unsuccessful.get(i).getDate();
                writer.print("Date: ");
                writer.println(date);
                writer.print("Username: ");
                writer.println(unsuccessful.get(i).getUsername());
                writer.print("Payment Method: ");
                writer.println(unsuccessful.get(i).getReason());
                writer.print("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

    public void getItemDetailsReport(){
        try {
            File file = new File("reports/item_details_report.txt");
            new File("reports").mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);

            writer.println("Item Details Report");
            writer.println("-------------------");
            writer.println("Product code;Product name;Quantity sold");
            ArrayList<Item> items = itemData.getItems();
            for (int i=0; i<items.size();i++) {
                writer.print(items.get(i).getCode());
                writer.print(";");
                writer.print(items.get(i).getName());
                writer.print(";");
                int quantitySold = 0;
                for(int n = 0; n <successful.size(); n++){
                    if(successful.get(n).getItemsSold().containsKey(items.get(i).getName())){
                        quantitySold += successful.get(n).getItemsSold().get(items.get(i).getName());
                    }
                }
                writer.print(quantitySold);
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
            File file = new File("data/transactions.txt");
            new File("data").mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);

            for (int i=0; i<successful.size();i++) {
                writer.println(successful.get(i).formatForExport());
            }
            writer.println("----");
            for (int i=0; i<unsuccessful.size();i++) {
                writer.println(unsuccessful.get(i).formatForExport());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

    private void loadPersistentData(){

        File file = new File("data/transactions.txt");
        if(file.exists() && !file.isDirectory()) {

            try {
                Scanner reader = new Scanner(file);
                boolean firstSection = true;
                while (reader.hasNextLine()){
                    String nextLine = reader.nextLine();
                    if (nextLine.equals("----")){
                        firstSection = false;
                    }else if (firstSection){
                        Transaction toAdd = new Transaction(null,null,0.0,0.0,null);
                        toAdd.setFromString(nextLine);
                        successful.add(toAdd);
                    }else{
                        Transaction toAdd = new Transaction(null,null,0.0,0.0,null);
                        toAdd.setFromString(nextLine);
                        unsuccessful.add(toAdd);
                    }
                }
                reader.close();
            } catch (FileNotFoundException e) {
                //System.out.println("An error occurred.");
                //e.printStackTrace();
            }
        }

    }


}