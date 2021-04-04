package R16A_Group_6_A2;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.*;

import R16A_Group_6_A2.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * SellerPage
 */
public class SellerPage {

    private JFrame frame;
    private JPanel panel;

    private JTextField editNameTextField;
    private JTextField editCodeTextField;
    private JComboBox editCategoryDropDown;
    private JTextField editQuantityTextField;
    private JTextField editPriceTextField;

    private User userProfile;
    private LogInPage logInPage;
    private ItemData itemData;
    private JList<String> itemList;
    private Item selectedItem;

    private JLabel mainLabel;
    private JLabel userFeedback;
    private boolean selected;

    private JLabel welcomingLabel;
    private JScrollPane scrollPane;

    private JLabel downloadLabel;

    public SellerPage(User userProfile, LogInPage logInPage, ItemData itemData){
        this.userProfile = userProfile;
        this.logInPage = logInPage;
        this.itemData = itemData;


        // setting main frame
        this.frame = new JFrame();
        this.panel = new JPanel();
        this.panel.setLayout(null);

        buildPage();

        this.frame.add(panel);

        // 
        this.frame.setBounds(0,0,1200,700);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    private void buildPage(){
        this.downloadLabel = new JLabel("");
        this.panel.add(downloadLabel);

        JLabel title = new JLabel("<html><h1 style = \"text-align: center; font-size: 30px; width: 300px; align-content: center;\"> Seller's Page </h1></html>");
        title.setBounds(20, 20, 400, 75);
        this.panel.add(title);

        JLabel welcome = new JLabel(String.format("<html><h3 style = \"text-align: center; font-size: 20px; width: 300px; align-content: center;\"> welcome %s</h3></html>", this.userProfile.getUsername()));
        welcome.setBounds(450, 20, 400, 75);
        this.panel.add(welcome);

        JLabel listlLabel = new JLabel("<html><p>List of Currently Available Items <br> Please choose one to edit </p></html>");
        listlLabel.setBounds(50,100,200,75);
        this.panel.add(listlLabel);

        this.mainLabel = new JLabel();
        this.mainLabel.setBounds(250,100,600,200);
        this.panel.add(this.mainLabel);

        this.welcomingLabel = new JLabel("<html><h1 style = \"text-align:center\">Welcome to the Seller's Page!<br> Please start by selecting an item <br> you wish to edit!<h1><html>");
        this.welcomingLabel.setBounds(250,150,300,400);
        this.panel.add(this.welcomingLabel);

        userFeedback = new JLabel();
        userFeedback.setBounds(450,100,300,75);
        this.userFeedback.setVisible(false);
        userFeedback.setForeground(Color.red);
        this.panel.add(userFeedback);

        DefaultListModel<String> itemListModel = new DefaultListModel<>();
        for(Item item: this.itemData.getItems()){
            itemListModel.addElement(item.getName());
        }
        itemList = new JList(itemListModel);
        scrollPane = new JScrollPane(itemList);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50,200,150,300);
        this.panel.add(scrollPane);

        JButton selectedButton = new JButton("Edit Selected Item");
        selectedButton.setBounds(50,550,150,75);
        selectedButton.addActionListener(e -> selectItem());
        this.panel.add(selectedButton);

        this.userFeedback = new JLabel();
        this.userFeedback.setForeground(Color.red);
        this.userFeedback.setBounds(750,500,500,150);
        this.panel.add(userFeedback);

        JButton logOutButton = new JButton("Log Out");
        logOutButton.setBounds(1000,100,150,75);
        logOutButton.addActionListener(e -> logOut());
        this.panel.add(logOutButton);

        JButton getSalesReportButton = new JButton("Get Sales Report");
        getSalesReportButton.setBounds(1000,250,150,75);
        getSalesReportButton.addActionListener(e -> getSalesReport());
        this.panel.add(getSalesReportButton);

        JButton getStockReportButton = new JButton(String.format("<html><p style = \"text-align:center\">%s</p></html>","Get Current Stock Report"));
        getStockReportButton.setBounds(1000,400,150,75);
        getStockReportButton.addActionListener(e -> getStockReport());
        this.panel.add(getStockReportButton);
    }

    private void logOut(){
        this.logInPage.logOut();
        this.frame.dispose();
    }

