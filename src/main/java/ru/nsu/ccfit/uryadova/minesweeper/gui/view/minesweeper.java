package ru.nsu.ccfit.uryadova.minesweeper.gui.view;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import ru.nsu.ccfit.uryadova.minesweeper.gui.controller.Controller;
import ru.nsu.ccfit.uryadova.minesweeper.model.Ranges;

import javax.swing.*;

public class minesweeper extends JFrame {

    private Controller controller;

    private int COLS = 9;
    private int newBombsCnt;
    private int newSizeCnt;
    private int ROWS = 9;
    private int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    private MinesweeperPanel panel;


    public static void main(String[] args) {
        new minesweeper();
    }

    public void newGame(int rows, int cols, int bombs) {
        controller = new Controller(rows, cols, bombs);
        controller.start();
        initMenu();
        setImages();
        panel = null;
        panel = new MinesweeperPanel(controller);
        initFrame(panel);
    }

    public minesweeper() {
        controller = new Controller(COLS, ROWS, BOMBS);
        controller.start();
        initMenu();
        setImages();
        panel = new MinesweeperPanel(controller);
        initFrame(panel);
    }

//    private void initPanel() {
//        panel = new JPanel() {
//            @Override // анонимный класс это называется
//            public void paintComponent(Graphics g) {
//                super.paintComponents(g);
//                for (Coord coord : Ranges.getAllCoords()) {
//                    g.drawImage((Image) controller.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
//                }
//            }
//        };
//        panel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                int x = e.getX() / IMAGE_SIZE;
//                int y = e.getY() / IMAGE_SIZE;
//                Coord coord = new Coord(x, y);
//                if (e.getButton() == MouseEvent.BUTTON1) //left
//                    controller.pressLeftButton(coord);
//                if (e.getButton() == MouseEvent.BUTTON3)
//                    controller.pressRightButton(coord);
//                panel.repaint();
//            }
//        });
//        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
//        add(panel);
//    }


    private void initFrame(MinesweeperPanel panel) {
        add(panel);
        setPreferredSize(new Dimension(panel.returnSize(), panel.returnSize()));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("mega saper");
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        pack();
    }


    public void initMenu() {
        JMenuBar menubar = new JMenuBar();
        JMenu newGame = new JMenu("New Game");
        JMenu highScores = new JMenu("High Scores");
        JMenu settings = new JMenu("Settings");

        newGame.add(new JSeparator());

        menubar.add(settings);
        menubar.add(newGame);
        menubar.add(highScores);
        setJMenuBar(menubar);

        newGame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                newGame(COLS, ROWS, BOMBS);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        settings.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JFrame frame = new JFrame("Settings");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //реальное закрытие программы, при закрытии окна(нажатии на красный крестик)
                frame.setSize(300, 160);


                final JPanel settingsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
                settingsPanel.setPreferredSize(new Dimension(300, 160));
                frame.add(settingsPanel);
                frame.add(settingsPanel);

                JLabel areaSize = new JLabel("Размеры");
//                areaSize.setBounds(10, 10, 100, 20);
                JLabel bombsCnt = new JLabel("Количество бомб");
//                bombsCnt.setBounds(10, 40, 100, 20);

                settingsPanel.add(areaSize);
                settingsPanel.add(bombsCnt);

                JTextField size = new JTextField(10);
                size.setBounds(10, 60, 100, 10);
                size.setBorder(BorderFactory.createLineBorder(Color.black));
                JTextField bombs = new JTextField(10);
                bombs.setBounds(10, 90, 30, 10);
                bombs.setBorder(BorderFactory.createLineBorder(Color.black));

                settingsPanel.add(size);
                settingsPanel.add(bombs);

                pack();
                int result = JOptionPane.showConfirmDialog(frame, settingsPanel,
                        "Settings", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE); //ok - 0, cancel - 2
                if (result == 0) {
                    String tmp1 = bombs.getText();
                    String tmp2 = size.getText();
                    if (!Objects.equals(tmp1, "") && !Objects.equals(tmp2, "")) {
                        newBombsCnt = Integer.parseInt(bombs.getText());
                        newSizeCnt = Integer.parseInt(size.getText());
                        ROWS = (newSizeCnt <= 20) ? newSizeCnt : 9;
                        COLS = (newSizeCnt <= 20) ? newSizeCnt : 9;
                        BOMBS = (newBombsCnt <= 20) ? newSizeCnt : 9;
                        newGame(COLS, ROWS, BOMBS);

                    } else
                        System.out.println(-1);
                }

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        highScores.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("3");
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        setVisible(true);
        pack();

    }

    private void setImages() {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private static Image getImage(String name) {
        String filename = "src/main/resources/img/" + name.toLowerCase() + ".png";
        ImageIcon imageIcon = new ImageIcon(filename);
        return imageIcon.getImage();

    }

}

