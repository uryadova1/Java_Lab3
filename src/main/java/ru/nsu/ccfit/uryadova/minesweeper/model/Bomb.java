package ru.nsu.ccfit.uryadova.minesweeper.model;

import ru.nsu.ccfit.uryadova.minesweeper.gui.view.Box;

public class Bomb {

    private static Matrix bombMap;
    private final int totalBomb;


    public Bomb(int totalBomb) {
        this.totalBomb = totalBomb;
    }

    public void init() {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBomb; i++) {
            placeBomb();
        }
    }

    private void placeBomb() {
        int cnt = 0;
        while (cnt < totalBomb) {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord))
                continue;
            bombMap.set(coord, Box.BOMB);
            incNumbersAroundBombs(coord);
            cnt++;
            break;
        }
    }

    static boolean isBombed(Coord coord){
        return bombMap.get(coord) == Box.BOMB;
    }

    public Box get(Coord coord) {
        return bombMap.get(coord);
    }
    private void  incNumbersAroundBombs(Coord coord){
        for (Coord around: Ranges.getCoordAround(coord))
            if (Box.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).getNextNumber());

    }

    public int getTotalBombs() {
        return totalBomb;
    }
}
