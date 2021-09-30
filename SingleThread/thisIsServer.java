package SingleThread;

import java.net.*;
import java.io.*;

public class thisIsServer 
{
    public static void main(String[] args) throws IOException
    {
        if( args.length == 0 )
        {
            System.out.println( "Please mention a port number for the server to run on." );
            System.exit( 0 );
        }

        int port = Integer.parseInt(args[0]);
        System.out.println("starting port on : " + port);

        System.out.println("Listening for the client!");
        
        try
        {
            ServerSocket thisServer = new ServerSocket(port);
            Socket socket1 = thisServer.accept();
    
            OutputStream socket1out = socket1.getOutputStream();
            DataOutputStream outputStream = new DataOutputStream(socket1out);
            outputStream.writeUTF("connection acquired");
            InputStream input = socket1.getInputStream();
            DataInputStream dataReceived = new DataInputStream(input);
            String s = new String (dataReceived.readUTF());
            System.out.println("string received from client : " + s);               
            String op = reverseString(s);
            System.out.println("sending reverse string back to client!");
            outputStream.writeUTF(op);
            System.out.println("closing server");
            outputStream.close();
            socket1.close();
            thisServer.close();
        } catch (Exception e) {
            System.out.println("exception thrown : " +e);
            System.exit(0);
        }
    }

    public static String reverseString(String s)
    {
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