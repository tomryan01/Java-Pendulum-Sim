package com.tomryan01.pendulum.control.model;

import com.tomryan01.pendulum.simulation.SimulationParameters;
import com.tomryan01.pendulum.draw.component.model.ComponentDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class PhysicsController extends JComponent {

    protected Map<System, Float> pos = new HashMap<>();
    protected Map<System, Float> vel = new HashMap<>();
    protected Map<System, ComponentDrawer> drawer;
    protected Map<System, MotionEquation> motionEquation = new HashMap<>();

    public PhysicsController(Map<System, ComponentDrawer> drawer){
        this.drawer = drawer;

        drawer.forEach((key, value) -> {
            pos.put(key, 0f);
            vel.put(key, SimulationParameters.START_ANGLE);
        });
    };

    public abstract void update();

    public abstract void reset();

    public abstract Map<System, Float> getPos();

    public abstract float getPos(System system);

    public abstract void updatePositionOnly(Map<System, Float> newPos);

    public void velocityVerlet(){

        pos.forEach((key, value) -> {
            float new_pos = (float) (pos.get(key) + vel.get(key) * SimulationParameters.GLOBAL_DELAY/1000 + 0.5f * motionEquation.get(key).equation(pos.get(key)) * Math.pow(SimulationParameters.GLOBAL_DELAY/1000f, 2));
            float new_accel = motionEquation.get(key).equation(new_pos);
            vel.put(key, vel.get(key) + 0.5f * (motionEquation.get(key).equation(pos.get(key)) + new_accel) * SimulationParameters.GLOBAL_DELAY/1000);
            pos.put(key, new_pos);
        });
    }

}
