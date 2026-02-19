package ui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Inspector extends JScrollPane {
    
    public Inspector() {

        JPanel elementList = new JPanel();
        elementList.setLayout(new BoxLayout(elementList, BoxLayout.Y_AXIS));

        this.add(elementList);

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.setBorder(null);
        this.setPreferredSize(new Dimension(200, 0));
    }
}
