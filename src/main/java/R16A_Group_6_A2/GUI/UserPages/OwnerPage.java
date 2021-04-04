package R16A_Group_6_A2;

import R16A_Group_6_A2.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

public class OwnerPage {

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel timeoutLabel;
    private JLabel warningText;
    private JLabel cardWarningText;
    private JLabel transactionsDownloadLabel;
    private JLabel usernamesDownloadLabel;
    private JList<String> userPanel;
    private JList<String> creditCardPanel;
    private JTextField timeout;
    private JTextField cardName;
    private JTextField cardNumber;
    private JTextField username;
    private JTextField password;
    private DefaultListModel<String> model;
    private DefaultListModel<String> cardModel;
    private UserData userData;
    private LogInPage login;
    private ShoppingPage shoppingPage;
    private Settings settings;
    private User owner;

    public OwnerPage(UserData userData, User owner, LogInPage login, Settings settings) {
        this.mainFrame = new JFrame("Owner Page");
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(null);
        this.userData = userData;
        this.login = login;
        this.settings = settings;
        this.owner = owner;

        buildPage();

        this.mainFrame.setSize(830, 725);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setVisible(true);

        this.mainFrame.add(this.mainPanel);

    }

    private void buildPage() {
        this.timeoutLabel = new JLabel();
        this.timeoutLabel.setBounds(125, 650, 150, 50);
        this.mainPanel.add(this.timeoutLabel);

        this.warningText = new JLabel("");
        this.warningText.setBounds(50, 555, 300, 50);
        this.mainPanel.add(this.warningText);

        this.cardWarningText = new JLabel();
        this.cardWarningText.setBounds(550, 475, 300, 50);
        this.mainPanel.add(this.cardWarningText);
      
        this.transactionsDownloadLabel = new JLabel("");
        this.transactionsDownloadLabel.setBounds(640, 650, 150, 50);
        this.mainPanel.add(this.transactionsDownloadLabel);

        this.usernamesDownloadLabel = new JLabel();
        this.usernamesDownloadLabel.setBounds(470, 650, 150, 50);
        this.mainPanel.add(this.usernamesDownloadLabel);

//        JButton shoppingPage = new JButton("Shopping Page");
//        shoppingPage.setBounds(50, 50, 150, 50);
//        shoppingPage.addActionListener(e -> goShoppingPage());
//        this.mainPanel.add(shoppingPage);

        JButton cashierPage = new JButton("Cashier Page");
        cashierPage.setBounds(175, 25, 150, 50);
        cashierPage.addActionListener(e -> cashierPage());
        this.mainPanel.add(cashierPage);

        JButton sellerPage = new JButton("Seller Page");
        sellerPage.setBounds(335, 25, 150, 50);
        sellerPage.addActionListener(e -> sellerPage());
        this.mainPanel.add(sellerPage);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(495, 25, 150, 50);
        logoutButton.addActionListener(e -> logOut());
        this.mainPanel.add(logoutButton);

        JLabel userActions = new JLabel("Users: ");
        userActions.setBounds(50, 70, 150, 50);
        this.mainPanel.add(userActions);

        JLabel cardsLabel = new JLabel("Credit Cards: ");
        cardsLabel.setBounds(470, 70, 150, 50);
        this.mainPanel.add(cardsLabel);

        JButton rmvUserButton = new JButton("Remove User");
        rmvUserButton.setBounds(210, 450, 150, 50);
        rmvUserButton.addActionListener(e -> removeUser());
        this.mainPanel.add(rmvUserButton);

        JButton addUserButton = new JButton("Add User");
        addUserButton.setBounds(50, 450, 150, 50);
        addUserButton.addActionListener(e -> addUser());
        this.mainPanel.add(addUserButton);

        JLabel usernameLabel = new JLabel("New User's Username: ");
        usernameLabel.setBounds(50, 495, 150, 50);
        this.mainPanel.add(usernameLabel);

        JLabel passwordLabel = new JLabel("New User's Password: ");
        passwordLabel.setBounds(210, 495, 150, 50);
        this.mainPanel.add(passwordLabel);

        this.username = new JTextField();
        this.username.setBounds(50, 535, 150, 25);
        this.mainPanel.add(this.username);

        this.password = new JTextField();
        this.password.setBounds(210, 535, 150, 25);
        this.mainPanel.add(this.password);

        JButton updateCashierButton = new JButton("Change to Cashier");
        updateCashierButton.setBounds(35, 365, 175, 25);
        updateCashierButton.addActionListener(e -> modifyUser("CASHIER"));
        this.mainPanel.add(updateCashierButton);

        JButton updateSellerButton = new JButton("Change to Seller");
        updateSellerButton.setBounds(205, 365, 175, 25);
        updateSellerButton.addActionListener(e -> modifyUser("SELLER"));
        this.mainPanel.add(updateSellerButton);

        JButton updateOwnerButton = new JButton("Change to Owner");
        updateOwnerButton.setBounds(205, 390, 175, 25);
        updateOwnerButton.addActionListener(e -> modifyUser("OWNER"));
        this.mainPanel.add(updateOwnerButton);

        JButton updateCustomerButton = new JButton("Change to Customer");
        updateCustomerButton.setBounds(35, 390, 175, 25);
        updateCustomerButton.addActionListener(e -> modifyUser("CUSTOMER"));
        this.mainPanel.add(updateCustomerButton);

        JButton updateSuperUserButton = new JButton("Change to Superuser");
        updateSuperUserButton.setBounds(120, 415, 175, 25);
        updateSuperUserButton.addActionListener(e -> modifyUser("SUPERUSER"));
        this.mainPanel.add(updateSuperUserButton);

        JButton addCardButton = new JButton("Add Credit Card");
        addCardButton.setBounds(470, 365, 150, 50);
        addCardButton.addActionListener(e -> addCard());
        this.mainPanel.add(addCardButton);

        JButton removeCardButton = new JButton("Remove Credit Card");
        removeCardButton.setBounds(630, 365, 150, 50);
        removeCardButton.addActionListener(e -> removeCard());
        this.mainPanel.add(removeCardButton);

        JLabel cardNameLabel = new JLabel("New Card Name: ");
        cardNameLabel.setBounds(470, 410, 150, 50);
        this.mainPanel.add(cardNameLabel);

        JLabel cardNumberLabel = new JLabel("New Card Number: ");
        cardNumberLabel.setBounds(630, 410, 150, 50);
        this.mainPanel.add(cardNumberLabel);

        this.cardName = new JTextField();
        this.cardName.setBounds(470, 450, 150, 25);
        this.mainPanel.add(this.cardName);

        this.cardNumber = new JTextField();
        this.cardNumber.setBounds(630, 450, 150, 25);
        this.mainPanel.add(this.cardNumber);

        JLabel reports = new JLabel("Reports: ");
        reports.setBounds(465, 565, 150, 50);
        this.mainPanel.add(reports);

        JButton userReportButton = new JButton("Usernames");
        userReportButton.setBounds(460, 600, 150, 50);
        userReportButton.addActionListener(e -> usernameReport());
        this.mainPanel.add(userReportButton);

        JButton transactionReportButton = new JButton("Cancelled Transactions");
        transactionReportButton.setBounds(620, 600, 175, 50);
        transactionReportButton.addActionListener(e -> {
            cancelledTransactionsReport();
        });
        this.mainPanel.add(transactionReportButton);

        this.model = new DefaultListModel<>();
        this.userPanel = new JList<>(this.model);
        JScrollPane usersScrollabPane = new JScrollPane(this.userPanel);
        usersScrollabPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        usersScrollabPane.setBounds(50,110,310,250);
        this.mainPanel.add(usersScrollabPane);

        this.cardModel = new DefaultListModel<>();
        this.creditCardPanel = new JList<>(this.cardModel);
        JScrollPane cardsScrollabPane  = new JScrollPane(this.creditCardPanel);
        cardsScrollabPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        cardsScrollabPane.setBounds(470, 110, 310, 250);
        this.mainPanel.add(cardsScrollabPane);

        this.timeout = new JTextField(String.valueOf(this.settings.getTimeout()));
        this.timeout.setBounds(160, 612, 100, 25);
        this.mainPanel.add(this.timeout);

        JLabel timeoutLabel = new JLabel("Update Timeout: ");
        timeoutLabel.setBounds(50, 600, 150, 50);
        this.mainPanel.add(timeoutLabel);

        JButton setTimeout = new JButton("Set Timeout");
        setTimeout.setBounds(270, 600, 150, 50);
        setTimeout.addActionListener(e -> updateTimeout());
        this.mainPanel.add(setTimeout);

        addUsers();
        addCards();
    }

