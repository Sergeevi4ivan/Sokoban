package com.javarush.task.task34.task3410.model;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class LevelLoader {

    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {

        if (level > 60) level %= 60;

        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = null;

        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(levels)))) {
            int sizeX;
            int sizeY;
            String str;
            while ((str = br.readLine()) != null) {
                if (str.contains("Maze: ") && level == Integer.valueOf(str.split(" ")[1])) {

                    br.readLine();
                    str = br.readLine();
                    sizeX = parseXY(str);

                    str = br.readLine();
                    sizeY = parseXY(str);

                    br.readLine();
                    br.readLine();
                    br.readLine();

                    for (int i = 0; i < sizeY; i++) {
                        str = br.readLine();
                        for (int j = 0; j < sizeX; j++) {
                            String[] strAsArray = str.split("");

                            switch (strAsArray[j]) {
                                case "X": {
                                    walls.add(new Wall(Model.FIELD_CELL_SIZE / 2 + j * Model.FIELD_CELL_SIZE, Model.FIELD_CELL_SIZE / 2 + i * Model.FIELD_CELL_SIZE));
                                    break;
                                }
                                case "*": {
                                    boxes.add(new Box(Model.FIELD_CELL_SIZE / 2 + j * Model.FIELD_CELL_SIZE, Model.FIELD_CELL_SIZE / 2 + i * Model.FIELD_CELL_SIZE));
                                    break;
                                }
                                case ".": {
                                    homes.add(new Home(Model.FIELD_CELL_SIZE / 2 + j * Model.FIELD_CELL_SIZE, Model.FIELD_CELL_SIZE / 2 + i * Model.FIELD_CELL_SIZE));
                                    break;
                                }
                                case "&": {
                                    boxes.add(new Box(Model.FIELD_CELL_SIZE / 2 + j * Model.FIELD_CELL_SIZE, Model.FIELD_CELL_SIZE / 2 + i * Model.FIELD_CELL_SIZE));
                                    homes.add(new Home(Model.FIELD_CELL_SIZE / 2 + j * Model.FIELD_CELL_SIZE, Model.FIELD_CELL_SIZE / 2 + i * Model.FIELD_CELL_SIZE));
                                    break;
                                }
                                case "@": {
                                    player = new Player(Model.FIELD_CELL_SIZE / 2 + j * Model.FIELD_CELL_SIZE, Model.FIELD_CELL_SIZE / 2 + i * Model.FIELD_CELL_SIZE);
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new GameObjects(walls, boxes, homes, player);
    }

    private int parseXY(String str) {
        if (str == null || str.isEmpty()) return 0;

        String[] strAsArray = str.trim().split(" ");
        return Integer.parseInt(strAsArray[2]);
    }
}
