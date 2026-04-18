package ui;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CanvasScrollPane extends JScrollPane {

    public CanvasScrollPane(Component view) {

        super(view);

        this.setBorder(null);
        this.setViewportBorder(null);

        this.getViewport().setBackground(new Color(16, 16, 16));

        styleScrollBar(this.getVerticalScrollBar());
        styleScrollBar(this.getHorizontalScrollBar());
    }
    
    private void styleScrollBar(JScrollBar bar) {
        bar.setUI(new BasicScrollBarUI() {

            protected void configureScrollBarColors() {
                this.thumbColor = new Color(80, 80, 80);
                this.trackColor = new Color(24, 24, 24);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return zeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return zeroButton();
            }

            private JButton zeroButton() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                b.setMinimumSize(new Dimension(0, 0));
                b.setMaximumSize(new Dimension(0, 0));
                return b;
            }
        });

        bar.setBorder(null);
    }
}
