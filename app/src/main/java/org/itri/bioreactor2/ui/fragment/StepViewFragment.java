package org.itri.bioreactor2.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.model.Script;
import org.itri.bioreactor2.model.step;
import org.itri.bioreactor2.ui.adpater.StepCardAdapter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Dictionary;

public class StepViewFragment extends Fragment {
    TextView textView;
    String title= null;
    final ArrayList<step> Step = new ArrayList<>();

    /*
    final ArrayList<String> StepTitle = new ArrayList<>();
    final ArrayList<String> StepDiscreption = new ArrayList<>();
    final ArrayList<Dictionary> StepSetTo = new ArrayList<>();
    final ArrayList<Dictionary> StepEndIf = new ArrayList<>();
    */

    private StepCardAdapter adapter;

    public StepViewFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {

        View rootView = inflater.inflate(R.layout.fragment_stepview, container, false);
        adapter = setupRecyclerView(rootView);

        savedInstanceState = this.getArguments();
        if (savedInstanceState != null){
            title = savedInstanceState.getString("file_Title");
        }

        Log.d("test","onCreat StepViewFragment: " + title);

        try {
            setupStepsDetail();
        } catch (FileNotFoundException e) {
            Log.d("FileNotFoundException","FileNotFoundException ");
            e.printStackTrace();
        }
        //setupToolBar(); activity only


        setupFloatingActionButton(rootView, adapter);
        return rootView;

    }

    private void setupStepsDetail() throws FileNotFoundException {
            /*
            StepTitle.clear();
            StepDiscreption.clear();
            */
            Step.clear();
            Script script = new Script(getActivity(), title);
            for (int i = 0; i < script.getScriptionSize(); i++) {
                Step.add(script.getCurrentStep(i));
                /*
                StepTitle.add(script.getCurrentStep(i).getStepTitle());
                StepDiscreption.add(script.getCurrentStep(i).getStepDescription());
                StepSetTo.add(script.getCurrentStep(i).getStepSetTo());
                StepEndIf.add(script.getCurrentStep(i).getStepEndIf());
                */
            }
            adapter.notifyItemInserted(Step.size());

    }

    private void setupFloatingActionButton(final View view, final StepCardAdapter adapter){
        //final View CoordinatorLayout = view.findViewById(R.id.CoordinatorLayout);
        FloatingActionButton addCard = (FloatingActionButton)view.findViewById(R.id.fab);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(view, "Add a Step", Snackbar.LENGTH_LONG).show();
                int position = Step.size()+1;
                //Step.add("STEP " + position);
                adapter.notifyItemInserted(Step.size());

            }
        });
    }

    private StepCardAdapter setupRecyclerView(View view){

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        //StepCardAdapter adapter = new StepCardAdapter(StepTitle, StepDiscreption, StepSetTo, StepEndIf, getActivity());
        StepCardAdapter adapter = new StepCardAdapter(Step, getActivity());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return adapter;

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
