package robotgame.io;

import javax.swing.JOptionPane;

public class PopUp {

    public void show(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

}
