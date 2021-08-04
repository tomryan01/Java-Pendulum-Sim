package com.tomryan01.pendulum.control;

import com.tomryan01.pendulum.control.model.PendulumController;
import com.tomryan01.pendulum.control.model.equation.SingleValueMotionEquation;
import com.tomryan01.pendulum.control.model.System;
import com.tomryan01.pendulum.draw.component.model.ComponentDrawer;
import com.tomryan01.pendulum.simulation.SimulationParameters;

import java.util.Map;

public class RodPendulumController extends PendulumController<SingleValueMotionEquation> {

    public RodPendulumController(System system, Map<System, ComponentDrawer> drawer) {
        super(drawer);
        freq = (float) (SimulationParameters.NAT_FREQUENCY * Math.sqrt(2f/3f));
        motionEquation.put(system, (Float pos) -> { return (float) (-1f * Math.pow(freq, 2f) * Math.sin(pos)); });
    }

    @Override
    protected void integrator(){
        pos.forEach((key, value) -> {
            float new_pos = (float) (pos.get(key) + vel.get(key) * SimulationParameters.GLOBAL_DELAY/1000 + 0.5f * motionEquation.get(key).equation(pos.get(key)) * Math.pow(SimulationParameters.GLOBAL_DELAY/1000f, 2));
            float new_accel = motionEquation.get(key).equation(new_pos);
            vel.put(key, vel.get(key) + 0.5f * (motionEquation.get(key).equation(pos.get(key)) + new_accel) * SimulationParameters.GLOBAL_DELAY/1000);
            pos.put(key, new_pos);
        });
    }
}
