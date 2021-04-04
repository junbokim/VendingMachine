package R16A_Group_6_A2;

import R16A_Group_6_A2.*;

import java.util.ArrayList;
import java.util.Objects;
import java.awt.Color;
import java.math.*;

import javax.swing.*;


public class ShoppingPage {

    protected JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel itemsPanel;
    private JList<String> shoppingCartPanel;
    private DefaultListModel<String> model;
    private ItemData itemData;
    private int x;
    private int y;
    private LogInPage login;
    private ShoppingCart shoppingCart;
    private User userProfile;
    private JLabel warningLabel;

    private boolean hasUserTransactionData;
    UserTransactionData userTransactionData;
    ArrayList<String> itemNames;

    public ShoppingPage(ItemData itemData, LogInPage login, User userProfile, int x, int y){

        // setting attributes
        this.shoppingCart = new ShoppingCart(itemData);
        this.login = login;
        this.itemData = itemData;
        this.userProfile = userProfile;

        // setting main frame
        this.mainFrame = new JFrame();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(null);
        userTransactionData = new UserTransactionData();
        itemNames =  userTransactionData.getLatest(this.userProfile.getUsername());
        if(itemNames.size() == 0){
            this.hasUserTransactionData = false;
        }
        else{
            this.hasUserTransactionData = true;
        }

        buildPage();

        // 
        this.mainFrame.setBounds(x,y,1200,800);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setVisible(true);
        hasUserTransactionData = false;

    }

    private void buildPage(){

        JLabel title = new JLabel("<html><h1 style = \"text-align: center; font-size: 30px; width: 300px; align-content: center\"> Shopping Page </h1></html>");
        title.setBounds(20, 20, 600, 50);
        this.mainPanel.add(title);

        warningLabel = new JLabel();
        warningLabel.setBounds(400,10,400,100);
        warningLabel.setForeground(Color.red);
        this.mainPanel.add(warningLabel);

        JLabel welcome = new JLabel("");
        welcomingMessage(this.userProfile.getUsername(), welcome);
        welcome.setBounds(900,20,250,125);
        this.mainPanel.add(welcome);

        JButton Cash = new JButton("<html><p style =\" text-align: center \"> Purchase<br>With Cash</p></html>");
        Cash.setBounds(900,175, 115,75);
        Cash.addActionListener(e -> navTransCash());
        this.mainPanel.add(Cash);

        JButton Card = new JButton("<html><p style =\" text-align: center \"> Purchase<br>With Card</p></html>");
        Card.setBounds(1035,175, 115,75);
        Card.addActionListener(e -> navTransCard());
        this.mainPanel.add(Card);

        JButton cancel = new JButton("Log Out");
        cancel.setBounds(900,275, 250,75);
        cancel.addActionListener(e -> logOut());
        this.mainPanel.add(cancel);

        this.model = new DefaultListModel<>();
        shoppingCartPanel = new JList<>(this.model);
        JScrollPane shoppingCartScrollabPane = new JScrollPane(shoppingCartPanel);
        shoppingCartScrollabPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        shoppingCartScrollabPane.setBounds(900,375,250,250);
        this.mainPanel.add(shoppingCartScrollabPane);

        JButton rmvButton = new JButton("Remove Item");
        rmvButton.setBounds(900,650,200,75);
        rmvButton.addActionListener(e -> removeItem());
        mainPanel.add(rmvButton);

        this.itemsPanel = new JPanel();
        this.itemsPanel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(25);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 100, 850, 650);
        buildUserTransactions();
        buildList();

        this.mainPanel.setPreferredSize(new java.awt.Dimension(1200, 800));
        this.mainPanel.add(scrollPane);

