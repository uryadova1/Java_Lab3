package ru.nsu.ccfit.uryadova.minesweeper.model;

public class Bomb {

    private Matrix bombMap;
    private int totalBomb;


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
