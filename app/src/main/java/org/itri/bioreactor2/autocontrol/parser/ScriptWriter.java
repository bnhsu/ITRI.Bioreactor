package org.itri.bioreactor2.autocontrol.parser;

import android.util.JsonWriter;

import org.itri.bioreactor2.autocontrol.component.step;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by A20356 on 2017/5/15.
 */
/*
[
   {
     "title": "step 1",
     "description": "step 1",
     "setTo":{
              "pH":"=7",
              "DO":"=20",
              "Pump2":"=50"
             }
     "endIf": {
       "waterlevel": "<0.2"
      }
   }
   {
     "title": "step 1",
     "description": "step 1",
     "setTo":{
              "pH":"=7",
              "DO":"=20",
              "Pump2":"=50"
             }
     "endIf": {
       "waterlevel": "<0.2"
      }
   }
 ]
 */

public class ScriptWriter {
    private Object key;

    public void writeJsonStream(OutputStream out, List<step> script) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeStepArray(writer, script);
        writer.close();
    }

    public void writeStepArray(JsonWriter writer, List<step> script) throws IOException {
        writer.beginArray();
        for (step message : script) {
            writeStep(writer, message);
        }
        writer.endArray();
    }

    public void writeStep(JsonWriter writer, step Step) throws IOException {
        writer.beginObject();
        writer.name("title").value(Step.getStepTitle());
        writer.name("description").value(Step.getStepDescription());
        /*
        if (newStep.getStepSetTo() != null) {
            writer.name("geo");
            writeDoublesArray(writer, message.getGeo());
        } else {
            writer.name("geo").nullValue();
        }
        */
        writer.name("setTo");
        writeSetto(writer, Step);
        writer.name("endIf");
        writerEndif(writer, Step);
        writer.endObject();
    }

    private void writeSetto(JsonWriter writer, step Step) throws IOException {
        writer.beginObject();
        for(Enumeration<Object> v = Step.getStepSetTo().keys(); v.hasMoreElements();){

            key = v.nextElement();
            if(key.toString().contains("Pump1")) {
                writer.name("Pump1").value(Step.getStepSetTo().get(key.toString()).toString());
            }
            else if(key.toString().contains("Pump2")){
                writer.name("Pump2").value(Step.getStepSetTo().get(key.toString()).toString());
            }
            else if(key.toString().contains("Pump3")){
                writer.name("Pump3").value(Step.getStepSetTo().get(key.toString()).toString());
            }
            else if(key.toString().contains("Stir")){
                writer.name("Stir").value(Step.getStepSetTo().get(key.toString()).toString());
            }
            else if(key.toString().contains("TMP")){

                writer.name("tmp").value(Step.getStepSetTo().get(key.toString()).toString());
            }
            else if(key.toString().contains("pH")){
                writer.name("pH").value(Step.getStepSetTo().get(key.toString()).toString());
            }
            else if(key.toString().contains("DO")){
                writer.name("DO").value(Step.getStepSetTo().get(key.toString()).toString());
            }
        }

        writer.endObject();
    }

    private void writerEndif(JsonWriter writer, step newStep) throws IOException{
        writer.beginObject();
        for(Enumeration<Object> v = newStep.getStepEndIf().keys(); v.hasMoreElements();){

            key = v.nextElement();
            if(key.toString().contains("TIME")) {
                writer.name("TIME").value(newStep.getStepEndIf().get(key.toString()).toString());
            }
            else if(key.toString().contains("Pump1")){
                writer.name("pump2").value(newStep.getStepEndIf().get(key.toString()).toString());
            }
        }
        writer.endObject();

    }

}
