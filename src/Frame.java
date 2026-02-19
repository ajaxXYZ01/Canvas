import java.awt.BorderLayout;
import java.time.LocalDateTime;

import javax.swing.JFrame;

import managers.Viewport2DElementManager;
import math.BazierCurve2D;
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

        BazierCurve2D curve = new BazierCurve2D();

        curve.addPoint(0, 0);
        curve.addPoint(1, 0);
        curve.addPoint(2, 0);
        curve.addPoint(3, 0);
        curve.addPoint(4, 0);

        Viewport2DElementManager.addElement(curve);

        new Frame();
    }

}