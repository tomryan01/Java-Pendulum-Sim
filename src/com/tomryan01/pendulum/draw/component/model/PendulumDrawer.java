package com.tomryan01.pendulum.draw.component.model;

import com.tomryan01.pendulum.simulation.SimulationParameters;

import java.awt.*;

public abstract class PendulumDrawer extends ComponentDrawer {

    protected int posX;
    protected int posY;
    protected float width;

    public PendulumDrawer(){
        width = 8f;
    }

    public void drawRod(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Stroke stroke = new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

        g2d.setColor(Color.black);
        g2d.setStroke(stroke);
        g2d.drawLine(SimulationParameters.SCREEN_WIDTH / 2, SimulationParameters.PENDULUM_INITIAL_HEIGHT, posX, posY);
    }

    @Override
    public abstract void draw(Graphics g);

    @Override
    public void updatePos(int x, int y){
        posX = x;
        posY = y;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        draw(g);
    }
}
