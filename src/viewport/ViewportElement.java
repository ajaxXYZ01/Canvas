package viewport;
import java.awt.Graphics2D;

import math.AABB;
import ui.Inspector;

public abstract class ViewportElement {

    protected AABB aabb;

    public ViewportElement() {
        
    }

    private boolean selected;

    public abstract void render(Graphics2D gfx2d, Viewport2D viewport2d);
    public abstract void select(int x ,int y, Viewport2D viewport2d);
    public abstract void offset(int dx, int dy, Viewport2D viewport2d);
    public abstract void inspector(Inspector inspector);

    public abstract boolean isSelected();

    public AABB getAABB() { return aabb; }

}