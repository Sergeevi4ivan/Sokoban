package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Model {

    GameObjects gameObjects;
    int currentLevel = 1;
    LevelLoader levelLoader;
    EventListener eventListener;
    public static final int FIELD_CELL_SIZE = 20;

    public Model() {
        try {
            levelLoader = new LevelLoader(Paths.get(getClass().getResource("../res/levels.txt").toURI()));
        } catch (URISyntaxException e) {
        }
    }
    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restartLevel(currentLevel);
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void move(Direction direction) {
        if (checkWallCollision(gameObjects.getPlayer(), direction)) {
            return;
        }
        if (checkBoxCollisionAndMoveIfAvailable(direction)) {
            return;
        }

        switch (direction) {
            case LEFT:
                gameObjects.getPlayer().move(-FIELD_CELL_SIZE, 0);
                break;
            case RIGHT:
                gameObjects.getPlayer().move(FIELD_CELL_SIZE, 0);
                break;
            case UP:
                gameObjects.getPlayer().move(0, -FIELD_CELL_SIZE);
                break;
            case DOWN:
                gameObjects.getPlayer().move(0, FIELD_CELL_SIZE);
                break;
        }
        checkCompletion();
        }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {

        for (GameObject go:
                gameObjects.getWalls()) {
            if (gameObject.isCollision(go, direction)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        for (Box box : gameObjects.getBoxes()) {
            if (gameObjects.getPlayer().isCollision(box, direction)) {
                for (Box item : gameObjects.getBoxes()) {
                    if (!box.equals(item)) {
                        if (box.isCollision(item, direction)) {
                            return true;
                        }
                    }
                    if (checkWallCollision(box, direction)) {
                        return true;
                    }
                }
                int dx = direction == Direction.LEFT ? -FIELD_CELL_SIZE : (direction == Direction.RIGHT ? FIELD_CELL_SIZE : 0);
                int dy = direction == Direction.UP ? -FIELD_CELL_SIZE : (direction == Direction.DOWN ? FIELD_CELL_SIZE : 0);
                box.move(dx, dy);
            }
        }
        return false;
    }

    public void checkCompletion() {

        int count = 0;
        for (Home h:
             gameObjects.getHomes()) {
            for (Box b:
                 gameObjects.getBoxes()) {
                if (h.getX() == b.getX() && h.getY() == b.getY()) {
                    count++;
                }
            }
        }
        if (count == gameObjects.getHomes().size()) {
            eventListener.levelCompleted(currentLevel);
        }
    }
}
