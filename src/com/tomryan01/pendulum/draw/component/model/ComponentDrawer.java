package com.tomryan01.pendulum.draw.component.model;

import com.tomryan01.pendulum.simulation.SimulationParameters;

import javax.swing.*;
import java.awt.*;

public abstract class ComponentDrawer extends JComponent{

    /*
    public void paint(Graphics g){
        //g.setColor(Color.white);
        //g.fillRect(0,0, SimulationParameters.SCREEN_WIDTH, SimulationParameters.SCREEN_HEIGHT);
    }
    */

    abstract void draw(Graphics g);

    abstract public void updatePos(int x, int y);

    abstract public void updateRootPos(int x, int y);
}
