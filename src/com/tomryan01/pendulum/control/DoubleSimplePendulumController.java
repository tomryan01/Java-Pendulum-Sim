package com.tomryan01.pendulum.control;

import com.tomryan01.pendulum.control.model.PendulumController;
import com.tomryan01.pendulum.control.model.System;
import com.tomryan01.pendulum.draw.component.model.ComponentDrawer;
import com.tomryan01.pendulum.simulation.SimulationParameters;

import java.util.List;
import java.util.Map;

public class DoubleSimplePendulumController extends SimplePendulumController {

    public DoubleSimplePendulumController(List<System> system, Map<System, ComponentDrawer> drawer){
        super(system, drawer);
    }

    @Override
    public void update(){
        pos.forEach((key, value) -> {
            velocityVerlet();

            //TODO: This is a little verbose, fix it
            if(key.getKey() == 2){
                drawer.get(key).updateRootPos(newX, newY);
            }

            newX = (int) Math.round((SimulationParameters.SCREEN_WIDTH / 2f) + (SimulationParameters.PENDULUM_LENGTH*Math.sin(pos.get(key))));
            newY = (int) Math.round((SimulationParameters.PENDULUM_INITIAL_HEIGHT) + (SimulationParameters.PENDULUM_LENGTH*Math.cos(pos.get(key))));

            drawer.get(key).updatePos(newX, newY);

            repaint();
        });
    }

    @Override
    public void updatePositionOnly(Map<System, Float> angle){
        pos.forEach((key, value) -> {
            pos.put(key, angle.get(key));

            //TODO: This is a little verbose, fix it
            if(key.getKey() == 2){
                drawer.get(key).updateRootPos(newX, newY);
            }

            newX = (int) Math.round((SimulationParameters.SCREEN_WIDTH / 2f) + (SimulationParameters.PENDULUM_LENGTH*Math.sin(pos.get(key))));
            newY = (int) Math.round((SimulationParameters.PENDULUM_INITIAL_HEIGHT) + (SimulationParameters.PENDULUM_LENGTH*Math.cos(pos.get(key))));

            drawer.get(key).updatePos(newX, newY);
        });

        repaint();
    }


}