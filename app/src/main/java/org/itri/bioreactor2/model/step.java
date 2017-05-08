package org.itri.bioreactor2.model;

import java.util.Dictionary;

/**
 * Created by norman on 2017/2/14.
 */

public class step {
    private String title;
    private String description;
    private Dictionary setTo;
    private Dictionary endIf;
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




}
