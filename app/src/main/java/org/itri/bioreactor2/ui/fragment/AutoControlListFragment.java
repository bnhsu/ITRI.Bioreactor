package org.itri.bioreactor2.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import org.itri.bioreactor2.R;
import org.itri.bioreactor2.autocontrol.component.Script;
import org.itri.bioreactor2.autocontrol.component.step;
import org.itri.bioreactor2.ui.adpater.AutoControlCardAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;


public class AutoControlListFragment extends Fragment {

    private int count = 0;
    private String newFileName = null;
    final ArrayList<String> AutoControlTitle = new ArrayList<>();

    public AutoControlListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_autocontrollist, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        Log.d("debugcrash"," onCreateView AutoControlListFragment");
        listRaw();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        final AutoControlCardAdapter adapter = new AutoControlCardAdapter(AutoControlTitle, getActivity());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

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
                        String fileName = AutoControlTitle.get(position);
                        AutoControlTitle.remove(position);
                        File oldjson = new File(getActivity().getExternalFilesDir(null).getAbsolutePath()
                                + File.separator + fileName);
                        oldjson.delete();
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

        onFloatingButton(rootView, adapter);
        return rootView;
    }

    private void onFloatingButton(final View view, final AutoControlCardAdapter adapter){
        FloatingActionButton addCard = (FloatingActionButton)view.findViewById(R.id.fab);
        Log.d("debugcrash"," on onFloatingButton");
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
                adapter.notifyItemInserted(AutoControlTitle.size());
            }
        });
    }

    // list all the conrtol file in the directory
    public void listRaw() {
        Log.d("debugcrash"," on listRaw");
        String path = getActivity().getExternalFilesDir(null).getAbsolutePath();
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                AutoControlTitle.add(file.getName());

            }
        }
    }

    private void jsonCreate(String fileName) throws IOException {
        /*
        File newJson = new File(getActivity().getExternalFilesDir(null).getAbsolutePath()
                + File.separator + fileName +".json");
        newJson.createNewFile();
        */

        Script newScript = new Script(getActivity(), fileName);
        step firstStep = new step();

        firstStep.setStepTitle("Step 1");
        firstStep.setStepDescription( "Description");
        Hashtable<String,String> setto = new Hashtable<>();
        Hashtable<String,String> endif = new Hashtable<>();
        setto.put("Pump1", "0");
        //setto.
        endif.put("TIME","300000");

        firstStep.setStepSetTo(setto);
        firstStep.setStepEndIf(endif);

        newScript.addNewStep(firstStep);
        newScript.wirteScript();

    }

    private void customDialog(){
        final View item = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_addautocontrol, null);
        new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("Enter New AutoControl Name")
                .setView(item)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) item.findViewById(R.id.edit_text);
                        newFileName = editText.getText().toString();
                        AutoControlTitle.add(newFileName);
                        try {
                            jsonCreate(newFileName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                }).show();
    }


}

