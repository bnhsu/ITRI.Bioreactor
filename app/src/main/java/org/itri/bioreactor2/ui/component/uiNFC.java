package org.itri.bioreactor2.ui.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by hsubrian on 2017/3/6.
 */

public class uiNFC {
    String listenToKey;

    public uiNFC(Context context, String listenToKey){
        init(context, listenToKey);
    }

    private void init(Context context, String listenToKey){

        context.registerReceiver(new uiNFC.DataParserBroadcastReceiver(), new IntentFilter("org.itri.bioreactor.DATA_PARSED." + listenToKey));
        this.listenToKey = listenToKey;

    }

    public class DataParserBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("test", "***DataParserBroadcastReceiver:" + listenToKey + ":" + intent.getStringExtra(listenToKey));

            try {
                if (intent.getStringExtra(listenToKey).contains("NEW")) {
                    Log.d("test", "NEW" +listenToKey);
                    Toast.makeText(context,"New Bottle Detected! System Calibration Start...", Toast.LENGTH_LONG).show();

                }
                else if(intent.getStringExtra(listenToKey).contains("OK")) {
                    Log.d("test", "OK" + listenToKey);
                    Toast.makeText(context,"Calibration Completed.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d("test", "DataParserBroadcastReceiver" + listenToKey);
                e.printStackTrace();
            }
        }
    }
}
