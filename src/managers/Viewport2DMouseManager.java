package managers;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.event.MouseInputListener;

import viewport.Viewport2D;

public class Viewport2DMouseManager implements MouseInputListener, MouseWheelListener {

    Viewport2D viewport;

    boolean mouseLeftDown;
    private static final float UP_FAC   = 1.1f;
    private static final float DOWN_FAC = 1 / UP_FAC;
    Point lastMousePressed;

    public Viewport2DMouseManager(Viewport2D viewport) {
        this.viewport    = viewport;
        lastMousePressed = new Point();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseLeftDown    = true;
        lastMousePressed = e.getPoint();
        Viewport2DElementManager.selectElements(e, viewport);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseLeftDown = false;

        // manually assign 0 to optimize: large object creation
        lastMousePressed.x = 0;
        lastMousePressed.y = 0;

        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseLeftDown) {
            
            int dx = e.getX() - lastMousePressed.x;
            int dy = e.getY() - lastMousePressed.y;

            lastMousePressed.x = e.getX();
            lastMousePressed.y = e.getY();

            if (Viewport2DElementManager.hasSelectedElements()) {
                Viewport2DElementManager.moveSelectedElements(dx, dy, viewport);
                viewport.UpdateWorldBounds();
                viewport.repaint();
                return;
            }

            Point vOrigin = viewport.getOrigin();
            viewport.setOrigin(vOrigin.x + dx, vOrigin.y + dy);

            viewport.UpdateWorldBounds();
            viewport.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        viewport.setMouse(e.getPoint());
        viewport.repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        
        float FACTOR = 1; // = 1 in case e.getWheelRotation() != -1 or 1, so no effect on viewport
        switch(e.getWheelRotation()) {

            case -1: // Wheel up
                FACTOR = UP_FAC;
                break;
            case 1: // Wheel down
                FACTOR = DOWN_FAC;
                break;
        }

        viewport.setPPU(viewport.getPPU() * FACTOR);

        viewport.UpdateStep();
        viewport.UpdateCenterOnScroll(e.getPoint(), FACTOR);
        viewport.UpdateWorldBounds();
        viewport.repaint();
    }
}