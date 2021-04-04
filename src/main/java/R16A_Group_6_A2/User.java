package R16A_Group_6_A2;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class User {

    public enum UserType {
        CUSTOMER,
        CASHIER,
        SELLER,
        OWNER,
        GUEST,
        SUPERUSER
    }

    private String username;
    private String password;

    private CreditCard card;

    private UserType userType;

    User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    User(String username, String password, UserType userType, String cardName, String cardNumber) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.card = new CreditCard(cardName, cardNumber);
    }

    public boolean isCustomer(){
        return this.userType == UserType.CUSTOMER;
    }

    public boolean isCashier(){
        return this.userType == UserType.CASHIER;
    }

    public boolean isSeller(){
        return this.userType == UserType.SELLER;
    }

    public boolean isOwner(){
        return this.userType == UserType.OWNER;
    }

    public boolean isAdmin(){
        return this.userType == UserType.OWNER || this.userType == UserType.SUPERUSER;
    }

    public boolean isGuest(){
        return this.userType == UserType.GUEST;
    }

    public boolean isSuperUser(){
        return this.userType == UserType.SUPERUSER;
    }

    public String getUsername(){
        return this.username;
    }

    public boolean tryLogIn(String username, String password){
        return (this.username.equals(username) && this.password.equals(password));
    }


    public boolean changeType(UserType userType) {
        this.userType = userType;
        return true;
    }

    public boolean setCard(String cardName, String cardNumber) {
        this.card = new CreditCard(cardName, cardNumber);
        return true;
    }

    public CreditCard getCard() {
        if (Objects.nonNull(card)) {
            return card;
        }
        return null;
    }

    public String formatForReport(){
        StringBuilder exportString = new StringBuilder();
        exportString.append(username);
        exportString.append(",");
        exportString.append(userType.toString()); // Yes, this is horrifically insecure, no, it's not a requirement to make it more secure
        return exportString.toString();
    }
  
    public String formatForExport(){
        StringBuilder exportString = new StringBuilder();
        exportString.append(username);
        exportString.append(",");
        exportString.append(password);
        exportString.append(",");
        exportString.append(userType.toString()); // Yes, this is horrifically insecure, no, it's not a requirement to make it more secure
        if (Objects.nonNull(card)) {
            exportString.append(",");
            exportString.append(card.getName());
            exportString.append(",");
            exportString.append(card.getNumber());
        }
        return exportString.toString();
    }

    public boolean setFromString(String inputString){
        String[] splits = inputString.split(",");
        List<String> strings = Arrays.asList(splits);
        if (strings.size() == 3){
            username = strings.get(0);
            password = strings.get(1);
            userType = UserType.valueOf(strings.get(2));
            return true;
        } else if (strings.size() == 5) {
            username = strings.get(0);
            password = strings.get(1);
            userType = UserType.valueOf(strings.get(2));
            card = new CreditCard(strings.get(3), strings.get(4));
            return true;
        }
        return false;
    }
}