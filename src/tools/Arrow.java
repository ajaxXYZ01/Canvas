package tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import math.vec2;
import ui.Inspector;
import math.Point2D;
import viewport.Viewport2D;
import viewport.ViewportElement;

// FIX: SHOULD NOT RESPOND TO VIEWPORT ZOOM
public class Arrow extends ViewportElement {

    private Point2D tail, head;
    private vec2 p1, p2, temp;

    private float angle, len; // len is in Pixels
    private final BasicStroke arrowStroke = new BasicStroke(2);

    public Arrow(float x1, float y1, float x2, float y2) {
        tail = new Point2D(x1, y1);
        tail.setColor(Color.WHITE);
        tail.setSize(4);

        head = new Point2D(x2, y2);
        head.setColor(Color.WHITE);
        head.setSize(4);

        temp = new vec2();
        angle = (float) Math.toRadians(45);
        len = 0.125f;

        UpdatePoints();
    }

    public void UpdatePoints() {
        temp.x = tail.x - head.x;
        temp.y = tail.y - head.y;

        vec2.normalize(temp);

        p1 = temp;
        p2 = temp.copy();

        float sin_angle = (float) Math.sin(angle);
        float cos_angle = (float) Math.cos(angle);

        vec2.rotate(p1,  sin_angle, cos_angle);
        vec2.rotate(p2, -sin_angle, cos_angle);

        p1.scale(len);
        p2.scale(len);

        vec2 headVec2 = new vec2(head.x, head.y);
        p1.add(headVec2);
        p2.add(headVec2);
    }

    @Override
    public void render(Graphics2D gfx2d, Viewport2D viewport2d) {

        gfx2d.setColor(Color.WHITE);
        gfx2d.setStroke(arrowStroke);

        int hx = viewport2d.screenX(head.x), hy = viewport2d.screenY(head.y);

        gfx2d.drawLine(viewport2d.screenX(tail.x), viewport2d.screenY(tail.y), hx, hy);
        gfx2d.drawLine(hx, hy, viewport2d.screenX(p1.x), viewport2d.screenY(p1.y));
        gfx2d.drawLine(hx, hy, viewport2d.screenX(p2.x), viewport2d.screenY(p2.y));

        tail.render(gfx2d, viewport2d);
        head.render(gfx2d, viewport2d);
    }

    @Override
    public void select(int x, int y, Viewport2D viewport2d) {
        head.select(x, y, viewport2d);
        tail.select(x, y, viewport2d);
    }

    @Override
    public void offset(int dx, int dy, Viewport2D viewport2d) {

        if (head.isSelected())
            head.offset(dx, dy, viewport2d);
        if (tail.isSelected())
            tail.offset(dx, dy, viewport2d);

        UpdatePoints();
    }

    @Override
    public boolean isSelected() {
        return head.isSelected() || tail.isSelected();
    }

    @Override
    public void inspector(Inspector inspector) {
        //
    }
}
