package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import utils.Colors;
import utils.Icons;
import viewport.Viewport2D;
import app.Window;

public class CanvasMenuBar extends JMenuBar {
    
    Window window;
    Viewport2D viewport;

    public CanvasMenuBar(Window window) {
        
        this.window = window;
        viewport = window.getViewport();

        this.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 64), 1));

        // --------------------------------
        // Menu Item creation
        // --------------------------------

        JMenu file = createMenu("File");
        JMenu view = createMenu("View");

        JMenuItem toggle_axis = createMenuItem("Toggle Axis");
        JMenuItem toggle_grid = createMenuItem("Toggle Grid");
        JMenuItem toggle_dot_lattice = createMenuItem("Toggle Dot Lattice");
        JMenuItem adaptive_grid = createMenuItem("Adaptive Grid");

        // --------------------------------
        // Menu Item Events
        // --------------------------------

        toggle_axis.addActionListener(e -> {
            boolean toggled = !viewport.canRenderAxis();
            if (toggled)
                toggle_axis.setIcon(Icons.CHECKED_ICON);
            else
                toggle_axis.setIcon(Icons.EMPTY_ICON);
            viewport.setAxisRender(toggled);
            viewport.repaint();
        });

        toggle_grid.addActionListener(e -> {
            boolean toggled = !viewport.canRenderGrid();
            if (toggled)
                toggle_grid.setIcon(Icons.CHECKED_ICON);
            else
                toggle_grid.setIcon(Icons.EMPTY_ICON);
            viewport.setGridRender(toggled);
            viewport.repaint();
        });

        toggle_dot_lattice.addActionListener(e -> {
            boolean toggled = !viewport.canRenderDotLattice();
            if (toggled)
                toggle_dot_lattice.setIcon(Icons.CHECKED_ICON);
            else
                toggle_dot_lattice.setIcon(Icons.EMPTY_ICON);
            viewport.setDotLatticeRender(toggled);
            viewport.repaint();
        });

        adaptive_grid.addActionListener(e -> {
            boolean toggled = !viewport.canInfiniteScroll();
            if (toggled)
                adaptive_grid.setIcon(Icons.CHECKED_ICON);
            else
                adaptive_grid.setIcon(Icons.EMPTY_ICON);
            viewport.setInfiniteScroll(toggled);
            viewport.repaint();
        });

        // --------------------------------
        // Add Menu Item to Menu
        // --------------------------------

        view.add(toggle_axis);
        view.add(toggle_grid);
        view.add(toggle_dot_lattice);
        view.add(adaptive_grid);

        // --------------------------------
        // Add Menus
        // --------------------------------

        this.add(file);
        this.add(view);

        this.setBackground(Colors.menu_bar);
        this.setPreferredSize(new Dimension(0, 32));

    }

    public JMenu createMenu(String name) {

        JMenu menu = new JMenu(name);

        menu.setBackground(Colors.menu);
        menu.setForeground(Colors.menu_text);
        menu.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        menu.setOpaque(true);

        return menu;
    }

    public JMenuItem createMenuItem(String name) {

        JMenuItem menuItem = new JMenuItem(name);

        menuItem.setBorder(null);
        menuItem.setBackground(Colors.menu_item);
        menuItem.setForeground(Colors.menu_item_text);
        menuItem.setBorderPainted(false);
        menuItem.setFocusPainted(false);
        menuItem.setOpaque(true);

        menuItem.setIcon(Icons.EMPTY_ICON);
        menuItem.setIconTextGap(8);

        return menuItem;
    }

    public JCheckBoxMenuItem createCheckBoxMenuItem(String name) {

        JCheckBoxMenuItem check_box = new JCheckBoxMenuItem(name);

        check_box.setBorder(null);
        check_box.setBackground(Colors.menu_item);
        check_box.setForeground(Colors.menu_item_text);
        check_box.setBorderPainted(false);
        check_box.setFocusPainted(false);
        check_box.setOpaque(true);

        check_box.setIcon(null);
        check_box.setSelectedIcon(Icons.CHECKED_ICON);
        check_box.setIconTextGap(8);

        return check_box;
    }

}
