package org.itri.bioreactor2.autocontrol;

import android.content.Context;

import org.itri.bioreactor2.model.pump;

/**
 * Created by norman on 2017/2/17.
 */

public class pumpAutomator extends Automator {
    static pump pump;

    public pumpAutomator(Context context, String listenTo, String goalValue) {
        super(context, listenTo, goalValue);
        pump = new pump(this.reactor, "Pump1");
    }

    public void performFeedback(String currentValue, String goalValue){
        float currentRPM = Float.valueOf(currentValue);
        float targetRPM = Float.valueOf(goalValue);
        pump.updateCurrentValue((int)currentRPM);
        if(currentRPM<targetRPM){
            pump.increase();
        }else{
            pump.decrease();
        }
    }

    public static void pumpStop(){
        pump.stop();
    }
}
