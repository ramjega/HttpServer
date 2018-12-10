import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.net.*;

public class Transaction {


    public void deposit(ArrayList<String> details, ArrayList<String> workingAccount, ArrayList<String> responseData, String payload) {
        getdepositdetails(details, payload);
        find(details, workingAccount);
        int inputAmount = 0;
        if(!workingAccount.isEmpty()) {
            int currentBalance = Integer.parseInt(workingAccount.get(5));

            try {
                inputAmount = Integer.parseInt(details.get(1));
            } catch (NumberFormatException ex) {
                String err = "{\"error\": \"Amount is invalid !! please enter the amount in number. \"}";
                responseData.add(err);
            }

            if(inputAmount > 0) {
                int newBalance = inputAmount + currentBalance;
                workingAccount.remove(5);
                workingAccount.add(5, Integer.toString(newBalance));
                new Account().storeAccount(workingAccount);
                //sending response
                String response = "{\"name\":\"" + workingAccount.get(0) + "\",\"nicNumber\":\"" + workingAccount.get(1) + "\",\"mobileNumber\":\"" + workingAccount.get(2) + "\",\"accountNumber\":\"" + workingAccount.get(3) + "\",\"pinNumber\":\"" + workingAccount.get(4) + "\",\"accountBalance\":\"" + workingAccount.get(5) + "\"}";
                responseData.add(response);

                String log = "Deposited  " + Integer.toString(inputAmount) + "LKR";
                logActivity(workingAccount, log);

            } else{
                String err = "{\"error\": \"Please enter a psoitive value!!\"}";
                responseData.add(err);
            }




        }else {
            String err = "{\"error\": \"Account not found or problem with account\"}";
            responseData.add(err);
        }

    }

    public void withdraw(ArrayList<String> details, ArrayList<String> workingAccount, ArrayList<String> responseData, String payload) {

        getWithdrawDetails(details, payload);
        find(details, workingAccount);
        int inputAmount = 0;
        if(!workingAccount.isEmpty()) {
            int currentBalance = Integer.parseInt(workingAccount.get(5));

                try {
                    inputAmount = Integer.parseInt(details.get(1));
                } catch (NumberFormatException ex) {
                    String err = "{\"error\": \"Amount is invalid !! please enter the amount in number. \"}";
                    responseData.add(err);
                }




            if(inputAmount > 0) {
                if (currentBalance >= inputAmount) {

                    int newBalance = currentBalance - inputAmount;
                    workingAccount.remove(5);
                    workingAccount.add(5, Integer.toString(newBalance));
                    new Account().storeAccount(workingAccount);
                    String response = "{\"name\":\"" + workingAccount.get(0) + "\",\"nicNumber\":\"" + workingAccount.get(1) + "\",\"mobileNumber\":\"" + workingAccount.get(2) + "\",\"accountNumber\":\"" + workingAccount.get(3) + "\",\"pinNumber\":\"" + workingAccount.get(4) + "\",\"accountBalance\":\"" + workingAccount.get(5) + "\"}";
                    responseData.add(response);

                    String log = "withdrawed " + Integer.toString(inputAmount) + "LKR";
                    logActivity(workingAccount, log);


                } else {
                    String err = "{\"error\": \"Insufficent balance!!. \"}";
                    responseData.add(err);

                }
            } else{
                String err = "{\"error\": \"Please enter a positive value!!\"}";
                responseData.add(err);
            }
        } else{
            String err = "{\"error\": \"failed to withdraw . Account not found or problem with account\"}";
            responseData.add(err);
        }


    }

    public void findAccount(ArrayList<String> details, ArrayList<String> workingAccount, ArrayList<String> responseData, String payload) {

        getFindAccountDetails(details, payload);
        find(details, workingAccount);
        if(!workingAccount.isEmpty()) {

                String response = "{\"name\":\""+workingAccount.get(0) +"\",\"nicNumber\":\""+workingAccount.get(1) +"\",\"mobileNumber\":\""+workingAccount.get(2) +"\",\"accountNumber\":\""+workingAccount.get(3) +"\",\"pinNumber\":\""+workingAccount.get(4) +"\",\"accountBalance\":\""+workingAccount.get(5) +"\"}";
                responseData.add(response);


        } else{
            String err = "{\"error\": \"Account not found!! or problem with account\"}";
            responseData.add(err);
        }


    }


