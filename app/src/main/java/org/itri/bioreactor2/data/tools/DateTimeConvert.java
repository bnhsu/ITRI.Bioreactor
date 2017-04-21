package org.itri.bioreactor2.data.tools;

import java.util.Calendar;

public class DateTimeConvert {
    
    public static long formatDateAsLong(Calendar cal){
        return (cal.getTimeInMillis()/1000); //scale: sec
     }
      
    public static Calendar getCalendarFromFormattedLong(long l){
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(l*1000);
        return cal;
     }
    
}


