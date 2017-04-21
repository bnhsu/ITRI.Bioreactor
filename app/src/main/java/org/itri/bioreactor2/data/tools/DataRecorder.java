package org.itri.bioreactor2.data.tools;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by norman on 4/15/14.
 */
public class DataRecorder {


    private boolean checkSaveData = false;
    private File SDCardPathFile = Environment.getExternalStorageDirectory();
    private String fileDir = SDCardPathFile.getAbsolutePath() + "/myData/";
    private String fileDateTime;
    private String fileSurfix = "_ppg.csv";
    private String filePath;

    public Boolean isRecording = false;
    public String dataHeader;

    public void startRecording(){
        if (!isRecording) {
            isRecording = true;
            fileDateTime = "";//MyDateTime.getDateTime("yyyyMMddHHmmss", Calendar.getInstance().getTime().getTime());
            filePath = fileDir + fileDateTime + fileSurfix;
        }
    }

    public Boolean recordData(String data){
        Boolean success = false;

        if(!isRecording) return false;

        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED) ){
            try {
                File dir = new File(fileDir);
                if( !dir.exists() ) dir.mkdirs();
                File file = new File(filePath);
                if(!file.exists())
                {
                    if(dataHeader!=null) {
                        FileWriter myFile = new FileWriter(filePath, true);
                        myFile.write(dataHeader);
                        myFile.close();
                    }
                }
                else
                {
                    FileWriter myFile = new FileWriter(filePath,true);
                    myFile.write(data);
                    myFile.close();
                }
                success = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public File stopRecording(){
        if(isRecording){
            isRecording = false;
        }
        return new File(filePath);
    }
}
