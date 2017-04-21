package org.itri.bioreactor2.model;

import android.util.Log;

import org.itri.bioreactor2.controller.BioreactorController;

import java.nio.charset.Charset;

/**
 * Created by norman on 2017/1/6.
 */

public class pump extends actuator{

    private String pumpID;
    public pump(BioreactorController reactor, String pumpID) {
        super(reactor);
        this.pumpID = pumpID;
        Log.d("Test","***********create " + pumpID);
    }

    public void increase() {
        if(currentValue<255) {
            currentValue = currentValue + 5;
            if (currentValue > 255) currentValue = 255;
            setPumpSpeed( currentValue);
            Log.d("", "increase pump speed to " + currentValue);
        }
    }

    public void longIncrease(){
        if(currentValue<255) {
            currentValue = currentValue + 5;
            if (currentValue > 255) currentValue = 255;
            setPumpSpeed( currentValue);
            Log.d("", "increase pump speed to " + currentValue);
        }
    }

    public void decrease() {
        if(currentValue>-255) {
            currentValue = currentValue - 5;
            if (currentValue <= -255) currentValue = -255;
            setPumpSpeed( currentValue);
            Log.d("", "decrease pump speed to " + currentValue);
        }
    }

    public void longDecrease(){
        if(currentValue>-255) {
            currentValue = currentValue - 5;
            if (currentValue <= -255) currentValue = -255;
            setPumpSpeed( currentValue);
            Log.d("", "decrease pump speed to " + currentValue);
        }
    }

    public void foreward(int speed){
        currentValue = speed;
        setPumpSpeed(currentValue);
    }

    public void stop(){
        currentValue = 0;
        setPumpSpeed(currentValue);
    }

    public void backward(int speed){
        currentValue = speed;
        setPumpSpeed(currentValue);

    }

    private void setPumpSpeed(int speed){
        if (speed >=-255 && speed <= 255) {
            String cmd = "";
            if(speed<0) {
                Log.d("test", "-" + pumpID );
                cmd = cmdTable.get(pumpID) + String.format("-%03d", Math.abs(speed));
            }else {
                Log.d("test", "+" + pumpID );
                cmd = cmdTable.get(pumpID) + String.format("%03d", speed);
            }
            if(cmd!=null) {
                Log.d("Test", "set pump speed value:" + speed + " cmd=" + cmd);
                reactor.sendBioreactorCommand(cmd.getBytes(Charset.forName("US-ASCII")));
                currentValue = speed;
            }
        }
    }
}