    private void logOut() {
        this.login.logOut();
        this.mainFrame.dispose();
    }

    private void cashierPage() {
        new CashierPage(this.owner, this.login);
        this.mainFrame.dispose();
    }

    private void sellerPage() {
        new SellerPage(this.owner, this.login, new ItemData());
        this.mainFrame.dispose();
    }

//    private void goShoppingPage() {
//        this.shoppingPage = new ShoppingPage(new ItemData(), this.login, "Owner");
//        this.mainFrame.setVisible(false);
//    }

    private void addUsers() {
        List<String> usernames = new ArrayList<>(this.userData.getUserLookup().keySet());
        List<User> users = new ArrayList<>(this.userData.getUserLookup().values());
        for (int i = 0; i < this.userData.getUserLookup().size(); i++) {
            String type;
            if (users.get(i).isCashier()) {
                type = "CASHIER";
            } else if (users.get(i).isCustomer()) {
                type = "CUSTOMER";
            } else if (users.get(i).isGuest()) {
                type = "GUEST";
            } else if (users.get(i).isOwner()) {
                type = "OWNER";
            } else if (users.get(i).isSeller()) {
                type = "SELLER";
            } else if (users.get(i).isSuperUser()) {
                type = "SUPERUSER";
            } else {
                type = "Unknown";
            }
            this.model.add(i, usernames.get(i) + " (" + type + ")");
        }
    }

