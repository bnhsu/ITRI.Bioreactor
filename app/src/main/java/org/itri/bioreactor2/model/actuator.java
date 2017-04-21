package org.itri.bioreactor2.model;

import android.util.Log;

import org.itri.bioreactor2.controller.BioreactorController;

import java.util.Hashtable;

/**
 * Created by norman on 2017/1/6.
 */

public abstract class actuator {
    public BioreactorController reactor;
    public String currentPump;
    public int currentValue;
    Hashtable<String, String> cmdTable;
    public actuator(BioreactorController reactor){
        this.reactor = reactor;
        //COMMAND TABLE
        cmdTable= new Hashtable<String,String>();
        cmdTable.put("STIR0","s");
        cmdTable.put("STIRINIT","1");
        cmdTable.put("STIR200","S00");
        cmdTable.put("STIR300","S01");
        cmdTable.put("STIR400","S02");
        cmdTable.put("STIR500","S03");
        cmdTable.put("STIR600","S04");
        cmdTable.put("STIR700","S05");
        cmdTable.put("STIR800","S06");
        cmdTable.put("STIR900","S07");
        cmdTable.put("STIR1000","S08");
        cmdTable.put("STIR1100","S09");
        cmdTable.put("STIR1200","S10");
        cmdTable.put("STIR1300","S11");
        cmdTable.put("STIR1400","S12");
        cmdTable.put("STIR1500","S13");
        cmdTable.put("STIRCLOSE","g");
        cmdTable.put("GETPH","p");
        cmdTable.put("GETWATERLEVEL","w");
        cmdTable.put("GETTEMP","t");
        cmdTable.put("GETRPM","q");
        cmdTable.put("Pump1","o");
        cmdTable.put("Pump2","a");
        cmdTable.put("Pump3","b");
        cmdTable.put("GETDO","d");
        cmdTable.put("STEPMOTOR","m");
        cmdTable.put("STEPMOTOR_P_R", "P");
        cmdTable.put("STEPMOTOR_N_R", "N");
        cmdTable.put("STEPMOTOR_STOP", "O");
        cmdTable.put("Heating","h");  //0 = off, 1~10 = heating level

     }

     public void increase(){}
     public void decrease(){}
     public void setValue(int val){} // for HeatBlank only

     public void longIncrease(){}
     public void longDecrease(){}
     public void updateCurrentValue(int val){
         this.currentValue = val;
         Log.d("Test","set currentVal:" + val);
     }
}
