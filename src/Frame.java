import java.awt.BorderLayout;
import java.time.LocalDateTime;

import javax.swing.JFrame;

import ui.Inspector;
import utils.Icons;
import viewport.Viewport2D;

class Frame extends JFrame {

    Frame() {

        this.setTitle("Canvas: " + LocalDateTime.now());
        this.setIconImage(Icons.PEN);
        this.setLayout(new BorderLayout());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(new Inspector(), BorderLayout.WEST);

        Viewport2D viewport = new Viewport2D(600, 600);
        this.add(viewport, BorderLayout.CENTER);
        
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String args[]) {

        

        new Frame();
    }

}