    private void addCards() {
        CreditCardData cardData = new CreditCardData();
        HashMap<String, CreditCard> cards = cardData.getCards();
        List<String> numbers = new ArrayList<>(cards.keySet());
        for (int i = 0; i < numbers.size(); i++) {
            this.cardModel.add(i, cards.get(numbers.get(i)).getName() + " (" + numbers.get(i) + ")");
        }
    }

    private void updateTimeout() {
        String valueText = this.timeout.getText();
        if (valueText.equals("")) {
            this.timeoutLabel.setText("Please enter a number.");
            return;
        }

        double value = 0.0;

        try {
            value = Double.parseDouble(valueText);
        }
        catch (NumberFormatException e) {
            this.timeoutLabel.setText("Please enter a number.");
            return;
        }

        this.settings.setTimeout(value);
    }

    private void addUser() {
        String username = this.username.getText();
        String password = this.password.getText();

        if (username.equals("")) {
            this.warningText.setText("Please Enter A Username.");
            return;
        } else if (password.equals("")) {
            this.warningText.setText("Please Enter A Password.");
            return;
        } else {
            this.warningText.setText("");
        }
        if (this.userData.addUser(username, password)) {
            this.model.add(this.model.size(), username + " (CUSTOMER)");
        } else {
            this.warningText.setText("A User with this Username Already Exists.");
        }
    }

    private void removeUser() {
        int index = this.userPanel.getSelectedIndex();
        if (index >= 0) {
            String[] nameArray = userPanel.getSelectedValue().split(" ");
            String name = "";
            for (int i = 0; i < nameArray.length - 1; i++) {
                if (i == nameArray.length - 2) {
                    name += nameArray[i];
                }
                else {
                    name += nameArray[i] + " ";
                }
            }
            if (this.userData.removeUser(this.owner, name)) {
                this.model.remove(index);
            }
        }
    }


    private void addCard() {
        CreditCardData cardData = new CreditCardData();

        String cardName = this.cardName.getText();
        String cardNumber = this.cardNumber.getText();

        if (cardName.equals("")) {
            this.cardWarningText.setText("Please Enter a Name.");
            return;
        } else if (cardNumber.equals("")) {
            this.cardWarningText.setText("Please Enter a Card Number.");
            return;
        }
        try {
            Integer num = Integer.parseInt(cardNumber);
        } catch (NumberFormatException e) {
            this.cardWarningText.setText("Please Enter Digits Only.");
            return;
        }
        if (cardData.addCard(cardName, cardNumber)) {
            this.cardModel.add(this.cardModel.size(), cardName + " (" + cardNumber + ")");
            this.cardWarningText.setText("");
        } else {
            this.cardWarningText.setText("That Card Number already exists.");
        }
    }

    private void removeCard() {
        CreditCardData cardData = new CreditCardData();
        int index = this.creditCardPanel.getSelectedIndex();
        if (index >= 0) {
            String[] cardInfo = this.creditCardPanel.getSelectedValue().split(" ");
            String number = cardInfo[cardInfo.length - 1];
            number = number.substring(1, number.length() - 1);
            if (cardData.removeCard(number)) {
                this.cardModel.remove(index);
            }
        }
    }

    private void cancelledTransactionsReport(){
        new TransactionData(new ItemData()).getUnsuccessfulReport();
        this.transactionsDownloadLabel.setText("Report downloaded!");
    }

    private void usernameReport() {
        this.userData.createUserReport();
        this.usernamesDownloadLabel.setText("Report downloaded!");
    }

    private void modifyUser(String userType) {
        int index = this.userPanel.getSelectedIndex();
        if (index >= 0) {
            String[] nameArray = userPanel.getSelectedValue().split(" ");
            String name = "";
            for (int i = 0; i < nameArray.length - 1; i++) {
                if (i == nameArray.length - 2) {
                    name += nameArray[i];
                }
                else {
                    name += nameArray[i] + " ";
                }
            }

            User.UserType userTypeEnum = null;
            if(userType == "GUEST"){
                userTypeEnum =  User.UserType.GUEST;
            }
            else if(userType == "CUSTOMER"){
                userTypeEnum = User.UserType.CUSTOMER;
            }
            else if(userType == "CASHIER"){
                userTypeEnum = User.UserType.CASHIER;
            }
            else if(userType == "SELLER"){
                userTypeEnum = User.UserType.SELLER;
            }
            else if(userType == "OWNER"){
                userTypeEnum = User.UserType.OWNER;
            }
            else if(userType == "SUPERUSER"){
                userTypeEnum = User.UserType.SUPERUSER;
            }

            if (this.userData.changeType(this.owner, name, userTypeEnum)) {
                this.model.set(index, name + " (" + userType + ")");
            }
        }
    }

}