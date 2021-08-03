package com.tomryan01.pendulum.control.model;

import com.tomryan01.pendulum.simulation.SimulationParameters;
import com.tomryan01.pendulum.draw.component.model.ComponentDrawer;

import javax.swing.*;
import java.util.List;


public abstract class PhysicsController extends JComponent {

    protected float pos;
    protected float vel;
    protected ComponentDrawer drawer;
    protected MotionEquation motionEquation;

    public abstract void update();

    public abstract void reset();

    public abstract float getPos();

    public abstract void updatePositionOnly(float pos);

    public List<Float> velocityVerlet(float pos, float vel, MotionEquation motionEquation){
        float new_pos = (float) (pos + vel * SimulationParameters.GLOBAL_DELAY/1000 + 0.5f * motionEquation.equation(pos) * Math.pow(SimulationParameters.GLOBAL_DELAY/1000f, 2));
        float new_accel = motionEquation.equation(new_pos);
        vel = vel + 0.5f * (motionEquation.equation(pos) + new_accel) * SimulationParameters.GLOBAL_DELAY/1000;
        pos = new_pos;
        return List.of(pos, vel);
    }

}
