package math;

import java.awt.Color;
import java.awt.Graphics2D;

import viewport.Viewport2D;
import viewport.ViewportElement;

public class Point2D extends ViewportElement {

    private float x, y;
    private Color color;
    private int size; // in pixels

    public Point2D(float x, float y) {
        this.x = x;
        this.y = y;
        size   = 10;
        color = new Color(0, 128, 255);
    }

    @Override
    public void render(Graphics2D gfx2d, Viewport2D viewport2d) {
        size *= 2;
        gfx2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 64));
        gfx2d.fillOval(viewport2d.screenX(x) - size / 2, viewport2d.screenY(y) - size / 2, size, size);
        
        size /= 2;
        gfx2d.setColor(color);
        gfx2d.fillOval(viewport2d.screenX(x) - size / 2, viewport2d.screenY(y) - size / 2, size, size);
    }

}