    public void transfer(ArrayList<String> details,ArrayList<String> workingAccount, ArrayList<String> transferAccount, ArrayList<String> responseData, String payload) {
        getTransferDetails(details, payload);
        find(details, workingAccount);
        findTransferAccount(details, transferAccount);

        if (!workingAccount.isEmpty() && !transferAccount.isEmpty()) {
            String fromAccount = workingAccount.get(3);
            String toAccount = transferAccount.get(3);

            int inputAmount = 0;
            int currentBalanceOfFromAccount = Integer.parseInt(workingAccount.get(5));
            int currentBalanceOfToAccount = Integer.parseInt(transferAccount.get(5));


            try {
                inputAmount = Integer.parseInt(details.get(2));
            } catch (NumberFormatException ex) {
                String err = "{\"error\": \"Amount is invalid !! please enter the amount in number. \"}";
                responseData.add(err);
            }

            if(inputAmount > 0) {

                if (currentBalanceOfFromAccount >= inputAmount) {

                    int newBalanceOfFromAccount = currentBalanceOfFromAccount - inputAmount;
                    workingAccount.remove(5);
                    workingAccount.add(5, Integer.toString(newBalanceOfFromAccount));
                    new Account().storeAccount(workingAccount);


                    int newBalanceOfToAccount = inputAmount + currentBalanceOfToAccount;
                    transferAccount.remove(5);
                    transferAccount.add(5, Integer.toString(newBalanceOfToAccount));

                    new Account().storeAccount(transferAccount);

                    String fromAccountlog = "Transfered " + Integer.toString(inputAmount) + "LKR to " + toAccount;
                    String toAccountlog = "Transfered " + Integer.toString(inputAmount) + "LKR from " + fromAccount;

                    //sending response
                    String response = "{\"fromAccountName\":\"" + workingAccount.get(0) + "\",\"fromAccountNicNumber\":\"" + workingAccount.get(1) + "\",\"fromAccountMobileNumber\":\"543" + workingAccount.get(2) + "\",\"fromAccountNumber\":\"" + workingAccount.get(3) + "\",\"fromAccountPinNumber\":\"" + workingAccount.get(4) + "\",\"fromAccountBalance\":\"" + workingAccount.get(5) + "\",\"toAccountName\":\"" + transferAccount.get(0) + "\",\"toAccountNicNumber\":\"" + transferAccount.get(1) + "\",\"toAccountMobileNumber\":\"" + transferAccount.get(2) + "\",\"toAccountNumber\":\"" + transferAccount.get(3) + "\",\"toAccountPinNumber\":\"" + transferAccount.get(4) + "\",\"toAccountBalance\":\"" + transferAccount.get(5) + " \"}";

                    responseData.add(response);

                    logActivity(workingAccount, fromAccountlog);
                    logActivity(transferAccount, toAccountlog);


                } else {
                    String err = "{\"error\": \"Insufficent balance!!. \"}";
                    responseData.add(err);
                }
            } else {
                String err = "{\"error\": \"Please enter a positive value!!\"}";
                responseData.add(err);
            }

        } else{
            String err = "{\"error\": \"failed to transfer . one or more account not found or problem with account\"}";
            responseData.add(err);
        }
    }


    public void findActivity(ArrayList<String> details, ArrayList<String> workingAccount, ArrayList<String> responseData, String payload) {
        getFindActivityDetails(details, payload);
        find(details, workingAccount);
        String activity = "";

        if(!workingAccount.isEmpty()) {
            for (int x = 6; x < workingAccount.size(); x++) {
                if (workingAccount.get(x) != null) {
                    String printLine = workingAccount.get(x);
                    activity = activity + ", "+ printLine;
                }
            }
            //sending response back
            String response = "{\"activity\" :\""+activity+"\"}";
            responseData.add(response);


        } else{
            String err = "{\"error\": \" Account not found or problem with account\"}";
            responseData.add(err);
        }


    }


