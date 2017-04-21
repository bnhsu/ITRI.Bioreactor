package org.itri.bioreactor2.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.data.database.DBHandler;
import org.itri.bioreactor2.ui.DeviceListActivity;

//import android.widget.RadioButton;
//import android.widget.RadioGroup;

public class AllSettingFragment extends Fragment {

    private final static String TAG = "Test";

    private Context context;
    private DBHandler db;
    private int dbCount = 0;

    private Button buttonSave;

    private EditText editBioreactor;

    // Intent request codes
    private static final int REQUEST_BIND_BIOREACTOR = 1;
    //private static final int REQUEST_BIND_O2SPPLY = 2;


    //boolean mStartPlaying = true;
    //boolean mStartRecording = true;

    /*****************************************/

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

	    View rootView = inflater.inflate(R.layout.activity_setting, container, false);

        this.context = rootView.getContext();
        //db = DBHandler.getInstance(context);
        SharedPreferences settings = context.getSharedPreferences("setting", 0);

        buttonSave = (Button) rootView.findViewById(R.id.button_ProfileSave);
        buttonSave.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // To start [Set Profile] activity.
                saveProfile();
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editBioreactor.getWindowToken(), 0);
            }
        } );


        editBioreactor = (EditText) rootView.findViewById(R.id.editBioreactor);
        editBioreactor.setOnClickListener(new OnClickListener() {
            Intent serverIntent = new Intent(context, DeviceListActivity.class);
            @Override
            public void onClick(View v) {
                startActivityForResult(serverIntent, REQUEST_BIND_BIOREACTOR);
            }
        });
        editBioreactor.setText(settings.getString("BIOREACTOR_NAME",""));


        return rootView;
    }

    public synchronized void onResume() {
        super.onResume();

    }

    /***********  Access to Database ***********/

    public void saveProfile(){

        SharedPreferences settings = context.getSharedPreferences("setting", 0);
        SharedPreferences.Editor editor = settings.edit();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (requestCode) {
            case REQUEST_BIND_BIOREACTOR:
                //here to store bpm device into prefrence
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    String name = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_NAME);
                    SharedPreferences settings = context.getSharedPreferences("setting", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("BIOREACTOR_ADDR",address);
                    editor.putString("BIOREACTOR_NAME",name);
                    Log.d("","set bioreactor address:" + address + " name:" + name);
                    editor.apply();
                    editor.commit();
                    Toast.makeText(context, "Bind to " + name, Toast.LENGTH_SHORT).show();
                    editBioreactor.setText(name);
                }
                break;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("Test", "Setting View onPause()");

        saveProfile();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editBioreactor.getWindowToken(), 0);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d("Test","Setting View Shown");
        }
        else {
            Log.d("Test","Setting View Hidden");
            if(context==null)return;
            saveProfile();
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editBioreactor.getWindowToken(), 0);
        }
    }


}
