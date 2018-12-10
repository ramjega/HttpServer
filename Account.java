import java.io.*;
import java.util.*;
import java.net.*;
import java.nio.*;
public class Account {


    public void create(ArrayList<String> details, ArrayList<String> responseData, String payload) {


        getPromptDetails(details, payload);

        if (!(details.get(0).equals("")) && !(details.get(1).equals("")) && !(details.get(2).equals(""))) {
            generateAccount(details);
            storeAccount(details);
            //sending response
            String response = "{\"name\":\""+details.get(0) +"\",\"nicNumber\":\""+details.get(1) +"\",\"mobileNumber\":\""+details.get(2) +"\",\"accountNumber\":\""+details.get(3) +"\",\"pinNumber\":\""+details.get(4) +"\",\"accountBalance\":\""+details.get(5) +"\"}";
            responseData.add(response);
            new Transaction().logActivity(details, "Account created");
        } else {
            String err = "{\"error\": \"One or more field is empty on invalid\"}";
            responseData.add(err);
        }

    }


    public void getPromptDetails(ArrayList<String> details, String payload) {

        String[] parts = payload.split(",");
        String name = parts[0].substring(8).replace("\"", "");
        String nic = parts[1].substring(12).replace("\"", "");
        String mobile = parts[2].substring(15).replace("\"", "").replace("}", "");

        System.out.println("name is "+name);
        System.out.println("nic is "+ nic);
        System.out.println("mobile is "+ mobile);

        details.add(name);
        details.add(nic);
        details.add(mobile);


    }

    public void generateAccount(ArrayList<String> details) {
        generateId(details);
        generatePin(details);
        details.add("0");


    }

    void generateId(ArrayList<String> details) {
        ArrayList<String> currentAccountNumStr = new ArrayList<String>();

//to read the last acc no from inital data
        try {
            File nameFile = new File("data/initalData.txt");
            FileReader nameReader = new FileReader(nameFile);
            BufferedReader reader = new BufferedReader(nameReader);

            String line = null;
            while ((line = reader.readLine()) != null) {
                currentAccountNumStr.add(line);
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int currentAccountIdInt = Integer.parseInt(currentAccountNumStr.get(0));
        int newAccountIdInt = currentAccountIdInt + 1;
        String newAccountId = Integer.toString(newAccountIdInt);
        details.add(newAccountId);
        currentAccountNumStr.remove(0);
        currentAccountNumStr.add(0, newAccountId);

// to write the new acc no in inital data
        fileWriter(currentAccountNumStr, "data/initalData.txt");

    }

    void generatePin(ArrayList<String> details) {

        Random pin = new Random();
        int pinNumberInt = pin.nextInt(1000) + 1000;
        String pinNumber = Integer.toString(pinNumberInt);
        details.add(pinNumber);

    }

    void storeAccount(ArrayList<String> arrayList) {
        int x = 0;
        String fileName = arrayList.get(3);
        if (arrayList.get(0) != null && arrayList.get(1) != null && arrayList.get(2) != null && arrayList.get(3) != null && arrayList.get(4) != null && arrayList.get(5) != null) {
            try {
                FileWriter writer = new FileWriter("data/accounts/" + fileName + ".txt");
                for (String item : arrayList) {

                    String printLine = arrayList.get(x);
                    writer.write(printLine + "\n");
                    x++;
                }

                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    void fileWriter(ArrayList<String> arrayList, String fileName) {
        int x = 0;
        try {
            FileWriter writer = new FileWriter(fileName);
            for (String item : arrayList) {

                String printLine = arrayList.get(x);
                writer.write(printLine + "\n");
                x++;
            }

            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    void fileReader(ArrayList<String> arrayList, String fileName, String errMsg, Socket sock) {
        try {
            File nameFile = new File(fileName);
            FileReader nameReader = new FileReader(nameFile);
            BufferedReader fileReader = new BufferedReader(nameReader);

            String line = null;
            while ((line = fileReader.readLine()) != null) {
                arrayList.add(line);
            }
            fileReader.close();

        } catch (Exception ex) {
        }


    }


}
