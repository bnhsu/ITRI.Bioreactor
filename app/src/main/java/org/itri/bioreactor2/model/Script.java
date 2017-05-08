package org.itri.bioreactor2.model;

import android.content.Context;
import android.util.Log;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.data.parser.ScriptParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by A20356 on 2017/3/15.
 */

public class Script {
    //private static Script sInstance;
    private Context context;
    private ArrayList<step> alist = null;
    private step CurrentStep;
    private static String FileTile;
    InputStream inputStream = null;
/*
    public static Script getInstance(Context context, String title) throws FileNotFoundException {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new Script(context.getApplicationContext(), title);
        }
        return sInstance;
    }
*/
    public Script(Context context, String FileTile) throws FileNotFoundException {
        this.context = context;
        this.FileTile = FileTile;
        String path = context.getExternalFilesDir(null).getAbsolutePath();
        inputStream = new FileInputStream(path + "/" + FileTile);
        ScriptParser parser = new ScriptParser();
        try {
            alist = parser.readJsonString(inputStream);
            Log.d("","");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<step> list(){
        return alist;
    }

    public int getScriptionSize(){
        int size = alist.size();
        return size;
    }

    public step getCurrentStep(int poistion){
        CurrentStep = alist.get(poistion);
        return CurrentStep;
    }

    public void clearAllStep(){
        alist.clear();
    }


}
