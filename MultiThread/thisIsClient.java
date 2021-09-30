package MultiThread;
import java.io.*;
import java.net.*;


public class thisIsClient 
{
    BufferedReader reader;
    BufferedWriter writer;
    String userID;
    Socket socket;
    static thisIsClient client;
    boolean running;

    public thisIsClient(Socket s, String userID)
    {
        try {
            running = true;
            socket = s;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userID = userID;
            sendReceiveRequests();
        } catch (Exception e) {
            closeClient(socket, reader, writer);
        }
    }

    public static void main(String args[]) throws IOException 
    {
        if( args.length == 0 )
        {
            System.out.println( "Please add a port number && hostname/ipaddress to connect to server." );
            System.exit( 0 );
        }

        int port = Integer.parseInt(args[0]);
        InetAddress address = InetAddress.getByName(args[1]);
        
        String info = userInput("What is your username : ");
        client = new thisIsClient(new Socket(address,port), info);
    }
    
    String receive;
    public void sendReceiveRequests()
    {
        try
        {         
            System.out.println("sending "+userID);  
            writer.write(userID);
            writer.newLine();
            writer.flush();
            
            while(running)
            {
                String reversal  = userInput("Enter a string to reverse : ");
                writer.write(reversal);
                writer.newLine();
                writer.flush();
                
                receive = reader.readLine();
                System.out.println("servers reply " + receive);
            }
        } catch (IOException e) {
            System.out.println("Server port not found, exception thrown : " +e);
            closeClient(socket,reader,writer);
        }
    }

    public static String userInput(String Query)
    {
        Console console = System.console();
        String send = console.readLine(Query);
        return send;
    }

    public void closeClient(Socket s, BufferedReader r, BufferedWriter w)
    {
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
}