package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Inspector extends CanvasScrollPane {
    
    private int width;
    private JPanel inspectorContent;

    public Inspector() {

        super(new JPanel());

        inspectorContent = (JPanel) getViewport().getView();

        width = 200;

        inspectorContent.setLayout(new BoxLayout(inspectorContent, BoxLayout.Y_AXIS));
        inspectorContent.setBackground(new Color(8, 8, 8));
        inspectorContent.setOpaque(true);

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setPreferredSize(new Dimension(width, 0));
    }

    public int getWidth() {
        return width;
    }

    public JPanel getContent() {
        return inspectorContent;
    }
}
