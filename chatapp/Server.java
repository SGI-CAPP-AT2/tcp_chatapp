package chatapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

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
    public static void main(String[] args) throws IOException {
        int port = 55000;
        if(args.length>0)
            port = Integer.parseInt(args[0]);
        ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
        while(true){
            Socket endpoint = ss.accept();
            ChatServerHandler chs = new ChatServerHandler(endpoint){
                public void onReceive(Message m){
                    System.out.println("Message at Server: \n   "+ m);
                    sendAll(m);
                }
            };
            cshs.add(chs);
            (new Thread(chs)).start();
        }
    }
}
