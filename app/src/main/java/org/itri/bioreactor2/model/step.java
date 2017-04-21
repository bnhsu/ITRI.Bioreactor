package org.itri.bioreactor2.model;

import java.util.Dictionary;

/**
 * Created by norman on 2017/2/14.
 */

public class step {
    public String title;
    public String description;
    public Dictionary setTo;
    public Dictionary endIf;
    public step(String title, String description, Dictionary setTo, Dictionary endIf){
        this.title = title;
        this.description = description;
        this.setTo = setTo;
        this.endIf = endIf;
    }
    public step(){}

}
