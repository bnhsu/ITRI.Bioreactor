package org.itri.bioreactor2.model;

import android.util.Log;

import org.itri.bioreactor2.controller.BioreactorController;

import java.nio.charset.Charset;

/**
 * Created by brianhsu on 2017/3/7.
 */

public class StepMoter extends actuator{


    public StepMoter(BioreactorController reactor) {
        super(reactor);
        Log.d("Test","***********create StepMoter ");
    }

    public void flipdown(){
        Log.d("Test","moter increase");
        setStepMotorAngle(180);
    }


    public void flipup() {
        setStepMotorAngle(-180);
    }

    public void StepMotorPositiveRotation(){
        String PositiveRotate = cmdTable.get("STEPMOTOR_P_R");
        reactor.sendBioreactorCommand(PositiveRotate.getBytes(Charset.forName("US-ASCII")));
    }

    public void StepMotorNegitiveRotation(){
        String NegitiveRotate = cmdTable.get("STEPMOTOR_N_R");
        reactor.sendBioreactorCommand(NegitiveRotate.getBytes(Charset.forName("US-ASCII")));

    }

    public void stop(){
        String Stop = cmdTable.get("STEPMOTOR_STOP");
        reactor.sendBioreactorCommand(Stop.getBytes(Charset.forName("US-ASCII")));
    }



    private void setStepMotorAngle(int angel){
        if (angel >=-999 && angel <= 999) {
            String cmd = "";
            if(angel<0) {
                cmd = cmdTable.get("STEPMOTOR") + String.format("-%03d", Math.abs(angel));
            }else {
                cmd = cmdTable.get("STEPMOTOR") + String.format("%03d", angel);
            }
            if(cmd!=null) {
                Log.d("Test", "set Step Motor angel value:" + angel + " cmd=" + cmd);
                reactor.sendBioreactorCommand(cmd.getBytes(Charset.forName("US-ASCII")));
                currentValue = angel;
            }
        }
    }
}
