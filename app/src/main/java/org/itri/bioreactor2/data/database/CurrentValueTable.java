package org.itri.bioreactor2.data.database;

/**
 * Created by A20356 on 2017/3/28.
 */

public class CurrentValueTable {
    //public Context context;
    private static CurrentValueTable sInstance;
    private float currentTmp, currentpH, currentDO;
    private int currentStirRPM, currentPump1RPM, currentPump2RPM, currentPump3RPM, currentHeating;

    public static CurrentValueTable getInstance() {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new CurrentValueTable(-1,-1,-1,-1,-1,-1,-1,-1);
        }
        return sInstance;
    }

    private  CurrentValueTable(float currentTmp, float currentpH, float currentDO, int currentStirRPM,
                               int currentPump1RPM, int currentPump2RPM, int currentPump3RPM, int currentHeating){
        //this.context = context;
        this.currentTmp = currentTmp;
        this.currentpH = currentpH;
        this.currentDO = currentDO;
        this.currentStirRPM = currentStirRPM;
        this.currentPump1RPM = currentPump1RPM;
        this.currentPump2RPM = currentPump2RPM;
        this.currentPump3RPM = currentPump3RPM;
        this.currentHeating = currentHeating;
    }

    public void setCurrentTmp(float currentTmp){
        this.currentTmp = currentTmp;
        //Log.d("CurrentValueTable", "currentTmp: " + currentTmp);
    }
    public float getCurrentTmp(){
        return currentTmp;
    }
    public void setCurrentpH(float currentpH){
        this.currentpH = currentpH;
        //Log.d("CurrentValueTable", "currentpH: " + currentpH);
    }
    public float getCureentpH(){
        return currentpH;
    }
    public void setCurrentDO(float currentDO){
        this.currentDO = currentDO;
        //Log.d("CurrentValueTable", "currentDO: " + currentDO);
    }
    public float getCurrentDO(){
        return currentDO;
    }
    public void setCurrentStirRPM(int currentStirRPM){
        this.currentStirRPM = currentStirRPM;
        //Log.d("CurrentValueTable", "currentStirRPM: " + currentStirRPM);
    }
    public int getCurrentStirRPM(){
        return currentStirRPM;
    }
    public void setCurrentPump1RPM(int currentPump1RPM){
        this.currentPump1RPM = currentPump1RPM;
        //Log.d("CurrentValueTable", "currentPump1RPM: " + currentPump1RPM);
    }
    public int getCurrentPump1RPM(){
        return currentPump1RPM;
    }
    public void setCurrentPump2RPM(int currentPump2RPM){
        this.currentPump2RPM = currentPump2RPM;
        //Log.d("CurrentValueTable", "currentPump2RPM: " + currentPump2RPM);
    }
    public int getCurrentPump2RPM(){
        return currentPump2RPM;
    }
    public void setCurrentPump3RPM(int currentPump3RPM){
        this.currentPump3RPM = currentPump3RPM;
        //Log.d("CurrentValueTable", "currentPump3RPM: " + currentPump3RPM);
    }
    public int getCurrentPump3RPM(){
        return currentPump3RPM;
    }
    public void setCurrentHeating(int currentHeating){
        this.currentPump3RPM = currentPump3RPM;
        //Log.d("CurrentValueTable", "currentPump3RPM: " + currentPump3RPM);
    }
    public int getCurrentHeating(){
        return currentHeating;
    }
}
