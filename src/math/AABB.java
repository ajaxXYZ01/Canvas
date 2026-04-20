package math;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

import ui.Inspector;
import utils.rand;
import viewport.Viewport2D;

public class AABB {

    public vec2 min;
    public vec2 max;

    private boolean showBounds;

    private Color DEBUG_OUTLINE_COLOR;
    private Color DEBUG_FILL_COLOR;
    { setBLUE(); }

    private static Stroke OUTLINE_STROKE = new BasicStroke(1);

    public AABB() {
        min = new vec2(-0.5f, -0.5f);
        max = new vec2( 0.5f,  0.5f);
    }

    public AABB(float min_x, float min_y, float max_x, float max_y) {
        min = new vec2(min_x, min_y);
        max = new vec2(max_x, max_y);
    }

    public AABB(vec2 min, vec2 max) {
        this.min = new vec2(min.x, min.y);
        this.max = new vec2(max.x, max.y);
    }

    public boolean overlapX(AABB b) {
        return (this.min.x <= b.max.x) && (this.max.x >= b.min.x);
    }

    public boolean overlapY(AABB b) {
        return (this.min.y <= b.max.y) && (this.max.y >= b.min.y);
    }

    public boolean intersects(AABB b) {
        return overlapX(b) && overlapY(b);
    }

    public boolean contains(vec2 point) {
        return this.min.x <= point.x
            && point.x <= this.max.x
            && this.min.y <= point.y
            && point.y <= this.max.y;
    }

    // --------------------------------
    // Utilities
    // --------------------------------

    public void setRED() {
        DEBUG_OUTLINE_COLOR = Color.RED;
        DEBUG_FILL_COLOR = compute_fill_color(DEBUG_OUTLINE_COLOR, 0.25f);
    }

    public void setGREEN() {
        DEBUG_OUTLINE_COLOR = new Color(0, 255, 128);
        DEBUG_FILL_COLOR = compute_fill_color(DEBUG_OUTLINE_COLOR, 0.25f);
    }

    public void setBLUE() {
        DEBUG_OUTLINE_COLOR = new Color(0, 128, 255);
        DEBUG_FILL_COLOR = compute_fill_color(DEBUG_OUTLINE_COLOR, 0.25f);
    }

    public void setWHITE() {
        DEBUG_OUTLINE_COLOR = Color.WHITE;
        DEBUG_FILL_COLOR = compute_fill_color(DEBUG_OUTLINE_COLOR, 0.1f);
    }

    public void RandomizeDebugColor() {
        DEBUG_OUTLINE_COLOR = new Color(rand.num(256), rand.num(256), rand.num(256));
        DEBUG_FILL_COLOR = compute_fill_color(DEBUG_OUTLINE_COLOR, 0.25f);
    }

    public Color compute_fill_color(Color color, float alpha) {

        if (alpha < 0 || alpha > 1)
            throw new RuntimeException("error: compute_fill_color: invalid alpha, " + alpha);
        
        float components[] = color.getRGBComponents(null);

        float r = components[0];
        float g = components[1];
        float b = components[2];

        return new Color(r, g, b, alpha);
    }

    public void showBounds() { showBounds = true; }
    public void hideBounds() { showBounds = false; }

    // --------------------------------
    // 
    // --------------------------------

    public void render(Graphics2D gfx2d, Viewport2D viewport2d) {
        
        if (!showBounds)
            return;

        int x = viewport2d.screenX(min.x);
        int y = viewport2d.screenY(max.y);

        int width  = (int) ((max.x - min.x) * viewport2d.getPPU());
        int height = (int) ((max.y - min.y) * viewport2d.getPPU());

        gfx2d.setStroke(OUTLINE_STROKE);

        gfx2d.setColor(DEBUG_FILL_COLOR);
        gfx2d.fillRect(x, y, width, height);

        gfx2d.setColor(DEBUG_OUTLINE_COLOR);
        gfx2d.drawRect(x, y, width, height);
    }

}
