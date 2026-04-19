package tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import math.Point2D;
import ui.CanvasScrollPane;
import ui.Inspector;
import viewport.Viewport2D;
import viewport.ViewportElement;

public class Text extends ViewportElement{
    
    private Point2D anchor;
    private String text;
    private String lines[];
    private float outline_width;
    private String longest_line;

    private Font font;

    public static BasicStroke OUTLINE_STROKE = new BasicStroke(
        1.0f,
        BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_MITER,
        10.0f,
        new float[] {6f, 6f},
        0f);

    public Text(Viewport2D viewport) {

        anchor = new Point2D(viewport.worldX(0), viewport.worldY(0));

        anchor.setSize(10);
        anchor.setColor(Color.ORANGE);

        text = "";
        font = new Font("ariel", Font.PLAIN, 12);

    }
    
    @Override
    public void render(Graphics2D gfx2d, Viewport2D viewport2d) {


        int x = viewport2d.screenX(anchor.x);
        int y = viewport2d.screenY(anchor.y);

        int size = anchor.getSize();
        
        float scale = viewport2d.getPPU() / viewport2d.getBASE_PPU();
        float lineHeight = gfx2d.getFontMetrics().getHeight();

        // --------------------------------
        // Rendering anchor
        // --------------------------------
        
        gfx2d.setColor(Color.WHITE);

        if (anchor.isSelected())
            gfx2d.fillRect(x - size / 2, y - size / 2, size, size);
        else
            gfx2d.drawRect(x - size / 2, y - size / 2, size, size);
        
        
        if (lines == null)
            return;

        // --------------------------------
        // Rendering outline
        // --------------------------------

        gfx2d.setColor(Color.ORANGE);

        if (anchor.isSelected()) {
            gfx2d.setFont(font);

            Stroke oldStroke = gfx2d.getStroke();
            gfx2d.setStroke(OUTLINE_STROKE);

            outline_width = gfx2d.getFontMetrics(font).stringWidth(longest_line);
            gfx2d.drawRect(x, y, (int) (outline_width * scale), (int) ((lines.length + 1) * lineHeight * scale));

            gfx2d.setStroke(oldStroke);
        }

        // --------------------------------
        // Rendering text
        // --------------------------------
        AffineTransform old = gfx2d.getTransform();

        gfx2d.translate(x + lineHeight / 2 * scale, y + lineHeight * scale);
        gfx2d.scale(scale, scale);

        gfx2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < lines.length; i++)
            gfx2d.drawString(lines[i], 0, i * lineHeight);

        gfx2d.setTransform(old);
    }

    @Override
    public void select(int x, int y, Viewport2D viewport2d) {
        anchor.select(x, y, viewport2d);
    }

    @Override
    public void offset(int dx, int dy, Viewport2D viewport2d) {
        anchor.offset(dx, dy, viewport2d);
    }
    
    @Override
    public boolean isSelected() {
        return anchor.isSelected();
    }

    @Override
    public void inspector(Inspector inspector) {

        JTextArea area = new JTextArea(4, 20);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setText(this.text != null? this.text : "");
        area.setBackground(new Color(16, 16, 16));
        area.setForeground(Color.WHITE);
        area.setCaretColor(Color.WHITE);
        area.setSelectionColor(new Color(60, 60, 60));
        area.setSelectedTextColor(Color.CYAN);
        area.setBorder(null);

        area.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
            
            private void update() {
                text = area.getText();
                lines = text.split("\\R");
                
                int max = Integer.MIN_VALUE;
                int len;

                for (String line : lines) {
                    len = line.length();
                    if (len > max) {
                        max = len;
                        longest_line = line + " ";
                    }
                }

            }

        });

        JLabel label = new JLabel("Text");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setForeground(Color.LIGHT_GRAY);

        JScrollPane scroll = new CanvasScrollPane(area);
        scroll.setPreferredSize(new Dimension(inspector.getWidth() - 20, 80));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JPanel pane = new JPanel();
        pane.setBackground(new Color(8, 8, 8));
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(label);
        pane.add(scroll);

        inspector.getContent().add(pane);
    }

}
