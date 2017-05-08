package org.itri.bioreactor2.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import org.itri.bioreactor2.R;
import org.itri.bioreactor2.ui.adpater.AutoControlCardAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


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

        listRaw();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        final AutoControlCardAdapter adapter = new AutoControlCardAdapter(AutoControlTitle, getActivity());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        onFloatingButton(rootView, adapter);
        return rootView;
    }

    private void onFloatingButton(final View view, final AutoControlCardAdapter adapter){
        FloatingActionButton addCard = (FloatingActionButton)view.findViewById(R.id.fab);
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
        String path = getActivity().getExternalFilesDir(null).getAbsolutePath();
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                AutoControlTitle.add(file.getName());
            }
        }
    }

    private void jsonCreate(String fileName) throws IOException {
        File newJson = new File(getActivity().getExternalFilesDir(null).getAbsolutePath()
                + File.separator + fileName +".json");
        newJson.createNewFile();
    }

    private void customDialog(){
        final View item = LayoutInflater.from(getActivity()).inflate(R.layout.addautocontrol_dialog, null);
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