    public void getdepositdetails(ArrayList<String> details, String payload){
        String[] parts = payload.split(",");
        String depositAccountNumber = parts[0].substring(24).replace("\"", "");
        String depositAmount = parts[1].substring(16).replace("\"", "").replace("}", "");

        System.out.println("depositAccountNumber is "+depositAccountNumber);
        System.out.println("depositAmount is "+ depositAmount);

        details.add(depositAccountNumber);
        details.add(depositAmount);
    }

    public void getFindAccountDetails(ArrayList<String> details, String payload){
        String[] parts = payload.split(",");
        String findAccountNumber = parts[0].substring(21).replace("\"", "").replace("}", "");

        System.out.println("findAccountNumber is "+findAccountNumber);

        details.add(findAccountNumber);
    }

    public void getWithdrawDetails(ArrayList<String> details, String payload){
        String[] parts = payload.split(",");
        String withdrawAccountNumber = parts[0].substring(25).replace("\"", "");
        String withdrawAmount = parts[1].substring(17).replace("\"", "").replace("}", "");

        System.out.println("withdrawAccount number is "+withdrawAccountNumber);
        System.out.println("withdraw amount is "+ withdrawAmount);

        details.add(withdrawAccountNumber);
        details.add(withdrawAmount);
    }
    public void getFindActivityDetails(ArrayList<String> details, String payload){
        String[] parts = payload.split(",");
        String findActivityNumber = parts[0].substring(29).replace("\"", "").replace("}", "");

        System.out.println("findActivityNumber is "+ findActivityNumber);

        details.add(findActivityNumber);
    }

    public void getTransferDetails(ArrayList<String> details, String payload){
        String[] parts = payload.split(",");
        String fromAccountNumber = parts[0].substring(21).replace("\"", "");
        String toAccountNumber = parts[1].substring(18).replace("\"", "");
        String tranferAmount = parts[2].substring(17).replace("\"", "").replace("}", "");

        System.out.println("fromAccountNumber number is "+fromAccountNumber);
        System.out.println("toAccountNumber  is "+ toAccountNumber);
        System.out.println("tranferAmount  is "+ tranferAmount);

        details.add(fromAccountNumber);
        details.add(toAccountNumber);
        details.add(tranferAmount);
    }

    public void find(ArrayList<String> details, ArrayList<String> workingAccount) {

        int accountNumber = 0;
        try {
            accountNumber = Integer.parseInt(details.get(0));

        } catch (NumberFormatException ex) {

        }
        if (accountNumber > 0) {
            findId(accountNumber, workingAccount);
        }




    }
    public void findTransferAccount(ArrayList<String> details, ArrayList<String> workingAccount) {

        int accountNumber = 0;
        try {
            accountNumber = Integer.parseInt(details.get(1));

        } catch (NumberFormatException ex) {

        }
        if (accountNumber > 0) {
            findId(accountNumber, workingAccount);
        }




    }

    public void findId(int accountNumber, ArrayList<String> workingAccount) {
        String filePath = Integer.toString(accountNumber) + ".txt";
        try {
            File nameFile = new File("data/accounts/" + filePath);
            FileReader nameReader = new FileReader(nameFile);
            BufferedReader reader = new BufferedReader(nameReader);

            String line = null;
            while ((line = reader.readLine()) != null) {
                workingAccount.add(line);
            }
            reader.close();
        } catch (Exception ex) {
        }

    }





    public void logActivity(ArrayList<String> workingAccount, String log) {

        if (workingAccount.get(0) != null && workingAccount.get(1) != null && workingAccount.get(2) != null && workingAccount.get(3) != null && workingAccount.get(4) != null && workingAccount.get(5) != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            if (workingAccount.size() > 15) {
                workingAccount.remove(6);
                workingAccount.add(log + " on " + dateFormat.format(new Date()));
            } else {
                workingAccount.add(log + " on " + dateFormat.format(new Date()));
            }

            new Account().storeAccount(workingAccount);
        }

    }



}
