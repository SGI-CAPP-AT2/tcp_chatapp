package chatapp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import chatapp.handlers.ChatUser;
import chatapp.gui.ClientGUI;
import chatapp.models.Message;

public class Client {
    public static class Helper{
        ClientGUI gui ;
        public void setGUI(ClientGUI gui){
            this.gui=gui;
        }
        public ClientGUI getGUI(){
            return this.gui;
        }
    } 
    public static void main(String[] args) throws Exception {
        int port = 55000;
        String ip = "localhost";
        if(args.length>0)
            port = Integer.parseInt(args[0]);
        if(args.length>1)
            ip = args[1];
        String uname = JOptionPane.showInputDialog("Enter your username: ");
        Helper hlp = new Helper();
        if(uname.length()>0){
            ChatUser chu = new ChatUser(ip, uname, port) {
                public void onMessageArrive(Message m){
                    hlp.getGUI().addMessage(m.toString());
                }
            };
            ClientGUI gui = new ClientGUI(uname){
                public void onSend(String msg){
                    try {
                        chu.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            hlp.setGUI(gui);
            gui.setup();
            gui.showUp();
            gui.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    chu.closeConnection();
                }
            });
        }
    }
}
