package math;

import java.awt.Color;
import java.awt.Graphics2D;

import viewport.Viewport2D;
import viewport.ViewportElement;

public class Point2D extends ViewportElement {

    public float x, y; // in world coordinate
    private int size; // in pixels
    private Color color;

    // flags
    private boolean selected;
    private boolean selectable;

    public Point2D(float x, float y) {

        this.x = x;
        this.y = y;

        size  = 8;
        color = new Color(0, 128, 255);

        setSelectable(true);
    }

    // --- Setters ---
    public void setSelectable(boolean flag) { selectable = flag; }
    public void select()   { selected = true; }
    public void unselect() { selected = false; }
    public void setSize(int size) { this.size = size; }
    public void setColor(Color color) { this.color = color; }

    // --- Getters ---

    // --- Utils ---
    public boolean isSelected() { return selected; }

    @Override
    public void render(Graphics2D gfx2d, Viewport2D viewport2d) {

        if (selected) {
            size *= 2;
            gfx2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 64));
            gfx2d.fillOval(viewport2d.screenX(x) - size / 2, viewport2d.screenY(y) - size / 2, size, size);
            
            size /= 2;
            gfx2d.setColor(color);
            gfx2d.fillOval(viewport2d.screenX(x) - size / 2, viewport2d.screenY(y) - size / 2, size, size);
        } else {
            gfx2d.setColor(color);
            gfx2d.fillOval(viewport2d.screenX(x) - size / 2, viewport2d.screenY(y) - size / 2, size, size);
        }
    }

    @Override
    public void select(int x, int y, Viewport2D viewport2d) {
        float dx = x - viewport2d.screenX(this.x);
        float dy = y - viewport2d.screenY(this.y);
        float r  = size * 0.5f * 1.5f;

        if (dx*dx + dy*dy <= r*r) {
            select();
        } else {
            unselect();
        }
    }

    @Override
    public void offset(int dx, int dy, Viewport2D viewport2d) {
        x += dx / viewport2d.getPPU();
        y -= dy / viewport2d.getPPU();
    }

}