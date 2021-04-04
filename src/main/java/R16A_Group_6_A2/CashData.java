package R16A_Group_6_A2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Scanner;

public class CashData {
    private Cash storedCash;

    private int defaultAmount = 5;

    private static String filepath = "data/cash.txt";

    public static void setTestingFilepath(){
        CashData.filepath = "testdata/cash.txt";
    }

    public CashData() {
        ArrayList<Integer> amounts = new ArrayList<>();
        for (int i=0; i< Cash.numTypes; i++){
            amounts.add(defaultAmount);
        }
        storedCash = new Cash(amounts);
        loadPersistentData();
    }

    public CashData(boolean load) {
        ArrayList<Integer> amounts = new ArrayList<>();
        for (int i=0; i< Cash.numTypes; i++){
            amounts.add(defaultAmount);
        }
        storedCash = new Cash(amounts);
        if (load){
            loadPersistentData();
        }
    }

    public Cash executeTransaction(double price, Cash payment){ // Execute a transaction and return change
        price = amendValue(price);
        Cash change = calculateChange(price, payment);
        storedCash = Cash.add(storedCash, payment);
        storedCash = Cash.subtract(storedCash, change);
        //System.out.println(storedCash.getAmounts());
        storePersistentData();
        return change;
    }

    public Cash calculateChange(double price, Cash payment){ // Calculate change object without executing transaction
        // Change behaviour favours the vending machine ($1.50 with only $1s gives $1 in change) - thanks capitalism!
        return Cash.calculateChange(Cash.add(storedCash,payment), payment.getValue() - price);

    }

    public boolean changeComplete(double price, Cash payment){ // Return whether the change is complete
        price = amendValue(price);
        double netCurrency = payment.getValue() - price - calculateChange(price, payment).getValue();

        return amendValue(netCurrency) == 0.0;
    }

    public boolean enoughPayment(double price, Cash payment){
        return (payment.getValue() - price) >= 0.0;
    }


    private double amendValue(double value){ // Fix the price to actually be in increments that can have change done
        return Math.round(value / Cash.values.get(0)) * Cash.values.get(0);
    }


    public void createChangeReport(){
        try {
            File file = new File("reports/change.txt");
            new File("reports").mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            StringBuilder exportString = new StringBuilder();
            exportString.append("Denominations,");
            for (int i=0; i<Cash.values.size();i++){
                if (i != 0){
                    exportString.append(",");
                }
                exportString.append("$");
                exportString.append(Cash.values.get(i));
            }
            writer.println(exportString.toString());
            writer.print("Quantities,");
            writer.println(storedCash.formatForExport());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

    public void updateAmount(ArrayList<Integer> amounts) {
        storedCash.updateAmounts(amounts);
        storePersistentData();
    }

    public ArrayList<Integer> getStoredAmounts() { return this.storedCash.getAmounts(); }

    private void storePersistentData(){
        try {
            File file = new File(CashData.filepath);
            new File(CashData.filepath.split("/")[0]).mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            writer.print(storedCash.formatForExport());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

    private void loadPersistentData(){

        File file = new File(CashData.filepath);
        if(file.exists() && !file.isDirectory()) {
            try {
                Scanner reader = new Scanner(file);
                if (reader.hasNextLine()){
                    storedCash.setFromString(reader.nextLine());
                }
                reader.close();
            } catch (FileNotFoundException e) {
                //System.out.println("An error occurred.");
                //e.printStackTrace();
            }
        }

    }
}