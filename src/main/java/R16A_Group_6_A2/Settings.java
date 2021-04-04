package R16A_Group_6_A2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Settings {

    private double timeout; // Timeout time in minutes

    public Settings() {
        this.timeout = 2.0; // Default value
        loadPersistentData();
    }

    Settings(boolean load) {
        this.timeout = 2.0; // Default value
        if (load){
            loadPersistentData();
        }

    }

    public void setTimeout(double newTimeout){
        if (newTimeout > 0.0){ // Basic verification
            this.timeout = newTimeout;
            storePersistentData();
        }
    }

    public double getTimeout(){
        return this.timeout;
    }

    private void storePersistentData(){
        try {
            File file = new File("data/settings.txt");
            new File("data").mkdir();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            writer.print(this.timeout);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Detected");
            e.printStackTrace();
        }
    }

    private void loadPersistentData(){

        File file = new File("data/settings.txt");
        if(file.exists() && !file.isDirectory()) {
            try {
                Scanner reader = new Scanner(file);
                if (reader.hasNextLine()){
                    this.timeout = Double.parseDouble(reader.nextLine());
                }
                reader.close();
            } catch (FileNotFoundException e) {
                //System.out.println("An error occurred.");
                //e.printStackTrace();
            }
        }

    }
}
