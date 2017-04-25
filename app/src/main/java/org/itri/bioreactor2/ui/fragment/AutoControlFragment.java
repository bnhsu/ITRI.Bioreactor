package org.itri.bioreactor2.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.itri.bioreactor2.R;


public class AutoControlFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public AutoControlFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_autocontrol, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter(new String[]{"Example One", "Example Two", "Example Three",
                "Example Four", "Example Five" , "Example Six" , "Example Seven","Example 8","Example 9",
                "Example 10","Example 11","Example 12"});
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter1(myDataset);
        //mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }



}

