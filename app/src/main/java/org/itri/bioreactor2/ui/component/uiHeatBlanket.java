package org.itri.bioreactor2.ui.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.controller.BioreactorController;
import org.itri.bioreactor2.data.database.CurrentValueTable;
import org.itri.bioreactor2.model.HeatBlanket;
import org.itri.bioreactor2.model.actuator;

/**
 * Created by norman on 2017/1/4.
 */
@SuppressWarnings("ResourceType")
public class uiHeatBlanket extends LinearLayout {
    String listenTo;
    BioreactorController reactor;
    static CurrentValueTable CurrentValueTable;
    LayoutInflater inflater;
    TextView textValue;
    SeekBar seekBar;
    actuator HeatBlanket;
    int lastprocess = 0;

    public uiHeatBlanket(Context context, String listenTo){
        super(context);
        init(context,listenTo);
    }


    private void init(Context context, String listenTo){

        if(this.isInEditMode())return;
        this.listenTo = listenTo;
        CurrentValueTable = CurrentValueTable.getInstance();
        reactor = BioreactorController.getInstance(context);
        HeatBlanket = new HeatBlanket(reactor);

        context.registerReceiver(new DataParserBroadcastReceiver(),new IntentFilter("org.itri.bioreactor.DATA_PARSED." + listenTo));

        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_heatblanket, this);

        seekBar = (SeekBar)findViewById(R.id.SeekBarHeating);
        textValue = (TextView) findViewById(R.id.textViewHeating);
        textValue.setText( "HEAT LEVEL: 0/10");
        seekBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                textValue.setText( "HEAT LEVEL: " + progress + "/" + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    HeatBlanket.setValue(progress);
                    lastprocess = progress;
            }
        });
    }

    public class DataParserBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent){

            try {
                if(intent.getStringExtra(listenTo)!=null)
                {
                    String strval = intent.getStringExtra(listenTo);
                    int currentValue = Integer.valueOf(strval).intValue();
                    if (listenTo.contains("Heating")) {
                        CurrentValueTable.setCurrentHeating(currentValue);
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
            //textValue.setText(strval);
            int val = Integer.parseInt(strval);
            int level = val/1000;
            seekBar.setProgress(level);
            textValue.setText( "Heating Level:" + level +"/10");
            HeatBlanket.updateCurrentValue(val);
            Log.d("Test","onReceive Done in uiHeating, Level:" + level );
        }
    };

}
