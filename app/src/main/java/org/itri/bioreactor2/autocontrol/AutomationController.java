package org.itri.bioreactor2.autocontrol;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import org.itri.bioreactor2.autocontrol.component.Script;
import org.itri.bioreactor2.autocontrol.component.step;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by A20356 on 2017/3/14.
 */

public class AutomationController  {
    public Context context;
    private String fileTitle;
    private static AutomationController sInstance;
    private ArrayList<step> StepList;
    private Object key;
    private step Currentstep;
    private String goalValue;
    private pumpAutomator pump;
    private pHAutomator pHAutomator;
    private oxygenAutomator oxygenAutomator;
    private int j = 0;

    public static AutomationController getInstance(Context context, String fileTitle) throws FileNotFoundException {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new AutomationController(context.getApplicationContext(), fileTitle);
        }
        return sInstance;
    }


    private AutomationController(Context context, String fileTitle) throws FileNotFoundException {
        this.context = context;
        this.fileTitle = fileTitle;
        Script st = new Script(context, fileTitle);
        st.readScrip();
        Currentstep = st.getCurrentStep(j++);
        StepList = st.list();
        //autoControl(StepList);
        autoSetto();    //Setto
        autoEndIf(StepList);    //Endif
    }
/*
    private void autoControl(ArrayList<step> StepList){
        this.StepList = StepList;
        Currentstep = StepList.get(j++);
        autoSetto();    //Setto
        autoEndIf(StepList);    //Endif
    }
*/
    private void autoSetto(){

        for(Enumeration<Object> v = Currentstep.getStepSetTo().keys(); v.hasMoreElements();){
            //key =  Currentstep.setTo.keys();
            //Log.d("TEST",Currentstep.setTo.get(key.toString()).toString());
            key = v.nextElement();
            if(key.toString().contains("Pump1")) {
                goalValue = Currentstep.getStepSetTo().get(key.toString()).toString();
                if(pump == null) {
                    pump = new pumpAutomator(context, key.toString(), goalValue);
                }
                else{
                    pump.updateGoalValue(goalValue);
                }
            }
            else if(key.toString().contains("pH")){
                goalValue = Currentstep.getStepSetTo().get(key.toString()).toString();
                if(pHAutomator == null) {
                    pHAutomator = new pHAutomator(context, key.toString(), goalValue);
                }else{
                    pHAutomator.updateGoalValue(goalValue);
                }
            }
            else if(key.toString().contains("DO")){
                goalValue = Currentstep.getStepSetTo().get(key.toString()).toString();
                if(oxygenAutomator == null) {
                    oxygenAutomator = new oxygenAutomator(context, key.toString(), goalValue);
                }else{
                    oxygenAutomator.updateGoalValue(goalValue);
                }

            }
        }
    }

    private void autoEndIf(final ArrayList<step> StepList){
        Long CountDownValue;
        this.StepList = StepList;
//        final Iterator i = StepList.iterator();

        for(Enumeration<Object> y = Currentstep.getStepEndIf().keys(); y.hasMoreElements();){
            //key = Currentstep.endIf.keys().nextElement();
            key = y.nextElement();
            if(key.toString().contains("TIME")) {
                goalValue = Currentstep.getStepEndIf().get(key.toString()).toString();
                CountDownValue = Long.parseLong(goalValue);
                CountDownTimer EndCountDownTimer = new CountDownTimer( CountDownValue,1000 ) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //Toast.makeText(context,"seconds remaining:"+millisUntilFinished/1000, Toast.LENGTH_SHORT).show();
                        Log.d("AUTOTEST", "seconds remaining:"+millisUntilFinished/1000 );
                    }
                    @Override
                    public void onFinish(){
                        //Toast.makeText(context,"CURRENT STEP DONE!", Toast.LENGTH_LONG).show();
                        Log.d("AUTOTEST", "CURRENT STEP DONE!");
                        //if(i.hasNext()){
                        if( j< StepList.size()){
                            //Currentstep = (step) i.next();
                            Currentstep = StepList.get(j);
                            autoSetto();    //Setto
                            autoEndIf(StepList);    //Endif
                            j = j+1;
                        }
                        else{
                            //Toast.makeText(context,"ALL STEP COMPLETE!", Toast.LENGTH_LONG).show();//
                            Log.d("AUTOTEST", "ALL STEP COMPLETE!");
                            if(pump != null){
                                pump.change_running_status(false);
                            }
                            if(pHAutomator != null) {
                                pHAutomator.change_running_status(false);
                            }
                            if(oxygenAutomator != null){
                                oxygenAutomator.change_running_status(false);
                            }
                        }
                    }
                }.start();
                //EndCountDownTimer.cancel();
            }
            else if(key.toString().contains("pH")){
                //goalValue = Currentstep.endIf.get(key.toString()).toString();
                //pHAutomator pHAutomator = new pHAutomator(context, goalValue);
            }
            else if(key.toString().contains("DO")){
                //goalValue = Currentstep.endIf.get(key.toString()).toString();
                //oxygenAutomator oxygenAutomator = new oxygenAutomator(context, goalValue);
            }
        }
    }
}
