package org.itri.bioreactor2.ui;

import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.ui.adpater.AutoControlCardAdapter;
import org.itri.bioreactor2.ui.adpater.StepCardAdapter;

public class StepViewActivity extends AppCompatActivity {
    TextView textView;
    String[] CardTitle = new String [] {"STEP 1", "STEP 2", "STEP 3", "STEP 4", "STEP 5", "STEP 6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepview);
        Log.d("test","onCreat StepViewActivity: " + getIntent().getStringExtra("StepList_Title"));

        setupToolBar();
        setupFloatingActionButton();
        setupRecyclerView();

    }

    private void setupToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        actionBar.setTitle(getIntent().getStringExtra("StepList_Title"));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }

    private void setupFloatingActionButton(){
        final View CoordinatorLayout = findViewById(R.id.CoordinatorLayout);
        FloatingActionButton addCard = (FloatingActionButton)findViewById(R.id.fab);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(CoordinatorLayout, "Add a Step", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void setupRecyclerView(){

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        StepCardAdapter adapter = new StepCardAdapter(CardTitle, this);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter1(myDataset);
        //mRecyclerView.setAdapter(mAdapter);
    }
}
