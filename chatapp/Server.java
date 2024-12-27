package chatapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

import chatapp.database.DatabaseHelper;
import chatapp.handlers.ChatServerHandler;
import chatapp.models.Message;

public class Server {
    public static Vector<ChatServerHandler> cshs = new Vector<>();
    public static void sendAll(Message m){
        for (ChatServerHandler chatServerHandler : cshs) {
            m.delivered=false;
            chatServerHandler.send(new Message(m));
        }
    }
    private static Vector<Message> messages = null;
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        int listenPort = 55000;
        String host = args[0], uname=args[1], password=args[2];
        if(args.length>3)
            listenPort = Integer.parseInt(args[3]);
        ServerSocket ss = new ServerSocket(listenPort);
        DatabaseHelper dbh = new DatabaseHelper(host, uname, password);
        messages = dbh.getMessages();
        while(true){
            Socket endpoint = ss.accept();
            messages = dbh.getMessages();
            ChatServerHandler chs = new ChatServerHandler(endpoint){
                public void onReceive(Message m){
                    System.out.println("Message at Server: \n   "+ m);
                    try{
                        dbh.addMessageIntoDatabase(m);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    sendAll(m);
                }
            };
            for(Message msg: messages){
                chs.send(msg);
            }
            cshs.add(chs);
            (new Thread(chs)).start();
        }
    }
}
