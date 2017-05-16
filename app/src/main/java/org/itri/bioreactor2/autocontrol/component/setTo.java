package org.itri.bioreactor2.autocontrol.component;

/**
 * Created by A20356 on 2017/5/16.
 */

public class setTo {
    private String Pump1;
    private String Pump2;
    private String Pump3;
    private String stir;
    private String pH;
    private String DO;
    private String TMP;

    public setTo(String Pump1, String Pump2, String Pump3, String stir, String pH, String DO, String TMP) {
        setPump1(Pump1);
        setPump2(Pump2);
        setPump3(Pump3);
        setstir(stir);
        setpH(pH);
        setDO(DO);
        setTMP(TMP);
    }

    public setTo(){}

    public String getPump1(){
        return Pump1;
    }
    public String getPump2(){
        return Pump2;
    }
    public String getPump3(){
        return Pump3;
    }
    public String getStir(){
        return stir;
    }
    public String getpH(){
        return pH;
    }
    public String getDO(){
        return DO;
    }
    public String getTMP(){
        return TMP;
    }

    public void setPump1(String pump1) {
        this.Pump1 = pump1;
    }
    public void setPump2(String pump2) {
        this.Pump2 = pump2;
    }
    public void setPump3(String pump3) {
        this.Pump3 = pump3;
    }
    public void setstir(String stir) {
        this.stir = stir;
    }
    public void setpH(String pH) {
        this.pH = pH;
    }
    public void setDO(String DO) {
        this.DO = DO;
    }
    public void setTMP(String TMP) {
        this.TMP = TMP;
    }
}
