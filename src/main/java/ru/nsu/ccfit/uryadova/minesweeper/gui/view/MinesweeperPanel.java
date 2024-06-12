package ru.nsu.ccfit.uryadova.minesweeper.gui.view;

import ru.nsu.ccfit.uryadova.minesweeper.gui.controller.Controller;
import ru.nsu.ccfit.uryadova.minesweeper.model.Coord;
import ru.nsu.ccfit.uryadova.minesweeper.model.Ranges;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinesweeperPanel extends JPanel {
    private final JPanel panel = new JPanel();
    private final Controller controller;
    private final int IMAGE_SIZE = 50;

    MinesweeperPanel(Controller controller) {
        this.controller = controller;
        addListeners();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        for (Coord coord : Ranges.getAllCoords()) {
            g.drawImage((Image) controller.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
        }
    }

    void addListeners(){
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(1);
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) //left
                    controller.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    controller.pressRightButton(coord);
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
    }

    int returnSize(){
        return Ranges.getSize().x * IMAGE_SIZE;
    }

    void updatePanel() {
        panel.repaint();
    }
}
