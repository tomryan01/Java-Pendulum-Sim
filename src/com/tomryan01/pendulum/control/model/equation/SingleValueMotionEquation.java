package com.tomryan01.pendulum.control.model.equation;

import com.tomryan01.pendulum.control.model.equation.MotionEquation;

public interface SingleValueMotionEquation extends MotionEquation<Float> {

    @Override
    float equation(Float pos);
}