    private void setupScreen(){
        JLabel editNameLabel = new JLabel("Please Enter New Item Name");
        editNameLabel.setBounds(250,350,200,25);
        this.panel.add(editNameLabel);

        this.editNameTextField = new JTextField();
        this.editNameTextField.setBounds(250,400,200,25);
        this.panel.add(editNameTextField);

        JButton editNameButton = new JButton("Update Item Name");
        editNameButton.setBounds(250,450,200,25);
        editNameButton.addActionListener(e -> editName());
        this.panel.add(editNameButton);

        JLabel editCodeLabel = new JLabel("Please Enter New Item Code");
        editCodeLabel.setBounds(500,350,200,25);
        this.panel.add(editCodeLabel);

        this.editCodeTextField = new JTextField();
        this.editCodeTextField.setBounds(500,400,200,25);
        this.panel.add(editCodeTextField);

        JButton editCodeButton = new JButton("Update Item Code");
        editCodeButton.setBounds(500,450,200,25);
        editCodeButton.addActionListener(e -> editItemCode());
        this.panel.add(editCodeButton);

        JLabel editCategoryLabel = new JLabel("Please Select New Item Category");
        editCategoryLabel.setBounds(750,350,200,25);
        this.panel.add(editCategoryLabel);

        this.editCategoryDropDown = new JComboBox();
        this.editCategoryDropDown.setBounds(750,400,200,25);
        this.editCategoryDropDown.setModel(new DefaultComboBoxModel(this.itemData.getCategories().toArray()));
        this.panel.add(editCategoryDropDown);

        JButton editCategoryButton = new JButton("Update Item Category");
        editCategoryButton.setBounds(750,450,200,25);
        editCategoryButton.addActionListener(e -> editCategory());
        this.panel.add(editCategoryButton);

        JLabel editQuantityLabel = new JLabel("Please Select New Item Quantity");
        editQuantityLabel.setBounds(250,500,200,25);
        this.panel.add(editQuantityLabel);

        this.editQuantityTextField = new JTextField();
        this.editQuantityTextField.setBounds(250,550,200,25);
        this.panel.add(editQuantityTextField);

        JButton editQuantityButton = new JButton("Update Item Quantity");
        editQuantityButton.setBounds(250,600,200,25);
        editQuantityButton.addActionListener(e -> editQuantity());
        this.panel.add(editQuantityButton);

        JLabel editPriceLabel = new JLabel("Please Select New Item Price");
        editPriceLabel.setBounds(500,500,200,25);
        this.panel.add(editPriceLabel);

        this.editPriceTextField = new JTextField();
        this.editPriceTextField.setBounds(500,550,200,25);
        this.panel.add(editPriceTextField);

        JButton editPriceButton = new JButton("Update Item Price");
        editPriceButton.setBounds(500,600,200,25);
        editPriceButton.addActionListener(e -> editPrice());
        this.panel.add(editPriceButton);
    }

    private void removeActionListener(JButton button){
        for(ActionListener al: button.getActionListeners()){
            button.removeActionListener(al);
        }
    }

    private void setMainLabel(){
        String str = "<html><p style = \"text-align: center; width: 800 ;font-size: 16px\">" +
                        "Selected Item : %s <br>"+
                        "Selected Item Code : %d <br>"+
                        "Selected Item Category : %s <br> "+
                        "Selected Item Quantity : %d <br> " +
                        "Selected Item Price : %.2f <br>"
                        + "</p></html>";
        Item item = this.selectedItem;
        this.mainLabel.setText(String.format(str, item.getName(), item.getCode(), item.getCategory(), item.getQuantity(), item.getPrice()));
    }

    private void findItem(String itemName){
        for(Item item : this.itemData.getItems()){
            if(item.getName().equals(itemName)){
                this.selectedItem =  item;
            }
        }
    }

