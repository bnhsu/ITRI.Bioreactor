package org.itri.bioreactor2.autocontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.itri.bioreactor2.controller.BioreactorController;

/**
 * Created by norman on 2017/2/14.
 */
public abstract class Automator {
    String listenTo = "";
    String goalValue;
    boolean running_status;
    public Context context;
    public BioreactorController reactor;

    public Automator(Context context, String listenTo, String goalValue) {
        this.goalValue = goalValue;
        this.listenTo = listenTo;
        this.context = context;
        this.reactor = BioreactorController.getInstance(context);
        running_status = true;
        //Log.d("Test2","listenTo: "+listenTo);
        context.registerReceiver(new Automator.DataParserBroadcastReceiver(),new IntentFilter("org.itri.bioreactor.DATA_PARSED." + listenTo));
        //Log.d("Test2","registerReceiver triggered!"+listenTo);
    }

    public class DataParserBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            //Log.d("Test2","onReceive triggered!"+listenTo);
            try {
                if(running_status) {
                    if (intent.getStringExtra(listenTo) != null) {
                        String strval = intent.getStringExtra(listenTo);
                        handler.obtainMessage(0, strval).sendToTarget();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void updateGoalValue(String goalValue){
        this.goalValue = goalValue;
    }

    public void change_running_status(boolean status){
        running_status = status;
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            String sensorValue = (String)msg.obj;
            Log.d("Test2", "goalValue:" + goalValue);
            performFeedback(sensorValue, goalValue);
            Log.d("Test2","onReceive Done in Automator" );
        }
    };

    public abstract void performFeedback(String currentValue, String goalValue);




}
