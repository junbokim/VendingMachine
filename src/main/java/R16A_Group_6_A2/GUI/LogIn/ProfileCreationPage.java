package R16A_Group_6_A2;

import R16A_Group_6_A2.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

public class ProfileCreationPage{

    private JFrame NewAccountFrame;
    private JPanel NewAccountPanel;

    private JTextField userName;
    private JPasswordField password;
    private JPasswordField confirmPassword;
    private JLabel mainLabel;
    
    private LogInPageInterface logInPage;

    public ProfileCreationPage(LogInPageInterface logInPage, int x, int y){
        this.logInPage = logInPage;
        this.NewAccountFrame = new JFrame("Create New Account");

        this.NewAccountPanel = new JPanel();
        this.NewAccountPanel.setLayout(null);

        this.NewAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.NewAccountFrame.add(NewAccountPanel);
        this.NewAccountFrame.setBounds(x,y,400,300);
        createPage();

        this.NewAccountFrame.setVisible(true);
    }

    private String strFactory(String str){
        return String.format("<html><p style = \"text-align: center; width: 300px; align-content: center\"> %s </p></html>", str);
    }


    private void createPage(){

        this.mainLabel = new JLabel(strFactory("Thank you! <br> I hope you enjoy <br> Please fill in the details!"));
        this.mainLabel.setBounds(0,10,400,75);
        NewAccountPanel.add(mainLabel);

        JLabel userNameLabel = new JLabel("User Name");
        userNameLabel.setBounds(25,100,100,25);
        NewAccountPanel.add(userNameLabel);

        this.userName = new JTextField();
        this.userName.setBounds(150,100,100,25);
        NewAccountPanel.add(userName);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(25,150,100,25);
        NewAccountPanel.add(passwordLabel);

        this.password = new JPasswordField();
        this.password.setBounds(150,150,100,25);
        NewAccountPanel.add(password);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(25,200,125,25);
        NewAccountPanel.add(confirmPasswordLabel);

        this.confirmPassword = new JPasswordField();
        this.confirmPassword.setBounds(150,200,100,25);
        NewAccountPanel.add(confirmPassword);

        JButton createAccount = new JButton("<html><p style = \"text-align: center;\">Create Account</p></html>");
        createAccount.setBounds(275,130,75,60);
        createAccount.addActionListener(e -> createProfile());
        NewAccountPanel.add(createAccount);
    }

    private void createProfile(){
        // checking if the passwords match
        if(this.password.getText().equals("") || this.confirmPassword.getText().equals("") || this.userName.getText().equals("") ){
            this.mainLabel.setText(strFactory("Warning: <br>You have left one or more <br> input field empty"));
            this.mainLabel.setVisible(true);
        }
        else if(!this.confirmPassword.getText().equals(this.password.getText())){
            this.mainLabel.setText(strFactory("Warning: <br>The password and confirmation password<br> do not match"));
            this.mainLabel.setVisible(true);
        }
        else{
            try{
                File f = new File("data/users.txt");
                Scanner reader = new Scanner(f);
                boolean taken = false;
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    String[] splitData = data.split(",");
                    if(splitData[0].equals(this.userName.getText())){
                        this.mainLabel.setText(strFactory("Warning: <br>This username is already taken"));
                        this.mainLabel.setVisible(true);
                        taken = true;
                        break;
                    }
                }
                reader.close();
                if(!taken){
                    this.logInPage.PassNewUser(this.userName.getText(), this.password.getText());
                    this.NewAccountFrame.setVisible(false);
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("Could not find database");
                e.printStackTrace();
            }
        }
    }
}