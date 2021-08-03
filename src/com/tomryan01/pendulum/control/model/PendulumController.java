package com.tomryan01.pendulum.control.model;

import com.tomryan01.pendulum.draw.component.model.PendulumDrawer;
import com.tomryan01.pendulum.simulation.SimulationParameters;

import java.awt.*;

public abstract class PendulumController extends PhysicsController {

    private int newX;
    private int newY;
    protected int length;
    public float freq;

    public PendulumController(PendulumDrawer pendulumDrawer){

        this.drawer = pendulumDrawer;
        pos = SimulationParameters.START_ANGLE; //pos for a pendulum is an angle
        length = (int) SimulationParameters.PENDULUM_LENGTH;
        freq = SimulationParameters.NAT_FREQUENCY;
        vel = 0; //vel for a pendulum is an angular velocity

        motionEquation = (float pos) -> { return (float) (-1f * Math.pow(freq, 2f) * Math.sin(pos)); };

    }

    @Override
    public float getPos(){
        return pos;
    }

    @Override
    public void updatePositionOnly(float angle){
        pos = angle;

        newX = (int) Math.round((SimulationParameters.SCREEN_WIDTH / 2f) + (SimulationParameters.PENDULUM_LENGTH*Math.sin(angle)));
        newY = (int) Math.round((SimulationParameters.PENDULUM_INITIAL_HEIGHT) + (SimulationParameters.PENDULUM_LENGTH*Math.cos(angle)));

        drawer.updatePos(newX, newY);
        repaint();
    }

    @Override
    public void paint(Graphics g){
        drawer.paint(g);
    }

    @Override
    public void update(){
        pos = velocityVerlet(pos, vel, motionEquation).get(0);
        vel = velocityVerlet(pos, vel, motionEquation).get(1);

        newX = (int) Math.round((SimulationParameters.SCREEN_WIDTH / 2f) + (SimulationParameters.PENDULUM_LENGTH*Math.sin(pos)));
        newY = (int) Math.round((SimulationParameters.PENDULUM_INITIAL_HEIGHT) + (SimulationParameters.PENDULUM_LENGTH*Math.cos(pos)));

        drawer.updatePos(newX, newY);
        repaint();
    }

    @Override
    public void reset(){
        vel = 0;
        pos = freq = SimulationParameters.NAT_FREQUENCY;
    }
}
