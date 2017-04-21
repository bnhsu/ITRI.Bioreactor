package org.itri.bioreactor2.ui.component;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.model.pump;

import static java.lang.Math.abs;


/**
 * Created by norman on 2017/2/6.
 */

public class uiPump extends uiActuator {
    Animation rotate, rotate_reverse;
    ImageView pumping;
//    Drawable pump_rotate,upArw,downArw;

    public uiPump(Context context, String listenTo, String title, String unit) {
        super(context, listenTo, title, unit);

        pumping = (ImageView)findViewById(R.id.img_pumping);
        rotate = AnimationUtils.loadAnimation(context, R.anim.rotate);
        rotate_reverse = AnimationUtils.loadAnimation(context, R.anim.rotate_reverse);
        rotate.setDuration(0);

        //pump_down = AnimationUtils.loadAnimation(context,R.anim.pumping_down);
        //pump_up = AnimationUtils.loadAnimation(context,R.anim.pumping_up);
        //upArw = ResourcesCompat.getDrawable(getResources(),R.drawable.ani_arrow_up,null);
        //downArw = ResourcesCompat.getDrawable(getResources(),R.drawable.ani_arrow_down,null);
    }

    public void configInflater(){
        inflater.inflate(R.layout.control_pump, this);
    }

    public void configModel(){
        model = new pump( reactor, listenTo);
    }

    public void onDataReceived(String data){
        int i = Integer.valueOf(data);

        if(i==0){
            pumping.clearAnimation();
        }else if(i>0){
            if(rotate.getDuration() != 250000/i){
                rotate.setDuration(250000/i);
                pumping.startAnimation(rotate);
            }
        }
        else
            if(rotate_reverse.getDuration() != 250000/abs(i)) {
                rotate_reverse.setDuration(250000/abs(i));
                pumping.startAnimation(rotate_reverse);
            }
    }




}
