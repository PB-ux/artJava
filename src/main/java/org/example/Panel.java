package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Panel extends JPanel implements KeyListener, ActionListener, MouseListener {
    static final int PANEL_WIDTH = 1000;
    static final int PANEL_HEIGHT = 600;

    final char KEY_LEFT = 'a';
    final char KEY_RIGHT = 'd';
    final char KEY_TOP = 'w';
    final char KEY_BOTTOM = 's';

    Timer timer;
    Point points;

    int xVelocity = 1;
    int yVelocity = 1;

    int x = 0;
    int y = 0;

    int widthBall = 50;
    int heightBall = 50;

    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    boolean isMoveTop = false;
    boolean isMoveBottom = false;

    boolean isMouseClick = false;
    boolean isKeyTyped = false;

    private List<Circle> circles;

    Panel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.lightGray);
        this.setFocusable(true);
        this.requestFocus();
        timer = new Timer(10, this);
        timer.start();
        this.addKeyListener(this);
        this.addMouseListener(this);
        circles = new ArrayList<>();
    }

    public void addCircle(Circle circle) {
        circles.add(circle);
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public void paint(Graphics g) {
        super.paint(g);

        for (Circle circle : circles) {
            circle.draw(g);
        }

        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.red);
        g2D.fillOval(x, y, 50, 50);
    }
    
    public void checkCollision() {
        if (x + widthBall > PANEL_WIDTH) {
            isMoveRight = false;
            isMoveLeft = true;
        }
        if (x < 0) {
            isMoveRight = true;
            isMoveLeft = false;
        }
        if (y + heightBall > PANEL_HEIGHT) {
            isMoveTop = true;
            isMoveBottom = false;
        }
        if (y < 0) {
            isMoveBottom = true;
            isMoveTop = false;
        }
    }
    
    public void moveBall() {
        boolean isMouseMove = isMouseClick && !isKeyTyped;
        boolean isKeyboardMove = isKeyTyped && !isMouseClick;
        int coorX = x + (widthBall / 2);
        int coorY = y + (heightBall / 2);

        if (isMouseMove && coorX > points.x) {
            x -= xVelocity;
        } else if (isMouseMove && coorX < points.x) {
            x += xVelocity;
        }

        if (isMouseMove && coorY > points.y) {
            y -= yVelocity;
        } else if (isMouseMove && coorY < points.y) {
            y += yVelocity;
        }

        if (isKeyboardMove && isMoveRight) x += xVelocity;
        if (isKeyboardMove && isMoveLeft) x -= xVelocity;
        if (isKeyboardMove && isMoveTop) y -= yVelocity;
        if (isKeyboardMove && isMoveBottom) y += yVelocity;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        isKeyTyped = true; 
        isMouseClick = false;
        switch(e.getKeyChar()) {
            case KEY_LEFT:
                isMoveLeft = true;
                isMoveRight = false;
                isMoveTop = false;
                isMoveBottom = false;
                break;
            case KEY_RIGHT:
                isMoveRight = true;
                isMoveLeft = false;
                isMoveTop = false;
                isMoveBottom = false;
                break;
            case KEY_BOTTOM:
                isMoveBottom = true;
                isMoveLeft = false;
                isMoveRight = false;
                isMoveTop = false;
                break;
            case KEY_TOP:
                isMoveTop = true;
                isMoveLeft = false;
                isMoveRight = false;
                isMoveBottom = false;
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkCollision();
        moveBall();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        points = e.getPoint();
        isMouseClick = true;
        isKeyTyped = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
