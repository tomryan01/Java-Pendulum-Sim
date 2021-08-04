package com.tomryan01.pendulum.control;

import com.tomryan01.pendulum.control.model.equation.MultipleValueMotionEquation;
import com.tomryan01.pendulum.control.model.PendulumController;
import com.tomryan01.pendulum.control.model.System;
import com.tomryan01.pendulum.draw.component.model.ComponentDrawer;
import com.tomryan01.pendulum.math.VectorFunctions;
import com.tomryan01.pendulum.simulation.SimulationParameters;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class DoubleSimplePendulumController extends PendulumController<MultipleValueMotionEquation> {

    List<System> systems;
    float A = 0.33f;
    float B = 1.33f;
    float C = 0.25f;
    float D = 1.5f;
    float E = 0.5f;

    public DoubleSimplePendulumController(List<System> system, Map<System, ComponentDrawer> drawer){
        super(drawer);

        this.systems = system;

        motionEquation.put(system.get(0), (List<Float> param) -> {
            float pos_1 = param.get(0);
            float pos_2 = param.get(1);
            float vel_1 = param.get(2);
            float vel_2 = param.get(3);

            //float top = (float) ((-1f * C * Math.sin(pos_1 - pos_2) * (B * Math.pow(vel_2, 2) + C * Math.pow(vel_1, 2) * Math.cos(pos_1 - pos_2)) - B*D * Math.sin(pos_1) + C*E * Math.sin(pos_2)*Math.cos(pos_1 - pos_2)));
            float a = (float) (-1f * C * Math.sin(pos_1 - pos_2));
            float b = (float) (B * Math.pow(vel_2, 2));
            float c = (float) (C * Math.pow(vel_1, 2) * Math.cos(pos_1 - pos_2));
            float d = (float) (B*D * Math.sin(pos_1));
            float e = (float) (C*E * Math.sin(pos_2)*Math.cos(pos_1 - pos_2));
            float top = a * (b + c) - d + e;
            float bottom = (float) ((A*B - C*C * Math.pow(Math.cos(pos_1-pos_2), 2)));

            /*
            java.lang.System.out.println("a: " + a);
            java.lang.System.out.println("b: " + b);
            java.lang.System.out.println("c: " + c);
            java.lang.System.out.println("d: " + d);
            java.lang.System.out.println("e: " + e);
            */

            return  top / bottom;
        });
        motionEquation.put(system.get(1), (List<Float> param) -> {
            float pos_1 = param.get(0);
            float pos_2 = param.get(1);
            float vel_1 = param.get(2);
            float vel_2 = param.get(3);

            return (float) ((C * Math.sin(pos_1 - pos_2) * (A * Math.pow(vel_1, 2) + C * Math.pow(vel_2, 2) * Math.cos(pos_1 - pos_2)) - A*E * Math.sin(pos_2) + C*D * Math.sin(pos_1)*Math.cos(pos_1 - pos_2)) / (A*B - C*C * Math.pow(Math.cos(pos_1-pos_2), 2)));
        });

    }

    MultipleValueMotionEquation equation_1 = (List<Float> param) -> {
        float pos_1 = param.get(0);
        float pos_2 = param.get(1);
        float vel_1 = param.get(2);
        float vel_2 = param.get(3);

        return (float) ((-1f * C * Math.sin(pos_1 - pos_2) * (B * Math.pow(vel_2, 2) + C * Math.pow(vel_1, 2) * Math.cos(pos_1 - pos_2)) - B*D * Math.sin(pos_1) + C*E * Math.sin(pos_2)*Math.cos(pos_1 - pos_2)) / (A*B - C*C * Math.pow(Math.cos(pos_1-pos_2), 2)));
    };

    MultipleValueMotionEquation equation_2 = (List<Float> param) -> {
        float pos_1 = param.get(0);
        float pos_2 = param.get(1);
        float vel_1 = param.get(2);
        float vel_2 = param.get(3);

        return (float) ((C * Math.sin(pos_1 - pos_2) * (A * Math.pow(vel_1, 2) + C * Math.pow(vel_2, 2) * Math.cos(pos_1 - pos_2)) - A*E * Math.sin(pos_2) + C*D * Math.sin(pos_1)*Math.cos(pos_1 - pos_2)) / (A*B - C*C * Math.pow(Math.cos(pos_1-pos_2), 2)));
    };

    Vector<Float> kCalc(Vector<Float> y){
        Vector<Float> k = new Vector<>();

        k.add(y.get(2));
        k.add(y.get(3));
        systems.forEach((s) -> {
            k.add(motionEquation.get(s).equation(y));
        });

        return k;
    }

    //R4K integrator
    @Override
    protected void integrator(){
        Vector<Float> k_1;
        Vector<Float> k_2;
        Vector<Float> k_3;
        Vector<Float> k_4;

        Vector<Float> y = new Vector<>();
        Vector<Float> y_dot = new Vector<>();

        //setup y vector
        systems.forEach((s) -> {
            y.add(pos.get(s));
        });
        systems.forEach((s) -> {
            y.add(vel.get(s));
        });

        k_1 = kCalc(y);
        k_2 = kCalc(VectorFunctions.add(y, VectorFunctions.multiplyByFloat(k_1, SimulationParameters.GLOBAL_DELAY/2000f)));
        k_3 = kCalc(VectorFunctions.add(y, VectorFunctions.multiplyByFloat(k_2, SimulationParameters.GLOBAL_DELAY/2000f)));
        k_4 = kCalc(VectorFunctions.add(y, VectorFunctions.multiplyByFloat(k_3, SimulationParameters.GLOBAL_DELAY/2000f)));

        Vector<Float> new_y = VectorFunctions.add(
                y,
                VectorFunctions.multiplyByFloat(
                        VectorFunctions.add(
                            VectorFunctions.add(k_1, VectorFunctions.multiplyByFloat(k_2, 2)),
                            VectorFunctions.add(VectorFunctions.multiplyByFloat(k_3, 2), k_4)
                        ),
                        0.1667f * SimulationParameters.GLOBAL_DELAY/1000f
                )
        );

        systems.forEach((s) -> {
            pos.put(s, new_y.get(systems.indexOf(s)));
            vel.put(s, new_y.get(systems.indexOf(s) + 2));
        });
    }

}