package R16A_Group_6_A2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;

import javax.swing.JPanel;

import R16A_Group_6_A2.*;

import javax.swing.*;

/**
 * CashierPage
 */
public class CashierPage {

    // Swings required attributes
    private JFrame frame;
    private JPanel panel;

    // Class required attributes
    private ArrayList<JTextField> inputvalues;
    private ArrayList<JLabel> quantityLabels;
    private User userProfile;
    private CashData cashData;

    private LogInPage login;
    private JLabel userFeedback;

    private JLabel downloadLabel;
    private ArrayList<Double> currencyList;

    public CashierPage(User userProfile, LogInPage logInPage){
        this.userProfile = userProfile;
        this.cashData = new CashData();
        this.login = logInPage;

        // setting main frame
        this.frame = new JFrame();
        this.panel = new JPanel();
        this.panel.setLayout(null);

        buildPage();

        this.frame.add(panel);

        // 
        this.frame.setBounds(0,0,950,650);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    private void createList(double value, int amount, int xval, int yval){

        String str = null;
        if(value < 1){
            str = String.format("%.2fc", value);
        }
        else{
            str = String.format("$%d", Math.round(value));
        }
        JLabel label = new JLabel(String.format("<html><pre style = \"padding-bottom: 10px;border-bottom: 2px solid black;\">%s\tCurrent Quantity:   %d</pre></html>", str , amount));
        label.setBounds(xval,yval,250,40);
        this.panel.add(label);
        this.quantityLabels.add(label);
        
        JTextField textField = new JTextField();
        textField.setBounds(xval + 260, yval, 150,25);
        this.panel.add(textField);
        this.inputvalues.add(textField);

    }

    private void rewriteList(ArrayList<Integer> values){

        ArrayList<Integer> valuesamount = new ArrayList<>();

        for(int i = values.size()-1 ; i >= 0; i--){
            valuesamount.add(values.get(i));
        }


        for(int i = 0; i < this.quantityLabels.size(); i++){
            double value = currencyList.get(i);
            int amount = valuesamount.get(i);
            String str = null;
            if(value < 1){
                str = String.format("%.2fc", value);
            }
            else{
                str = String.format("$%d", Math.round(value));
            }
            String labelStr = String.format("<html><pre style = \"padding-bottom: 10px;border-bottom: 2px solid black;\">%s\tCurrent Quantity:   %d</pre></html>", str , amount);
            this.quantityLabels.get(i).setText(labelStr);
        }

    }

    private void buildPage(){
        this.downloadLabel = new JLabel("");
        this.panel.add(downloadLabel);

        JLabel title = new JLabel("<html><h1 style = \"text-align: center; font-size: 30px; width: 300px; align-content: center;\"> Cashier Page </h1></html>");
        title.setBounds(20, 20, 400, 75);
        this.panel.add(title);

        JLabel welcome = new JLabel(String.format("<html><h3 style = \"text-align: center; font-size: 20px; width: 300px; align-content: center;\"> welcome %s</h3></html>", this.userProfile.getUsername()));
        welcome.setBounds(450, 20, 400, 75);
        this.panel.add(welcome);

        currencyList = new ArrayList<>();
        for(int i = Cash.values.size()-1; i >=0;i--){
            currencyList.add(Cash.values.get(i));
        }

        ArrayList<Double> cents = new ArrayList<>();
        ArrayList<Double> dollars = new ArrayList<>();
        this.inputvalues = new ArrayList<>();
        this.quantityLabels = new ArrayList<>();
        for(Double amount : currencyList){

            if(amount >= 1){
                dollars.add(amount);
            }
            else{
                cents.add(amount);
            }

        }

        // getCash needs to be removed and placed with another method.
        ArrayList<Integer> storedAmount = new ArrayList<>();
        for(int i = this.cashData.getStoredAmounts().size()-1; i >= 0; i--){
            storedAmount.add(this.cashData.getStoredAmounts().get(i));
        }


        for(int i = 0; i <  dollars.size(); i++){
            createList(dollars.get(i), storedAmount.get(i), 50 , 100 + (60 * i));
        }

        for(int i = 0; i < cents.size(); i++){
            createList(cents.get(i), storedAmount.get(i + dollars.size()), 500, 100 + (60*i));
        }

        Collections.reverse(this.inputvalues);

        JButton changeSubmitButton = new JButton("<html><p style =\" text-align: center \"> Set Changes </p></html>");
        changeSubmitButton.setBounds(100,525, 150,50);
        changeSubmitButton.addActionListener(e -> setChange());
        this.panel.add(changeSubmitButton);

        JButton salesReportButton = new JButton("<html><p style =\" text-align: center \"> Get Sales Report </p></html>");
        salesReportButton.setBounds(300,525, 150,50);
        salesReportButton.addActionListener(e -> salesReport());
        this.panel.add(salesReportButton);

        JButton changeReportButton = new JButton("<html><p style =\" text-align: center \"> Get Current <br>Change report</p></html>");
        changeReportButton.setBounds(500,525, 150,50);
        changeReportButton.addActionListener(e -> changeReport());
        this.panel.add(changeReportButton);
        
        JButton logOutButton = new JButton("<html><p style =\" text-align: center \"> Log Out </p></html>");
        logOutButton.setBounds(700,525, 150,50);
        logOutButton.addActionListener(e -> logOut());
        this.panel.add(logOutButton);

        this.userFeedback = new JLabel();
        this.userFeedback.setBounds(400,400,400,400);
        this.userFeedback.setForeground(Color.red);
        this.userFeedback.setVisible(false);
        this.panel.add(this.userFeedback);
        
    }

    private void logOut(){
        this.login.logOut();
        this.frame.dispose();
    }

    private String strFormat(String str){
        return String.format("<html><p style = \"text-align:center\">%s</p></html>", str);
    }

    private void setChange(){
        try{
            ArrayList<Integer> values = new ArrayList<>();

            for(int i = 0; i < this.inputvalues.size(); i++){
                if(this.inputvalues.get(i).getText().equals("")){
                    values.add(this.cashData.getStoredAmounts().get(i));
                }
                else{
                    values.add(Integer.parseInt(this.inputvalues.get(i).getText()));
                }   
            }
            rewriteList(values);
            this.cashData.updateAmount(values);
        }
        catch (NumberFormatException e){
            this.userFeedback.setText(strFormat("You have entered non-numeric value <br> in one of the inputs. <br> Please try again"));
            this.userFeedback.setVisible(true);
        }
    }

    private void salesReport(){
        new TransactionData(new ItemData()).getSuccessfulReport();
        this.downloadLabel.setBounds(315,560, 150,50);
        this.downloadLabel.setText("Report downloaded!");
    } 

    private void changeReport(){
        new CashData().createChangeReport();
        this.downloadLabel.setBounds(515,560, 150,50);
        this.downloadLabel.setText("Report downloaded!");
    }
}

