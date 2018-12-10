import java.io.*;
import java.util.*;
import java.net.*;

public class CommandExecutor {


    public void start(ArrayList<String> resonseData, String fileRequested, String payload) {
        ArrayList<String> details = new ArrayList<String>();
        ArrayList<String> workingAccount = new ArrayList<String>(15);
        ArrayList<String> transferAccount = new ArrayList<String>();
        String command = fileRequested;

        if (command.equals("/create")) {
            System.out.println("create requested");
              new Account().create(details,resonseData, payload);

        } else if (command.equals("/deposit")) {
            new Transaction().deposit(details,workingAccount,resonseData, payload);

        } else if (command.equals("/withdraw")) {
            new Transaction().withdraw(details,workingAccount,resonseData, payload);

        } else if (command.equals("/transfer")) {
            new Transaction().transfer(details,workingAccount,transferAccount, resonseData, payload);
        } else if (command.equals("/find")) {
            new Transaction().findAccount(details,workingAccount, resonseData, payload);
        } else if (command.equals("/findActivity")) {
            new Transaction().findActivity(details,workingAccount, resonseData, payload);
        }
    }


}
