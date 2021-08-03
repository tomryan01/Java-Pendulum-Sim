package com.tomryan01.pendulum.simulation;

public class SimulationParameters {

    public static int GLOBAL_DELAY = 8; //ms

    public static float PENDULUM_LENGTH = 150; //cm
    public static int SCREEN_HEIGHT = 400;
    public static int SCREEN_WIDTH = 900;
    public static int PENDULUM_INITIAL_HEIGHT = SCREEN_HEIGHT / 3;
    public static int WINDOW_WIDTH = 900;
    public static int WINDOW_HEIGHT = 800;
    public static float START_ANGLE = 1.57f;
    public static float GRAVITY = 9.81f;
    public static float NAT_FREQUENCY = (float) Math.sqrt(GRAVITY/(PENDULUM_LENGTH/100));



}
