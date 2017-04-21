package org.itri.bioreactor2.ui.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.controller.BioreactorController;
import org.itri.bioreactor2.model.StepMoter;

/**
 * Created by norman on 2017/1/4.
 */
@SuppressWarnings("ResourceType")
public class uiStepMotor extends LinearLayout {
    String listenTo;
    BioreactorController reactor;
    LayoutInflater inflater;
    Animation flipdown, flipup;
    ImageView img_stepmotor;
    TextView textTitle;
    Button btnFlipdown, btnFilpup;
    org.itri.bioreactor2.model.StepMoter motor;

    public uiStepMotor(Context context, String listenTo, String title){
        super(context);
        if(this.isInEditMode())return;

        this.listenTo = listenTo;

        //CONFIG BROACAST RECEVICER
        //context.registerReceiver(new DataParserBroadcastReceiver(),new IntentFilter("org.itri.bioreactor.DATA_PARSED." + listenTo));

        reactor = BioreactorController.getInstance(context);

        //CONFIG LAYOUT
        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_stepmotor, this);
        motor = new StepMoter(reactor);
        img_stepmotor = (ImageView) findViewById(R.id.img_stepmotor);
        flipdown = AnimationUtils.loadAnimation(context,R.anim.flipdown);
        flipup = AnimationUtils.loadAnimation(context,R.anim.flipup);
        textTitle = (TextView) findViewById(R.id.text_sensor_title);

        //CONFIG BUTTONS
        btnFilpup = (Button) findViewById(R.id.btn_nr180);
        btnFilpup.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                flipup();
                flipup.setDuration(1000);
                img_stepmotor.startAnimation(flipup);
                flipup.setFillAfter(true);
                btnFilpup.setVisibility(INVISIBLE);
                btnFlipdown.setVisibility(VISIBLE);

            }
        });

        btnFlipdown = (Button) findViewById(R.id.btn_pr180);
        btnFlipdown.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                flipdown();
                flipdown.setDuration(1000);
                img_stepmotor.startAnimation(flipdown);
                flipdown.setFillAfter(true);
                btnFlipdown.setVisibility(INVISIBLE);
                btnFilpup.setVisibility(VISIBLE);
            }
        });

        textTitle.setText(title);

    }

    public void flipdown() {
        motor.flipdown();
    }
    public void flipup() {
        motor.flipup();
    }
/*
    public class DataParserBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context,Intent intent){
            Log.d("Test","onReceive triggered!"+listenTo);
            try {
                if(intent.getStringExtra(listenTo)!=null) {
                    String strval = intent.getStringExtra(listenTo);
                    handler.obtainMessage(0,strval).sendToTarget();
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
            strval = strval.trim();
            textValue.setText(strval);
            int val = Integer.parseInt(strval);
            motor.updateCurrentValue(val);

            // do something here while dataReceived

            Log.d("Test","onReceive Done in uiAcuator" );
        }
    };
*/

}
