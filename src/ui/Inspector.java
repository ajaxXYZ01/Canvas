package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Inspector extends JScrollPane {
    
    private int width;
    private JPanel inspectorContent;

    public Inspector() {

        width = 200;

        inspectorContent = new JPanel();
        inspectorContent.setLayout(new BoxLayout(inspectorContent, BoxLayout.Y_AXIS));

        this.setViewportView(inspectorContent);

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.getViewport().setBackground(new Color(16,16,16));

        this.setBorder(null);
        this.setPreferredSize(new Dimension(width, 0));
    }

    public int getWidth() {
        return width;
    }

    public JPanel getContent() {
        return inspectorContent;
    }
}
