package com.tomryan01.pendulum.draw.component;

import com.tomryan01.pendulum.draw.component.model.PendulumDrawer;

import java.awt.*;

public class RodPendulumDrawer extends PendulumDrawer {
    public RodPendulumDrawer(){
        width = 16f;
    }

    @Override
    public void draw(Graphics g) {
        drawRod(g);
    }
}
