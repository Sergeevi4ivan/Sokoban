package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Box extends CollisionObject implements Movable {

    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(getX() - getWidth() / 2, getY() - getHeight() / 2, width, height);
        graphics.setColor(Color.YELLOW);
        graphics.drawLine(getX() - getWidth() / 2, getY() - getHeight() / 2, getX() + getWidth() / 2, getY() + getHeight() / 2);
        graphics.drawLine(getX() + getWidth() / 2, getY() - getHeight() / 2, getX() - getWidth() / 2, getY() + getHeight() / 2);

    }
}
