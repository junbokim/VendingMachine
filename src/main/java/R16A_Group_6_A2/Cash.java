package R16A_Group_6_A2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

public class Cash {
    private double value;

    private ArrayList<Integer> amounts;
    public static ArrayList<Double> values = new ArrayList<>(Arrays.asList(0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0));

    public static int numTypes = values.size();

    public Cash(ArrayList<Integer> amounts) {
        this.updateAmounts(amounts);

    }

    private void updateValue(){
        double newValue = 0.0;
        for (int i=0; i<Cash.numTypes; i++){
            newValue += Cash.values.get(i) * this.amounts.get(i);
        }
        this.value = newValue;
    }

    public void updateAmounts(ArrayList<Integer> amounts) {
        this.amounts = amounts;
        this.updateValue();
    }

    public double getValue(){
        return this.value;
    }

    public ArrayList<Integer> getAmounts(){
        return this.amounts;
    }

    public static Cash subtract(Cash cash1, Cash cash2) {
        ArrayList<Integer> amounts1 = cash1.amounts;
        ArrayList<Integer> amounts2 = cash2.amounts;
        ArrayList<Integer> newAmounts = new ArrayList<>();
        for (int i=0; i<Cash.numTypes; i++){
            newAmounts.add(amounts1.get(i) - amounts2.get(i));
        }
        return new Cash(newAmounts);
    }

    public static Cash add(Cash cash1, Cash cash2) {
        ArrayList<Integer> amounts1 = cash1.amounts;
        ArrayList<Integer> amounts2 = cash2.amounts;
        ArrayList<Integer> newAmounts = new ArrayList<>();
        for (int i=0; i<Cash.numTypes; i++){
            newAmounts.add(amounts1.get(i) + amounts2.get(i));
        }
        return new Cash(newAmounts);
    }

    public static Cash calculateChange(Cash cashInput, double changeAmount){
        ArrayList<Integer> changeAmounts = new ArrayList<>();
        for (int i=0; i<Cash.numTypes; i++){
            changeAmounts.add(0);
        }

        int changeIndex = numTypes-1;
        double changeRemaining = changeAmount;
        while (changeIndex >= 0){

            // Fix the double rounding problems
            changeRemaining = Math.round(changeRemaining / Cash.values.get(0)) * Cash.values.get(0);

            if (changeRemaining >= Cash.values.get(changeIndex) &&  cashInput.amounts.get(changeIndex) > 0){
                int cleanDivs = (int)floor(changeRemaining / Cash.values.get(changeIndex));
                int numChange = min(cashInput.amounts.get(changeIndex),cleanDivs); // Find number of currency objects to put into change

                // Update change and remaining amount
                changeAmounts.set(changeIndex, numChange + changeAmounts.get(changeIndex));
                changeRemaining -= numChange * Cash.values.get(changeIndex);
            }

            changeIndex -= 1;

            //System.out.print(Math.round(changeRemaining / Cash.values.get(0)) * Cash.values.get(0));
            //System.out.print("   ");
            //System.out.println(changeAmounts);

        }

        return new Cash(changeAmounts);
    }

    private String delimiter = ",";

    public String formatForExport(){
        StringBuilder exportString = new StringBuilder();
        for (int i=0; i<amounts.size();i++){
            if (i != 0){
                exportString.append(delimiter);
            }
            exportString.append(amounts.get(i));
        }
        return exportString.toString();
    }

    public boolean setFromString(String inputString){
        String[] splits = inputString.split(delimiter);
        List<String> stringAmounts = Arrays.asList(splits);
        if (stringAmounts.size() == numTypes){
            amounts = new ArrayList<>();
            for(int i=0; i<numTypes; i++) {
                amounts.add(Integer.valueOf(stringAmounts.get(i)));
            }
            return true;
        }
        return false;

    }



}
