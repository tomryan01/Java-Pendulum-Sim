package com.tomryan01.pendulum.control.model;

import com.tomryan01.pendulum.draw.component.model.ComponentDrawer;
import com.tomryan01.pendulum.draw.component.model.PendulumDrawer;
import com.tomryan01.pendulum.simulation.SimulationParameters;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class PendulumController<T> extends PhysicsController<T> {

    protected int newX;
    protected int newY;
    public float freq = SimulationParameters.NAT_FREQUENCY;

    public PendulumController(Map<System, ComponentDrawer> drawer){
        super(drawer);
    }

    @Override
    public Map<System, Float> getPos(){
        return pos;
    }

    @Override
    public float getPos(System system){ return pos.get(system); }

    @Override
    public void updatePositionOnly(Map<System, Float> angle){
        pos.forEach((key, value) -> {
            pos.put(key, angle.get(key));

            //TODO: This is a little verbose, fix it
            if(key.getKey() == 2){
                drawer.get(key).updateRootPos(newX, newY);

                newX = (int) Math.round(newX + (SimulationParameters.PENDULUM_LENGTH*Math.sin(pos.get(key))));
                newY = (int) Math.round(newY + (SimulationParameters.PENDULUM_LENGTH*Math.cos(pos.get(key))));
            } else {
                newX = (int) Math.round((SimulationParameters.SCREEN_WIDTH / 2f) + (SimulationParameters.PENDULUM_LENGTH * Math.sin(pos.get(key))));
                newY = (int) Math.round((SimulationParameters.PENDULUM_INITIAL_HEIGHT) + (SimulationParameters.PENDULUM_LENGTH * Math.cos(pos.get(key))));
            }

            drawer.get(key).updatePos(newX, newY);
        });

        repaint();
    }

    @Override
    public void paint(Graphics g){
        drawer.forEach((key, value) -> {
            drawer.get(key).paint(g);
        });
    }

    @Override
    public void update(){
        pos.forEach((key, value) -> {
            integrator();

            //TODO: This is a little verbose, fix it
            if(key.getKey() == 2){
                drawer.get(key).updateRootPos(newX, newY);

                newX = (int) Math.round(newX + (SimulationParameters.PENDULUM_LENGTH*Math.sin(pos.get(key))));
                newY = (int) Math.round(newY + (SimulationParameters.PENDULUM_LENGTH*Math.cos(pos.get(key))));
            } else {
                newX = (int) Math.round((SimulationParameters.SCREEN_WIDTH / 2f) + (SimulationParameters.PENDULUM_LENGTH*Math.sin(pos.get(key))));
                newY = (int) Math.round((SimulationParameters.PENDULUM_INITIAL_HEIGHT) + (SimulationParameters.PENDULUM_LENGTH*Math.cos(pos.get(key))));
            }

            drawer.get(key).updatePos(newX, newY);

            repaint();
        });
    }

    @Override
    public void reset(){
        vel.forEach((key, value) -> {
            vel.put(key, 0f);
        });
    }
}
