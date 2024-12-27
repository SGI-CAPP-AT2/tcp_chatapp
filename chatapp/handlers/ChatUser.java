package chatapp.handlers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import chatapp.models.Message;

public abstract class ChatUser {
    String username;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    public ChatUser(String username, int port) throws UnknownHostException, IOException {
        this.socket = new Socket("localhost", port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        Thread recieveThread = new Thread(() -> {
            try {
                Message message;
                while ((message = (Message) in.readObject()) != null) {
                    onMessageArrive(message);
                }
                in.close();
            } catch (Exception e) {}
        }, username + ":send");
        this.username = username;
        recieveThread.start();
    }

    public void sendMessage(String message) throws Exception {
        System.out.println("Sending ... "+ message);
        out.writeObject(new Message(username, message, new Date()));
    }

    public void closeConnection(){
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void onMessageArrive(Message message);
}
