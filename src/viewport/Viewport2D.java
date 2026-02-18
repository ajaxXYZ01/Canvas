package viewport;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import managers.Viewport2DMouseManager;

public class Viewport2D extends JPanel {

    private Point mouse, center;

    private float PPU; // Pixel Per Unit
    public static final float BASE_PPU = 100;
    private float step;
    private float minX, minY, maxX, maxY;

    private Color backgroundColor;

    // Flags
    private boolean canDrawGrid;
    private boolean canDrawAxis;
    private boolean canDrawDotLattice;

    // CONTANTS
    private static BasicStroke gridStroke1 = new BasicStroke(1);
    private static BasicStroke axisStroke2 = new BasicStroke(2);

    public Viewport2D(int sizeX, int sizeY) {

        this.setPreferredSize(new Dimension(sizeX, sizeY));
        this.setBackground(new Color(8, 8, 8));
        backgroundColor = new Color(8, 8, 8);

        Viewport2DMouseManager viewportMouseInput = new Viewport2DMouseManager(this);
        this.addMouseListener(viewportMouseInput);
        this.addMouseMotionListener(viewportMouseInput);
        this.addMouseWheelListener(viewportMouseInput);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                UpdateWorldBounds();
            }
        });

        mouse    = new Point();
        center   = new Point(sizeX / 2, sizeY / 2);

        PPU  = 100;
        step = 1;

        minX = worldX(0);
        minY = worldY(sizeY);

        maxX = worldX(sizeX);
        maxY = worldY(0);

        setGridRender(true);
        setAxisRender(true);
        setDotLatticeRender(false);
    }

    // --- Rendering ---

    @Override
    protected void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);

        Graphics2D gfx2d = (Graphics2D) gfx;
        gfx2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        gfx2d.setColor(backgroundColor);
        // gfx2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        Debug(gfx2d);
        
        // Background
        drawGrid(gfx2d, step / 2, Color.DARK_GRAY);
        drawGrid(gfx2d, step, Color.GRAY);
        drawDotLattice(gfx2d, 4);

        drawAxis(gfx2d);

        // FUN
        
        
    }

    private void Debug(Graphics2D gfx2d) {
        gfx2d.setColor(Color.WHITE);
        gfx2d.drawString("mouse screen position: " + mouse.x + ", " + mouse.y, 0, 16);
        gfx2d.drawString("mouse world position: " + worldX(mouse.x) + ", " + worldY(mouse.y), 0, 32);
        gfx2d.drawString("PPU: " + PPU, 0, 48);
    }

    private void drawGrid(Graphics2D gfx2d, float step, Color color) {

        if (!canDrawGrid) return;

        gfx2d.setStroke(gridStroke1); // FIX: optimize
        gfx2d.setColor(color);

        float gridMinX = (float) (Math.ceil(minX / step) * step);

        for (float x = gridMinX; x <= maxX; x += step) {
            int x1 = screenX(x);
            gfx2d.drawLine(x1, 0, x1, this.getHeight());
        }
        
        float gridMinY = (float) (Math.ceil(minY / step) * step);

        for (float y = gridMinY; y <= maxY; y += step) {
            int y1 = screenY(y);
            gfx2d.drawLine(0, y1, this.getWidth(), y1);
        }
    }

    private void drawAxis(Graphics2D gfx2d) {
        
        if (!canDrawAxis) return;

        gfx2d.setStroke(axisStroke2); // FIX: optimize

        int originX = screenX(0);
        int originY = screenY(0);
        
        gfx2d.setColor(Color.GREEN);
        if (originX >= 0 && originX <= this.getWidth()) {
            gfx2d.drawLine(originX, 0, originX, this.getHeight());
        }
        gfx2d.setColor(Color.RED);
        if (originY >= 0 && originY <= this.getHeight()) {
            gfx2d.drawLine(0, originY, this.getWidth(), originY);
        }
    }

    /* FIX: code infinite zoom illusion */
    private void drawDotLattice(Graphics2D gfx2d, int thickness) {

        if (!canDrawDotLattice) return;

        gfx2d.setColor(Color.DARK_GRAY);

        float gridMinX = (float) (Math.ceil(minX / step) * step);
        float gridMinY = (float) (Math.ceil(minY / step) * step);

        for (float y = gridMinY; y <= maxY; y += step) {

            for (float x = gridMinX; x <= maxX; x += step) {

                int x1 = screenX(x);
                int y1 = screenY(y);
                
                gfx2d.fillOval(x1 - thickness / 2, y1 - thickness / 2, thickness, thickness);
            }
        }
    }

    // --- Setters ---
    public void setMouse(Point point) {
        mouse.x = point.x;
        mouse.y = point.y;
    }

    public void setOrigin(int x, int y) {
        center.x = x;
        center.y = y;
    }

    public void setPPU(float value) {
        PPU = value;
    }

    public void setGridRender(boolean flag) {
        canDrawGrid = flag;
    }

    public void setAxisRender(boolean flag) {
        canDrawAxis = flag;   
    }

    public void setDotLatticeRender(boolean flag) {
        canDrawDotLattice = flag;
    }
    // --- Getters ---
    public Point getOrigin() { return center; }
    public float getPPU()    { return PPU; }

    // --- Utils ---
    public int screenX(float worldX) { return (int) (center.x + worldX * PPU); }
    public int screenY(float worldY) { return (int) (center.y - worldY * PPU); }

    float worldX(int screenX) { return (screenX - center.x) / PPU; }
    float worldY(int screenY) { return (center.y - screenY) / PPU; }

    public void UpdateWorldBounds() {
        minX = worldX(0);
        minY = worldY(this.getHeight());

        maxX = worldX(this.getWidth());
        maxY = worldY(0);
    }

    public void UpdateCenterOnScroll(Point event, float factor) {
        int pivotX_init = (int) event.getX();
        int pivotY_init = (int) event.getY();

        float worldPivotX_init = worldX(pivotX_init);
        float worldPivotY_init = worldY(pivotY_init);

        float worldPivotX_final = worldPivotX_init * factor;
        float worldPivotY_final = worldPivotY_init * factor;

        int pivotX_final = screenX(worldPivotX_final);
        int pivotY_final = screenY(worldPivotY_final);

        center.x -= pivotX_final - pivotX_init;
        center.y -= pivotY_final - pivotY_init;
    }
}