package org.itri.bioreactor2.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.ui.adpater.StepCardAdapter;

public class StepViewFragment extends Fragment {
    TextView textView;
    String title;
    String[] CardTitle = new String [] {"STEP 1", "STEP 2", "STEP 3", "STEP 4", "STEP 5", "STEP 6"};

    public StepViewFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        Bundle bundle = this.getArguments();
        if (saveInstanceState != null){
            title = bundle.getString("StepList_Title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {

        View rootView = inflater.inflate(R.layout.fragment_stepview, container, false);
        Log.d("test","onCreat StepViewFragment: " + title);

        //setupToolBar(); activity only
        setupFloatingActionButton(rootView);
        setupRecyclerView(rootView);
        return rootView;

    }

    private void setupFloatingActionButton(final View view){
        //final View CoordinatorLayout = view.findViewById(R.id.CoordinatorLayout);
        FloatingActionButton addCard = (FloatingActionButton)view.findViewById(R.id.fab);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Add a Step", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void setupRecyclerView(View view){

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        StepCardAdapter adapter = new StepCardAdapter(CardTitle, getActivity());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter1(myDataset);
        //mRecyclerView.setAdapter(mAdapter);
    }

    /* activity only
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
*/

}
