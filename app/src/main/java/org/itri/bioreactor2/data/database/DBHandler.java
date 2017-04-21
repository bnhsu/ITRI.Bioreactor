package org.itri.bioreactor2.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {
    private final static String TAG = DBHandler.class.getSimpleName();

    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static final String DATABASE_NAME = "bioreactor.db";

    // SensorData Table Name
    private static final String TABLE_SENSOR = "sensor";

    
    // Sensor Table Columns names
    private static final String KEY_ID            = "id";
    private static final String KEY_OXYGEN          = "Oxygen";
    private static final String KEY_PH        = "pH";
    private static final String KEY_TEMP      = "Temp";
    private static final String KEY_STIRR     = "Stirr";
    private static final String KEY_PUMP        = "Pump";
    private static final String KEY_TIME          = "Time";

    private static final String[] COLUMNS_SENSOR =
        {KEY_ID, KEY_OXYGEN, KEY_PH, KEY_TEMP,KEY_STIRR,KEY_PUMP, KEY_TIME };
    
    private static DBHandler sInstance; 
    public static DBHandler getInstance(Context context) {

        // Use the application context, which will ensure that you 
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
          sInstance = new DBHandler(context.getApplicationContext());
        }
        return sInstance;
    }
    
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
       
    public void Connection_Close() {
        sInstance.close();
        sInstance =null;
    }
    
    /*  SQLite Datatypes
     * 1. NULL. The value is a NULL value.
     * 2. INTEGER. The value is a signed integer, stored in 1, 2, 3, 4, 6, or 8 bytes depending on the magnitude of the value.
     * 3. REAL. The value is a floating point value, stored as an 8-byte IEEE floating point number.
     * 4. TEXT. The value is a text string, stored using the database encoding (UTF-8, UTF-16BE or UTF-16LE).
     * 5. BLOB. The value is a blob of data, stored exactly as it was input.
     */  
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        // SQL statement to create profile table   
        createTable(db);
    }
    private void createTable(SQLiteDatabase db) {
        //Create Profile table
        Log.d(TAG,"DB profile create.");
        
        String CREATE_SENSOR_TABLE = "CREATE TABLE " + TABLE_SENSOR + "("
                + KEY_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT," 
                + KEY_OXYGEN      + " REAL,"
                + KEY_PH     + " REAL,"
                + KEY_TEMP   + " REAL,"
                + KEY_PUMP  + " INTEGER,"
                + KEY_STIRR  + " INTEGER,"
                + KEY_TIME      + " INTEGER "
                + ")";

        db.execSQL(CREATE_SENSOR_TABLE);

    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE + ";"); 
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR + ";"); 
//        createTable(db);     
        
        int upgradeTo = oldVersion + 1;
        while (upgradeTo <= newVersion)
        {
            switch (upgradeTo)
            {
                /*
                case 2:
                    db.execSQL("ALTER TABLE " + TABLE_PROFILE + " ADD COLUMN " + KEY_CODE + " TEXT "
                               + " AFTER " + KEY_NAME);
                    break;
                case 3:
                    //db.execSQL(...);
                    break;
                */
            }
            upgradeTo++;
        }
        
    }
    
    /**
     * CRUD operations (create "add", read "get", update, delete) profile
     */
    
    /**  Sensor data */
    public void addSensorData(SensorData sensorData){
        Log.d("Bioreactor Sensor Data", sensorData.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = sensorDataToContent(sensorData);
        // 3. insert
        db.insert(TABLE_SENSOR, null, values); 
        // key/value -> keys = column names/ values = column values
 
        // 4. close
        //db.close();  
          //**there is no need to close the database . it will automatically close when it is not needed.
    }
    
    public SensorData getSensorData(int id){
        
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor =
                db.query(TABLE_SENSOR, // a. table
                        COLUMNS_SENSOR, // b. column names
                        KEY_ID + " = ?", // c. selections 
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null  // h. limit
                        ); 
 
        // 3. if we got results get the first one
        SensorData sensorData = null;
        try {
            if (cursor.moveToFirst()) {
 
        // 4. build profile object
                sensorData = contentToSensorData(cursor);
            }
        } catch(IllegalStateException e) {
                Log.w(TAG, "No DB record in sensor data.");
                e.printStackTrace();
                return null;
        }        
        cursor.close(); //Close cursor object
      
        Log.d( TAG, "getResult("+id+")" + sensorData.toString());
 
        // 5. return book
        return sensorData;
    }

    //startTimeStamp scale: millisec
    public List<SensorData> getSensorDataByTime(Long startTimeStamp, Long endTimeStamp ) {
        List<SensorData> sensorDatas = new LinkedList<SensorData>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_SENSOR + " WHERE " + KEY_TIME +
                       " BETWEEN "+ startTimeStamp + " AND " + endTimeStamp +
                       " ORDER BY "+ KEY_TIME + " ASC";
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build profile and add it to list
        SensorData sensorData = null;
        
        try {
            if (cursor.moveToFirst()) {
                do {
                    // build Workout Result
                    sensorData = contentToSensorData(cursor);

                    // Add profile to profiles
                    sensorDatas.add(sensorData);
                    //FIXME while only one record in db.
                    if(cursor.isLast() == true)
                        break;
                } while (cursor.moveToNext());
            }
        } catch(IllegalStateException e) {
            Log.w(TAG, "No DB record in sensor datas.");
            e.printStackTrace();
            //return sensorDatas;
        }        
        cursor.close(); //Close cursor object
  
        Log.d (TAG, "getSensorDataByTime()"+ sensorDatas.toString());
  
        // return profiles
        return sensorDatas;
    }    
    
    public List<SensorData> getAllSensorData() {
        List<SensorData> sensorDatas = new LinkedList<SensorData>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_SENSOR;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build profile and add it to list
        SensorData sensorData = null;
        
        try {
            if (cursor.moveToFirst()) {
                do {
                    // build Workout Result
                    sensorData = contentToSensorData(cursor);

                    // Add profile to profiles
                    sensorDatas.add(sensorData);
                    //FIXME while only one record in db.
                    if(cursor.isLast() == true)
                        break;
                } while (cursor.moveToNext());
            }
        } catch(IllegalStateException e) {
            Log.w(TAG, "No DB record in sensor data.");
            e.printStackTrace();
            return null;
        }        
        cursor.close(); //Close cursor object
  
        Log.d (TAG, "getSensorDataByTime()"+ sensorDatas.toString());
  
        // return profiles
        return sensorDatas;
    }     

    // Getting SensorData Count
    public int getSensorDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SENSOR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();       
        cursor.close();
        //db.close();
        return count;         // return count
    }


    private SensorData contentToSensorData(Cursor cursor) {
        SensorData sensorData = new SensorData();

        //sensorData.setSpO2(Integer.parseInt(cursor.getString(0)));
        sensorData.setOxygen(cursor.getDouble(1));
        sensorData.setPH(cursor.getDouble(2));
        sensorData.setTemp(cursor.getDouble(3));
        sensorData.setStirr(cursor.getInt(4));
        sensorData.setPump(cursor.getInt(5));
        sensorData.setTime(cursor.getLong(6));
        return sensorData;      
    }
    
    private ContentValues sensorDataToContent(SensorData sensorData) {
        
        ContentValues values = new ContentValues();
        values.put(KEY_OXYGEN,     sensorData.getOxygen());
        values.put(KEY_PH,    sensorData.getPH());
        values.put(KEY_TEMP,  sensorData.getTemp());
        values.put(KEY_STIRR, sensorData.getStirr());
        values.put(KEY_PUMP,     sensorData.getPump());
        values.put(KEY_TIME,     sensorData.getTime().getTimeInMillis());
  
        return values;
    }
}
