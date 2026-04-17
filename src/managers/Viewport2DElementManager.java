package managers;

import java.util.ArrayList;

import ui.Inspector;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import viewport.Viewport2D;
import viewport.ViewportElement;

public class Viewport2DElementManager {

    Inspector inspector;
    private ArrayList <ViewportElement> elementList = new ArrayList <> ();

    public Viewport2DElementManager(Inspector inspector) {
        this.inspector = inspector;
    }

    public void addElement(ViewportElement element) {
        elementList.add(element);
        element.inspector(inspector);
        inspector.revalidate();
        inspector.repaint();
    }
    
    public void removeElement(int index) {
        elementList.remove(index);
    }

    public ArrayList <ViewportElement> getList() { return elementList; }

    public void selectElements(MouseEvent event, Viewport2D viewport) {
        for (ViewportElement element : elementList) {
            element.select(event.getX(), event.getY(), viewport);
        }
    }

    public void moveSelectedElements(int dx, int dy, Viewport2D viewport) {
        for (ViewportElement element : elementList) {
            element.offset(dx, dy, viewport);
        }
    }

    public void renderElements(Graphics2D gfx2d, Viewport2D viewport) {
        for (ViewportElement element : elementList) {
            element.render(gfx2d, viewport);
        }
    }

    public boolean hasSelectedElements() {
        boolean bool = false;
        for (ViewportElement element : elementList) {
            bool |= element.isSelected();
        }

        return bool;
    }
}