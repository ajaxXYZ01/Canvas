package math;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import viewport.Viewport2D;
import viewport.ViewportElement;

public class BazierCurve2D extends ViewportElement {
    
    ArrayList <Point2D> controlPoints;

    private int bufferSize;
    private Point buffer [];

    // Variables
    int quality;
    BasicStroke curveStroke;

    private float timeStep;

    public BazierCurve2D() {
        controlPoints  = new ArrayList <> ();

        quality  = 32;
        timeStep = 1f / quality;

        setStrokeSize(2);
    }

    // --- Utils ---
    public void addPoint(float x, float y) {
        controlPoints.add(new Point2D(x, y));
    }

    public void setStrokeSize(float thickness) {
        curveStroke = new BasicStroke(thickness);
    }

    public static float lerp(float a, float b, float t) {
        return (b - a) * t + a;
    }

    // Simple Data Structure for point coordinates
    private class Point {
        float x, y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        void print() {
            System.out.println(x + ", " + y);
        }
    }

    private void init_Buffer() {

        bufferSize = controlPoints.size();

        if (buffer == null || buffer.length != bufferSize)
            buffer = new Point [bufferSize];
        
        for (int index = 0; index < bufferSize; index++) {
            buffer[index] = new Point(0, 0); // allocate only once
        }

        for (int index = 0; index < bufferSize; index++) {
            Point2D anchor = controlPoints.get(index);
            buffer[index].x = anchor.x;
            buffer[index].y = anchor.y;
        }
    }

    public Point getInterpolatedPoint(float t) {

        init_Buffer();

        for (; bufferSize > 1; bufferSize--) {
            for (int index = 0; index < bufferSize - 1; index++) {
                Point p1 = buffer[index];
                Point p2 = buffer[index + 1];
                buffer[index].x = lerp(p1.x, p2.x, t);
                buffer[index].y = lerp(p1.y, p2.y, t);
            }
        }
        
        return buffer[0];
    }
    
    // --- Overrides ---

    @Override
    public void render(Graphics2D gfx2d, Viewport2D viewport2d) {
        
        gfx2d.setColor(Color.WHITE);
        gfx2d.setStroke(curveStroke);

        for (float t = 0; t <= 1 - timeStep; t += timeStep) {
            Point temp1 = getInterpolatedPoint(t);
            Point temp2 = getInterpolatedPoint(t + timeStep);
            gfx2d.drawLine(viewport2d.screenX(temp1.x), viewport2d.screenY(temp1.y), viewport2d.screenX(temp2.x), viewport2d.screenY(temp2.y));
        }

        for (Point2D point2d : controlPoints) {
            point2d.render(gfx2d, viewport2d);
        }
    }    

    @Override
    public void select(int x, int y, Viewport2D viewport2d) {
        for (Point2D point2d : controlPoints) {
            point2d.select(x, y, viewport2d);
        }
    }

    @Override
    public void offset(int dx, int dy, Viewport2D viewport2d) {
        for (Point2D point2d : controlPoints) {
            if (point2d.isSelected())
                point2d.offset(dx, dy, viewport2d);
        }
    }

    @Override
    public boolean isSelected() {
        boolean bool = false;
        for (Point2D point2d : controlPoints) {
            bool |= point2d.isSelected();
        }
        return bool;
    }

}