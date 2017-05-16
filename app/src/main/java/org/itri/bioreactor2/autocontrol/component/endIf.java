package org.itri.bioreactor2.autocontrol.component;

/**
 * Created by A20356 on 2017/5/16.
 */

public class endIf {
    private String TIME;

    public endIf(String TIME){
        setTIME(TIME);

    }
    public endIf(){}

    public String getTIME(){
        return TIME;
    }

    public void setTIME(String TIME){
        this.TIME = TIME;
    }
}
