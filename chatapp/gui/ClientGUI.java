package chatapp.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

public abstract class ClientGUI extends JFrame {
    private JTextArea chatArea;
    private JScrollPane scpane;
    private JTextField msgField;
    private JButton msgButton;
    private JPanel msgSection;
    private String uname;

    public ClientGUI(String username) {
        uname=username;
        this.setLayout(new BorderLayout());
        this.setSize(400, 700);
        chatArea = new JTextArea();
        scpane = new JScrollPane(chatArea);
        msgSection = new JPanel(new GridBagLayout());
        msgSection.setSize(400, 50);
        msgField = new JTextField();
        msgButton = new JButton("Send");
        msgButton.addActionListener((ActionEvent ae) -> {
            onSend(msgField.getText());
            msgField.setText("");
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        msgSection.add(msgField, gbc);
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        msgSection.add(msgButton, gbc);
    }

    public void setup() {
        this.add(scpane, BorderLayout.CENTER);
        this.add(msgSection, BorderLayout.PAGE_END);
        this.add(new JLabel("Chat App Microproject "), BorderLayout.PAGE_START);
        chatArea.setEditable(false);
    }

    public void showUp() {
        this.setTitle("Messages for "+uname);
        this.setVisible(true);
    }

    public void addMessage(String message) {
        String newString = chatArea.getText();
        newString += message+"\n";
        chatArea.setText(newString);
        
    }

    public abstract void onSend(String message);
}
