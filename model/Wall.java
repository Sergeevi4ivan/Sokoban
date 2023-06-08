package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Wall extends CollisionObject {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(new Color(128,64,48));
        graphics.fillRect(getX() - getWidth() / 2, getY() - getHeight() / 2, width, height);
    }
}
