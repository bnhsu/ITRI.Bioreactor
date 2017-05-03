package org.itri.bioreactor2.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.ui.adpater.AutoControlCardAdapter;

import java.util.ArrayList;
import java.util.List;


public class AutoControlListFragment extends Fragment {


    private int count = 0;

    public AutoControlListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_autocontrollist, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);



        final ArrayList<String> CardTitle = new ArrayList<>();
        CardTitle.add("Cell 1");


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        final AutoControlCardAdapter adapter = new AutoControlCardAdapter(CardTitle, getActivity());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        FloatingActionButton addCard = (FloatingActionButton)rootView.findViewById(R.id.fab);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(rootView, "Add a Card", Snackbar.LENGTH_LONG).show();
                int position = CardTitle.size()+1;
                CardTitle.add("Cell " + position);
                adapter.notifyItemInserted(CardTitle.size());
            }
        });
        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter1(myDataset);
        //mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }



}

