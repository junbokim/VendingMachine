package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CashDataTest {
    @Test
    void testCreation() { // Test if it actually makes
        CashData.setTestingFilepath();
        assertNotNull(new CashData(true), "CashData object should generate");
        assertNotNull(new CashData(false), "CashData object should generate");
        assertNotNull(new CashData(), "CashData object should generate");
    }

    @Test
    void testChangeValue() {
        CashData.setTestingFilepath();
        CashData reserve = new CashData(false);
        Cash payment = new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3)));
        Cash change = reserve.calculateChange(271.35,payment);
        assertEquals(change.getAmounts(),new ArrayList<>(Arrays.asList(1,1,0,1,1,1,1,0,1,0,0)), "Change should match");
    }

    @Test
    void testTransaction() {
        CashData.setTestingFilepath();
        CashData reserve = new CashData(false);
        Cash change = reserve.executeTransaction(271.35,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3))));
        assertEquals(change.getAmounts(),new ArrayList<>(Arrays.asList(1,1,0,1,1,1,1,0,1,0,0)), "Change should match");
    }

    @Test
    void testChangeComplete() {
        CashData.setTestingFilepath();
        CashData reserve = new CashData(false);
        assertTrue(reserve.changeComplete(271.35,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3)))), "Change should be sufficient");

        reserve.executeTransaction(271.55,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3))));
        reserve.executeTransaction(271.55,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3))));
        reserve.executeTransaction(271.55,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3))));
        assertFalse(reserve.changeComplete(271.55,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3)))), "Change should be insufficient");
        //assertFalse(reserve.changeComplete(9999.95,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,10)))), "Change should be insufficient");
    }

    @Test
    void testEnoughPayment() {
        CashData.setTestingFilepath();
        CashData reserve = new CashData(false);
        assertTrue(reserve.enoughPayment(271.35,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3)))), "Payment should be sufficient");
        assertFalse(reserve.enoughPayment(301.20,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3)))), "Payment should be sufficient");
    }

    @Test
    void testGetAmounts() {
        CashData.setTestingFilepath();
        CashData reserve = new CashData(false);
        ArrayList<Integer> amounts = new ArrayList<>(Arrays.asList(5,5,5,5,5,5,5,5,5,5,5));
        for (int i = 0; i < amounts.size(); i++) {
            assertEquals(amounts.get(i), reserve.getStoredAmounts().get(i));
        }
    }

    @Test
    void testUpdateAmounts() {
        CashData.setTestingFilepath();
        CashData reserve = new CashData(false);
        ArrayList<Integer> amounts = new ArrayList<>(Arrays.asList(5,5,5,5,5,5,5,5,5,5,5));
        for (int i = 0; i < amounts.size(); i++) {
            assertEquals(amounts.get(i), reserve.getStoredAmounts().get(i));
        }
        amounts = new ArrayList<>(Arrays.asList(6,5,5,5,5,5,5,5,5,5,5));
        reserve.updateAmount(amounts);
        assertEquals(amounts, reserve.getStoredAmounts());
    }

    @Test
    void testPersistentData() { // Same as testChangeComplete, but over two
        CashData.setTestingFilepath();
        CashData reserve = new CashData(false);
        reserve.executeTransaction(271.55,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3))));
        reserve.executeTransaction(271.55,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3))));
        reserve.executeTransaction(271.55,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3))));

        CashData newReserve = new CashData();
        assertFalse(newReserve.changeComplete(271.55,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3)))), "Change should be insufficient");
    }

    @Test
    void testReportGeneration(){
        CashData reserve = new CashData(false);
        reserve.executeTransaction(271.35,new Cash(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,3))));
        reserve.createChangeReport();

        File file = new File("reports/change.txt");
        if(file.exists() && !file.isDirectory()) {
            try {
                Scanner reader = new Scanner(file);
                assertEquals(reader.nextLine(), "Denominations,$0.05,$0.1,$0.2,$0.5,$1.0,$2.0,$5.0,$10.0,$20.0,$50.0,$100.0");
                assertEquals(reader.nextLine(), "Quantities,4,4,5,4,4,4,4,5,4,5,8");
                reader.close();
            } catch (FileNotFoundException e) {
                //System.out.println("An error occurred.");
                //e.printStackTrace();
            }
        }
    }

}
