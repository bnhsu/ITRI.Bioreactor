package org.itri.bioreactor2.autocontrol;

import android.content.Context;

import org.itri.bioreactor2.model.stirrer;

/**
 * Created by norman on 2017/2/17.
 */

public class oxygenAutomator extends Automator {
    stirrer stir;
    public oxygenAutomator(Context context, String listenTo, String goalValue) {
        super(context,listenTo, goalValue);
        this.listenTo = "DO";
        stir = new stirrer(this.reactor);
    }

    public void performFeedback(String currentValue, String goalValue){
        float currentDO = Float.valueOf(currentValue);
        float targetDO = Float.valueOf(goalValue);
        if(currentDO<targetDO){
            stir.increase();
        }else{
            stir.decrease();
        }
    }
}
