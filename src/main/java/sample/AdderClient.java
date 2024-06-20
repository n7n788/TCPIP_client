package sample;

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class AdderClient {

  public static void main(String[] args) throws Exception {
    int client_count = 0;
    JFrame frame = new JFrame("Swing");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.setSize(400, 200);
    frame.setLocation(100, 100);
    frame.setLocationRelativeTo(null);

    try (Socket socket = new Socket("localhost", 10000);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      ) {
      
        JLabel label = new JLabel("count: -");
        JButton button = new JButton("Add");
        button.addActionListener((ActionEvent e) -> {
            try {
                writer.println("1");
            } catch (Exception ex) {
                System.exit(1);
            }
        });
        frame.add(label, BorderLayout.NORTH);
        frame.add(button, BorderLayout.CENTER);

        writer.println("0");
        label.setText("count: " + reader.readLine());
    
        frame.setVisible(true);

      while (true) {
        if (client_count >= 5) {
          System.exit(0);
        }
        client_count++;
        label.setText("count: " + reader.readLine());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
