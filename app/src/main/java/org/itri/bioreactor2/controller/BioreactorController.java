package org.itri.bioreactor2.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.itri.bioreactor2.data.parser.BioreactorParser;
import org.itri.bioreactor2.data.parser.DataParser;

import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by norman on 2017/1/5.
 */

public class BioreactorController {
    private static BioreactorController sInstance;
    private BluetoothController bioreactorBluetoothController = null;
    public DataParser parser;
    static private StringBuffer mBluetoothOutStringBuffer;
    public Context context;
    private String mConnectedDeviceName = null;
    static int bioreactorPacketCounter = 0;
    Hashtable<String, String> cmdTable;

    public static BioreactorController getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new BioreactorController(context.getApplicationContext());
        }
        return sInstance;
    }

    private BioreactorController(Context context) {

        this.context = context;

        if (bioreactorBluetoothController == null){
            bioreactorBluetoothController = new BluetoothController(context, mBluetoothHandler);
            mBluetoothOutStringBuffer = new StringBuffer("");
        }

        //Init DataParser
        parser = new BioreactorParser(context);

        //TIMER ZONE

        Timer feedPinger = new Timer();
        feedPinger.scheduleAtFixedRate(isAliveTask,5000,1000);
        //Timer sim = new Timer();
        //sim.scheduleAtFixedRate(task_simulate_data,7000,1000);
    }

    public synchronized void resume() {
        if (bioreactorBluetoothController != null) {
                // Only if the state is STATE_NONE, do we know that we haven't started already
                if (bioreactorBluetoothController.getState() == BluetoothController.STATE_NONE) {
                    // Start the Bluetooth  services
                    bioreactorBluetoothController.start();
                    if (!bioreactorBluetoothController.isAutoConnect()) {
                        bioreactorBluetoothController.turnOnAutoConnectWithPreferenceKey("BIOREACTOR_ADDR",5000);
                    }
                }
        }
    }

    public void destroy() {
        // Stop the Bluetooth services
        bioreactorBluetoothController.turnOffAutoConnect();
        if (bioreactorBluetoothController != null) bioreactorBluetoothController.stop();
    }



    private final Handler mBluetoothHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case BluetoothController.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothController.STATE_CONNECTED:
                            break;
                        case BluetoothController.STATE_CONNECTING:
                            break;
                        case BluetoothController.STATE_LISTEN:
                        case BluetoothController.STATE_NONE:
                            break;
                    }
                    break;
                case BluetoothController.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    break;
                case BluetoothController.MESSAGE_READ:
                    parser.parseData((byte[]) msg.obj, msg.arg1);
                    feedLive = 20;
                    retry = 3;
                    break;
                case BluetoothController.MESSAGE_DEVICE_NAME:

                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(BluetoothController.DEVICE_NAME);
                   // Toast.makeText(context, "Connected to "
                   //         + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    bioreactorPacketCounter = 0;
                    break;
                case BluetoothController.MESSAGE_TOAST:
                    //Toast.makeText(context, msg.getData().getString(BluetoothService.TOAST),
                    //        Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothController.MESSAGE_CONN_LOST:
                    parser.disconnect();
                    break;
            }
        }
    };

    static int feedLive = -1;
    public TimerTask isAliveTask = new TimerTask(){
        @Override
        public void run(){
            if(bioreactorBluetoothController.getState()== bioreactorBluetoothController.STATE_CONNECTED) {
                if (feedLive < 0) {
                    feedLive = 20;
                    requestAutoFeed();
                    Log.d("Test", "request Auto Feed");
                } else {
                    feedLive--;
                }
            }
        }
    };

    static int retry = 3;
    public void requestAutoFeed(){
        sendBioreactorCommand("T05".getBytes());
        if (retry<0){
            retry = 3;
            //self disconnect
            bioreactorBluetoothController.disconnect();
            Log.d("Test","self disconnect");
        }else{
            retry--;
        }
    }

    int i = 0;
    public TimerTask task_simulate_data = new TimerTask() {
        @Override
        public void run() {
            if(i<10) {
                i++;
            }else{
                i = 0;
            }

            Intent intent1 = new Intent("DataParserIntent");
            intent1.putExtra("NFC", "NEW");
            intent1.setAction("org.itri.bioreactor.DATA_PARSED.NFC");
            Log.d("test","BioreactorController uiFNCtest ------- new");
            context.sendBroadcast(intent1);

            Intent intent2 = new Intent("DataParserIntent");
            intent2.putExtra("NFC", "OK");
            //intent2.putExtra("pH", "700");
            intent2.setAction("org.itri.bioreactor.DATA_PARSED.NFC");
            Log.d("test","BioreactorController uiFNCtest ------- OK");
            context.sendBroadcast(intent2);
        }
    };



    public void sendBioreactorCommand(byte[] message) {
        // reassure object is not null
        if (bioreactorBluetoothController == null) {
            bioreactorBluetoothController = new BluetoothController(context, mBluetoothHandler);
            mBluetoothOutStringBuffer = new StringBuffer("");
        }
        // Log.d("","send bioreactor command!");
        // Check that we're actually connected before trying anything
        if (bioreactorBluetoothController.getState() != BluetoothController.STATE_CONNECTED) {
            //Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        // Check that there's actually something to send
        if (message.length > 0) {
            bioreactorBluetoothController.write(message);
            // Reset out string buffer to zero and clear the edit text field
            mBluetoothOutStringBuffer.setLength(0);
        }
    }
}
