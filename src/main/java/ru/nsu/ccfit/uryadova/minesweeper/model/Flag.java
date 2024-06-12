package ru.nsu.ccfit.uryadova.minesweeper.model;

import ru.nsu.ccfit.uryadova.minesweeper.gui.view.Box;

public class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    public void init() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    public Box get(Coord coord) {
        return flagMap.get(coord);
    }

    public void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    public void setFlagToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
    }

    public void togglFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED:
                setClosedToBox(coord);
                break;
            case CLOSED:
                setFlagToBox(coord);
                break;
        }
    }

    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    public int getCountOfClosedBpxes() {
        return countOfClosedBoxes;
    }

    public void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
        System.out.println("bombed");
    }

    public void setOpenedtoClosedBombBox(Coord coord) {
        if (flagMap.get(coord) == Box.CLOSED)
            flagMap.set(coord, Box.OPENED);
    }

    public void setNoBombToFlagedSafeBox(Coord coord) {
        if (flagMap.get(coord) == Box.FLAGED)
            flagMap.set(coord, Box.NOBOMB);
    }
}
