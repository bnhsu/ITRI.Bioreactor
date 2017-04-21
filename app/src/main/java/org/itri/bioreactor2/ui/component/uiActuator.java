package org.itri.bioreactor2.ui.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.controller.BioreactorController;
import org.itri.bioreactor2.data.database.CurrentValueTable;
import org.itri.bioreactor2.model.actuator;
/**
 * Created by norman on 2017/1/4.
 */
@SuppressWarnings("ResourceType")
public abstract class uiActuator extends LinearLayout {
    String listenTo;
    BioreactorController reactor;
    static CurrentValueTable CurrentValueTable;
    LayoutInflater inflater;
    TextView textValue, textUnit, textTitle;
    Button btnDec, btnInc;
    actuator model;
    boolean isBtnIncLongPressed = false;
    boolean isBtnDecLongPressed = false;
    public uiActuator(Context context, String listenTo, String title, String unit){
        super(context);
        init(context,listenTo,title,unit);
    }

    public uiActuator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.node, 0, 0);
        try {
            init(context,ta.getString(0),ta.getString(1),ta.getString(2));
        } finally {
            ta.recycle();
        }
    }

    private void init(Context context, String listenTo, String title, String unit){
        if(this.isInEditMode())return;

        this.listenTo = listenTo;
        CurrentValueTable = CurrentValueTable.getInstance();
        context.registerReceiver(new DataParserBroadcastReceiver(),new IntentFilter("org.itri.bioreactor.DATA_PARSED." + listenTo));
        reactor = BioreactorController.getInstance(context);

        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        configInflater();

        configModel();

        textValue = (TextView) findViewById(R.id.text_sensor_value);
        textUnit = (TextView) findViewById(R.id.text_sensor_unit);
        textTitle = (TextView) findViewById(R.id.text_sensor_title);
        btnInc = (Button)findViewById(R.id.btn_inc);
        btnDec = (Button)findViewById(R.id.btn_dec);
        btnInc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increase();
            }
        });
        btnDec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                decrease();
            }
        });
        btnInc.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                longIncrease();
                return true;
            }
        });
        btnDec.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                longDecrease();
                return true;
            }
        });
        btnInc.setOnTouchListener(new touchListener());
        btnDec.setOnTouchListener(new touchListener());


        textTitle.setText(title);
        textUnit.setText(unit);

    }

    public abstract void configInflater();

    public abstract void configModel();

    public class DataParserBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent){
            //Log.d("Test123","onReceive key:"+listenTo);
            //Log.d("Test123","onReceive value: "+intent.getStringExtra(listenTo));
            try {
                if(intent.getStringExtra(listenTo)!=null)
                {
                    String strval = intent.getStringExtra(listenTo);
                    int currentValue = Integer.valueOf(strval).intValue();
                    //Log.d("Test123","onReceive inside: "+currentValue);
                    if (listenTo.contains("RPM")) {
                        CurrentValueTable.setCurrentStirRPM(currentValue);
                    } else if (listenTo.contains("Pump1")) {
                        CurrentValueTable.setCurrentPump1RPM(currentValue);
                    } else if (listenTo.contains("Pump2")) {
                        CurrentValueTable.setCurrentPump2RPM(currentValue);
                    } else if (listenTo.contains("Pump3")) {
                        CurrentValueTable.setCurrentPump3RPM(currentValue);
                    }
                    handler.obtainMessage(0, strval).sendToTarget();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            String strval = (String)msg.obj;
            textValue.setText(strval);
            int val = Integer.parseInt(strval);
            model.updateCurrentValue(val);
            onDataReceived(strval);
            Log.d("Test","onReceive Done in uiAcuator" );
        }
    };


    public abstract void onDataReceived(String data);


    private class touchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if(view.getId() == btnInc.getId()){
                    if (isBtnIncLongPressed) {
                        model.longIncrease();
                        isBtnIncLongPressed = false;
                        Log.d("Test", "longIncrease release");
                    }
                }else if(view.getId() == btnDec.getId()){
                    if (isBtnDecLongPressed) {
                        model.longDecrease();
                        isBtnDecLongPressed = false;
                        Log.d("Test", "longDecrease release");
                    }
                }

            }
            return false;
        }
    }

    public void increase() {
        model.increase();
    }

    public void decrease() {
        model.decrease();
    }


    public void longIncrease(){
        model.longIncrease();
        isBtnIncLongPressed = true;
        Log.d("Test", "longIncrease pressed");
    }

    public void longDecrease(){
        model.longDecrease();
        isBtnDecLongPressed = true;
        Log.d("Test", "longDecrease pressed");
    }

}