        this.mainFrame.setContentPane(this.mainPanel);
        this.mainFrame.pack();
    }

    private void addItem(String name, int quant){
        
        if(quant == 0){
            this.warningLabel.setText("You have selected 0 quantity");
            return;
        }

        shoppingCart.addItem(name, quant);

        model.removeAllElements();

        ShoppingCart shoppingItem;

        for(int i = 0; i < shoppingCart.getNames().size(); i++){
            String tempName = shoppingCart.getNames().get(i);
            model.addElement(String.format("%s (x%d)",tempName , shoppingCart.getQuantity(tempName)));
        }
    }


    private void removeItem(){
        if(shoppingCartPanel.getSelectedIndex() >=0){
            String[] nameArray = shoppingCartPanel.getSelectedValue().split(" ");
            String name = "";
            for (int i = 0; i < nameArray.length - 1; i++) {
                if (i == nameArray.length - 2) {
                    name += nameArray[i];
                }
                else {
                    name += nameArray[i] + " ";
                }
            }
            shoppingCart.removeItem(name);
            model.remove(shoppingCartPanel.getSelectedIndex());
        }
    }

    // Function responsible to structuring the String to format in CSS style, for Item Type Name Heading
    private String strFactoryHeading(String str){
        return String.format("<html><h1> %s </h1></html>", str);
    }

    // Function responsible to structuring the String to format in CSS style, for Item Card's Item Name Heading
    private String strFactoryItemName(String str){
        return String.format("<html><p style = \"text-align: center; width: 80px; align-content: center\"> %s </p></html>", str);
    }

    // Dynamic creation of individual Item cards per item in the Items Database
    private void createCard(Item item, int x, int y){
        JPanel panel = new JPanel();
        panel.setBounds(x, y,125 , 175);
        panel.setLayout(null);

        JLabel Name = new JLabel(strFactoryItemName(item.getName()));
        Name.setBounds(10,0,100,25);
        panel.add(Name);

        JLabel priceLabel = new JLabel(String.format("Price: $%.2f", item.getPrice()));
        priceLabel.setBounds(10, 25, 100, 25);
        panel.add(priceLabel);

        ArrayList<String> quant = new ArrayList<>();

        for(int i = 0; i <= item.getQuantity(); i++){
            quant.add(Integer.toString(i));
        }

        JComboBox quantity = new JComboBox(quant.toArray());
        quantity.setBounds(10, 50, 100, 25);
        panel.add(quantity);

        JButton addItemButton = new JButton("Add to Cart");
        addItemButton.addActionListener(e -> addItem(item.getName(), quantity.getSelectedIndex()));
        addItemButton.setBounds(10,100,100,50);
        panel.add(addItemButton);

        JLabel inStock = new JLabel("In Stock: " + item.getQuantity());
        inStock.setBounds(10, 75, 100, 25);
        panel.add(inStock);

        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        this.itemsPanel.add(panel);
    }

    private void buildUserTransactions(){
        if(this.hasUserTransactionData){
            this.hasUserTransactionData = true;
            JLabel userRecentTransaction = new JLabel(strFactoryHeading("Most recent items bought"));
            userRecentTransaction.setBounds(20,0,300,50);
            this.itemsPanel.add(userRecentTransaction);
            Item itemsearch = null;
            for(int i = 0; i < itemNames.size(); i++){
                for(Item items: this.itemData.getItems()){
                    if(items.getName().equals(itemNames.get(i))){
                        itemsearch = items;
                    }
                }
                createCard(itemsearch, 20 + 150 * i, 50);
            }
        }

    }

    //  Function responsible to build the Item list in the scrollable pannel
    private void buildList(){
        ArrayList<Item> items = itemData.getItems();
        JLabel itemHeading;
        int bufferSpace;
        if(this.hasUserTransactionData){
            bufferSpace = 1;
        }
        else{
            bufferSpace = 0;
        }

        int x = 0;
        for(int i = 0; i < itemData.getCategories().size(); i++){
            itemHeading = new JLabel();
            itemHeading.setText(strFactoryHeading(itemData.getCategories().get(i)));

            itemHeading.setBounds(20, 250 * (i + bufferSpace), 200,50);
            itemHeading.getY();
            this.itemsPanel.add(itemHeading);
            int num_item = 0;
            for(int j = 0; j < itemData.getItems().size(); j++){
                if(itemData.getItems().get(j).getCategory().equals(itemData.getCategories().get(i))){
                    createCard(itemData.getItems().get(j), 20 + 150 * num_item, 250 * (i + bufferSpace) + 50);


                    if( x < (20 + 150 * num_item + 150)){
                        x = 20 + 150 * num_item + 150;
                    }
                    num_item ++;
                }
            }
        }

        if(this.hasUserTransactionData){
            this.itemsPanel.setPreferredSize(new java.awt.Dimension(x,1250));
        }
        else{
            this.itemsPanel.setPreferredSize(new java.awt.Dimension(x,1000));
        }

    }

    private void logOut(){
        this.login.logOut();
        this.mainFrame.dispose();
    }

    public void timeOut(){
        this.warningLabel.setText("You were timed out");
        this.warningLabel.setVisible(true);
        this.mainFrame.setVisible(true);
    }

    private void welcomingMessage(String userName, JLabel welcome){
        String tempcont = "<html> <p style = \" text-align:center; font-size = 25px \"> Hello <b> %s! </b> <br> Welcome to our Vending Machine."+
        "<br> please enjoy our goods!<br> Thank you </p></html>";
        String content = String.format(
            tempcont, 
            userName);
            welcome.setText(content);
    }

    private Boolean verifyCart(){
        if(this.shoppingCart.getItems().size() == 0){
            this.warningLabel.setText("You have not selected any item to purchase");
            this.warningLabel.setVisible(true);
            return false;
        }
        this.warningLabel.setVisible(false);
        return true;
    }

    private void navTransCash(){
        if(verifyCart()){
            this.mainFrame.setVisible(false);
            new TransactionCashGUI(this, this.mainFrame ,this.login,this.userProfile, this.shoppingCart,this.itemData, this.mainFrame.getX(), this.mainFrame.getY());
        }
    }

    private void navTransCard(){
        if(verifyCart()){
            this.mainFrame.setVisible(false);
            new TransactionCardGUI(this,this.mainFrame ,this.login,this.userProfile, this.shoppingCart,this.itemData, this.mainFrame.getX(), this.mainFrame.getY());
        }
    }

}