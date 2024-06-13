package ru.nsu.ccfit.uryadova.minesweeper.gui.controller;

import ru.nsu.ccfit.uryadova.minesweeper.gui.view.Box;
import ru.nsu.ccfit.uryadova.minesweeper.gui.view.MinesweeperView;
import ru.nsu.ccfit.uryadova.minesweeper.model.*;

import java.io.IOException;

public class Controller {
    Bomb bomb;
    Flag flag;

    private final MinesweeperTimer timer;

    static GameState gameState;

    public static GameState getGameState() {
        return gameState;
    }

    public Controller(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        timer = new MinesweeperTimer();
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void newGame(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        bomb.init();
        flag.init();
        setGamePlayed();
        timer.restartTime();
        MinesweeperView.newGame();
        MinesweeperView.update();
    }

    public void start() {
        gameState = GameState.PLAYED;
        timer.startTimer();
        bomb.init();
        flag.init();
        setGamePlayed();
    }

    public Box getBox(Coord coord) {
        if (flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) throws IOException {
        if (gameOver()) return;
        openBox(coord);
        flag.setOpenedToBox(coord);
        checkWinner();
        MinesweeperView.update();
    }

    private boolean gameOver() {
        return gameState == GameState.BOMBED;
    }


    private void checkWinner() throws IOException {
        if (gameState == GameState.PLAYED)
            if (flag.getCountOfRealBombs() == bomb.getTotalBombs()) {
                MinesweeperView.showWinner();
                RecordTable.RecordWrite(timer, this);
                gameState = GameState.WINNER;
            }
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED:
                return;
            case FLAGED:
                return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        openBoxAround(coord);
                        return;
                    case BOMB:
                        openBombs(coord);
                        return;
                    default:
                        flag.setOpenedToBox(coord);
                }
        }
    }

    private void openBombs(Coord bombed) {
        gameState = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB) {
                flag.setOpenedtoClosedBombBox(coord);
            } else {
                flag.setNoBombToFlagedSafeBox(coord);
            }
        }
        flag.setBombedToBox(bombed);
        MinesweeperView.showLooser();

    }

    private void openBoxAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) throws IOException {
        if (gameOver()) return;
        flag.togglFlagedToBox(coord);
        checkWinner();
        MinesweeperView.update();
    }

    public void setGamePlayed() {
        gameState = GameState.PLAYED;
    }


    public int returnBombsCnt() {
        return bomb.getTotalBombs();
    }
}