package org.itri.bioreactor2.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.autocontrol.AutomationController;

public class AutoControlActivity extends Activity {

    static AutomationController automationController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_control);

    }
    public void backToMonitor(View view){
        Log.d("activty","Switch to mainactivty");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void startAutoControl(View view){
        automationController = AutomationController.getInstance(this);
        Log.d("activty","start to AutoControl");
    }
}
