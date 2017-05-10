package org.itri.bioreactor2.model;

import java.util.Dictionary;
import java.util.Enumeration;

/**
 * Created by norman on 2017/2/14.
 */

public class step {
    private String title;
    private String description;
    private Dictionary setTo;
    private Dictionary endIf;
    private Object key;

    public step(String title, String description, Dictionary setTo, Dictionary endIf){
        setStepTitle(title);
        setStepDescription(description);
        setStepSetTo(setTo);
        setStepEndIf(endIf);
    }
    public step(){}

    public String getStepTitle(){
        return title;
    }
    public String getStepDescription(){
        return description;
    }
    public Dictionary getStepSetTo(){
        return setTo;
    }
    public Dictionary getStepEndIf(){
        return endIf;
    }

    public void setStepTitle(String title){
        this.title = title;
    }
    public void setStepDescription(String Description){
        this.description = Description;
    }
    public void setStepSetTo(Dictionary setTo){
        this.setTo = setTo;
    }
    public void setStepEndIf(Dictionary endIf){
        this.endIf = endIf;
    }

    public String listAllSetTo(){
        String setToString = null;

        for(Enumeration<Object> v = getStepSetTo().keys(); v.hasMoreElements();){

            key = v.nextElement();
            if(key.toString().contains("Pump1")) {
                setToString = ("Pump1: " + getStepSetTo().get(key.toString()).toString() +" rpm\n");
            }
            else if(key.toString().contains("Pump2")){
                setToString = setToString + ("Pump2: " + getStepSetTo().get(key.toString()).toString() +" rpm\n");
            }
            else if(key.toString().contains("Pump3")){
                setToString = setToString +("Pump3: " + getStepSetTo().get(key.toString()).toString() +" rpm\n");
            }
            else if(key.toString().contains("Stir")){
                setToString = setToString +("Stir: " + getStepSetTo().get(key.toString()).toString() +" rpm\n");
            }
            else if(key.toString().contains("TMP")){
                setToString = setToString +("TMP: " + getStepSetTo().get(key.toString()).toString() +" °C\n");
            }
            else if(key.toString().contains("pH")){
                setToString = setToString +("pH: " + getStepSetTo().get(key.toString()).toString() +"\n");
            }
            else if(key.toString().contains("DO")){
                setToString = setToString +("DO: " + getStepSetTo().get(key.toString()).toString() +" %\n");
            }
        }

        return setToString;
    }
    public String listAllEndIF(){
        String endIfString = null;

        for(Enumeration<Object> v = getStepEndIf().keys(); v.hasMoreElements();){

            key = v.nextElement();
            if(key.toString().contains("TIME")) {
                endIfString = (getStepEndIf().get(key.toString()).toString() +" seconds \n");
            }
            else if(key.toString().contains("Pump2")){
                endIfString = endIfString + ("Pump2:" + getStepEndIf().get(key.toString()).toString() +"\n");
            }
            else if(key.toString().contains("Pump3")){
                endIfString = endIfString +("Pump3:" + getStepEndIf().get(key.toString()).toString() +"\n");
            }
            else if(key.toString().contains("Stir")){
                endIfString = endIfString +("Stir:" + getStepEndIf().get(key.toString()).toString() +"\n");
            }
            else if(key.toString().contains("TMP")){
                endIfString = endIfString +("TMP:" + getStepEndIf().get(key.toString()).toString() +"\n");
            }
            else if(key.toString().contains("pH")){
                endIfString = endIfString +("pH:" + getStepEndIf().get(key.toString()).toString() +"\n");
            }
            else if(key.toString().contains("DO")){
                endIfString = endIfString +("DO:" + getStepEndIf().get(key.toString()).toString() +"\n");
            }
        }

        return endIfString;
    }

}
