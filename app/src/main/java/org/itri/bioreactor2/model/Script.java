package org.itri.bioreactor2.model;

import android.content.Context;
import android.util.Log;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.data.parser.ScriptParser;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by A20356 on 2017/3/15.
 */

public class Script {
    private static Script sInstance;
    private Context context;
    ArrayList<step> alist = null;
    public static Script getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new Script(context.getApplicationContext());
        }
        return sInstance;
    }

    public Script(Context context) {
        this.context = context;
        ScriptParser parser = new ScriptParser();
        try {
            alist = parser.readJsonString(context.getResources().openRawResource(R.raw.step_example));
            Log.d("","");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<step> list(){
        return alist;
    }

    public step getCurrentStep(){


        return null;
    }



}
