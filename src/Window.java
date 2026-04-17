import java.awt.BorderLayout;
import java.time.LocalDateTime;

import javax.swing.JFrame;

import managers.Viewport2DElementManager;
import ui.Inspector;
import utils.Icons;
import viewport.Viewport2D;

class Window extends JFrame {

    private Inspector inspector;
    private Viewport2D viewport;
    private Viewport2DElementManager scene;

    Window(int width, int height) {

        this.setTitle("Canvas: " + LocalDateTime.now());
        this.setIconImage(Icons.PEN);
        this.setLayout(new BorderLayout());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inspector = new Inspector();
        scene     = new Viewport2DElementManager(inspector);
        viewport  = new Viewport2D(width - inspector.getWidth(), height);
        viewport.setCurrentScene(scene);

        this.add(inspector, BorderLayout.WEST);
        this.add(viewport, BorderLayout.CENTER);
        
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    Inspector getInspector()            { return inspector; }
    Viewport2DElementManager getScene() { return scene;     }
    Viewport2D getViewport()            { return viewport;  }

}