package managers;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import viewport.Viewport2D;
import viewport.ViewportElement;

public class Viewport2DElementManager {

    private static ArrayList <ViewportElement> elementList = new ArrayList <> ();

    public static void addElement(ViewportElement element) {
        elementList.add(element);
    }
    
    public static void removeElement(int index) {
        elementList.remove(index);
    }

    public static ArrayList <ViewportElement> getList() { return elementList; }

    public static void selectElements(MouseEvent event, Viewport2D viewport) {
        for (ViewportElement element : elementList) {
            element.select(event.getX(), event.getY(), viewport);
        }
    }

    public static void moveSelectedElements(int dx, int dy, Viewport2D viewport) {
        for (ViewportElement element : elementList) {
            element.offset(dx, dy, viewport);
        }
    }

    public static void renderElements(Graphics2D gfx2d, Viewport2D viewport) {
        for (ViewportElement element : elementList) {
            element.render(gfx2d, viewport);
        }
    }

    public static boolean hasSelectedElements() {
        boolean bool = false;
        for (ViewportElement element : elementList) {
            bool |= element.isSelected();
        }

        return bool;
    }
}