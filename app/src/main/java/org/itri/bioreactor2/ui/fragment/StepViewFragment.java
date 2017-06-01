package org.itri.bioreactor2.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import org.itri.bioreactor2.R;
import org.itri.bioreactor2.autocontrol.AutomationController;
import org.itri.bioreactor2.autocontrol.component.Script;
import org.itri.bioreactor2.autocontrol.component.step;
import org.itri.bioreactor2.ui.StepEditorActivity;
import org.itri.bioreactor2.ui.adpater.StepCardAdapter;
import org.itri.bioreactor2.ui.dialog.EditStepDialog;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class StepViewFragment extends Fragment {
    String title= null;
    final ArrayList<step> Step = new ArrayList<>();
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
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        adapter = setupRecyclerView(rv);
        savedInstanceState = this.getArguments();
        if (savedInstanceState != null){
            title = savedInstanceState.getString("file_Title");
            Log.d("debugcrash"," on StepViewFragment get file title");
            try {
                setupStepsDetail();
                Log.d("debugcrash","StepViewFragment setupStepsDetail done");
            } catch (FileNotFoundException e) {
                Log.d("debugcrash","FileNotFoundException ");
                e.printStackTrace();
            }
        }
        setupToolBar(rootView);
        setupRemoveStep(rv);
        setupFloatingActionButton(rootView, adapter);

        return rootView;

    }
    private void setupToolBar(View v){
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //ActionBar actionBar = getSupportActionBar();
        toolbar.setTitle(title);
        if(title != null) {
            toolbar.inflateMenu(R.menu.step_action);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_start:
                            try {
                                AutomationController.getInstance(getActivity(), title);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(mContext, "Edit is clicked!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                }
            });
        }
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        //actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupStepsDetail() throws FileNotFoundException {
        Step.clear();
        Log.d("debugcrash","StepViewFragment setupStepsDetail ");
        Script script = new Script(getActivity(), title);
        script.readScrip();
        for (int i = 0; i < script.getScriptionSize(); i++) {
            Step.add(script.getCurrentStep(i));
        }
        adapter.notifyItemInserted(Step.size());
    }

    private void setupFloatingActionButton(final View view, final StepCardAdapter adapter){
        //final View CoordinatorLayout = view.findViewById(R.id.CoordinatorLayout);
        FloatingActionButton addCard = (FloatingActionButton)view.findViewById(R.id.fab);
        Log.d("debugcrash","StepViewFragment setupFloatingActionButton");
        if(title != null) {
            addCard.setVisibility(View.VISIBLE);
            addCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    step newStep = new step();

                    String newTitle = null;
                    String newDescription = null;
                    Hashtable<String,String> newSetto = new Hashtable<String,String>();
                    Hashtable<String,String> newEndif = new Hashtable<String,String>();
                    newStep.setStepTitle(newTitle);
                    newStep.setStepDescription(newDescription);
                    newStep.setStepSetTo(newSetto);
                    newStep.setStepEndIf(newEndif);
                    //int position = Step.size()+1;

                    Intent StepEditor = new Intent();
                    StepEditor.setClass(getActivity(), StepEditorActivity.class);
                    startActivity(StepEditor);

                    //EditStepDialog editStepDialog = new EditStepDialog(getActivity(), newStep);
                    //editStepDialog.show();
                    //Step.add(editStepDialog.getEditStep());

                    adapter.notifyItemInserted(Step.size());

                }
            });
        }
    }

    private StepCardAdapter setupRecyclerView(RecyclerView rv){
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);
        StepCardAdapter adapter = new StepCardAdapter(Step, getActivity());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return adapter;
    }

    private void setupRemoveStep(RecyclerView rv){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert); //alert for confirm to delete
                builder.setMessage("Are you sure to delete?");    //set message
                builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        step fileName = Step.get(position);
                        Step.remove(position);
                        // do remove step form json file
                        adapter.notifyItemRemoved(position);    //item removed from recylcerview
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                        adapter.notifyItemRangeChanged(position, adapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                    }
                }).show();  //show alert dialog
                //Remove swiped item from list and notify the RecyclerView
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rv);
    }



/* activity only
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }
*/

}
