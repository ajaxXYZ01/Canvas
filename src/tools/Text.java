package tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;

import math.Point2D;
import ui.Inspector;
import viewport.Viewport2D;
import viewport.ViewportElement;

public class Text extends ViewportElement{
    
    Point2D cornor;
    String text;

    public Text() {
        cornor = new Point2D(0, 0);

        cornor.setSize(4);
        cornor.setColor(Color.ORANGE);

        text = "";
    }
    
    @Override
    public void render(Graphics2D gfx2d, Viewport2D viewport2d) {
        cornor.render(gfx2d, viewport2d);

        gfx2d.setColor(Color.WHITE);
        gfx2d.drawString(text, viewport2d.screenX(cornor.x) + 4, viewport2d.screenY(cornor.y) + 8);
    }

    @Override
    public void select(int x, int y, Viewport2D viewport2d) {
        cornor.select(x, y, viewport2d);
    }

    @Override
    public void offset(int dx, int dy, Viewport2D viewport2d) {
        cornor.offset(dx, dy, viewport2d);
    }
    
    @Override
    public boolean isSelected() {
        return cornor.isSelected();
    }

    @Override
    public void inspector(Inspector inspector) {

        JTextArea area = new JTextArea(4, 20);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        area.setText(this.text != null? this.text : "");
        // area.setBackground(new Color(16,16,16));

        area.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
            
            private void update() {
                text = area.getText();
            }

        });

        JLabel label = new JLabel("Text:");

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(inspector.getWidth() - 20, 80));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        inspector.getContent().add(label);
        inspector.getContent().add(scroll);
    }

}
