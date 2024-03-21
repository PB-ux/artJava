package org.example;

import java.awt.*;
import java.util.Random;
import java.util.List;

public class Circle implements Runnable {
    private Color color;
    public int x, y, dx, dy, diameter;
    private Panel panel;

    public Circle(int x, int y, int dx, int dy, int diameter, Color color, Panel panel) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.diameter = diameter;
        this.color = color;
        this.panel = panel;
    }

    @Override
    public void run() {
        while(true) {
            move();
            checkCollision(panel.getCircles());
            panel.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkCollision(List<Circle> circles) {
        for (Circle otherCircle : circles) {
            if (otherCircle != this) {
                int dx = otherCircle.x - this.x;
                int dy = otherCircle.y - this.y;
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                int minDistance = this.diameter / 2 + otherCircle.diameter / 2;

                if (distance < minDistance) {
                    int moveX = (dx / distance) * (minDistance - distance);
                    int moveY = (dy / distance) * (minDistance - distance);

                    this.x -= moveX;
                    this.y -= moveY;

                    otherCircle.x += moveX;
                    otherCircle.y += moveY;

                    this.dx = -this.dx;
                    this.dy = -this.dy;
                    otherCircle.dx = -otherCircle.dx;
                    otherCircle.dy = -otherCircle.dy;
                }
            }
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(color);
        g2D.fillOval(x, y, diameter, diameter);
    }

    public void move() {
        x += dx;
        y += dy;

        if (x <= 0 || x + diameter >= 1000 ) {
            dx = -dx;
            color = getRandomColor();
        }

        if (y <= 0 || y + diameter >= 600) {
            dy = -dy;
            color = getRandomColor();
        }
    }

    public Color getRandomColor() {
        Random random = new Random();

        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

}
