package ru.nsu.ccfit.uryadova.minesweeper.gui.view;

import ru.nsu.ccfit.uryadova.minesweeper.gui.controller.Controller;
import ru.nsu.ccfit.uryadova.minesweeper.model.RecordTable;
import ru.nsu.ccfit.uryadova.minesweeper.model.Coord;
import ru.nsu.ccfit.uryadova.minesweeper.model.Ranges;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MinesweeperView extends JFrame {
    private static MinesweeperView instance;

    private final Controller controller;

    private int COLS = 9;
    private int newBombsCnt;
    private int newSizeCnt;
    private int ROWS = 9;
    private int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    private static JPanel panel;
    static JLabel timerLabel;


    public static void main(String[] args) {
        new MinesweeperView();
    }

    public MinesweeperView() {
        instance = this;
        initTimer();
        controller = new Controller(COLS, ROWS, BOMBS);
        controller.start();
        initMenu();
        setImages();
        initPanel();
        initFrame();
    }

    private void initPanel() {
        if (panel != null) {
            remove(panel);
        }
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) controller.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) { // left
                    try {
                        controller.pressLeftButton(coord);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    try {
                        controller.pressRightButton(coord);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
        add(panel, BorderLayout.CENTER);
        pack();
    }


    public static void update(){
        if (panel != null) {
            panel.repaint();
        }
    }
    public static MinesweeperView getInstance() {
        return instance;
    }

    public static void newGame(){
        MinesweeperView instance = getInstance();
        if (instance != null) {
            instance.remove(panel);
            instance.initPanel();
            instance.revalidate();
            instance.repaint();
            update();
        }

    }


    private void initFrame() {
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
                controller.newGame(ROWS, COLS, BOMBS);
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
                    if (!Objects.equals(tmp1, "") && !Objects.equals(tmp2, "")){
                        newBombsCnt = Integer.parseInt(bombs.getText());
                        newSizeCnt = Integer.parseInt(size.getText());
                        ROWS = (newSizeCnt <= 20)? newSizeCnt: 9;
                        COLS = (newSizeCnt <= 20)? newSizeCnt: 9;
                        BOMBS = (newBombsCnt <= 20)? newBombsCnt: 9;
                        controller.newGame(ROWS, COLS, BOMBS);

                    }
                    else
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
                List<String[]> csvData = null;
                try {
                    csvData = RecordTable.RecordRead();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                String[] columnNames = (String[]) ((List<?>) csvData).get(0);
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                // Adding rows to the model, skipping the first row (header)
                for (int i = 1; i < csvData.size(); i++) {
                    model.addRow(csvData.get(i));
                }

                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);

                JFrame frame = new JFrame("CSV Table");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
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


    private void initTimer(){
        timerLabel = new JLabel();
        timerLabel.setText("Time: 0");
        add(timerLabel, BorderLayout.SOUTH);
        setVisible(true);
        pack();
    }
    public static void updateTime(int elapsedTime) {
        timerLabel.setText("Time: " + elapsedTime);
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
    public static void showWinner(){
        JOptionPane.showMessageDialog(null,
                "u win",
                null, JOptionPane.INFORMATION_MESSAGE,
                null);
    }

    public static void showLooser(){
        JOptionPane.showMessageDialog(null,
                "u lose((",
                null, JOptionPane.INFORMATION_MESSAGE,
                null);
    }
}
