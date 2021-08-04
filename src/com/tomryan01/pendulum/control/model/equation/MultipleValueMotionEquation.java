package com.tomryan01.pendulum.control.model.equation;

import java.util.List;

public interface MultipleValueMotionEquation extends MotionEquation<List<Float>> {

    @Override
    float equation(List<Float> param);
}
