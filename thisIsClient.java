import java.io.*;
import java.net.*;

public class thisIsClient 
{
    public static void main(String args[]) throws IOException 
    {
        if( args.length == 0 )
        {
            System.out.println( "Please add a port number to connect to server." );
            System.exit( 0 );
        }

        int port = Integer.parseInt(args[0]);
      
        try
        {            
            Socket socket1 = new Socket("localhost",port);

            InputStream socket1In = socket1.getInputStream();
            DataInputStream inputStream = new DataInputStream(socket1In);

            String st = new String (inputStream.readUTF());
            System.out.println("Server " +st);
    
            OutputStream socket1out = socket1.getOutputStream();
            DataOutputStream outputStream = new DataOutputStream(socket1out);        
        
            outputStream.writeUTF(stringReversal());
    
            inputStream = new DataInputStream(socket1In);
            st = new String (inputStream.readUTF());
            System.out.println("Reversed string received from server is " +st);

            System.out.println("closing client now!");
            inputStream.close();
            socket1In.close();
            socket1.close();
        } catch (SocketException e) {
            System.out.println("Server port not found, exception thrown : " +e);
        }
    }       

    public static String stringReversal()
    {
        Console console = System.console();
        String send = console.readLine("Enter a string to reverse : ");
        return send;
    }
}