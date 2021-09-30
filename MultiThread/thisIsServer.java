package MultiThread;

import java.net.*;
import java.io.*;

public class thisIsServer 
{
    static thisIsServer thisServer;
    ServerSocket serverSocket;
    boolean running;

    public thisIsServer(ServerSocket server)
    {
        this.serverSocket = server;
        this.running = true;
    }
    public static void main(String[] args) throws IOException
    {
        if( args.length == 0 )
        {
            System.out.println( "Please mention a port number for the server to run on." );
            System.exit( 0 );
        }

        int port = Integer.parseInt(args[0]);
        System.out.println("starting port on : " + port);

        thisServer = new thisIsServer(new ServerSocket(port));
        thisServer.runServer();
    }

    public void runServer()
    {
        try
        {   System.out.println("Listening for the client!");
            while(thisServer.running)
            {
                Socket socket = thisServer.serverSocket.accept();
                System.out.println("New client has entered!");

                //OutputStream socket1out = socket.getOutputStream();
                //DataOutputStream outputStream = new DataOutputStream(socket1out);
                //outputStream.writeUTF("connection acquired");

                multiClientHandler clientHandler = new multiClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println("exception thrown : " +e);
            closeServer();
        }
    }

    public void closeServer()
    {
        try {
            if(serverSocket != null)
            {
                serverSocket.close();
                running = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}