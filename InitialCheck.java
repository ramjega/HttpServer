import java.io.*;
import java.net.*;
import java.util.*;

public class InitialCheck {
    public void check() {
        ArrayList<String> initalData = new ArrayList<String>();
        ArrayList<String> initalUser = new ArrayList<String>();
        File theData = new File("data");
        File theAccounts = new File("data/accounts");
        File theUsers = new File("data/users");
        File theInitalData = new File("data/initalData.txt");
        File theInitalUser = new File("data/users/50000.txt");

        // to print err msg when changes in directory
        if (!theData.exists() || !theAccounts.exists() || !theUsers.exists() || !theInitalData.exists() || !theInitalUser.exists()) {
            System.out.println("Are you runing SRM bank first time ? / Did you change anything in app directory");

        }

        // to create directories and files if any thing missin
        if (!theData.exists()) {

            try {
                theData.mkdir();
                System.out.println("System has created data directory");

            } catch (SecurityException se) {
                System.out.println("Cannot create data directory!! Change the app directory and try again.");
            }

        }


        if (theData.exists() && !theAccounts.exists()) {

            try {
                theAccounts.mkdir();
                System.out.println("System has Accounts directory");

            } catch (SecurityException se) {
                System.out.println("Cannot create accounts directory!! Change the app directory and try again.");
            }

        }


        if (theData.exists() && !theUsers.exists()) {

            try {
                theUsers.mkdir();
                System.out.println("System has users directory");

            } catch (SecurityException se) {
                System.out.println("Cannot create users directory!! Change the app directory and try again.");
            }

        }

        if (theData.exists() && !theInitalData.exists()) {
            try {
                initalData.add(0, "10000");
                initalData.add(1, "50000");
                System.out.println("initalData.txt not found !!.System has created it automatically with an inital account number of 10000 and userId with 50000 , If you are not satisfied with this number please change the inital Account number in data/initalData.txt");
                new Account().fileWriter(initalData, "data/initalData.txt");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("some thing went wrong. read the readme.txt and make sure all the inital data are correct");
            }
        }

        if (theAccounts.exists() && !theInitalUser.exists()) {
            try {
                initalUser.add(0, "50000");
                initalUser.add(1, "systemUser");
                initalUser.add(2, "systemUser");
                initalUser.add(3, "active");
                initalUser.add(4, "admin");
                System.out.println("Inital User not found!! System has created an inital user. you can login to admin with");
                System.out.println("User id  - 50000");
                System.out.println("Password - systemUser");
                new Account().fileWriter(initalUser, "data/users/50000.txt");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("some thing went wrong. read the readme.txt and make sure all the inital data are correct");
            }
        }


    }

}

