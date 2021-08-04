package com.tomryan01.pendulum.control.model.equation;

import java.util.List;

public interface MotionEquation<T> {

    float equation(T pos);

}
