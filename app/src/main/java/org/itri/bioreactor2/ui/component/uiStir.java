package org.itri.bioreactor2.ui.component;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.model.stirrer;

/**
 * Created by norman on 2017/2/6.
 */

public class uiStir extends uiActuator {
    Animation amni;
    ImageView stir;
    public uiStir(Context context, String listenTo, String title, String unit) {
        super(context, listenTo, title, unit);
        stir = (ImageView)findViewById(R.id.img_stirrer);
        amni = AnimationUtils.loadAnimation(context,R.anim.rotate);
        amni.setDuration(0);


    }

    public void configInflater(){
        inflater.inflate(R.layout.control_stirr, this);
    }

    public void configModel(){
        model = new stirrer(reactor);
    }


    public void onDataReceived(String data){
        int i = Integer.valueOf(data);
        if(i==0){
            if(amni.getDuration()>0) {
                amni.setDuration(0);
                stir.startAnimation(amni);
            }
        }else{
            if(amni.getDuration()==0) {
                amni.setDuration(250000/i);
                stir.startAnimation(amni);
            }
        }
    }
}
