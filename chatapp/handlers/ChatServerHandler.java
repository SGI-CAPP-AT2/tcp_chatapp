package chatapp.handlers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

import chatapp.models.Message;

public abstract class ChatServerHandler implements Runnable {
    Socket endpoint;
    Vector<Message> mVector = new Vector<>();
    ObjectOutputStream out;
    ObjectInputStream in;

    public ChatServerHandler(Socket s) throws IOException {
        endpoint = s;
        out = new ObjectOutputStream(endpoint.getOutputStream());
        in = new ObjectInputStream(endpoint.getInputStream());
    }

    public void run() {
        try {
            Thread receivingThread = new Thread(() -> {
                boolean errorInConnection = false;
                while (!errorInConnection) {
                    Message m;
                    try {
                        if ((m = (Message) in.readObject()) != null)
                            this.onReceive(m);
                        continue;
                    } catch (Exception e) {
                        System.out.println("Exception while Reading! Stopping the thread");
                        errorInConnection = true;
                    }
                }
            });
            receivingThread.start();
            Vector<Message> v;
            while (true) {
                v = new Vector<>(mVector);
                for (Message msg : v) {
                    if (!msg.delivered) {
                        out.writeObject(msg);
                        msg.delivered = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception at thread \n");
            e.printStackTrace();
        }
    }

    public abstract void onReceive(Message m) throws SQLException;

    public void send(Message m) {
        mVector.add(m);
    }
}
