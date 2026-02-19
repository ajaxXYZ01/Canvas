import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import managers.Viewport2DElementManager;
import math.BazierCurve2D;
import viewport.Viewport2D;

class Frame extends JFrame {

    Frame() {

        this.setTitle("Canvas: " + LocalDateTime.now());

        try {
            this.setIconImage(ImageIO.read(new File("E:\\Java projects\\Canvas\\res\\icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(new Viewport2D(1024, 512));
        
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        new Frame();
    }

}