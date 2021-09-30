package MultiThread;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class multiClientHandler implements Runnable
{
    public static ArrayList<multiClientHandler> clientHandlers = new ArrayList<>();
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    String clientUserID;
    boolean running;

    public multiClientHandler(Socket socket)
    {
        System.out.println("multi clien const ");

        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientUserID = reader.readLine();
            clientHandlers.add(this);
            this.running = true;
            System.out.println("multiclient init "+clientUserID);
            broadcastMsg("new client to server : " + clientUserID, false);
        } catch (Exception e) {
            System.out.println("broke multi "+clientUserID);
            e.printStackTrace();
        }        
    }      

    public static String stringReversal()
    {
        Console console = System.console();
        String send = console.readLine("Enter a string to reverse : ");
        return send;
    }

    @Override
    public void run() {
        String receiveString;
        System.out.println("rbefore runnig "+clientUserID);

        while(running)
        {
            try {
                System.out.println("running client handler for "+clientUserID);
                receiveString = reader.readLine();
                broadcastMsg(receiveString, true);
            } catch (Exception e) {
                closeHandler(socket, reader, writer);
                break;
            }
        }      
    }

    public void broadcastMsg(String s, boolean reversal)
    {
        for(multiClientHandler clientHandler :clientHandlers)
        {
            String input = s;
            if(reversal)
            {    input = reverseString(clientHandler.clientUserID, s);
            try {
                if(clientHandler.clientUserID.equals(this.clientUserID))
                {
                    clientHandler.writer.write(input);
                    clientHandler.writer.newLine();
                    clientHandler.writer.flush();
                    System.out.print("Sending reversed string to user : " +clientUserID);
                }
            } catch (Exception e) {
                closeHandler(socket, reader, writer);
            }
            }
        }
    }

    public void closeHandler(Socket s, BufferedReader r, BufferedWriter w)
    {
        clientHandlers.remove(this);
        broadcastMsg("client left server :" + clientUserID, false);
        running = false;
        try {
            if(r!=null)
                r.close();
            if(w!=null)
                w.close();
            if(s!=null)
                s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String reverseString(String ID, String s)
    {
        System.out.println("string received from client "+ID + " is " +s);
        char[] c = s.toCharArray();
            String outputString = "";
            if(c.length <= 80)
            {
                StringBuilder outputBuild = new StringBuilder();
                for(int i = c.length - 1; i >=0 ; i--)
                {
                    if(Character.isUpperCase(c[i]))
                        outputBuild.append(Character.toLowerCase(c[i]));
                    else
                        outputBuild.append(Character.toUpperCase(c[i]));
                }
                outputString = outputBuild.toString();
                System.out.println("string reversed to " +outputString);
            }
            else
                outputString = "Entered string is too long!!";

        return outputString;
    }
}