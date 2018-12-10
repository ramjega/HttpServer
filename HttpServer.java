import java.io.*;
import java.net.*;
import java.util.*;

public class HttpServer implements Runnable {
    static final File ROOT = new File(".");
    static final String defalutFile = "index.html";
    static final int PORT = 8080;
    static final boolean connection = true;

    private Socket connect;

    public HttpServer(Socket s) {
        connect = s;
    }

    public static void main(String[] args) {
        new InitialCheck().check();
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server is started. \nListening on " + PORT + "..\n");

            while (true) {
                HttpServer myServer = new HttpServer(serverConnect.accept());


                if (connection) {
                    System.out.println("Connection opened.");
                }


                Thread thread = new Thread(myServer);
                thread.start();

            }
        } catch (IOException ex) {
            System.err.println("Server connection error " + ex.getMessage());
        }
    }

    public void run() {
        ArrayList<String> responseData = new ArrayList<String>();
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        String fileRequested = null;
        StringBuilder payload = new StringBuilder();

        try {
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            out = new PrintWriter(connect.getOutputStream());
            dataOut = new BufferedOutputStream(connect.getOutputStream());
            String input = in.readLine();


            StringTokenizer parse = new StringTokenizer(input);
            String method = parse.nextToken();
            fileRequested = parse.nextToken();


            if (method.equals("POST")) {
                //read the post headers
                String headerLine = null;
                while ((headerLine = in.readLine()).length() != 0) {
                }

             //code to read the post payload data
                while (in.ready()) {
                    payload.append((char) in.read());
                }

                System.out.println("payload is " + payload);
                //Starting command exucter
                new CommandExecutor().start(responseData,fileRequested, payload.toString());
                //send response back to client
                String result = responseData.get(0);
                int resultLength = (int) result.length();
                byte[] resultData = result.getBytes();

                out.println("HTTP/1.1 200 OK");
                out.println("Date: " + new Date());
                out.println("Content-type: " + "application/json");
                out.println("Content-length: " + resultLength);
                out.println();
                out.flush();

                dataOut.write(resultData, 0, resultLength);
                dataOut.flush();
                if (connection) {
                    System.out.println("File " + result + " of type " + "\"application/json\"" + " returned");
                }


            }


            if (fileRequested.endsWith("/") )  {
                fileRequested += defalutFile;
            }



            File file = new File(ROOT, fileRequested);
            int fileLength = (int) file.length();
            String content = getContentType(fileRequested);
            byte[] fileData = null;

            if (method.equals("GET")) {

                if(fileRequested.equals("/create")){
                    System.out.println("requsting for result");
                }
                fileData = readFileData(file, fileLength);

                out.println("HTTP/1.1 200 OK");
                out.println("Date: " + new Date());
                out.println("Content-type: " + content);
                out.println("Content-length: " + fileLength);
                out.println();
                out.flush();

                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();
            }


            if (connection) {
                System.out.println("File " + fileRequested + " of type " + content + " returned");
            }

        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());


        } catch (IOException io) {
            System.err.println(io.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                dataOut.close();
                connect.close();
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }


            if (connection) {
                System.out.println("Connection closed.\n");
            }

        }

    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html"))
            return "text/html";

        else if (fileRequested.endsWith(".js"))
            return "application/javascript";
        else
            return "text/plain";
    }




}
