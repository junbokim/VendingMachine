package R16A_Group_6_A2;

import R16A_Group_6_A2.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.HashMap;
import java.awt.Color;
import java.math.*;

import javax.swing.*;

import R16A_Group_6_A2.Cash;
import R16A_Group_6_A2.CashData;
import R16A_Group_6_A2.ShoppingCart;
import R16A_Group_6_A2.*;

/**
 * TransactionCashGUI
 */
public class TransactionCashGUI {

    // Java Swing Attribute
    private JFrame frame;
    private JPanel panel;
    private ArrayList<JTextField> textFields;
    private JLabel timer;
    private JLabel givenAmount;
    private JLabel priceWarning;
    private JLabel change;

    // Class attribute
    private JFrame shoppingPageFrame;
    private ShoppingPage shoppingPage;
    private LogInPage logInPage;
    private int seconds;
    private double price;
    private ArrayList<JButton> buttons;
    private User userProfile;
    HashMap<Item, Integer> itemsSold;
    private TransactionData transactionData;
    private ItemData itemData;
    private Settings settings;
    private Timer timerVar;

    public TransactionCashGUI(ShoppingPage shoppingPage,JFrame shoppingPageFrame, LogInPage logInPage, User userProfile, ShoppingCart ShoppingCart,ItemData itemData, int x, int y){
        this.shoppingPage = shoppingPage;
        this.shoppingPageFrame = shoppingPageFrame;
        this.logInPage = logInPage;
        this.price = ShoppingCart.getPrice();
        this.userProfile = userProfile;
        this.itemsSold = ShoppingCart.getItems();

        this.itemData = itemData;
        this.transactionData = new TransactionData(this.itemData);

        this.buttons = new ArrayList<>();

        // setting main frame and panel
        this.frame = new JFrame();
        this.panel = new JPanel();

        // setting the default values necessary for Frame to show
        this.panel.setLayout(null);
        this.frame.setSize(1150,600);
        this.frame.setBounds(x,y,1150,600);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(this.panel);

        // Timmer settings
        this.seconds = 0;
        this.settings = new Settings();
        timerVar = new Timer(1000, e-> tick());

        textFields = new ArrayList<>();
        buildScreen();

        timerVar.start();
        this.frame.setVisible(true);

    }

    private String strheading(String str){
        return String.format("<html><p style = \"text-align: center; width: 75px; align-content: center\"> %s </p></html>", str);
    }

    private void buildCashInput(){
        JLabel l100= new JLabel(strheading("$100"));
        l100.setBounds(525,100,100,25);
        this.panel.add(l100);

        JTextField t100 = new JTextField();
        t100.setBounds(525,125,100,25);
        this.panel.add(t100);
        this.textFields.add(t100);

        JLabel l50 = new JLabel(strheading("$50"));
        l50.setBounds(150,200,100,25);
        this.panel.add(l50);

        JTextField t50 = new JTextField();
        t50.setBounds(150,225, 100, 25);
        this.panel.add(t50);
        this.textFields.add(t50);

        JLabel l20 = new JLabel(strheading("$20"));
        l20.setBounds(300,200,100,25);
        this.panel.add(l20);

        JTextField t20 = new JTextField();
        t20.setBounds(300,225, 100, 25);
        this.panel.add(t20);
        this.textFields.add(t20);

        JLabel l10 = new JLabel(strheading("$10"));
        l10.setBounds(450,200,100,25);
        this.panel.add(l10);

        JTextField t10 = new JTextField();
        t10.setBounds(450,225, 100, 25);
        this.panel.add(t10);
        this.textFields.add(t10);

        JLabel l5 = new JLabel(strheading("$5"));
        l5.setBounds(600,200,100,25);
        this.panel.add(l5);

        JTextField t5 = new JTextField();
        t5.setBounds(600,225, 100, 25);
        this.panel.add(t5);
        this.textFields.add(t5);

        JLabel l2 = new JLabel(strheading("$2"));
        l2.setBounds(750,200,100,25);
        this.panel.add(l2);

        JTextField t2 = new JTextField();
        t2.setBounds(750,225, 100, 25);
        this.panel.add(t2);
        this.textFields.add(t2);

        JLabel l1 = new JLabel(strheading("$1"));
        l1.setBounds(900,200,100,25);
        this.panel.add(l1);

        JTextField t1 = new JTextField();
        t1.setBounds(900,225, 100, 25);
        this.panel.add(t1);
        this.textFields.add(t1);

        JLabel l50c = new JLabel(strheading("50c"));
        l50c.setBounds(300,300,100,25);
        this.panel.add(l50c);

        JTextField t50c = new JTextField();
        t50c.setBounds(300,325, 100, 25);
        this.panel.add(t50c);
        this.textFields.add(t50c);

        JLabel l20c = new JLabel(strheading("20c"));
        l20c.setBounds(450,300,100,25);
        this.panel.add(l20c);

        JTextField t20c = new JTextField();
        t20c.setBounds(450,325, 100, 25);
        this.panel.add(t20c);
        this.textFields.add(t20c);

        JLabel l10c = new JLabel(strheading("10c"));
        l10c.setBounds(600,300,100,25);
        this.panel.add(l10c);

        JTextField t10c = new JTextField();
        t10c.setBounds(600,325, 100, 25);
        this.panel.add(t10c);
        this.textFields.add(t10c);

        JLabel l5c = new JLabel(strheading("5c"));
        l5c.setBounds(750,300,100,25);
        this.panel.add(l5c);

        JTextField t5c = new JTextField();
        t5c.setBounds(750,325, 100, 25);
        this.panel.add(t5c);
        this.textFields.add(t5c);

    }

