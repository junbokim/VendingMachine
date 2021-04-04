package R16A_Group_6_A2;

import java.sql.Timestamp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.LinkedHashSet;


public class UserTransactionData{

    public HashMap<String, ArrayList<Transaction>> userTransaction;

    public UserTransactionData() {
        userTransaction = new HashMap<>();
        loadPersistentData();
    }

    public UserTransactionData(boolean load) {
        userTransaction = new HashMap<>();

        if (load) {
            loadPersistentData();
        }
    }

    public boolean addUserTransaction(String username, Transaction transaction){
        if (userTransaction.containsKey(username)) {
            ArrayList<Transaction> transactions = userTransaction.get(username);
            for (Transaction check : transactions) {
                if (checkDuplicate(check, transaction)) {
                    return false;
                }
            }
            transactions.add(transaction);
            userTransaction.remove(username);
            userTransaction.put(username, transactions);
        } else {
            ArrayList<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            userTransaction.put(username, transactions);
        }
        storePersistentData();
        return true;
    }

    public ArrayList<String> getLatest(String username) {
        ArrayList<Transaction> transactions = userTransaction.get(username);
        ArrayList<String> latest = new ArrayList<>(); // lower index = latest item purchased
        int size = 5;
        ArrayList<String> orderedItemnames = new ArrayList<>(); // sorted from the list of items
        ArrayList<ArrayList<String>> perTransaction = new ArrayList<>(); // ordered transaction
        for (ArrayList<String> items : new ItemData().getItemNames()) {
            for (String item : items) {
                orderedItemnames.add(item);
            }
        }
        if (Objects.nonNull(transactions)) {
            while (latest.size() < size && transactions.size() > 0) { // Checks if there is a transaction made by 'username'
                long highest = 0;
                Transaction latestTransaction = new Transaction(null,null,0.0,0.0,null);
                int toRemove = -1; // If this is still -1, then there is no more data which will end the loop
                for (int i = 0; i < transactions.size(); i ++) {
                    Transaction transaction = transactions.get(i);
                    if (highest < transaction.getDate().getTime()) {
                        highest = transaction.getDate().getTime();
                        latestTransaction = transaction;
                        toRemove = i;
                    }
                }
                if(!(toRemove < 0)) {
                    HashMap<String, Integer> items = latestTransaction.getItemsSold();
                    ArrayList<String> itemNamesPerTrans = new ArrayList<>(); 
                    for (String itemnames : items.keySet()) {
                        latest.add(itemnames);
                        itemNamesPerTrans.add(itemnames);
                    }
                    perTransaction.add(itemNamesPerTrans);
                    transactions.remove(toRemove);
                    LinkedHashSet<String> hashSet = new LinkedHashSet<>(latest); //removes duplicates, but ruins order
                    latest = new ArrayList<>(hashSet);
                } else {
                    break;
                }
            }
        }

        ArrayList<String> fixedLatest = new ArrayList<>();
        ArrayList<String> latestFive = new ArrayList<>();
        for (ArrayList<String> perTransNames : perTransaction) {
            ArrayList<String> perTransNamesOrdered = new ArrayList<>();
            for (String sortedItem : orderedItemnames) {
                for (String item : perTransNames) {
                    if (sortedItem.equals(item)) {
                        perTransNamesOrdered.add(sortedItem);
                    }
                }
            }
            for (String names : perTransNamesOrdered) {
                if (latestFive.size() < 5) {
                    latestFive.add(names);
                    LinkedHashSet<String> hashSet = new LinkedHashSet<>(latestFive);
                    latestFive = new ArrayList<>(hashSet);
                }
            }
        }
        for (String sortedItem : orderedItemnames) {
            for (String item : latestFive) {
                if (sortedItem.equals(item)) {
                    fixedLatest.add(sortedItem);
                }
            }
        }

        return fixedLatest;
    }

    private boolean checkDuplicate(Transaction transaction1, Transaction transaction2) { // Returns true if they are the same
        if (transaction1.getDate().equals(transaction2.getDate()) &&
            transaction1.getItemsSold().equals(transaction2.getItemsSold()) &&
            (transaction1.getAmount() == transaction2.getAmount()) &&
            (transaction1.getReturnedChange() == transaction2.getReturnedChange()) &&
            transaction1.getPaymentMethod().equals(transaction2.getPaymentMethod())) {
            return true;
        } else {
            return false;
        }
    }

    private void storePersistentData(){

        try {
            File file = new File("data/userTransactions.txt");
            new File("data").mkdir();
            PrintWriter writer = new PrintWriter(file);
            for (String username : userTransaction.keySet()) {
                for (Transaction transaction : userTransaction.get(username)) {
                    writer.println(username + ";;" + transaction.formatForExport());
                }
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadPersistentData(){

        File file = new File("data/userTransactions.txt");
        if(file.exists() && !file.isDirectory()) {

            try {
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()){
                    String nextLine = reader.nextLine();
                    String[] splits = nextLine.split(";;");
                    Transaction toAdd = new Transaction(null,null,0.0,0.0,null);
                    toAdd.setFromString(splits[1]);
                    addUserTransaction(splits[0], toAdd);
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}