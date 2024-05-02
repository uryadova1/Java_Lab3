package ru.nsu.ccfit.uryadova.minesweeper.gui.controller;

import ru.nsu.ccfit.uryadova.minesweeper.model.*;

public class Game {
    Bomb bomb;
    Flag flag;

    GameState gameState;

    public GameState getGameState() {
        return gameState;
    }

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.init();
        flag.init();
        gameState = GameState.PLAYED;
    }

    public Box getBox(Coord coord) {
        if (flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) return;
        openBox(coord);
        flag.setOpenedToBox(coord);
        checkWinner();
    }

    private boolean gameOver() {
        return gameState == GameState.BOMBED;
    }


    private void checkWinner() {
        if (gameState == GameState.PLAYED)
            if (flag.getCountOfClosedBpxes() == bomb.getTotalBombs())
                System.out.println("u win!!!!!!!!!!!!");
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
                        return;
                }
        }
    }

    private void openBombs(Coord bombed) {
        gameState = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        System.out.println(bombed.x);
        System.out.println( bombed.y);
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB) {
                flag.setOpenedtoClosedBombBox(coord);
            } else {
                flag.setNoBombToFlagedSafeBox(coord);
            }
        }
        flag.setBombedToBox(bombed);

    }

    private void openBoxAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) return;
        flag.togglFlagedToBox(coord);
    }

    public void setGamePlayed() {
        gameState = GameState.PLAYED;
    }
}
