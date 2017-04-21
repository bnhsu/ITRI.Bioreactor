package org.itri.bioreactor2.model;

import android.util.Log;

import org.itri.bioreactor2.controller.BioreactorController;

import java.nio.charset.Charset;

/**
 * Created by norman on 2017/1/6.
 */

public class stirrer extends actuator{
    public stirrer(BioreactorController reactor) {
        super(reactor);
        Log.d("Test","***********create stirrer ");
    }

    public void stop(){
        setStirrerSpeed(0);
    }

    public void increase() {
        if(currentValue<1500) {
            if(currentValue<200) {
                currentValue = 200;
            }else {
                currentValue = currentValue + 100;
            }
            if (currentValue > 1500) currentValue = 1500;
            setStirrerSpeed(currentValue);
        }
    }

    public void decrease() {
        if(currentValue>0) {
            currentValue = currentValue - 100;
            if (currentValue < 200) currentValue = 0;
            setStirrerSpeed(currentValue);
            Log.d("", "decrease stirrer speed to " + currentValue);
        }
    }

    private void setStirrerSpeed(int speed){
        if (speed >= 0 && speed <= 1500) {
            String cmd = cmdTable.get("STIR" + Integer.toString(speed));
            if(cmd!=null) {
                Log.d("Test", "set Stirrer speed value:" + speed + " cmd=" + cmd);
                reactor.sendBioreactorCommand(cmd.getBytes(Charset.forName("US-ASCII")));
                currentValue = speed;
            }
        }
    }
}
