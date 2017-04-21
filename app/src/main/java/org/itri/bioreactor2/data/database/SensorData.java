package org.itri.bioreactor2.data.database;

import java.util.Calendar;
import java.util.GregorianCalendar;

/* User SensorData Setting */

public class SensorData {
	
	// Private members
	private double        oxygen;
	private double        ph;
	private double     temp;
	private int       stirr;
	private int       pump;
	private Calendar time;
    
	// Empty constructor
	public SensorData() {
		this.time = Calendar.getInstance();
	}
	// constructor
	public SensorData(double oxygen, double ph, double temp,int stirr,int pump,Calendar time) {
	    
		this.oxygen = oxygen;
		this.ph = ph;
		this.temp = temp;
		this.stirr = stirr;
        this.pump = pump;
		this.time = new GregorianCalendar();
		this.time.setTimeInMillis(time.getTimeInMillis());;

	}

	public double getOxygen() {
	    return oxygen;
	}
	
    public void setOxygen(double oxygen) {
        this.oxygen = oxygen;
    }

    public double getPH() {
        return ph;
    }
    
    public void setPH(double ph) {
        this.ph = ph;
    }
    
    public void setTemp(double temp){
        this.temp = temp;
    }
    
    public double getTemp() {
        return this.temp;
    }

    public int getStirr() {
        return stirr;
    }
    
    public void setStirr(int stirr) {
        this.stirr = stirr;
    }

    public int getPump() {
        return pump;
    }

    public void setPump(int pump) {
        this.pump = pump;
    }
	
    // getting Sensor record time
    public Calendar getTime() {
        return time;
    }
    
    public void setTime(Calendar time) {
        this.time.setTimeInMillis(time.getTimeInMillis());;
    }
    
    public void setTime(Long time) {
        this.time.setTimeInMillis(time);
    }

//    public void setTime3339(String time3339) {
//        time.parse3339(time3339);
//    }
    
    public String toString() {
        return String.format("oxygen:[%f], pH[%f], Temp[%f], Stirr[%d], Pump[%d], time[%s]",
        //        this.spo2, this.pulse, this.duration, this.time.format2445());
                this.oxygen, this.ph, this.temp, this.stirr, this.pump, this.time.toString());
                
    }
}
