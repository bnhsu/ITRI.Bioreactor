package org.itri.bioreactor2.ui.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.ui.adpater.AutoControlCardAdapter;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class AutoControlListFragment extends Fragment {


    private int count = 0;

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
                Snackbar.make(view, "Add a Card", Snackbar.LENGTH_LONG).show();
                int position = AutoControlTitle.size()+1;
                AutoControlTitle.add("Cell " + position);
                adapter.notifyItemInserted(AutoControlTitle.size());
            }
        });
    }


    // list all the conrtol file in the directory
    public void listRaw() {
        String path = getActivity().getExternalFilesDir(null).getAbsolutePath();
        String fileName = null;
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String[] fileFullName = file.getName().split("\\.");
                fileName = fileFullName[0];
                AutoControlTitle.add(file.getName());
            }
        }
    }

}

