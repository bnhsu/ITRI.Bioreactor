package org.itri.bioreactor2.data.parser;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by norman on 4/25/14.
 */


public class BioreactorParser implements DataParser {
    private Context context;
    private DataParserListener listener;
    private byte[] buffer = new byte[32];
    private int index = -1;
    private Map<String,String> dataReturn = new HashMap<String, String>();

    public void addListener(DataParserListener listener){
        this.listener = listener;
    }

    boolean isConnected;
    long startTimeStamp;
    long currTimeStamp;
    long elapsedTimeStamp;

    //Sensor Database
    //private DBHandler db;
    //static SensorData sensorData = new SensorData();

    public BioreactorParser(Context context){
        this.context = context;
        isConnected = false;
        
        //Sensor Database initial
        //db = DBHandler.getInstance(context);
    }
    public void parseData(byte[] data,int len){
        dataReturn.clear();
        dataReturn.put("TYPE", "BIOREACTOR");
        byte[] readBuf = data;
        for(int i=0;i<len;i++)
        {
            int read = readBuf[i] & 0xff;
            //Log.d("Test","read:"+Integer.toString(read,16) + "(" + read + ")" + " ind:" + index);

            if (read == 64){ //[0x40]Bioreactor Header detect
                index = 0;
                buffer[index] = (byte)read;
                index++;
                //Log.d("Test","@ head hit!");
            }else if(index>-1 && buffer[0] == 64){ // keep reading
                //Log.d("Test","logging...");
                buffer[index] = (byte)read;
                if(index>0 && buffer[0]==64 && buffer[index-1] == 13 && buffer[index] == 10){ // [0x0D][0x0A] End Detect
                    String strVal = new String(buffer,1,index-1, Charset.forName("US-ASCII"));
                    strVal = dataCorrector(strVal);
                    Log.d("Test","parser result:" + strVal);
                    String[] aryVal = strVal.split("=");
                    if(aryVal.length==2) {
                        String key = aryVal[0];
                        String val = aryVal[1];
                        Intent intent = new Intent("DataParserIntent");
                        intent.putExtra(key, val.trim());
                        intent.setAction("org.itri.bioreactor.DATA_PARSED." + key);
                        context.sendBroadcast(intent);
                    }
                    Arrays.fill(buffer, (byte) 0);
                    //Log.d("Test", "end hit!");
                    index = -1;
                }else {
                    index++;
                }
            }


        }
    }

    private String dataCorrector(String data){
        if(data.contains("Suspend")){
            data = "RPM=0";
        }else if(data.contains("Close")){
            data = "RPM=0";
        }
        return data;
    }

    private int lostCount;
    private int previousCounter;
    private boolean checkCounter(int counter){
        boolean isGood = false;
        if (previousCounter == 255){
            if(counter == 0) isGood = true;
        }else{
            if(previousCounter+1 == counter) isGood = true;
        }
        previousCounter = counter;
        if(!isGood) lostCount++;
        return  isGood;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(int[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public void disconnect(){
        isConnected = false;
    }
}
