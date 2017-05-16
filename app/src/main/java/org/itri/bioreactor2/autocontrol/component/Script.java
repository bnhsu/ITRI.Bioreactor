package org.itri.bioreactor2.autocontrol.component;

import android.content.Context;
import android.util.Log;

import org.itri.bioreactor2.autocontrol.parser.ScriptParser;
import org.itri.bioreactor2.autocontrol.parser.ScriptWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by A20356 on 2017/3/15.
 */

public class Script {
    //private static Script sInstance;
    private Context context;
    private ArrayList<step> alist = new ArrayList<step>();
    private step CurrentStep;
    private static String FileTile;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    private String path;

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
        path = context.getExternalFilesDir(null).getAbsolutePath();
    }
    public void readScrip() throws FileNotFoundException {
        inputStream = new FileInputStream(path + "/" + FileTile + ".json");
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

    public step getCurrentStep(int position){
        CurrentStep = alist.get(position);
        return CurrentStep;
    }

    public void setCurrentStep(int position, step newStep){
        alist.set(position, newStep);
    }

    public void addNewStep( step newStep){
        alist.add(newStep);
    }

    public void removeCurrentStep(int position){
        alist.remove(position);
    }

    public void clearAllStep(){
        alist.clear();
    }

    public void wirteScript() throws IOException {
        outputStream = new FileOutputStream(path + "/" + FileTile + ".json");
        ScriptWriter scriptWriter = new ScriptWriter();
        scriptWriter.writeJsonStream(outputStream, alist);
    }

}
