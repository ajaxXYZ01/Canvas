package app;

import java.awt.BorderLayout;
import java.time.LocalDateTime;

import javax.swing.JFrame;

import managers.Viewport2DElementManager;
import ui.CanvasMenuBar;
import ui.Inspector;
import utils.Icons;
import viewport.Viewport2D;

public class Window extends JFrame {

    private Inspector inspector;
    private Viewport2D viewport;
    private Viewport2DElementManager scene;
    private CanvasMenuBar menuBar;

    Window(int width, int height) {

        this.setTitle("Canvas: " + LocalDateTime.now());
        this.setIconImage(Icons.PEN);
        this.setLayout(new BorderLayout());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inspector = new Inspector();
        scene     = new Viewport2DElementManager(inspector);
        viewport  = new Viewport2D(width - inspector.getWidth(), height);
        menuBar   = new CanvasMenuBar(this);

        viewport.setCurrentScene(scene);

        this.add(inspector, BorderLayout.WEST);
        this.add(viewport, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public Inspector getInspector()            { return inspector; }
    public Viewport2DElementManager getScene() { return scene;     }
    public Viewport2D getViewport()            { return viewport;  }

}