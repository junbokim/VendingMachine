package R16A_Group_6_A2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class CreditCardData {

    private HashMap<String, CreditCard> creditList;

    public CreditCardData() {
        creditList = new HashMap<>();
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("data/credit_cards.json"));
            Type creditCardType = new TypeToken<ArrayList<CreditCard>>(){}.getType();
            List<CreditCard> creditCards = new Gson().fromJson(reader, creditCardType);
            for (CreditCard i : creditCards) { // Use of Card Number as key as there are same cardholder names
                creditList.put(i.getNumber(), i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, CreditCard> getCards() { return this.creditList; }

    public boolean verifyCard(String name, String number) {
        CreditCard checkCard = creditList.get(number);
        if (Objects.nonNull(checkCard)) {
            if (checkCard.verify(name, number)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeCard(String number){
        CreditCard checkCard = creditList.get(number);
        if (Objects.nonNull(checkCard)) {
            creditList.remove(number);
            storePersistentData();
            return true;
        }
        return false;
    }

    public boolean addCard(String name, String number){
        if (!creditList.containsKey(number)) {
            creditList.put(number, new CreditCard(name, number));
            storePersistentData();
            return true;
        }
        return false;
    }

    private void storePersistentData(){
        try {
            File file = new File("data/credit_cards.json");
            new File("data").mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            writer.println("[");

            int counter = 1;
            for (CreditCard card : creditList.values()) {
                writer.println("{");

                writer.print("\"name\": \"");
                writer.print(card.getName());
                writer.println("\",");

                writer.print("\"number\": \"");
                writer.print(card.getNumber());
                writer.println("\"");

                if(counter == creditList.values().size()){
                    writer.println("}");
                }
                else{
                    writer.println("},");
                }

                counter++;
            }
            writer.println("]");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

}