package ru.nsu.ccfit.uryadova.minesweeper.gui.controller;

import ru.nsu.ccfit.uryadova.minesweeper.gui.view.MinesweeperView;
import ru.nsu.ccfit.uryadova.minesweeper.model.GameState;

import javax.swing.*;

public class MinesweeperTimer {
    private Thread thread;
    private int elapsedTime;
    private volatile boolean running;

    void startTimer() {
        running = true;
        thread = new Thread(() -> {
            while (running && Controller.getGameState() == GameState.PLAYED) {
                try {
                    updateTime();
                    SwingUtilities.invokeLater(() -> MinesweeperView.updateTime(elapsedTime));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        thread.start();
    }

    private void updateTime() {
        elapsedTime++;
    }

    public int returnTime() {
        return elapsedTime;
    }

    public void restartTime() {
        stopTimer();
        elapsedTime = 0;
        startTimer();
    }

    public void stopTimer() {
        running = false;
        if (thread != null) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        elapsedTime = 0;
    }
}
