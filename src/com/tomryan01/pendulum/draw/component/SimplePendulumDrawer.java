package com.tomryan01.pendulum.draw.component;

import com.tomryan01.pendulum.draw.component.model.PendulumDrawer;

import java.awt.*;

public class SimplePendulumDrawer extends PendulumDrawer {

    public SimplePendulumDrawer(){
        width = 4f;
    }

    void drawBob(Graphics g, int x, int y, int r){
        Graphics2D g2d = (Graphics2D) g;
        x = x - r/2;
        y = y - r/2;
        g2d.fillOval(x, y, r, r);
    }

    @Override
    public void draw(Graphics g) {
        drawRod(g);
        drawBob(g, posX, posY, 40);
    }
}
