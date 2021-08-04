package com.tomryan01.pendulum.math;

import java.util.Vector;

public class VectorFunctions {

    public static Vector<Float> multiplyByFloat(Vector<Float> v, float f){
        for(int i = 0; i < v.size(); i++){
            v.set(i, v.get(i) * f);
        }
        return v;
    }

    public static Vector<Float> add(Vector<Float> v1, Vector<Float> v2){
        assert(v1.size() == v2.size());
        for(int i = 0; i < v1.size(); i++){
            v1.set(i, v1.get(i) + v2.get(i));
        }
        return v1;
    }
}