    private void buildScreen(){
        JLabel heading = new JLabel("<html><h1 style = \"text-align: center; font-size: 30px; width: 900px; align-content: center\">Cash Payment Transaction</h1></html>");
        heading.setBounds(0,0,1150,75);      
        this.panel.add(heading);

        buildCashInput();

        this.priceWarning = new JLabel("");
        priceWarning.setForeground(Color.red);
        this.priceWarning.setBounds(50,50,400,100);
        this.priceWarning.setVisible(false);
        this.panel.add(priceWarning);

        JLabel shoppingPrice = new JLabel(String.format("Overall Price: $%.2f", this.price));
        shoppingPrice.setBounds(50,75,150,100);
        this.panel.add(shoppingPrice);

        this.givenAmount = new JLabel();
        givenAmount.setBounds(225,75,500,100);
        this.givenAmount.setVisible(false);
        this.panel.add(givenAmount);

        this.change = new JLabel();
        this.change.setBounds(50,100,300,500);
        this.change.setVisible(false);
        this.panel.add(this.change);

        JButton payButton = new JButton("Pay");
        payButton.setBounds(325,400,150,50);
        payButton.addActionListener(e -> payValidation());
        this.panel.add(payButton);
        this.buttons.add(payButton);

        JButton cancelButton = new JButton("Go back");
        cancelButton.addActionListener(e -> goBack());
        cancelButton.setBounds(500,400,150,50);
        this.panel.add(cancelButton);
        this.buttons.add(cancelButton);

        JButton logOut = new JButton("Log Out");
        logOut.setBounds(675,400,150,50);
        logOut.addActionListener(e -> logOut());
        this.panel.add(logOut);

        JLabel timerWarningLabel = new JLabel("<html><p style = \"text-align: center;\"> Please do keep in mind <br> 2 Minutes of inactivity <br> will automatically log you out</p> </html>");
        timerWarningLabel.setBounds(900,0,200,100);
        this.panel.add(timerWarningLabel);

        JLabel timerLabel = new JLabel("Time inactive");
        timerLabel.setBounds(875,100,200,25);
        this.panel.add(timerLabel);

        timer = new JLabel("");
        timer.setBounds(975,100,200,25);
        this.panel.add(timer);
    }

    private void logOut(){
        this.timerVar.stop();
        this.frame.dispose();
        this.shoppingPageFrame.dispose();
        this.logInPage.logOut();
    }

    private void goBack(){
        this.frame.dispose();
        this.timerVar.stop();
        this.shoppingPageFrame.setVisible(true);
    }

