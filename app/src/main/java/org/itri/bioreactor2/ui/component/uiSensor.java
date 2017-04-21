package org.itri.bioreactor2.ui.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.data.database.CurrentValueTable;

/**
 * Created by norman on 2017/1/4.
 */
@SuppressWarnings("ResourceType")
public class uiSensor extends LinearLayout {
    String listenToKey, listenToErr;
    TextView textValue, textError;
    static CurrentValueTable CurrentValueTable;

    public uiSensor(Context context, String listenToKey, String listenToErr) {
        super(context);
        CurrentValueTable = CurrentValueTable.getInstance();
        init(context, listenToKey, listenToErr);
    }


    public uiSensor(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.node, 0, 0);
        try {
            init(context, ta.getString(0), ta.getString(1));
        } finally {
            ta.recycle();
        }
    }

    private void init(Context context, String listenToKey, String listenToErr) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.listenToKey = listenToKey;
        this.listenToErr = listenToErr;

        if(listenToKey.contains("pH")){
            inflater.inflate(R.layout.control_ph, this);
        }else if(listenToKey.contains("DO")){
            inflater.inflate(R.layout.control_do, this);
        }else if(listenToKey.contains("Temp")){
            inflater.inflate(R.layout.control_temp, this);
        }

        textValue = (TextView) findViewById(R.id.text_sensor_value);
        textError = (TextView) findViewById((R.id.text_sensor_Error));

        context.registerReceiver(new DataParserBroadcastReceiver(), new IntentFilter("org.itri.bioreactor.DATA_PARSED." + listenToKey));
        context.registerReceiver(new ErrorParserBroadcastReceiver(), new IntentFilter("org.itri.bioreactor.DATA_PARSED." + listenToErr));



    }

    public class DataParserBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getStringExtra(listenToKey) != null) {
                    textValue.setText(intent.getStringExtra(listenToKey));
                    float currentValue = Float.parseFloat(intent.getStringExtra(listenToKey));
                    if(listenToKey.contains("Temp")){
                        CurrentValueTable.setCurrentTmp(currentValue);
                        Log.d("activty","TEMP onReceive");
                    }else if(listenToKey.contains("pH")){
                        CurrentValueTable.setCurrentpH(currentValue);
                    }else if(listenToKey.contains("DO")){
                        CurrentValueTable.setCurrentDO(currentValue);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ErrorParserBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.d("Test","**** ErrParserRecever onReceived!" );
          //  Log.d("Test","**** ErrParserRecever onReceived!" + intent.getStringExtra(listenToErr));
            //int intErrValue = Integer.valueOf(intent.getStringExtra(listenToErr));
            try {
                switch (listenToErr) {
                    case "pHErr":

                        parseErrorMsg(intent.getStringExtra(listenToErr));
                        break;

                    case "DOErr":
                        parseErrorMsg(intent.getStringExtra(listenToErr));
                        break;
                }
            } catch (Exception e) {
                Log.d("Test","parseErrorMsg Exception " + e.getLocalizedMessage());
                textError.setVisibility(INVISIBLE);
                e.printStackTrace();
            }
        }
    }

    public void parseErrorMsg(String errCode){

    // Interpret the error code
        Log.d("Test","parseErrorMsg:" + errCode);
        int intErrCode = Integer.parseInt(errCode.replaceAll("[\\D]", ""));
        Log.d("Test","parseErrorMsg:" + intErrCode);
        int shift;
        String errMsg = new String();
        if(intErrCode != 0){
            for(shift=0; shift<=15; shift++) {

                if ((intErrCode & (1 << shift)) != 0) {
                    switch (shift) {
                        case 0:
                            //Log.d("Test","**** parserErrorMsg bit 0 onReceived!" );
                            errMsg = errMsg + "ADC 1 OVERFLOW\n";

                            break;
                        case 1:
                            //Log.d("Test","**** parserErrorMsg bit 1 onReceived!" );
                            errMsg = errMsg + "ADC 2 OVERFLOW\n";
                            break;

                        case 2:
                            // Log.d("Test","**** parserErrorMsg bit 2 onReceived!" );
                            errMsg = errMsg + "AMP TOO LOW\n";
                            break;

                        case 3:
                            //Log.d("Test","**** parserErrorMsg bit 3 onReceived! ------- Reserved" );
                            break;

                        case 4:
                            //Log.d("Test","**** parserErrorMsg bit 4 onReceived! ------- Reserved" );
                            break;

                        case 5:
                            //Log.d("Test","**** parserErrorMsg bit 5 onReceived!" );
                            errMsg = errMsg + "NO SENSOR CAL\n";
                            break;

                        case 6:
                            // Log.d("Test","**** parserErrorMsg bit 6 onReceived!" );
                            errMsg = errMsg + "REF PATH FAILURE\n";
                            break;

                        case 7:
                            //Log.d("Test","**** parserErrorMsg bit 7 onReceived! ------- Reserved" );
                            break;

                        case 8:
                            //Log.d("Test","**** parserErrorMsg bit 8 onReceived!" );
                            errMsg = errMsg + "FPGA INTERRUPT FAILURE\n";
                            break;

                        case 9:
                            //Log.d("Test","**** parserErrorMsg bit 9 onReceived!" );
                            errMsg = errMsg + "+ COUNTER OVERFLOW\n";
                            break;

                        case 10:
                            //Log.d("Test","**** parserErrorMsg bit 10 onReceived!" );
                            errMsg = errMsg + "+ COUNTER OVERFLOW\n";
                            break;

                        case 11:
                            //Log.d("Test","**** parserErrorMsg bit 11 onReceived!" );
                            errMsg = errMsg + "WDT RESET\n";
                            break;

                        case 12:
                            //Log.d("Test","**** parserErrorMsg bit 12 onReceived!" );
                            errMsg = errMsg + "SIG PHASE SHIFT\n";
                            break;

                        case 13:
                            //Log.d("Test","**** parserErrorMsg bit 13 onReceived!" );
                            errMsg = errMsg + "PC REG OVERFLOW\n";
                            break;

                        case 14:
                            //Log.d("Test","**** parserErrorMsg bit 14 onReceived! ------- Reserved" );
                            break;

                        case 15:
                            //Log.d("Test","**** parserErrorMsg bit 15 onReceived! ------- Reserved" );
                            break;

                    }

                    Log.d("Test", "**** parserErrorMsg \n" + errMsg);
                    textError.setVisibility(VISIBLE);
                    textError.setText(errMsg);

                }

            }
        }
    }
}
