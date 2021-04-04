package R16A_Group_6_A2;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Transaction{
    public enum PaymentMethod {
        CASH,
        CARD
    }

    private Timestamp date;
    private HashMap<String, Integer> itemsSold;
    private double amount;
    private double returnedChange;
    private PaymentMethod paymentMethod;
    private String username;
    private String reason;

    public Transaction(Timestamp date, HashMap<String, Integer> itemsSold, double amount, double returnedChange, PaymentMethod paymentMethod){
        this.date = date;
        this.itemsSold =itemsSold;
        this.amount = amount;
        this.returnedChange = returnedChange;
        this.paymentMethod = paymentMethod;
    }

    public Transaction(Timestamp date, String username, String reason){
        this.date = date;
        this.username = username;
        this.reason = reason;
    }

    public Timestamp getDate(){
        return this.date;
    }

    public HashMap<String, Integer> getItemsSold(){
        return this.itemsSold;
    }

    public double getAmount(){
        return this.amount;
    }

    public double getReturnedChange(){
        return this.returnedChange;
    }

    public PaymentMethod getPaymentMethod(){
        return this.paymentMethod;
    }

    public String getUsername(){
        return this.username;
    }

    public String getReason(){
        return this.reason;
    }

    public String formatForExport(){
        StringBuilder exportString = new StringBuilder();
        exportString.append(date.getTime());
        exportString.append(",");
        exportString.append(amount);
        exportString.append(",");
        exportString.append(returnedChange);
        exportString.append(",");
        if (paymentMethod != null){
            exportString.append(paymentMethod.toString());
        }else{
            exportString.append("null");
        }

        exportString.append(",");
        exportString.append(username);
        exportString.append(",");
        exportString.append(reason);
        exportString.append(":");

        boolean firstHashItem = true;
        if (itemsSold != null) {
            for (HashMap.Entry<String, Integer> entry : itemsSold.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                if (firstHashItem) {
                    firstHashItem = false;
                } else {
                    exportString.append(";");
                }
                exportString.append(key);
                exportString.append(",");
                exportString.append(value);

            }
        }
        return exportString.toString();
    }

    public boolean setFromString(String inputString){
        String[] splits = inputString.split(":");
        List<String> attributes = Arrays.asList(Arrays.asList(splits).get(0).split(","));
        if (splits.length > 1) {
            List<String> hashStrings = Arrays.asList(Arrays.asList(splits).get(1).split(";"));
            itemsSold = new HashMap<>();
            for (int i = 0; i < hashStrings.size(); i++) {
                List<String> row = Arrays.asList(hashStrings.get(i).split(","));
                itemsSold.put(row.get(0), Integer.parseInt(row.get(1)));
            }
        }
        if (attributes.size() == 6){
            date = new Timestamp(Long.parseLong(attributes.get(0)));
            amount = Double.parseDouble(attributes.get(1));
            returnedChange = Double.parseDouble(attributes.get(2));
            if (!attributes.get(3).equals("null")) {
                paymentMethod = PaymentMethod.valueOf(attributes.get(3));
            }
            username = attributes.get(4);
            reason = attributes.get(5);


            return true;
        }
        return false;

    }



}