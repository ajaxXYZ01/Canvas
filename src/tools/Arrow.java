package tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

import managers.Viewport2DElementManager;
import math.vec2;
import ui.Inspector;
import utils.Colors;
import math.AABB;
import math.Point2D;
import viewport.Viewport2D;
import viewport.ViewportElement;

public class Arrow extends ViewportElement {

    public Point2D tail, head;
    private vec2 p1, p2, temp;

    private float angle, base_len, len; // len is in world units
    private BasicStroke arrowStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    private float thickness;
    private int CAP;
    private int JOIN;

    public Arrow(float x1, float y1, float x2, float y2) {

        super();

        tail = new Point2D(x1, y1);
        tail.setColor(Color.WHITE);
        tail.setSize(4);

        head = new Point2D(x2, y2);
        head.setColor(Color.WHITE);
        head.setSize(4);

        temp = new vec2();
        angle = (float) Math.toRadians(45);
        
        base_len = 0.25f;
        len = base_len;

        aabb = new AABB();

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

        aabb.min.x = Math.min(tail.x, head.x);
        aabb.min.y = Math.min(tail.y, head.y);
        aabb.max.x = Math.max(tail.x, head.x);
        aabb.max.y = Math.max(tail.y, head.y);

    }

    public void UpdateScale(Viewport2D viewport) {

        p1.x -= head.x;
        p1.y -= head.y;

        p2.x -= head.x;
        p2.y -= head.y;

        p1.scale(1 / len);
        p2.scale(1 / len);

        len = base_len * viewport.getBASE_PPU() / viewport.getPPU();

        p1.scale(len);
        p2.scale(len);

        p1.x += head.x;
        p1.y += head.y;

        p2.x += head.x;
        p2.y += head.y;
    }

    public void setThickness(float t) {
        this.arrowStroke = new BasicStroke(t);
    }

    @Override
    public void render(Graphics2D gfx2d, Viewport2D viewport2d) {

        gfx2d.setColor(Color.WHITE);
        gfx2d.setStroke(arrowStroke);

        UpdateScale(viewport2d);

        int hx = viewport2d.screenX(head.x), hy = viewport2d.screenY(head.y);

        gfx2d.drawLine(viewport2d.screenX(tail.x), viewport2d.screenY(tail.y), hx, hy);
        gfx2d.drawLine(hx, hy, viewport2d.screenX(p1.x), viewport2d.screenY(p1.y));
        gfx2d.drawLine(hx, hy, viewport2d.screenX(p2.x), viewport2d.screenY(p2.y));

        tail.render(gfx2d, viewport2d);
        head.render(gfx2d, viewport2d);

        aabb.render(gfx2d, viewport2d);
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

        JLabel label = new JLabel("Arrow");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setForeground(Color.LIGHT_GRAY);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 16, 2);

        slider.setBackground(Colors.inspector_pane);
        slider.setForeground(Color.WHITE);
        slider.setOpaque(true);

        slider.setBorder(null);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setFocusable(false);
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);
        slider.setSnapToTicks(true);

        slider.setPreferredSize(new Dimension(inspector.getContent().getWidth(), 0));

        slider.addChangeListener(e -> {
            setThickness(slider.getValue());
            inspector.getViewport().repaint();
        });

        JPanel pane = new JPanel();
        pane.setBackground(Colors.inspector_pane);
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(label);
        pane.add(slider);

        inspector.getContent().add(pane);
    }
}
