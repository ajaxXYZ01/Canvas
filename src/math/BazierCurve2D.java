package math;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import viewport.Viewport2D;
import viewport.ViewportElement;

public class BazierCurve2D extends ViewportElement {
    
    ArrayList <Point2D> anchorPoints;
    ArrayList <Point2D> controlPoints;

    private int bufferSize;
    private Point buffer [];

    int quality;
    private float timeStep;

    public BazierCurve2D() {
        anchorPoints  = new ArrayList <> ();
        controlPoints = new ArrayList <> ();

        quality = 16;
        timeStep = 1f / quality;
    }

    // --- Utils ---
    public void addPoint(float x, float y) {
        anchorPoints.add(new Point2D(x, y));
        recalculateHandles();
    }

    public static float lerp(float a, float b, float t) {
        return (b - a) * t + a;
    }

    public void recalculateHandles() {
        controlPoints.clear();

        for (int index = 0; index < anchorPoints.size() - 1; index++) {
            Point2D p1 = anchorPoints.get(index);
            Point2D p2 = anchorPoints.get(index + 1);

            float midx = (p1.x + p2.x) / 2;
            float midy = (p1.y + p2.y) / 2;

            Point2D midPoint = new Point2D(midx, midy);
            midPoint.setColor(Color.RED);

            controlPoints.add(midPoint);
        }
    }

    private void init_Buffer() {

        bufferSize = 2 * anchorPoints.size() - 1;

        if (buffer == null || buffer.length != bufferSize)
            buffer = new Point [bufferSize];
        
        for (int index = 0; index < bufferSize; index++) {
            buffer[index] = new Point(0, 0); // allocate only once
        }

        for (int index = 0; index < bufferSize; index++) {

            if (index % 2 == 0) {
                Point2D anchor = anchorPoints.get(index / 2);
                buffer[index].x = anchor.x;
                buffer[index].y = anchor.y;
            } else {
                Point2D control = controlPoints.get(index / 2);
                buffer[index].x = control.x;
                buffer[index].y = control.y;
            }
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
    
    @Override
    public void render(Graphics2D gfx2d, Viewport2D viewport2d) {
        
        gfx2d.setColor(Color.WHITE);
        for (float t = 0; t <= 1 - timeStep; t += timeStep) {
            Point temp1 = getInterpolatedPoint(t);
            Point temp2 = getInterpolatedPoint(t + timeStep);
            gfx2d.drawLine(viewport2d.screenX(temp1.x), viewport2d.screenY(temp1.y), viewport2d.screenX(temp2.x), viewport2d.screenY(temp2.y));
        }

        for (Point2D point2d : anchorPoints) {
            point2d.render(gfx2d, viewport2d);
        }
        for (Point2D point2d : controlPoints) {
            point2d.render(gfx2d, viewport2d);
        }
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

}