package com.tomryan01.pendulum.control;

import com.tomryan01.pendulum.control.model.PendulumController;
import com.tomryan01.pendulum.control.model.System;
import com.tomryan01.pendulum.draw.component.RodPendulumDrawer;
import com.tomryan01.pendulum.draw.component.model.ComponentDrawer;
import com.tomryan01.pendulum.simulation.SimulationParameters;

import java.util.Map;

public class RodPendulumController extends PendulumController {

    public RodPendulumController(System system, Map<System, ComponentDrawer> drawer) {
        super(drawer);
        freq = (float) (SimulationParameters.NAT_FREQUENCY * Math.sqrt(2f/3f));
        motionEquation.put(system, (float pos) -> { return (float) (-1f * Math.pow(freq, 2f) * Math.sin(pos)); });
    }
}
