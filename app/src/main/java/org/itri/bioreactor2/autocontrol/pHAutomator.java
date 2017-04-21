package org.itri.bioreactor2.autocontrol;

import android.content.Context;

import org.itri.bioreactor2.data.database.CurrentValueTable;
import org.itri.bioreactor2.model.stirrer;

/**
 * Created by norman on 2017/2/17.
 */

public class pHAutomator extends Automator {
    static stirrer stir;
    static CurrentValueTable CurrentValueTable;
    public pHAutomator(Context context, String listenTo, String goalValue) {
        super(context ,listenTo, goalValue);
        this.listenTo = "pH";

        stir = new stirrer(this.reactor);
    }

    public void performFeedback(String currentValue, String goalValue){
        //Log.d("Test2","performFeedback call" );
        float currentPH = Float.valueOf(currentValue);
        float targetPH = Float.valueOf(goalValue);
        //Log.d("TEST1", "goalValue:" + goalValue);
        CurrentValueTable = CurrentValueTable.getInstance();
        stir.updateCurrentValue(CurrentValueTable.getCurrentStirRPM());
        if(currentPH<targetPH){
            stir.increase();
        }else{
            stir.decrease();
        }

    }

}
