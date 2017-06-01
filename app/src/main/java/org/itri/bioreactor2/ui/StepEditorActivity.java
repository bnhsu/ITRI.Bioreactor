package org.itri.bioreactor2.ui;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.autocontrol.AutomationController;

import java.io.FileNotFoundException;

public class StepEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_editor);
        setupToolBar();
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setupToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.menu_stepedit);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_save:
                        //mStep.setStepTitle(editTitle.getText().toString());
                        //mStep.setStepDescription(editDescription.getText().toString());
                        //mStep.setStepSetTo(setto);
                        // Save the step edit
                        //Toast.makeText(mContext, "Edit is clicked!", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }


}
