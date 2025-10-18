package main;

import gui.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Simple version - look and feel hata diya
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}