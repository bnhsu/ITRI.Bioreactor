package org.itri.bioreactor2.model;

import android.util.Log;

import org.itri.bioreactor2.controller.BioreactorController;

import java.nio.charset.Charset;

/**
 * Created by norman on 2017/1/6.
 */

public class HeatBlanket extends actuator{
    public HeatBlanket(BioreactorController reactor) {
        super(reactor);
        Log.d("Test","***********create Heat Jacket ");
    }

    public void HeatingOff(){
        setValue(0);
    }

    public void increase() {
        if(currentValue<10000) {
            if(currentValue<1000) {
                currentValue = 1000;
            }else {
                currentValue = currentValue + 1000;
            }
            if (currentValue > 10000) {
                currentValue = 10000;
            }
            int HeatingLevel = currentValue/1000;
            setValue(HeatingLevel);
        }
    }

    public void decrease() {
        if(currentValue>0) {
            currentValue = currentValue - 1000;
            if (currentValue < 1000) currentValue = 0;
            int HeatingLevel = currentValue/1000;
            setValue(HeatingLevel);
            Log.d("", "decrease HeatingLevel to " + HeatingLevel);
        }
    }

    public void setValue(int HeatingLevel){
        if (HeatingLevel >= 0 && HeatingLevel <= 10) {
            String cmd = cmdTable.get("Heating") + Integer.toString(HeatingLevel);
            if(cmd!=null) {
                Log.d("Test", "set HeatingLevel value:" + HeatingLevel + " cmd=" + cmd);
                reactor.sendBioreactorCommand(cmd.getBytes(Charset.forName("US-ASCII")));
                currentValue = HeatingLevel * 1000;
            }
        }
    }
}
