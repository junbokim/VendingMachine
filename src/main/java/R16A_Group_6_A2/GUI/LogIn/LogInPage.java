package R16A_Group_6_A2;

import R16A_Group_6_A2.*;

import java.util.ArrayList;
import java.util.Objects;

import javax.swing.*;

public class LogInPage implements LogInPageInterface{
    
    private JFrame LogInFrame;
    private JPanel LogInPanel;

    private JTextField userName;
    private JPasswordField password;

    private JLabel mainLabel;
    private UserData userData;

    private ProfileCreationPage newProfile;
    private ShoppingPage shoppingPage;
    private OwnerPage ownerPage;
    private Settings settings;

    private ItemData itemData;
  
    public LogInPage(UserData userData){

        this.userData = userData;
        this.settings = new Settings();

        this.LogInFrame = new JFrame("Vending Machine App");
        this.LogInPanel = new JPanel();
        
        this.LogInPanel.setLayout(null);
        this.LogInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.LogInFrame.add(LogInPanel);
        this.LogInFrame.setSize(400,300);
        
        this.itemData = new ItemData();

        createforms();
        this.LogInFrame.setVisible(true);
    }

    private String strFactory(String str){
        return String.format("<html><p style = \"text-align: center; width: 200px; align-content: center\"> %s </p></html>", str);
    }

    private void verifyUser(){
        String usernameVal = userName.getText();
        String passwordVal = password.getText();

        if(this.userName.getText().equals("") || this.password.getText().equals("")){
            this.mainLabel.setText(strFactory("Warning:<br> One or more of <br> user input were empty"));
        }
        else{
            User userProfile = userData.tryLogIn(usernameVal, passwordVal);

            if(Objects.isNull(userProfile)){
                this.mainLabel.setText(strFactory("Warning:<br> The Log In Credentials <br> Were Incorrect"));
            }
            else{
                this.password.setText("");
                this.userName.setText("");
                if (userProfile.isOwner() || userProfile.isSuperUser()) {
                    this.ownerPage = new OwnerPage(this.userData, userProfile , this, this.settings);
                }
                else if(userProfile.isCashier()){
                    new CashierPage(userProfile, this);
                } 
                else if(userProfile.isSeller()){
                    new SellerPage(userProfile, this, this.itemData);
                }
                else {
                    successfulLogIn(userProfile);
                }

                this.LogInFrame.setVisible(false);
            }
        }
    }

    private void successfulLogIn(User userProfile){
        this.password.setText("");
        this.userName.setText("");
        new ShoppingPage(this.itemData, this, userProfile, this.LogInFrame.getX(), this.LogInFrame.getY());
        this.LogInFrame.setVisible(false);
    }

    private void NewAccountPage(){
        this.LogInFrame.setVisible(false);
        new ProfileCreationPage(this, this.LogInFrame.getX(), this.LogInFrame.getY());
    }

    public void PassNewUser(String UserName, String Password){
        this.userData.addUser(UserName, Password);
        User newuserProfile = userData.tryLogIn(UserName, Password);
        successfulLogIn(newuserProfile);
    }

    private void createforms(){
        this.mainLabel = new JLabel(strFactory("Welcome! <br> Please Log In <br> To use the application!"));
        this.mainLabel.setBounds(0,10,200,60);
        LogInPanel.add(mainLabel);

        JLabel userNameLabel = new JLabel("User Name");
        userNameLabel.setBounds(25,100,100,25);
        LogInPanel.add(userNameLabel);

        this.userName = new JTextField();
        this.userName.setBounds(125,100,100,25);
        LogInPanel.add(userName);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(25,175,100,25);
        LogInPanel.add(passwordLabel);

        this.password = new JPasswordField();
        this.password.setBounds(125,175,100,25);
        LogInPanel.add(password);

        JButton guestLogIn = new JButton("<html><p style = \"text-align: center;\">Guest Access</p></html>");
        guestLogIn.setBounds(250,25,100,50);
        guestLogIn.addActionListener(e -> guestAccess());
        LogInPanel.add(guestLogIn);

        JButton LogIn = new JButton("<html><p style = \"text-align: center;\">Log In</p></html>");
        LogIn.setBounds(250,100,100,50);
        LogIn.addActionListener(e -> verifyUser());
        LogInPanel.add(LogIn);

        JButton createAccount = new JButton("<html><p style = \"text-align: center;\">Create Account</p></html>");
        createAccount.setBounds(250,175,100,50);
        createAccount.addActionListener(e -> NewAccountPage());
        LogInPanel.add(createAccount);
    }

    private void guestAccess(){
        this.password.setText("");
        this.userName.setText("");
        new ShoppingPage(this.itemData, this, this.userData.getGuest() , this.LogInFrame.getX(), this.LogInFrame.getY());
        this.LogInFrame.setVisible(false);
    }
    
    public void logOut(){
        this.LogInFrame.setVisible(true);
    }
  
}