    private void redrawList(){

        this.panel.remove(this.scrollPane);

        DefaultListModel<String> itemListModel = new DefaultListModel<>();
        for(Item item: this.itemData.getItems()){
            itemListModel.addElement(item.getName());
        }
        itemList = new JList(itemListModel);
        scrollPane = new JScrollPane(itemList);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50,200,150,300);
        this.panel.add(scrollPane);
    }

    private void selectItem(){
        if(this.itemList.getSelectedIndex() == -1){
            this.userFeedback.setText("You have yet to select any item");
            this.userFeedback.setVisible(true);
            return;
        }
        if(!selected){
            setupScreen();
            this.welcomingLabel.setVisible(false);
        }
        findItem(this.itemList.getSelectedValue());
        setMainLabel();
        this.userFeedback.setVisible(false);
        this.selected = true;
    }

    private String strFormat(String str){
        return String.format("<html><p style = \"text-align:center\">%s</p></html>", str);
    }

    private void restoreSelection(String str){
        for(int i = 0; i< this.itemList.getModel().getSize();i++){
            if(this.itemList.getModel().getElementAt(i).equals(str)){
                this.itemList.setSelectedIndex(i);
                break;
            }
        }
    }

    private void editName(){
        if(!this.selected){
            this.userFeedback.setText(strFormat("You have not selected any item yet<br> please try again after selecting an item"));
            this.userFeedback.setVisible(true);
            return;
        }
        if(this.editNameTextField.getText().equals("")){
            this.userFeedback.setText(strFormat("You did not fill in Item Name <br> Please Fill in and try again"));
            this.userFeedback.setVisible(true);
            return;
        }
        boolean ret_val = this.itemData.changeItemName(this.selectedItem,this.editNameTextField.getText() );
        if(!ret_val){
            this.userFeedback.setText(strFormat("You have entered a Item name that we already have<br> Please try again "));
            this.userFeedback.setVisible(true);
            return;
        }
        setMainLabel();
        redrawList();
        this.userFeedback.setVisible(false);
        restoreSelection(this.selectedItem.getName());
        this.editNameTextField.setText("");
    }

    private void editItemCode(){
        if(!this.selected){
            this.userFeedback.setText(strFormat("You have not selected any item yet<br> please try again after selecting an item"));
            this.userFeedback.setVisible(true);
            return;
        }
        try{
            int newItemCode = Integer.parseInt(this.editCodeTextField.getText());
            if(newItemCode < 0){
                this.userFeedback.setText(strFormat("You have input a negative value <br> please try again"));
                this.userFeedback.setVisible(true);
                return;
            }
            boolean ret_val = this.itemData.changeItemCode(this.selectedItem, newItemCode);
            if(!ret_val){
                this.userFeedback.setText(strFormat("The Item code is already taken by other Item <br> Please try again"));
                this.userFeedback.setVisible(true);
                return;
            }
            setMainLabel();
            this.userFeedback.setVisible(false);
            this.editCodeTextField.setText("");
        }
        catch (NumberFormatException e){
            this.userFeedback.setText(strFormat("You did not put in correct value for ITEM CODE <br> Please Enter again"));
            this.userFeedback.setVisible(true);
        }
    }

    private void editCategory(){
        if(!this.selected){
            this.userFeedback.setText(strFormat("You have not selected any item yet<br> please try again after selecting an item"));
            this.userFeedback.setVisible(true);
            return;
        }
        this.itemData.changeItemCategory(this.selectedItem, this.editCategoryDropDown.getSelectedItem().toString() );
        setMainLabel();
        this.userFeedback.setVisible(false);
        this.editCategoryDropDown.setSelectedIndex(0);
    }

    private void editQuantity(){
        if(!this.selected){
            this.userFeedback.setText(strFormat("You have not selected any item yet<br> please try again after selecting an item"));
            this.userFeedback.setVisible(true);
            return;
        }
        try{
            boolean ret_val = this.itemData.changeItemQuantity(this.selectedItem, Integer.parseInt(this.editQuantityTextField.getText()));
            if(!ret_val){
                this.userFeedback.setText(strFormat("The quantity you have inserted is above the limit<br> Please Enter a valid quantity"));
                this.userFeedback.setVisible(true);
                return;
            }
            setMainLabel();
            this.userFeedback.setVisible(false);
            this.editQuantityTextField.setText("");
        }
        catch (NumberFormatException e){
            this.userFeedback.setText(strFormat("You did not put in correct value for ITEM QUANTITY <br> please try again"));
            this.userFeedback.setVisible(true);
        }
    }

    private void editPrice(){
        if(!this.selected){
            this.userFeedback.setText(strFormat("You have not selected any item yet<br> please try again after selecting an item"));
            this.userFeedback.setVisible(true);
            return;
        }
        try{
            boolean ret_val = this.itemData.changeItemPrice(this.selectedItem, Double.parseDouble(this.editPriceTextField.getText()));
            if(!ret_val){
                this.userFeedback.setText(strFormat("You have input a negative value <br> please try again"));
                this.userFeedback.setVisible(true);
                return;
            }
            setMainLabel();
            this.userFeedback.setVisible(false);
            this.editPriceTextField.setText("");
        }
        catch (NumberFormatException e){
            this.userFeedback.setText(strFormat("You have not put in correct values for ITEM PRICE <br> Please try again."));
            this.userFeedback.setVisible(true);
        }
    }

    private void getSalesReport(){
        new TransactionData(new ItemData()).getItemDetailsReport();
        this.downloadLabel.setBounds(1000,300,150,75);
        this.downloadLabel.setText("Report downloaded!");
    }

    private void getStockReport(){
        new ItemData().getAvailableItemsReport();
        this.downloadLabel.setBounds(1000,450,150,75);
        this.downloadLabel.setText("Report downloaded!");
    }

}