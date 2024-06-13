package ru.nsu.ccfit.uryadova.minesweeper.model;

import ru.nsu.ccfit.uryadova.minesweeper.gui.controller.Controller;
import ru.nsu.ccfit.uryadova.minesweeper.gui.controller.MinesweeperTimer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordTable {
    static String filename = "src/main/resources/RecordTable.csv";

    public static void RecordWrite(MinesweeperTimer timer, Controller controller) throws IOException {
        List<String[]> csvData = RecordRead();
        try (FileWriter csv_file = new FileWriter(filename, false)) {
            int time = timer.returnTime();
            int bombs = controller.returnBombsCnt();
            String[] newRecord = {String.valueOf(time), String.valueOf(bombs)};
            csvData.add(newRecord);

            for (String[] record : csvData) {
                csv_file.append(String.join(";", record)).append('\n');
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static List<String[]> RecordRead() throws IOException {
        List<String[]> csvData = new ArrayList<>();
        try (BufferedReader csv_file = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = csv_file.readLine()) != null) {
                String[] fields = line.split(";");
                csvData.add(fields);
            }
        }
        return csvData;
    }
}