    // This is the function that manages the time, it will increment seconds variable every seconds
    private void tick(){
        seconds++;
        int minutes = Math.round(seconds/60);
        int second = seconds%60;

        if(minutes == 0){
            this.timer.setText(String.format("%d Seconds", second));
        }
        else{
            this.timer.setText(String.format("%d Minutes %d Seconds", minutes, second));
        }
        if(minutes >= this.settings.getTimeout()){
            this.timerVar.stop();
            this.shoppingPage.timeOut();
            this.frame.dispose();
        }

    }


    private boolean inputValidation(){
        for(int i = 0; i < this.textFields.size(); i++){
            if(!this.textFields.get(i).getText().equals("")){
                try{
                    Integer.parseInt(this.textFields.get(i).getText());
                }
                catch(NumberFormatException e){
                    this.priceWarning.setText("Warning: Value input was not Numbers");
                    this.priceWarning.setVisible(true);
                    return false;
                }
            }
        }
        return true;
    }

    private Cash getGivenAmount(){
        ArrayList<Integer> amount = new ArrayList<>();
        for(int i = 0; i < this.textFields.size(); i++){
            if(!this.textFields.get(i).getText().equals("")){
                amount.add((Integer.parseInt(this.textFields.get(i).getText())));
            }
            else{
                amount.add(0);
            }
        }
        Collections.reverse(amount);
        Cash cashobj = new Cash(amount);
        return cashobj;
    }

    private void InsufficientPayment(Cash cashobj){
        double diff = this.price - cashobj.getValue();
        this.priceWarning.setText(String.format("Insufficient payment by: $%.2f", diff));
        this.priceWarning.setVisible(true);
        this.transactionData.createUnsuccessfulTransaction(this.userProfile, "Insufficient Payment");
    }

    private String printChange(Cash change){
        String str = String.format("<html><p style = \"text-align: center\">Transaction Complete!<br> Change to give: $%.2f<br> given changes : <br><br>", change.getValue());
        double value = 0;
        int amount = 0;
        for(int i = 0; i < change.getAmounts().size(); i++){
            value = Cash.values.get(i);
            amount = change.getAmounts().get(i);
            if(amount > 0){
                if(value > 0){
                    str += String.format("$%d (x%d) : $%d<br> ",Math.round(value), amount, Math.round(value * amount));
                }
                else{
                    str += String.format("%.2fc (x%d) : $%.2f<br> ", value, amount, value * amount);
                }
            }
        }
        str+= "</p></html>";
        return str;
    }

    private void payValidation(){
        if(!inputValidation()){
            return;
        }

        Cash cashobj = getGivenAmount();
        CashData cashData = new CashData();
        boolean enough = cashData.enoughPayment(this.price, cashobj);

        this.givenAmount.setText(String.format("Given Amount: $%.2f", cashobj.getValue()));
        this.givenAmount.setVisible(true);
        if(!enough){
            InsufficientPayment(cashobj);
            return;
        }

        boolean enoughChange = cashData.changeComplete(this.price, cashobj);
        if(!enoughChange){
            this.priceWarning.setText("The Vending machine does not hold enough change for you");
            this.priceWarning.setVisible(true);
            this.transactionData.createUnsuccessfulTransaction(this.userProfile, "Unable to provide enough Change");
            return;
        }

        this.priceWarning.setVisible(false);
        pay(cashobj,cashData);
    }

    private void pay(Cash cashobj,CashData cashData){
        Cash change = cashData.executeTransaction(this.price,cashobj);
        this.transactionData.createSuccessfulTransaction(this.itemsSold, cashobj.getValue(), change.getValue(), Transaction.PaymentMethod.CASH);

        for(JButton button: this.buttons){
            button.setVisible(false);
        }
        this.timerVar.stop();
        this.change.setText(printChange(change));
        this.change.setVisible(true);        
        storeTransaction();
    }

    private void storeTransaction(){
        ArrayList<Transaction> transactions = this.transactionData.getSuccessful();
        UserTransactionData userTransactionData = new UserTransactionData();
        userTransactionData.addUserTransaction(this.userProfile.getUsername(), transactions.get(transactions.size()-1));
    }


}