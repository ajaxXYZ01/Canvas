package managers;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.event.MouseInputListener;

import viewport.Viewport2D;

public class Viewport2DMouseManager implements MouseInputListener, MouseWheelListener {

    Viewport2D viewport;

    boolean mouseDown;
    private static final float UP_FAC = 1.1f;
    private static final float DOWN_FAC = 1 / UP_FAC;
    Point lastMouse;

    public Viewport2DMouseManager(Viewport2D viewport) {
        this.viewport = viewport;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        lastMouse = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;

        // manually assign 0 to optimize: large object creation
        lastMouse.x = 0;
        lastMouse.y = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseDown) {
            
            int dx = e.getX() - lastMouse.x;
            int dy = e.getY() - lastMouse.y;

            lastMouse.x = e.getX();
            lastMouse.y = e.getY();

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

        viewport.UpdateCenterOnScroll(e.getPoint(), FACTOR);
        viewport.UpdateWorldBounds();
        viewport.repaint();
    }
    
}