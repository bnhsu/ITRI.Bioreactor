package org.itri.bioreactor2.data.parser;

/**
 * Created by norman on 2017/2/14.
 */

import android.util.JsonReader;

import org.itri.bioreactor2.model.step;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

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
public class ScriptParser {
        public ArrayList<step> readJsonString(InputStream in) throws IOException {
            JsonReader reader = new JsonReader(new InputStreamReader(in));
            try {
                return readStepsArray(reader);
            } finally {
                reader.close();
            }
        }

        public ArrayList<step> readStepsArray(JsonReader reader) throws IOException {

            ArrayList<step> steps = new ArrayList<step>();
            reader.beginArray();
            while (reader.hasNext()) {
                steps.add(readStep(reader));
            }
            reader.endArray();
            return steps;
        }

        public step readStep(JsonReader reader) throws IOException {
            step st = new step();

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("title")) {
                    st.setStepTitle(reader.nextString());
                } else if (name.equals("description")) {
                    st.setStepDescription(reader.nextString());
                } else if (name.equals("setTo")){
                    st.setStepSetTo( readSetTo(reader));
                } else if (name.equals("endIf")) {
                    st.setStepEndIf(readSetTo(reader));
                }else {
                    reader.skipValue();
                }
            }
            reader.endObject();
           return st;
        }


    public Hashtable<String,String> readSetTo(JsonReader reader)throws IOException {
        String key = null;
        Hashtable<String,String> SetTo = new Hashtable<String,String>();

        reader.beginObject();
        while (reader.hasNext()) {
            key = reader.nextName();
            SetTo.put(key, reader.nextString());
        }
        reader.endObject();
        return SetTo;
    }
}
