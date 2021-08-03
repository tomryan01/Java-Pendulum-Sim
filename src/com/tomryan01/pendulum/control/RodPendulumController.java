package com.tomryan01.pendulum.control;

import com.tomryan01.pendulum.control.model.PendulumController;
import com.tomryan01.pendulum.draw.component.RodPendulumDrawer;
import com.tomryan01.pendulum.simulation.SimulationParameters;

public class RodPendulumController extends PendulumController {

    public RodPendulumController(RodPendulumDrawer drawer) {
        super(drawer);
        freq = (float) (SimulationParameters.NAT_FREQUENCY * Math.sqrt(2f/3f));
    }
}
