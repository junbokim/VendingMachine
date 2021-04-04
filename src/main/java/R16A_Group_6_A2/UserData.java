package R16A_Group_6_A2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class UserData {

    private HashMap<String, User> userLookup;
    private User guest;

    private static String filepath = "data/users.txt";

    public static void setTestingFilepath(){
        UserData.filepath = "testdata/users.txt";
    }

    public UserData() {
        userLookup = new HashMap<>();
        guest = new User("guest","guest", User.UserType.GUEST);
        loadPersistentData();
    }

    UserData(boolean load) {
        userLookup = new HashMap<>();
        guest = new User("guest","guest", User.UserType.GUEST);

        if (load) {
            loadPersistentData();
        }
    }

    public boolean addUser(String username, String password){
        if (!userLookup.containsKey(username)){
            User newUser = new User(username, password, User.UserType.CUSTOMER);
            userLookup.put(username, newUser);

            storePersistentData();

            return true;
        }
        return false;

    }

    public boolean removeUser(User owner, String username) {
        if (userLookup.containsKey(username)) {
            User user = userLookup.get(username);
            if (!user.equals(owner) && !user.isSuperUser()) {
                userLookup.remove(username);
                storePersistentData();
                return true;
            }
        }
        return false;
    }

    public User tryLogIn(String username, String password){
        User attemptUser = userLookup.get(username);
        if (Objects.nonNull(attemptUser)){
            if (attemptUser.tryLogIn(username, password)){
                return attemptUser;
            }
        }

        return null;
    }

    public User getGuest(){
        return this.guest;
    }

    public boolean changeType(User owner, String username, User.UserType userType) {
        if (owner.isAdmin() && !userType.equals(User.UserType.GUEST)) {
            User attemptUser = userLookup.get(username);
            if (Objects.nonNull(attemptUser) && !attemptUser.isGuest() && !attemptUser.isSuperUser()){
                attemptUser.changeType(userType);
                return true;
            }
        }
        return false;
    }

    public HashMap<String, User> getUserLookup() { return this.userLookup; }

    public boolean setCard(String username, String cardName, String cardNumber) {
        User attemptUser = userLookup.get(username);
        if (Objects.nonNull(attemptUser) && !attemptUser.isGuest()) { // Guest cannot save card details
            attemptUser.setCard(cardName, cardNumber);
            storePersistentData();
            return true;
        }
        return false;
    }

    public CreditCard getCard(String username) {
        User attemptUser = userLookup.get(username);
        if (Objects.nonNull(attemptUser) && !attemptUser.isGuest()) {
            return attemptUser.getCard();
        }
        return null;
    }

    public void createUserReport(){
        try {
            File file = new File("reports/users.txt");
            new File("reports").mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            writer.println("username,role");
            for (User user : userLookup.values()) {
                writer.println(user.formatForReport());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

    private void storePersistentData(){
        try {
            File file = new File(UserData.filepath);
            new File(UserData.filepath.split("/")[0]).mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            writer.println(guest.formatForExport());
            for (User user : userLookup.values()) {
                writer.println(user.formatForExport());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

    private void loadPersistentData(){

        File file = new File(UserData.filepath);
        if(file.exists() && !file.isDirectory()) {

            try {
                Scanner reader = new Scanner(file);
                boolean firstLine = true;
                while (reader.hasNextLine()){
                    String nextLine = reader.nextLine();
                    if (firstLine){
                        guest.setFromString(nextLine);
                        firstLine = false;
                    }else{
                        User newUser = new User("","",User.UserType.CUSTOMER);
                        newUser.setFromString(nextLine);
                        userLookup.put(newUser.getUsername(), newUser);
                    }
                }
                reader.close();
            } catch (FileNotFoundException e) {
                //System.out.println("An error occurred.");
                //e.printStackTrace();
            }
        }

    }

}