package com.tomryan01.pendulum.simulation;
import com.tomryan01.pendulum.control.DoubleSimplePendulumController;
import com.tomryan01.pendulum.control.RodPendulumController;
import com.tomryan01.pendulum.control.SimplePendulumController;
import com.tomryan01.pendulum.control.model.PendulumController;
import com.tomryan01.pendulum.control.model.System;
import com.tomryan01.pendulum.draw.component.RodPendulumDrawer;
import com.tomryan01.pendulum.draw.component.SimplePendulumDrawer;
import com.tomryan01.pendulum.draw.component.model.ComponentDrawer;
import jdk.dynalink.DynamicLinker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationController implements ActionListener {

    private PendulumController pendulumController;

    private Timer timer;
    private JButton startButton;
    private JButton simplePendulumButton;
    private JButton rodPendulumButton;
    private JLabel posLabel;
    private JSlider startAngleSlider;
    private String pendulumType = "simple";
    private Boolean start = false;
    private JFrame f;

    private System simplePendulumSystem = new System(1);
    private System rodPendulumSystem = new System(1);
    private System firstDoubleSimplePendulumSystem = new System(1);
    private System secondDoubleSimplePendulumSystem = new System(2);
    private List<System> currentSystems;

    private Map<System, ComponentDrawer> simplePendulumDrawerMap = new HashMap<>();
    private Map<System, ComponentDrawer> rodPendulumDrawerMap = new HashMap<>();
    private Map<System, ComponentDrawer> doubleSimplePendulumDrawerMap = new HashMap<>();

    private SimplePendulumController simplePendulumController;
    private RodPendulumController rodPendulumController;
    private DoubleSimplePendulumController doubleSimplePendulumController;

    public SimulationController() {
        //Create main frame
        f = new JFrame();

        //Set up system -> drawer map
        simplePendulumDrawerMap.put(simplePendulumSystem, new SimplePendulumDrawer());
        rodPendulumDrawerMap.put(rodPendulumSystem, new RodPendulumDrawer());
        doubleSimplePendulumDrawerMap.put(firstDoubleSimplePendulumSystem, new SimplePendulumDrawer());
        doubleSimplePendulumDrawerMap.put(secondDoubleSimplePendulumSystem, new SimplePendulumDrawer());

        //Set up pendulum controllers
        simplePendulumController = new SimplePendulumController(simplePendulumSystem, simplePendulumDrawerMap);
        rodPendulumController = new RodPendulumController(rodPendulumSystem, rodPendulumDrawerMap);
        doubleSimplePendulumController = new DoubleSimplePendulumController(List.of(firstDoubleSimplePendulumSystem, secondDoubleSimplePendulumSystem), doubleSimplePendulumDrawerMap);

        //initiate initial controller
        pendulumController = simplePendulumController;
        currentSystems = List.of(simplePendulumSystem);
        pendulumController.setBounds(0, 0, SimulationParameters.SCREEN_WIDTH, SimulationParameters.SCREEN_HEIGHT);

        //create a start/stop button
        startButton = new JButton("Start");
        startButton.setBounds(SimulationParameters.WINDOW_WIDTH / 2 - 150/2, (SimulationParameters.SCREEN_HEIGHT + SimulationParameters.WINDOW_HEIGHT) / 2, 150, 50);
        startButton.addActionListener(this);

        //create button to switch to simple pendulum
        simplePendulumButton = new JButton("Simple");
        simplePendulumButton.setBounds(SimulationParameters.WINDOW_WIDTH / 2 - 150/2 - 300, (SimulationParameters.SCREEN_HEIGHT + SimulationParameters.WINDOW_HEIGHT) / 2 - 100, 150, 40);
        simplePendulumButton.addActionListener(this);

        //create button to switch to simple pendulum
        rodPendulumButton = new JButton("Rod");
        rodPendulumButton.setBounds(SimulationParameters.WINDOW_WIDTH / 2 - 150/2 - 300, (SimulationParameters.SCREEN_HEIGHT + SimulationParameters.WINDOW_HEIGHT) / 2 + 100, 150, 40);
        rodPendulumButton.addActionListener(this);

        //create a label to display angle
        posLabel = new JLabel("Angle: " +  SimulationParameters.START_ANGLE);
        posLabel.setBounds(SimulationParameters.WINDOW_WIDTH / 2 - 75/2, (SimulationParameters.SCREEN_HEIGHT + SimulationParameters.WINDOW_HEIGHT) / 2 - 125, 75, 50);

        //create a slider to set start angle
        startAngleSlider = new JSlider(JSlider.HORIZONTAL, -314, 314, (int) SimulationParameters.START_ANGLE * 100);
        startAngleSlider.setBounds(SimulationParameters.WINDOW_WIDTH / 2 - 200/2, (SimulationParameters.SCREEN_HEIGHT + SimulationParameters.WINDOW_HEIGHT) / 2 + 125, 200, 50);

        //setup timer
        timer = new Timer(SimulationParameters.GLOBAL_DELAY, this);
        timer.start();

        //setup frame
        f.setSize(SimulationParameters.WINDOW_WIDTH, SimulationParameters.WINDOW_HEIGHT);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(startButton);
        f.add(pendulumController);
        f.add(posLabel);
        f.add(startAngleSlider);
        f.add(simplePendulumButton);
        f.add(rodPendulumButton);
        f.setLayout(null);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == timer){
            //System.out.println(pendulumController.freq);
            timer.start();
            if(start) {
                //this is the standard screen update that occurs every tick
                pendulumController.update();
                posLabel.setText(String.valueOf(pendulumController.getPos(currentSystems.get(0))));
            } else{
                //update start position of pendulum due to slider
                //TODO: Change
                pendulumController.updatePositionOnly(mapFromSystems(currentSystems, List.of((float) startAngleSlider.getValue() / 100)));
            }
        } else if(e.getSource() == startButton){
            start = !start;
            if(start){
                startButton.setText("Stop");
                pendulumController.reset();
            } else{
                startButton.setText("Start");
                pendulumController.reset();
            }
        } else if(e.getSource() == simplePendulumButton){
            if(!pendulumType.equals("simple")){
                pendulumType = "simple";
                updatePendulum();
                pendulumController.update();
            }
        } else if(e.getSource() == rodPendulumButton){
            if(!pendulumType.equals("rod")){
                pendulumType = "rod";
                updatePendulum();
                pendulumController.update();
            }
        }
    }

    //Updates all necessary attributes when the type of pendulum is changed
    private void updatePendulum() {
        f.remove(pendulumController);
        switch(pendulumType){
            case "rod":
                pendulumController = rodPendulumController;
                currentSystems = List.of(rodPendulumSystem);
                break;
            default:
                pendulumController = simplePendulumController;
                currentSystems = List.of(simplePendulumSystem);
                break;
        }
        pendulumController.reset();
        pendulumController.setBounds(0, 0, SimulationParameters.SCREEN_WIDTH, SimulationParameters.SCREEN_HEIGHT);
        f.add(pendulumController);
        //TODO: Change
        pendulumController.updatePositionOnly(mapFromSystems(currentSystems, List.of((float) startAngleSlider.getValue() / 100)));
    }

    //TODO: Move to new class
    private Map<System, Float> mapFromSystems(List<System> system, List<Float> value){
        HashMap map = new HashMap<System, Float>();
        system.forEach((element) -> {
            map.put(element, value.get(system.indexOf(element)));
        });
        return map;
    }
}

