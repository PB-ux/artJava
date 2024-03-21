package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.List;

public class Frame extends JFrame {
    private static Panel panel;
    private static Integer COUNT_CIRCLES = 10;

    Frame() {
        panel = new Panel();

        this.generateCircles(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void generateCircles(Panel panel) {
        for (int i = 0; i < COUNT_CIRCLES; i++) {
            Random random = new Random();
            Circle circle = new Circle(random.nextInt(300), random.nextInt(500), 1, 1, 50, new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)), panel);

            panel.addCircle(circle);
            changePosition(panel.getCircles(), circle);

            new Thread(circle).start();
        }
    }

    public void changePosition(List<Circle> circles, Circle circle) {
        for (int i = 0; i < circles.size(); i++) {
            Circle otherCircle = circles.get(i);
            boolean IsIntersectionCircle = circle.x + circle.diameter > otherCircle.x + otherCircle.diameter || circle.x + circle.diameter < otherCircle.x + otherCircle.diameter;

            if (IsIntersectionCircle) circle.x += circle.diameter;
            if (circle.x > Panel.PANEL_WIDTH) circle.x -= circle.diameter;
            if (circle.y < 0) circle.x += circle.diameter;
            if (circle.x < 0) circle.x += circle.diameter;
            if (circle.y > Panel.PANEL_HEIGHT) circle.y -= circle.diameter;
        }
    }
}
