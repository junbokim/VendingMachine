package R16A_Group_6_A2;

import R16A_Group_6_A2.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.awt.Color;
import java.math.*;

import javax.swing.*;

import R16A_Group_6_A2.*;

/**
 * TransactionCardGUI
 */
public class TransactionCardGUI {

    // Java Swing Attribute
    private JFrame frame;
    private JPanel panel;
    private JLabel timer;

    JTextField cardName;
    JTextField cardNumber;

    ArrayList<JButton> buttons;

    JLabel verificationWarning;
    JLabel successLabel;

    JButton storeCard;

    // Class attribute
    private JFrame shoppingPageFrame;
    private ShoppingPage shoppingPage;
    private LogInPage logInPage;
    private int seconds;
    private User userProfile;
    private double price;
    HashMap<Item, Integer> itemsSold;
    private TransactionData transactionData;
    private ItemData itemData;
    private Settings settings;
    private Timer timerVar;

    public TransactionCardGUI(ShoppingPage shoppingPage,JFrame shoppingPageFrame, LogInPage logInPage, User userProfile, ShoppingCart ShoppingCart,ItemData itemData, int x, int y){
        this.shoppingPageFrame = shoppingPageFrame;
        this.shoppingPage = shoppingPage;
        this.logInPage = logInPage;
        this.userProfile = userProfile;
        this.price = ShoppingCart.getPrice();
        this.itemsSold = ShoppingCart.getItems();
        this.itemData = itemData;
        this.transactionData = new TransactionData(this.itemData);

        buttons = new ArrayList<>();

        // setting main frame and panel
        this.frame = new JFrame();
        this.panel = new JPanel();

        // setting the default values necessary for Frame to show
        this.panel.setLayout(null);
        this.frame.setBounds(x,y,500,400);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(this.panel);

        // Timmer settings
        this.seconds = 0;
        this.settings = new Settings();
        timerVar = new Timer(1000, e-> tick());

        buildScreen();

        timerVar.start();
        this.frame.setVisible(true);
    }

    private void buildScreen(){
        JLabel heading = new JLabel("<html><h1>Card Payment</h1></html>");
        heading.setBounds(50,0,200,100);
        this.panel.add(heading);

        JLabel timerWarningLabel = new JLabel("<html><p style = \"text-align: center;\"> Please do keep in mind <br> 2 Minutes of inactivity <br> will automatically log you out</p> </html>");
        timerWarningLabel.setBounds(250,0,250,100);
        this.panel.add(timerWarningLabel);

        JLabel timerLabel = new JLabel("Time inactive");
        timerLabel.setBounds(225,85,200,25);
        this.panel.add(timerLabel);

        timer = new JLabel("");
        timer.setBounds(325,85,200,25);
        this.panel.add(timer);

        verificationWarning = new JLabel("<html><p style = \"text-align: center;\">The card detail was not verified <br> please try again</p></html>");
        verificationWarning.setBounds(50,110,300,50);
        verificationWarning.setForeground(Color.red);
        verificationWarning.setVisible(false);
        this.panel.add(verificationWarning);

        JLabel cardNameLabel = new JLabel("Card Holder Name");
        cardNameLabel.setBounds(50,150,200,25);
        this.panel.add(cardNameLabel);

        cardName = new JTextField();
        cardName.setBounds(50,200,200,25);
        this.panel.add(cardName);

        JLabel cardNumberLabel = new JLabel("Credit Card Number");
        cardNumberLabel.setBounds(50,250,200,25);
        this.panel.add(cardNumberLabel);

        this.cardNumber = new JTextField();
        this.cardNumber.setBounds(50,300,200,25);
        this.panel.add(this.cardNumber);

        System.out.println(Objects.nonNull(this.userProfile.getCard()));

        if(this.userProfile.isCustomer() && Objects.nonNull(this.userProfile.getCard())){
            this.cardName.setText(this.userProfile.getCard().getName());
            this.cardNumber.setText(this.userProfile.getCard().getNumber());
        }
        if(this.userProfile.isCustomer()){
            this.successLabel = new JLabel("<html><p style = \"text-align: center;\">Transaction Complete! <br> Did you want to store your <br> card detail for later?</p> </html>");
        }
        else{
            this.successLabel = new JLabel("<html><p style = \"text-align: center;\">Transaction Complete! </p></html>");
        }
        this.successLabel.setBounds(290,125,175,75);
        this.successLabel.setVisible(false);
        this.panel.add(this.successLabel);

        JButton pay = new JButton("Pay");
        pay.setBounds(275,125,175,50);
        pay.addActionListener(e -> pay());
        this.panel.add(pay);
        this.buttons.add(pay);

        JButton goback = new JButton("Go Back");
        goback.setBounds(275,200,175,50);
        goback.addActionListener(e -> goBack());
        this.panel.add(goback);
        this.buttons.add(goback);

        if(this.userProfile.isCustomer()){
            this.storeCard = new JButton("Store my Card details");
            storeCard.setBounds(275,200,175,50);
            storeCard.addActionListener(e -> storeCardDetail());
            storeCard.setVisible(false);
            this.panel.add(storeCard);
        }

        JButton Cancel = new JButton("Cancel / Log Out");
        Cancel.setBounds(275,275,175,50);
        Cancel.addActionListener(e -> logOut());
        this.panel.add(Cancel);
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

    private void storeCardDetail(){
        UserData userData = new UserData();
        userData.setCard(this.userProfile.getUsername(), this.cardName.getText(), this.cardNumber.getText());
        this.userProfile.setCard( this.cardName.getText(), this.cardNumber.getText());
        this.verificationWarning.setText("Card storage complete!");
        this.verificationWarning.setVisible(true);
        this.verificationWarning.setForeground(Color.black);
        this.verificationWarning.setBounds(300,90,175,75);
    }

    private void pay(){
        this.seconds = 0;
        CreditCardData creditCardData = new CreditCardData();
        boolean verified = creditCardData.verifyCard(cardName.getText(), cardNumber.getText());
        if(verified){
            for(JButton button: this.buttons){
                button.setVisible(false);
            }
            this.timerVar.stop();
            this.verificationWarning.setVisible(false);
            if(this.userProfile.isCustomer()){
                this.storeCard.setVisible(true);
            }
            this.successLabel.setVisible(true);
            this.transactionData.createSuccessfulTransaction(this.itemsSold, this.price, 0, Transaction.PaymentMethod.CARD);
            storeTransaction();
        }
        else{
            this.verificationWarning.setVisible(true);
            this.transactionData.createUnsuccessfulTransaction(this.userProfile, "Incorrect Card Credential input");
        }
    }

    private void storeTransaction(){
        ArrayList<Transaction> transactions = this.transactionData.getSuccessful();
        UserTransactionData userTransactionData = new UserTransactionData();
        userTransactionData.addUserTransaction(this.userProfile.getUsername(), transactions.get(transactions.size()-1));
    }

}