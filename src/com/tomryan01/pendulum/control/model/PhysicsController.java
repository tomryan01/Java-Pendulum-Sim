package com.tomryan01.pendulum.control.model;

import com.tomryan01.pendulum.simulation.SimulationParameters;
import com.tomryan01.pendulum.draw.component.model.ComponentDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class PhysicsController<T> extends JComponent {

    protected Map<System, Float> pos = new HashMap<>();
    protected Map<System, Float> vel = new HashMap<>();
    protected Map<System, ComponentDrawer> drawer;
    protected Map<System, T> motionEquation = new HashMap<>();

    public PhysicsController(Map<System, ComponentDrawer> drawer){
        this.drawer = drawer;

        drawer.forEach((key, value) -> {
            pos.put(key, 0f);
            vel.put(key, SimulationParameters.START_ANGLE);
        });
    };

    public abstract void update();

    public abstract void reset();

    protected abstract void integrator();

    public abstract Map<System, Float> getPos();

    public abstract float getPos(System system);

    public abstract void updatePositionOnly(Map<System, Float> newPos);

}
