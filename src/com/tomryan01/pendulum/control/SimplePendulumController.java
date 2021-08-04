package com.tomryan01.pendulum.control;

import com.tomryan01.pendulum.control.model.MotionEquation;
import com.tomryan01.pendulum.control.model.PendulumController;
import com.tomryan01.pendulum.control.model.System;
import com.tomryan01.pendulum.draw.component.SimplePendulumDrawer;
import com.tomryan01.pendulum.draw.component.model.ComponentDrawer;

import java.util.List;
import java.util.Map;

public class SimplePendulumController extends PendulumController {

    //used to create a simple pendulum
    public SimplePendulumController(System system, Map<System, ComponentDrawer> drawer){
        super(drawer);

        motionEquation.put(system, (float pos) -> { return (float) (-1f * Math.pow(freq, 2f) * Math.sin(pos)); });

    }

    //used by double pendulum to call super()
    protected SimplePendulumController(List<System> system, Map<System, ComponentDrawer> drawer){
        super(drawer);

        system.forEach((element) -> {
            motionEquation.put(element, (float pos) -> { return (float) (-1f * Math.pow(freq, 2f) * Math.sin(pos)); });
        });

    }
}
